package vip.proyi.miaosha.service;

import com.baomidou.mybatisplus.extension.service.IService;
import vip.proyi.miaosha.entity.Goods;
import vip.proyi.miaosha.vo.GoodsVo;

import java.util.List;

public interface IGoodsService extends IService<Goods> {
    List<GoodsVo> goodsVoList();

    GoodsVo getGoodsVoByGoodsId(long goodsId);


}
