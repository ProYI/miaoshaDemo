package vip.proyi.miaosha.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vip.proyi.miaosha.comment.MiaoShaKey;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.service.IMiaoShaGoodsService;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.service.IMiaoShaService;
import vip.proyi.miaosha.service.IOrderInfoService;
import vip.proyi.miaosha.utils.RedisUtil;
import vip.proyi.miaosha.vo.GoodsVo;

@Service
public class MiaoShaServiceImpl implements IMiaoShaService {
    @Autowired
    IMiaoShaOrderService miaoShaOrderService;
    @Autowired
    IMiaoShaGoodsService miaoShaGoodsService;
    @Autowired
    IOrderInfoService orderInfoService;
    @Autowired
    RedisUtil redisUtil;

    @Override
    @Transactional
    public OrderInfo miaoSha(MiaoShaUser user, GoodsVo goods) {
        // 减少库存 减少库存失败就不再往下进行
        boolean success = miaoShaGoodsService.reduceStock(goods);
        if (success) {
            // 下订单 两步：秒杀订单 商品订单
            return orderInfoService.createOrder(user, goods);
        } else {
            redisUtil.setObj(MiaoShaKey.isGoodsOver, ""+goods.getId(), true);
            return null;
        }
    }
}
