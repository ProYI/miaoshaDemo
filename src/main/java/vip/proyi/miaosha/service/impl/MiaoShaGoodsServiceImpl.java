package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.MiaoShaGoods;
import vip.proyi.miaosha.mapper.IMiaoShaGoodsMapper;
import vip.proyi.miaosha.service.IMiaoShaGoodsService;
import vip.proyi.miaosha.vo.GoodsVo;

import javax.annotation.Resource;

@Service
public class MiaoShaGoodsServiceImpl extends ServiceImpl<IMiaoShaGoodsMapper, MiaoShaGoods> implements IMiaoShaGoodsService {
    @Resource
    IMiaoShaGoodsMapper miaoShaGoodsMapper;

    @Override
    public boolean reduceStock(GoodsVo goods) {
        MiaoShaGoods g = new MiaoShaGoods();
        g.setGoodsId(goods.getId());
        int ret = miaoShaGoodsMapper.reduceStock(g);
        return ret>0;
    }
}
