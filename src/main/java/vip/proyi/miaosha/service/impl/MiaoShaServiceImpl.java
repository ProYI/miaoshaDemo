package vip.proyi.miaosha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.service.IMiaoShaGoodsService;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IMiaoShaService;
import vip.proyi.miaosha.service.IOrderInfoService;
import vip.proyi.miaosha.vo.GoodsVo;

@Service
public class MiaoShaServiceImpl implements IMiaoShaService {
    @Autowired
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    IMiaoShaGoodsService miaoShaGoodsService;
    @Autowired
    IOrderInfoService orderInfoService;

    @Override
    @Transactional
    public OrderInfo miaoSha(MiaoShaUser user, GoodsVo goods) {
        // 减少库存
        miaoShaGoodsService.reduceStock(goods);

        // 下订单 两步：秒杀订单 商品订单
        return orderInfoService.createOrder(user, goods);

    }
}
