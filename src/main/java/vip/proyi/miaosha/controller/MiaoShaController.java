package vip.proyi.miaosha.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import vip.proyi.miaosha.base.ResponseCode;
import vip.proyi.miaosha.base.ResponseModel;
import vip.proyi.miaosha.comment.GoodsKey;
import vip.proyi.miaosha.config.rabbitmq.MQConstant;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IMiaoShaService;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.GoodsVo;
import vip.proyi.miaosha.vo.MiaoShaMessage;

import java.util.HashMap;
import java.util.List;

@RequestMapping("/miaosha")
@Controller
public class MiaoShaController implements InitializingBean {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    IMiaoShaService miaoShaService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    RabbitTemplate rabbitTemplate;
    /**
     * 在系统初始化时操作
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList = goodsService.goodsVoList();
        if (null == goodsList) {
            return;
        }
        for (GoodsVo goods : goodsList) {
            redisUtil.setObj(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), goods.getGoodsStock());
        }

    }

    private HashMap<Long, Boolean> localOverMap =  new HashMap<Long, Boolean>();

    @PostMapping("/do_miaosha")
    @ResponseBody
    public ResponseModel<Integer> doMiaoSha(Model model, MiaoShaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);

        if (null == user) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.SESSION_ERROR.getCode(), ResponseCode.SESSION_ERROR.getDesc());
        }

        /*// 判断库存
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.MIAOSHA_STOCK_OVER.getCode(), ResponseCode.MIAOSHA_STOCK_OVER.getDesc());
        }

        // 判断是否已经秒杀到了
        MiaoShaOrder order = miaoShaOrderService.getOne(
                new QueryWrapper<MiaoShaOrder>().lambda().eq(MiaoShaOrder::getUserId, user.getId())
                        .eq(MiaoShaOrder::getGoodsId, goods.getId()));
        if (null != order) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.MIAOSHA_NO_REPEATE.getCode(), ResponseCode.MIAOSHA_NO_REPEATE.getDesc());

        }

        // 减库存、下订单、写入秒杀订单
        OrderInfo orderInfo = miaoShaService.miaoSha(user, goods);
        return ResponseModel.createBySuccess(orderInfo);*/

        // 内存标记，减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.MIAOSHA_STOCK_OVER.getCode(), ResponseCode.MIAOSHA_STOCK_OVER.getDesc());
        }

        //预减库存
        long stock = redisUtil.decrObj(GoodsKey.getMiaoshaGoodsStock, ""+goodsId);
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.MIAOSHA_STOCK_OVER.getCode(), ResponseCode.MIAOSHA_STOCK_OVER.getDesc());
        }
        // 判断是否已经秒杀到了
        MiaoShaOrder order = miaoShaOrderService.getOne(
                new QueryWrapper<MiaoShaOrder>().lambda().eq(MiaoShaOrder::getUserId, user.getId())
                        .eq(MiaoShaOrder::getGoodsId, goodsId));
        if (null != order) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.MIAOSHA_NO_REPEATE.getCode(), ResponseCode.MIAOSHA_NO_REPEATE.getDesc());
        }
        // 队列
        MiaoShaMessage message = new MiaoShaMessage();
        message.setMiaoShaUser(user);
        message.setGoodsId(goodsId);
        rabbitTemplate.convertAndSend(MQConstant.MIAOSHA_EXCHANGE, MQConstant.MIAOSHA_ROUNTINGKEY, message);
        return ResponseModel.createBySuccess(0); // 0 代表排队中
    }

    /**
     * 获取秒杀结果
     *
     * @param model
     * @param user
     * @param goodsId
     * @return orderId 表示成功
     * @return -1 秒杀失败
     * @return 0 排队中
     */
    @GetMapping("/result")
    @ResponseBody
    public ResponseModel<Long> miaoShaResult(Model model, MiaoShaUser user, @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        if (null == user) {
            return ResponseModel.createByFAILEDCodeMessage(ResponseCode.SESSION_ERROR.getCode(), ResponseCode.SESSION_ERROR.getDesc());
        }
        long result = miaoShaOrderService.getMiaoShaResult(user.getId(), goodsId);
        return ResponseModel.createBySuccess(result);
    }
}
