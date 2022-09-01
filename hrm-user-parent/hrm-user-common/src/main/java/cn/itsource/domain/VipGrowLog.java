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
 * 成长值记录
 * </p>
 *
 * @author bobo
 * @since 2022-02-12
 */
@TableName("t_vip_grow_log")
public class VipGrowLog extends Model<VipGrowLog> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("create_time")
    private Date createTime;
    /**
     * 登录用户
     */
    @TableField("user_id")
    private Long userId;
    /**
     * 来源
     */
    @TableField("from_reason")
    private String fromReason;
    /**
     * 成长值
     */
    private Integer score;
    /**
     * 备注
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

    public String getFromReason() {
        return fromReason;
    }

    public void setFromReason(String fromReason) {
        this.fromReason = fromReason;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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
        return "VipGrowLog{" +
        ", id=" + id +
        ", createTime=" + createTime +
        ", userId=" + userId +
        ", fromReason=" + fromReason +
        ", score=" + score +
        ", remark=" + remark +
        "}";
    }
}
