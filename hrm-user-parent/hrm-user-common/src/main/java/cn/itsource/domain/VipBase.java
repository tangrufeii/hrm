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
 * 会员基本信息
 * </p>
 *
 * @author bobo
 * @since 2022-02-12
 */
@TableName("t_vip_base")
public class VipBase extends Model<VipBase> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime;
    @TableField("update_time")
    private Date updateTime;
    /**
     * 登录账号
     */
    @TableField("sso_id")
    private Long ssoId;
    /**
     * 注册渠道
     */
    @TableField("reg_channel")
    private Integer regChannel;
    /**
     * 注册时间
     */
    @TableField("reg_time")
    private Date regTime;
    /**
     * QQ
     */
    private String qq;
    /**
     * 用户等级
     */
    private Integer level;
    /**
     * 成长值
     */
    @TableField("grow_score")
    private Integer growScore;
    /**
     * 推荐人
     */
    @TableField("refer_id")
    private Long referId;
    /**
     * 性别
     */
    private Integer sex;
    /**
     * 生日
     */
    private Date birthday;
    /**
     * 居住地区域
     */
    @TableField("area_code")
    private Integer areaCode;
    /**
     * 详细地址
     */
    private String address;


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

    public Long getSsoId() {
        return ssoId;
    }

    public void setSsoId(Long ssoId) {
        this.ssoId = ssoId;
    }

    public Integer getRegChannel() {
        return regChannel;
    }

    public void setRegChannel(Integer regChannel) {
        this.regChannel = regChannel;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getGrowScore() {
        return growScore;
    }

    public void setGrowScore(Integer growScore) {
        this.growScore = growScore;
    }

    public Long getReferId() {
        return referId;
    }

    public void setReferId(Long referId) {
        this.referId = referId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "VipBase{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", ssoId=" + ssoId +
        ", regChannel=" + regChannel +
        ", regTime=" + regTime +
        ", qq=" + qq +
        ", level=" + level +
        ", growScore=" + growScore +
        ", referId=" + referId +
        ", sex=" + sex +
        ", birthday=" + birthday +
        ", areaCode=" + areaCode +
        ", address=" + address +
        "}";
    }
}
