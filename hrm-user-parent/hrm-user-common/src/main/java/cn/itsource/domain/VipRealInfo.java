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
 * 会员实名资料
 * </p>
 *
 * @author bobo
 * @since 2022-02-12
 */
@TableName("t_vip_real_info")
public class VipRealInfo extends Model<VipRealInfo> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 登录用户
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 真实姓名
     */
    @TableField("real_name")
    private String realName;
    /**
     * 身份证号
     */
    @TableField("id_card_no")
    private String idCardNo;
    /**
     * 身份证正面
     */
    @TableField("id_card_front")
    private String idCardFront;
    /**
     * 身份证反面
     */
    @TableField("id_card_back")
    private String idCardBack;
    /**
     * 手持身份证
     */
    @TableField("id_card_hand")
    private String idCardHand;
    /**
     * 审核状态
     */
    private Integer state;
    /**
     * 提交时间
     */
    @TableField("apply_time")
    private Date applyTime;
    /**
     * 审核时间
     */
    @TableField("audit_time")
    private Date auditTime;
    /**
     * 审核人
     */
    @TableField("audit_user")
    private String auditUser;
    /**
     * 审核备注
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public String getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(String idCardFront) {
        this.idCardFront = idCardFront;
    }

    public String getIdCardBack() {
        return idCardBack;
    }

    public void setIdCardBack(String idCardBack) {
        this.idCardBack = idCardBack;
    }

    public String getIdCardHand() {
        return idCardHand;
    }

    public void setIdCardHand(String idCardHand) {
        this.idCardHand = idCardHand;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    public Date getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser;
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
        return "VipRealInfo{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", userId=" + userId +
        ", realName=" + realName +
        ", idCardNo=" + idCardNo +
        ", idCardFront=" + idCardFront +
        ", idCardBack=" + idCardBack +
        ", idCardHand=" + idCardHand +
        ", state=" + state +
        ", applyTime=" + applyTime +
        ", auditTime=" + auditTime +
        ", auditUser=" + auditUser +
        ", remark=" + remark +
        "}";
    }
}
