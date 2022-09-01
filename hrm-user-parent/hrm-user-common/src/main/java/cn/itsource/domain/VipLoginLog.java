package cn.itsource.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 登录记录
 * </p>
 *
 * @author bobo
 * @since 2022-02-12
 */
@TableName("t_vip_login_log")
public class VipLoginLog extends Model<VipLoginLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime;
    @TableField("user_id")
    private Long userId;
    /**
     * IP
     */
    private String ip;
    /**
     * 客户端
     */
    @TableField("client_info")
    private String clientInfo;
    /**
     * 登录方式
     */
    @TableField("login_type")
    private Integer loginType;
    /**
     * 登录是否成功
     */
    private Integer success;
    /**
     * 结果说明
     */
    private String remark;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getClientInfo() {
        return clientInfo;
    }

    public void setClientInfo(String clientInfo) {
        this.clientInfo = clientInfo;
    }

    public Integer getLoginType() {
        return loginType;
    }

    public void setLoginType(Integer loginType) {
        this.loginType = loginType;
    }

    public Integer getSuccess() {
        return success;
    }

    public void setSuccess(Integer success) {
        this.success = success;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipLoginLog{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", userId=" + userId +
        ", ip=" + ip +
        ", clientInfo=" + clientInfo +
        ", loginType=" + loginType +
        ", success=" + success +
        ", remark=" + remark +
        "}";
    }
}
