package org.rmysj.api.commons.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

/**
 * Created by rmysj on 2017/6/29 下午2:12.
 * 用于支持jsonp
 */
@ControllerAdvice(basePackages = "org.rmysj.api.jsonpController")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice{

    public JsonpAdvice() {

        super("callback");
    }
}
