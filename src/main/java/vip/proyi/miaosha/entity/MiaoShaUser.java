package vip.proyi.miaosha.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import java.util.Date;

@Data
@TableName("miaosha_user")
public class MiaoShaUser extends Model<MiaoShaUser> {
    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    @TableField("nickname")
    private String nickname;

    @TableField("password")
    private String password;

    @TableField("salt")
    private String salt;

    @TableField("head")
    private String head;

    @TableField("register_date")
    private Date registerDate;

    @TableField("last_login_date")
    private Date lastLoginDate;

    @TableField("login_count")
    private Integer loginCount;
}
