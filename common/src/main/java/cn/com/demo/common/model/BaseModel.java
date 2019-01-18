package cn.com.demo.common.model;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.exception.AppException;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by demo on 15/6/2.
 */
public class BaseModel<T> implements Serializable {
    public void validate(Validator validator) throws AppException {
        Set<ConstraintViolation<T>> failures = validator.validate((T)this);
        if(!failures.isEmpty()){
            ConstraintViolation violation = failures.iterator().next();
            throw new AppException(CommonMessageCode.VALIDATION_ERROR, violation.getPropertyPath()+violation.getMessage());
        }
    }
}
