package vip.proyi.miaosha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName(value = "miaosha_order")
public class MiaoShaOrder {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;
    @TableField(value = "user_id")
    private Long userId;
    @TableField(value = "order_id")
    private Long orderId;
    @TableField(value = "goods_id")
    private Long goodsId;
}
