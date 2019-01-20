package com.lsz.depot.core.utils;

import java.util.HashMap;
import java.util.Map;

public class ThreadStore {
    private static final String PRM_REQUEST_ID = "_requestId";
    private static final String PRM_REQ_IP_ADDR = "_reqIpAddress";
    private static final String PRM_CLIENT_IP_ADDR = "_clientIpAddress";
    private static final String PRM_SOURCE_ID = "_sourceId";
    private static final String PRM_CALLER = "_caller";
    private static final String PRM_USER = "_userId";
    private static final String PRM_CALLER_REQUEST_ID = "__callerRequestId";

    private static ThreadLocal<Map<String, String>> store = new ThreadLocal<>();

    public static Map<String, String> getAll() {
        Map<String, String> t = store.get();
        if (t == null) {
            t = new HashMap<>();
            store.set(t);
        }
        return t;
    }

    public static void setAll(Map<String, String> t) {
        store.set(t);
    }

    public static String get(String key) {
        return getAll().get(key);
    }

    public static void set(String key, String value) {
        Map<String, String> t = store.get();
        if (t == null) {
            t =  new HashMap<>();
            store.set(t);
        }
        t.put(key, value);
    }

    public static String getRequestId() {
        return get(PRM_REQUEST_ID);
    }

    public static void setRequestId(String value) {
        set(PRM_REQUEST_ID, value);
    }

    public static String getCallerRequestId() {
        return get(PRM_CALLER_REQUEST_ID);
    }

    public static void setCallerRequestId(String value) {
        set(PRM_CALLER_REQUEST_ID, value);
    }

    public static String getRequestIpAddress() {
        return get(PRM_REQ_IP_ADDR);
    }

    public static void setRequestIpAddress(String value) {
        set(PRM_REQ_IP_ADDR, value);
    }

    public static String getClientIpAddress() {
        return get(PRM_CLIENT_IP_ADDR);
    }

    public static void setClientIpAddress(String value) {
        set(PRM_CLIENT_IP_ADDR, value);
    }

    public static String getSourceId() {
        return get(PRM_SOURCE_ID);
    }

    public static void setSourceId(String value) {
        set(PRM_SOURCE_ID, value);
    }

    public static String getCaller() {
        return get(PRM_CALLER);
    }

    public static void setCaller(String value) {
        set(PRM_CALLER, value);
    }

    public static String getUserId() {
        return get(PRM_USER);
    }

    public static void setUserId(String value) {
        set(PRM_USER, value);
    }

    public static void clear() {
        Map<String, String> t = store.get();
        if (t == null) {
            t =  new HashMap<>();
            store.set(t);
        }
        t.clear();
    }
}
