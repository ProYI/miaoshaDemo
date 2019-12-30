package vip.proyi.miaosha.vo;

import lombok.Data;
import vip.proyi.miaosha.entity.MiaoShaUser;

@Data
public class MiaoShaMessage {
    private MiaoShaUser miaoShaUser;
    private long goodsId;
}
