package com.lsz.depot.framework.exception;


import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.lsz.core.common.ResponseInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;
import java.util.Map;

@Slf4j
@ControllerAdvice
@ResponseBody
public class ApiExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseInfo<Object> handle(Exception exp) {
        if (exp.getCause() instanceof InvalidFormatException) {
            InvalidFormatException ex = (InvalidFormatException) exp.getCause();
            InvalidResult invalidArgument = new InvalidResult();
            invalidArgument.setField(ex.getPath().toString());
            invalidArgument.setValue(ex.getValue());
            return new ResponseInfo<>("9995", "参数不正确", invalidArgument);
        } else if (exp instanceof BusinessException) {
            BusinessException ex = (BusinessException) exp;
            return ex.getResponse();
        } else if (exp.getCause() instanceof BusinessException) {
            BusinessException ex = (BusinessException) exp.getCause();
            return ex.getResponse();
        } else {
            log.error("未知异常", exp);
            return ResponseInfo.error(null);
        }
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseInfo<Map<String, String>> argumentMismatchHandler(MethodArgumentTypeMismatchException exp) {
        return new ResponseInfo<>("9996", "参数类型不匹配", ImmutableMap.of("field", exp.getName()));
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseInfo<Map<String, String>> argumentMissHandler(MissingServletRequestParameterException exp) {
        return new ResponseInfo<>("9997", "参数不能为空", ImmutableMap.of("field", exp.getParameterName()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseInfo<List<InvalidResult>> argumentInvalidHandler(MethodArgumentNotValidException exp) {
        List<InvalidResult> invalidArguments = Lists.newArrayList();
        for (FieldError error : exp.getBindingResult().getFieldErrors()) {
            InvalidResult invalidArgument = new InvalidResult();
            invalidArgument.setMessage(error.getDefaultMessage());
            invalidArgument.setField(error.getField());
            invalidArgument.setValue(error.getRejectedValue());
            invalidArguments.add(invalidArgument);
        }
        return new ResponseInfo<>("9998", "参数格式不正确", invalidArguments);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private class InvalidResult {
        private String field;

        private Object value;

        private String message;
    }
}
