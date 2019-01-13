package com.lsz.depot.apply.config;


import com.lsz.core.utils.TokenUtil;
import com.lsz.depot.framework.annotation.AspectExt;
import com.lsz.depot.framework.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
@Slf4j
public class WebAspect {

    /**
     * 开发模式
     */
    private static final String DEV_ACTIVE = "dev";
//    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Value("${spring.profiles.active}")
    private String active;

    @Value("${system.auth.auth}")
    private String auth;


    //    @Around("execution(* com.lsz.depot.apply.*.api.*(..))")
    @Around("execution (com.lsz.core.common.ResponseInfo com.lsz.depot.apply.api.*Api.*(..))")
    public Object invoke(ProceedingJoinPoint point) throws Throwable {
        Signature signature = point.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        Class<?> aClass = point.getTarget().getClass();

        String logLevel = "";
        boolean auth = true;
        if (method.isAnnotationPresent(AspectExt.class)) {
            AspectExt aspectExt = method.getAnnotation(AspectExt.class);
            logLevel = aspectExt.logLevel();
            auth = aspectExt.auth();
        }
        Claims tokenClaims = TokenUtil.getTokenClaims();
        String currentUserId = null;
        Date loginDate = null;
        if (!CollectionUtils.isEmpty(tokenClaims)) {
            currentUserId = tokenClaims.getId();
            loginDate = tokenClaims.getIssuedAt();
        }
        if (auth) {
            if (loginDate == null) {
                //dev就不抛出异常
                if (!DEV_ACTIVE.equals(active)) {
                    throw BusinessException.authException();
                }
            }
        }
        if (StringUtils.isEmpty(logLevel)) {
            if (DEV_ACTIVE.equals(active)) {
                log.warn("类名:{} ----- 方法名:{} ----- userId:{} ----- 登录时间:{}", aClass.getName(), method.getName(), currentUserId, loginDate);
            }
        }
        Object[] args = point.getArgs();
        Object proceed = null;
        try {
            proceed = point.proceed(args);
            return proceed;
        } finally {
//            final String uid = userId;
//            final Object result = proceed;
//            executorService.execute(() -> saveLog(uid, point, args, result));
        }
    }


}
