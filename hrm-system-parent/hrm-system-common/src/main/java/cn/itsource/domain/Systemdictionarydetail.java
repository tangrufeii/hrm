package cn.itsource.domain;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yazi
 * @since 2022-08-16
 */
@TableName("t_systemdictionarydetail")
public class Systemdictionarydetail extends Model<Systemdictionarydetail> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("type_id")
    private Long typeId;
    @TableField("dic_key")
    private Long dicKey;
    @TableField("dic_value")
    private String dicValue;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public Long getDicKey() {
        return dicKey;
    }

    public void setDicKey(Long dicKey) {
        this.dicKey = dicKey;
    }

    public String getDicValue() {
        return dicValue;
    }

    public void setDicValue(String dicValue) {
        this.dicValue = dicValue;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Systemdictionarydetail{" +
        ", id=" + id +
        ", typeId=" + typeId +
        ", dicKey=" + dicKey +
        ", dicValue=" + dicValue +
        "}";
    }
}
