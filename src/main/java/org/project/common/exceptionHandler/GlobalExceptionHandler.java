package org.project.common.exceptionHandler;

import org.project.common.response.Res;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Global Exception Handler to handle the exception occurs in the
 * <code>controller</code> layer.
 *
 * @author Lin Tang
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseBody
    public Res handleNullPointerException(NullPointerException e) {
        final Res res = Res.error();
        res.setMsg("发生了空指针异常...");
        return res;
    }

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseBody
    public Res handleRuntimeException(RuntimeException e) {
        final Res res = Res.error();
        res.setMsg("发生了运行时异常...");
        return res;
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Res handleException(Exception e) {
        final Res res = Res.error();
        res.setMsg("发生了异常...");
        return res;
    }

}
