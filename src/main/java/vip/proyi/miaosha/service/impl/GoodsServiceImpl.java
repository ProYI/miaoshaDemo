package vip.proyi.miaosha.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import vip.proyi.miaosha.entity.Goods;
import vip.proyi.miaosha.mapper.IGoodsMapper;
import vip.proyi.miaosha.service.IGoodsService;
import vip.proyi.miaosha.vo.GoodsVo;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsServiceImpl extends ServiceImpl<IGoodsMapper, Goods> implements IGoodsService {
    @Resource
    IGoodsMapper goodsMapper;

    @Override
    public List<GoodsVo> goodsVoList() {
        return goodsMapper.goodsVoList();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }
}
