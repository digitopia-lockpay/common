package com.digitopia.lockpay.common.enums;

import com.digitopia.lockpay.common.model.exception.BasicErrorsEnum;

/**
 *  Frame works errors 
 */
public enum FrameworkErrorsEnum implements BasicErrorsEnum {
	/**
     * User ID Not Found in the request header
     */
	USER_ID_NOT_FOUND("LP99990", HTTPStatusCodesEnum.BAD_REQUEST, "user id not found"),
	/**
     * Request ID Not Found in the request header
     */
	REQUEST_ID_NOT_FOUND("LP99991", HTTPStatusCodesEnum.BAD_REQUEST, "request id not found"),
	/**
     * Prefered Lan Not Found in the request header
     */
	PREFERED_LANGUAGE_NOT_FOUND("LP99992", HTTPStatusCodesEnum.BAD_REQUEST, "prefered language not found");


	private String errorCode;
	private HTTPStatusCodesEnum httpErrorCode;
	private String description;

	FrameworkErrorsEnum(String errorCode, HTTPStatusCodesEnum httpErrorCode, String description) {
		this.errorCode = errorCode;
		this.httpErrorCode = httpErrorCode;
		this.description = description;
	}

	@Override
	public HTTPStatusCodesEnum getHttpErrorCode() {
		return httpErrorCode;
	}

	@Override
	public String getErrorCode() {
		return errorCode;
	}

	@Override
	public String getDescription() {
		return description;
	}
}
