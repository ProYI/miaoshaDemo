package vip.proyi.miaosha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
@TableName(value = "goods")
public class Goods {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @TableField(value = "goods_name")
    private String goodsName;

    @TableField(value = "goods_img")
    private String goodsTitle;

    @TableField(value = "goods_name")
    private String goodsImg;

    @TableField(value = "goods_detail")
    private String goodsDetail;

    @TableField(value = "goods_price")
    private BigDecimal goodsPrice;

    @TableField(value = "goods_stock")
    private Integer goodsStock;

}
