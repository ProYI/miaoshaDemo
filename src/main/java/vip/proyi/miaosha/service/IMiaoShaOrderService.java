package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.MiaoShaOrder;

public interface IMiaoShaOrderService extends IService<MiaoShaOrder> {
    long getMiaoShaResult(Long userId, long goodsId);
}
