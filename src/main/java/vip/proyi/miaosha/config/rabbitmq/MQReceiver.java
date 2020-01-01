package vip.proyi.miaosha.config.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IMiaoShaService;
import vip.proyi.miaosha.vo.GoodsVo;
import vip.proyi.miaosha.vo.MiaoShaMessage;

@Service
@Slf4j
public class MQReceiver {
    @Autowired
    IGoodsService goodsService;
    @Autowired
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    IMiaoShaService miaoShaService;

    @RabbitListener(bindings = {
            @QueueBinding(value = @Queue(value = MQConstant.MIAOSHA_QUEUE, durable = "true"),
                    exchange = @Exchange(value = MQConstant.MIAOSHA_EXCHANGE, type = ExchangeTypes.TOPIC),
                    key = MQConstant.MIAOSHA_ROUNTINGKEY)})
    @RabbitHandler
    public void getMiaoShaMessage(String message) {
        log.info("[秒杀队列 message]：" + message);
        MiaoShaMessage mm = JSON.parseObject(message, MiaoShaMessage.class);
        MiaoShaUser user = mm.getMiaoShaUser();
        long goodsId = mm.getGoodsId();

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goods.getStockCount();
        if (stock <= 0) {
            return;
        }
        // 判断是否已经秒杀到
        MiaoShaOrder order = miaoShaOrderService.getOne(
                new QueryWrapper<MiaoShaOrder>().lambda().eq(MiaoShaOrder::getUserId, user.getId())
                        .eq(MiaoShaOrder::getGoodsId, goods.getId()));
        if (null != order) {
            return;
        }

        // 减库存、下订单、写入秒杀订单
        OrderInfo orderInfo = miaoShaService.miaoSha(user, goods);
    }
}
