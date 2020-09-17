package org.rmysj.api.commons.exception;


import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.rmysj.api.commons.controller.BaseController;
import org.rmysj.api.commons.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;


import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author bxgms
 */
@RestControllerAdvice
public class ApiExceptionHandler
{
    private static final Logger log = Logger.getLogger(ApiExceptionHandler.class);


    /**
     * 业务异常
     */
    @ExceptionHandler(CustomException.class)
    public Map<String,Object> businessException(CustomException e)
    {
        Map<String,Object> result = Maps.newHashMap();
        if (StringUtils.isNull(e.getCode()))
        {
            result.put(BaseController.STATUS,BaseController.WARN);
            result.put(BaseController.DESC,e.getMessage());
            return result;
        }
        result.put(BaseController.STATUS,e.getCode());
        result.put(BaseController.DESC,e.getMessage());
        return result;
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Map<String,Object> handlerNoFoundException(Exception e)
    {
        log.error(e.getMessage(), e);
        Map<String,Object> result = Maps.newHashMap();
        result.put(BaseController.STATUS,BaseController.NOT_FOUND);
        result.put(BaseController.DESC,"路径不存在，请检查路径是否正确");
        return result;
    }


    @ExceptionHandler(Exception.class)
    public Map<String,Object> handleException(Exception e)
    {
        log.error(e.getMessage(), e);
        Map<String,Object> result = Maps.newHashMap();
        result.put(BaseController.STATUS,BaseController.WARN);
        result.put(BaseController.DESC,e.getMessage());
        return result;
    }
}
