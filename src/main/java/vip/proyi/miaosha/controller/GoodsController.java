package vip.proyi.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import vip.proyi.miaosha.comment.GoodsKey;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.service.IMiaoShaUserService;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    IMiaoShaUserService userService;
    @Autowired
    IGoodsService goodsService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    /**
     * 获取商品列表
     *
     * 做页面缓存，直接返回一个渲染后的html
     * @param model
     * @param user
     * @return
     */
    @GetMapping(value = "/to_list", produces = "text/html")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoShaUser user) {
        model.addAttribute("user", user);

        // 查询商品列表
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        model.addAttribute("goodsList", goodsList);

        // 取缓存
        String html = redisUtil.getObj(GoodsKey.getGoodsList, "", String.class);
        if (!StringUtils.isEmpty(html)) {
            return html;
        }

        WebContext webContext = new WebContext(request,
                response, request.getServletContext(), request.getLocale(), model.asMap());
        // 手动渲染
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", webContext);
        if (!StringUtils.isEmpty(html)) {
            redisUtil.setObj(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    @GetMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoShaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user", user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        // 秒杀时间
        long startAt = goods.getStartDate().toInstant().getEpochSecond();
        long endAt = goods.getEndDate().toInstant().getEpochSecond();
        // 秒级时间戳 北京时间比UTC时间晚8小时
        long now = Instant.now().getEpochSecond();

        int miaoShaStatus = 0;
        int remainSeconds = 0;

        if (now < startAt) {
            // 秒杀未开始，倒计时
            miaoShaStatus = 0;
            remainSeconds = (int) (startAt - now);
        } else if (now > endAt) {
            // 秒杀已结束
            miaoShaStatus = 2;
            remainSeconds = -1;
        } else {
            // 秒杀进行中
            miaoShaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("miaoShaStatus", miaoShaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }
}
