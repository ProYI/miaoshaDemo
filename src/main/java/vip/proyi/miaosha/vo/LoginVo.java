package vip.proyi.miaosha.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import vip.proyi.miaosha.validator.Mobile;

import javax.validation.constraints.NotNull;

@Data
public class LoginVo {
    @NotNull
    @Mobile
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;
}
