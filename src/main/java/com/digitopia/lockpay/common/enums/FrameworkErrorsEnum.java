package com.digitopia.lockpay.common.enums;

import com.digitopia.lockpay.common.model.exception.BasicErrorsEnum;

/**
 *  Frame works errors 
 */
public enum FrameworkErrorsEnum implements BasicErrorsEnum {
	/**
     * User ID Not Found in the request header
     */
	PROFILE_ID_NOT_FOUND("LP99990", HTTPStatusCodesEnum.BAD_REQUEST, "profile id not found"),
	/**
     * Request ID Not Found in the request header
     */
	SESSION_ID_NOT_FOUND("LP99991", HTTPStatusCodesEnum.BAD_REQUEST, "session id not found"),
	/**
     * Request ID Not Found in the request header
     */
	LATITUDE_NOT_FOUND("LP99991", HTTPStatusCodesEnum.BAD_REQUEST, "latitude not found"),
	/**
     * Request ID Not Found in the request header
     */
	LONGITUDE_NOT_FOUND("LP99991", HTTPStatusCodesEnum.BAD_REQUEST, "longitude id not found");


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
