package vip.proyi.miaosha.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import vip.proyi.miaosha.entity.Goods;
import vip.proyi.miaosha.vo.GoodsVo;

import java.util.List;

@Mapper
public interface IGoodsMapper extends BaseMapper<Goods> {
    @Select("select g.*, mg.stock_count, mg.miaosha_price, mg.start_date, mg.end_date" +
            " from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> goodsVoList();

    @Select("select g.*, mg.stock_count, mg.miaosha_price, mg.start_date, mg.end_date" +
            " from miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId}")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") long goodsId);
}
