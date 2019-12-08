package vip.proyi.miaosha.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vip.proyi.miaosha.base.ResponseCode;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IMiaoShaService;
import vip.proyi.miaosha.vo.GoodsVo;

@RequestMapping("/miaosha")
@Controller
public class MiaoShaController {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    IMiaoShaService miaoShaService;

    @PostMapping("/do_miaosha")
    public String doMiaoSha(Model model, MiaoShaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);

        if (null == user) {
            return "login";
        }

        // 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            model.addAttribute("errmsg", ResponseCode.MIAOSHA_STOCK_OVER.getDesc());
            return "miaosha_fail";
        }

        // 判断是否已经秒杀到了
        MiaoShaOrder order = miaoShaOrderService.getOne(
                new QueryWrapper<MiaoShaOrder>().lambda().eq(MiaoShaOrder::getUserId, user.getId())
                        .eq(MiaoShaOrder::getGoodsId, goods.getId()));
        if (null != order) {
            model.addAttribute("errmsg", ResponseCode.MIAOSHA_NO_REPEATE.getDesc());
            return "miaosha_fail";
        }

        // 减库存、下订单、写入秒杀订单
        OrderInfo orderInfo = miaoShaService.miaoSha(user, goods);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goods);
        return "order_detail";
    }
}
