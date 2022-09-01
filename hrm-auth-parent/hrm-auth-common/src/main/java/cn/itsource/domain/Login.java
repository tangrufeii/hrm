package cn.itsource.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 登录表
 */
@TableName("t_login")
@Data
public class Login extends Model<Login> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String username;
    private String password;
    /**
     * 0是后台，1是前台
     */
    private Integer type;
    private Boolean enabled;
    @TableField("account_non_expired")
    private Boolean accountNonExpired;
    @TableField("credentials_non_expired")
    private Boolean credentialsNonExpired;
    @TableField("account_non_locked")
    private Boolean accountNonLocked;

    @TableField(exist = false)
    private Long tenantId;

    @TableField(exist = false)
    private String tenantName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Login{" +
        ", id=" + id +
        ", username=" + username +
        ", password=" + password +
        ", type=" + type +
        ", enabled=" + enabled +
        ", accountNonExpired=" + accountNonExpired +
        ", credentialsNonExpired=" + credentialsNonExpired +
        ", accountNonLocked=" + accountNonLocked +
        "}";
    }
}
