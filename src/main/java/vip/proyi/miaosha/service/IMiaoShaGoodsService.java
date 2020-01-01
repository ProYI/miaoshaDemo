package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.MiaoShaGoods;
import vip.proyi.miaosha.vo.GoodsVo;

public interface IMiaoShaGoodsService extends IService<MiaoShaGoods> {
    boolean reduceStock(GoodsVo goods);
}
