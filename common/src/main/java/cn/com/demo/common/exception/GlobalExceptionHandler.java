package cn.com.demo.common.exception;

import cn.com.demo.common.CommonMessageCode;
import cn.com.demo.common.MessageUtil;
import cn.com.demo.common.Result;
import org.apache.ibatis.exceptions.PersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by skydream1 on 2014/9/14.
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    @Resource
    private MessageUtil messageUtil;

    @ExceptionHandler(AppException.class)
    @ResponseBody
    public Result handleAppException(AppException ex) {
        String msg = messageUtil.getMessage(ex.getMsgCode(), ex.getArgs());
        logger.error(msg, ex);
        return new Result<>(false, msg);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result handleMissRequestParameterException(MissingServletRequestParameterException ex) {
        logger.error(ex.getMessage(), ex);
        String msg = ex.getMessage();
        return new Result<>(false, msg);

    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    @ResponseBody
    public Result handleNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        logger.error(ex.getMessage(), ex);
        String msg = "Request method '" + ex.getMethod() + "' not supported";
        return new Result<>(false, msg);

    }

    @ExceptionHandler(OtherException.class)
    @ResponseBody
    public Result handleOtherException(OtherException ex) {
        String msg = messageUtil.getMessage(ex.getMsgCode(), ex.getArgs());
        logger.error(msg, ex);
        return new Result<>(false, msg);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Result handleDataIntegrityViolationException(HttpServletRequest request, DataIntegrityViolationException ex) throws DataIntegrityViolationException {
        logger.error(ex.getMessage(), ex);
        if (isAjax(request)) {
            String msg = messageUtil.getMessage(CommonMessageCode.DATA_INTEGRITY_VIOLATION_EXCEPTION_ERROR);
            return new Result<>(false, msg);
        } else {
            throw ex;
        }
    }

    @ExceptionHandler(PersistenceException.class)
    @ResponseBody
    public Result handlePersistenceException(HttpServletRequest request, PersistenceException ex) {
        logger.error(ex.getMessage(), ex);
        if (isAjax(request)) {
            String msg = messageUtil.getMessage(CommonMessageCode.DB_MYBATIS_EXECUTE_ERROR);
            return new Result<>(false, msg);
        } else {
            throw ex;
        }
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public Result handleNoHandlerFoundException(HttpServletRequest request, NoHandlerFoundException ex) {
        logger.error(ex.getMessage(), ex);
        String msg = "API路径：" + ex.getRequestURL()+"不存在";
        return new Result<>(false, msg);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(HttpServletRequest request, Exception ex) throws Exception {
        logger.error(ex.getMessage(), ex);
        if (isAjax(request)) {
            String msg = messageUtil.getMessage(CommonMessageCode.SERVER_ERROR, new Object[]{ex.getMessage()});
            return new Result<>(false, msg);
        } else {
            throw ex;
        }
    }

    private boolean isAjax(HttpServletRequest request) {
        boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
        return isAjax || request.getRequestURI().contains("/api/");
    }
}
