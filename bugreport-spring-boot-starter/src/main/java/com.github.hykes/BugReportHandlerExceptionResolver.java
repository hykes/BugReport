package com.github.hykes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 使用Order注解 确保该解析器先于其他处理器执行
 * @author hehaiyangwork@gmail.com
 * @date 2019-05-08 03:27:00
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class BugReportHandlerExceptionResolver {

    @Autowired
    private BugReport bugReport;

    @ExceptionHandler
    public void resolveException(Exception e) throws Exception {
        this.bugReport.report(e);
        throw e;
    }
}