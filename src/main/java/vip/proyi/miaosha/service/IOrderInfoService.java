package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.vo.GoodsVo;

public interface IOrderInfoService extends IService<OrderInfo> {
    OrderInfo createOrder(MiaoShaUser user, GoodsVo goods);
}
