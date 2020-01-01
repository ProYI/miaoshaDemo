package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.proyi.miaosha.comment.OrderKey;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.mapper.IOrderInfoMapper;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IOrderInfoService;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.GoodsVo;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderInfoService extends ServiceImpl<IOrderInfoMapper, OrderInfo> implements IOrderInfoService {
    @Resource
    IOrderInfoMapper orderInfoMapper;
    @Resource
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoShaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoShaPrice());
        orderInfo.setOrderChannel(1);
        orderInfo.setStatus(0);
        orderInfo.setUserId(user.getId());
        orderInfoMapper.insert(orderInfo);

        MiaoShaOrder miaoShaOrder = new MiaoShaOrder();
        miaoShaOrder.setGoodsId(goods.getId());
        miaoShaOrder.setUserId(user.getId());
        miaoShaOrder.setOrderId(orderInfo.getId());
        miaoShaOrderService.save(miaoShaOrder);

        redisUtil.setObj(OrderKey.getMiaoShaOrderByUidGid,
                "" + user.getId() + "_" + goods.getId(), miaoShaOrder);

        return orderInfo;
    }
}
