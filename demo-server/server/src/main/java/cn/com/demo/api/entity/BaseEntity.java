package cn.com.demo.api.entity;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.exception.AppException;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * @author  alearning
 */
public class BaseEntity<T> extends Model {

    /**
     * 实体编号（唯一标识）
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建日期
     */
    @TableField("create_date")
    private Date createDate;

    /**
     * 更新日期
     */
    @TableField("update_date")
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void preInsert() {
        this.createDate = new Date();
        this.updateDate = new Date();
    }

    public void preUpdate() {
        this.updateDate = new Date();
    }

    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<T>> failures = validator.validate((T) this);
        if (!failures.isEmpty()) {
            ConstraintViolation violation = failures.iterator().next();
            throw new AppException(CommonMessageCode.VALIDATION_ERROR, violation.getPropertyPath() + violation.getMessage());
        }
    }

    @Override
    protected Serializable pkVal() {
        return id;
    }
}
