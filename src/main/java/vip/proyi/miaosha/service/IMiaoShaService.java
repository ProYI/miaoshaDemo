package vip.proyi.miaosha.service;

import vip.proyi.miaosha.entity.MiaoShaUser;
import vip.proyi.miaosha.entity.OrderInfo;
import vip.proyi.miaosha.vo.GoodsVo;

public interface IMiaoShaService {
    OrderInfo miaoSha(MiaoShaUser user, GoodsVo goods);
}
