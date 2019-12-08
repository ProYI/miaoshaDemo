package vip.proyi.miaosha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName(value = "order_info")
public class OrderInfo {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "goods_id")
    private Long goodsId;
    @TableField(value = "delivery_addr_id")
    private Long  deliveryAddrId;
    @TableField(value = "goods_name")
    private String goodsName;
    @TableField(value = "goods_count")
    private Integer goodsCount;
    @TableField(value = "goods_price")
    private BigDecimal goodsPrice;
    @TableField(value = "order_channel")
    private Integer orderChannel;
    @TableField(value = "status")
    private Integer status;
    @TableField(value = "create_date")
    private Date createDate;
    @TableField(value = "pay_date")
    private Date payDate;
}
