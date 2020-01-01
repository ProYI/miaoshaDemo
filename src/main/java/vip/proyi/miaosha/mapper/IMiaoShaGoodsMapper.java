package vip.proyi.miaosha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import vip.proyi.miaosha.entity.MiaoShaGoods;

@Mapper
public interface IMiaoShaGoodsMapper extends BaseMapper<MiaoShaGoods> {
    @Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId}")
    int reduceStock(MiaoShaGoods g);
}
