package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.comment.MiaoShaKey;
import vip.proyi.miaosha.comment.OrderKey;
import vip.proyi.miaosha.entity.MiaoShaOrder;
import vip.proyi.miaosha.mapper.IMiaoShaOrderMapper;
import vip.proyi.miaosha.service.IMiaoShaOrderService;
import vip.proyi.miaosha.utils.RedisUtil;

@Service
public class MiaoShaOrderServiceImpl extends ServiceImpl<IMiaoShaOrderMapper, MiaoShaOrder> implements IMiaoShaOrderService {
    @Autowired
    RedisUtil redisUtil;

    @Override
    public long getMiaoShaResult(Long userId, long goodsId) {
        MiaoShaOrder order = this.getMiaoShaOrderByUserIdGoodsId(userId, goodsId);
        if (null != order) {
            return order.getOrderId();
        } else {
            boolean isOver = redisUtil.exists(MiaoShaKey.isGoodsOver, ""+goodsId);
            if (isOver) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    public MiaoShaOrder getMiaoShaOrderByUserIdGoodsId(long userId, long goodsId) {
        return redisUtil.getObj(OrderKey.getMiaoShaOrderByUidGid,
                userId + "_" + goodsId, MiaoShaOrder.class);
    }
}
