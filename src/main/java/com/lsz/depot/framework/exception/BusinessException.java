package com.lsz.depot.framework.exception;


import com.lsz.depot.core.common.ResponseInfo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BusinessException extends RuntimeException {
	private static final long serialVersionUID = -778142600038732285L;
	private String message;
	private String code;

	public BusinessException(String message) {
		this(ResponseInfo.CODE_ERROR,message);
	}

	public BusinessException(String code, String message) {
		this.code = code;
		this.message = message;
		log.warn("BusinessException",this);
	}

	@Override
	public String getMessage() {
		return message;
	}

	public String getCode() {
		return code;
	}

	public ResponseInfo<Object> getResponse() {
		return new ResponseInfo<Object>(code, message, null);
	}

	public static BusinessException authException(){
		return new BusinessException("401","访问被拒绝");
	}
}
