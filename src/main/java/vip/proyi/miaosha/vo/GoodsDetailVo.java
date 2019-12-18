package vip.proyi.miaosha.vo;

import lombok.Data;
import vip.proyi.miaosha.entity.MiaoShaUser;

@Data
public class GoodsDetailVo {
    private int miaoShaStatus = 0;
    private int remainSeconds = 0;
    private GoodsVo goods;
    private MiaoShaUser user;
}
