package com.lsz.depot.framework.filter;

import com.alibaba.fastjson.JSONObject;

import com.google.common.collect.Sets;

import com.google.common.io.CharStreams;
import com.lsz.depot.core.utils.ThreadStore;
import lombok.extern.slf4j.Slf4j;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Slf4j
public class LogFilter extends OncePerRequestFilter {
    public static final Set<String> STATIC = Sets.newHashSet(
            "htm", "html", "js", "map", "css", "jpg", "png", "gif", "ico", "bmp",
            "otf", "eot", "svg", "ttf", "woff", "woff2", "swf", "wav", "ogg", "mp3"
    );
    private static final Set<String> SKIPS = Sets.newHashSet(
            "/health", "/metrics"
    );
    protected boolean includeParam = true;
    private int maxParamLength = 0;

    public static boolean isStatic(String uri) {
        String[] ext = uri.split("\\.");
        return STATIC.contains(ext[ext.length - 1]);
    }

    protected static boolean needProcess(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return !isStatic(uri) && !SKIPS.contains(uri);
    }

    protected static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }

    public void setMaxParamLength(int maxParamLength) {
        this.maxParamLength = maxParamLength;
    }

    protected void process(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        WrappedHttpResponse custResponse = WrappedHttpResponse.wrap(response);
        long beg = System.currentTimeMillis();
        if (includeParam) {
            String params = JSONObject.toJSONString(request.getParameterMap());
            // 处理PostBody请求
            if (StringUtils.isEmpty(params) && "POST".equalsIgnoreCase(request.getMethod())) {
                params = CharStreams.toString(request.getReader());
            }
            if (maxParamLength > 0 && params.length() > maxParamLength) {
                params = params.substring(0, maxParamLength);
            }
            log.info("##### Begin Request[{}] Param[{}]", uri, params);
        } else {
            log.info("##### Begin Request[{}]", uri);
        }
        String ip = getIpAddress(request);
        ThreadStore.setRequestIpAddress(ip);
        if (StringUtils.isEmpty(ThreadStore.getClientIpAddress())) {
            ThreadStore.setClientIpAddress(ip);
        }
        log.info("##### Request[{}] CallInfo[user={}, caller={}, callerId={}, sourceId={}, ip={}]", uri, ThreadStore.getUserId(), ThreadStore.getCaller(), ThreadStore.getCallerRequestId(), ThreadStore.getSourceId(), ip);
        try {
            filterChain.doFilter(request, custResponse);
        } finally {
            long end = System.currentTimeMillis();
            String responseStr = custResponse.getResponseContent();
            if (maxParamLength > 0 && responseStr.length() > maxParamLength) {
                responseStr = responseStr.substring(0, maxParamLength);
            }
            log.info("##### Finish Request[{}] Cost[{}ms] Response[{}]", uri, end - beg, responseStr);
            ThreadStore.clear();
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (needProcess(request)) {
            process(request, response, filterChain);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
