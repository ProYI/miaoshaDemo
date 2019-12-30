package vip.proyi.miaosha.config.rabbitmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.vo.MiaoShaMessage;

@Service
@Slf4j
public class MQReceiver {
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
    }
}
