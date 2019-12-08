package vip.proyi.miaosha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "miaosha_goods")
public class MiaoShaGoods {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @TableField(value = "goods_id")
    private Long goodsId;

    @TableField(value = "miaosha_price")
    private BigDecimal miaoShaPrice;

    @TableField(value = "stock_count")
    private Integer stockCount;

    @TableField(value = "start_date")
    private Date startDate;

    @TableField(value = "end_date")
    private Date endDate;
}
