package com.digitopia.lockpay.common.model.exception;

import com.digitopia.lockpay.common.enums.HTTPStatusCodesEnum;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class ApplicationException extends Exception {

	// The business error code of exception
	private String errorCode;
	// The corresponding httpErrorCode of the exception
	private HTTPStatusCodesEnum httpErrorCode;
	// The details description of the exception
	private String details;

	public ApplicationException(BasicErrorsEnum errorEnum) {
		this.errorCode = errorEnum.getErrorCode();
		this.httpErrorCode = errorEnum.getHttpErrorCode();
		this.details = errorEnum.getDescription();
	}

	public ApplicationException(BasicErrorsEnum errorEnum, String cause) {
		this.errorCode = errorEnum.getErrorCode();
		this.httpErrorCode = errorEnum.getHttpErrorCode();
		this.details = cause;
	}
}
