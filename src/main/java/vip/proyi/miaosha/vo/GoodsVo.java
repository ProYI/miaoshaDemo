package vip.proyi.miaosha.vo;

import lombok.Data;
import vip.proyi.miaosha.entity.Goods;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class GoodsVo extends Goods {
    /**
     * 秒杀数量
     */
    private Integer stockCount;
    private BigDecimal miaoShaPrice;
    private Date startDate;
    private Date endDate;

}
