package com.digitopia.lockpay.common.enums;

import com.digitopia.lockpay.common.model.exception.BasicErrorsEnum;

public enum GeneralStatusEnum implements BasicErrorsEnum {

	GENERAL_ERROR("LP00099", HTTPStatusCodesEnum.INTERNAL_SERVER_ERROR, "General Error"),
	CONSTRAINT_VIOLATE("LP00098", HTTPStatusCodesEnum.BAD_REQUEST, "Constrain Violation"),
	MISSING_PARAM("LP00097", HTTPStatusCodesEnum.BAD_REQUEST, "Missing Param"),
	SUCCESS("I000000", HTTPStatusCodesEnum.OK, "Success");


	private String errorCode;
	private HTTPStatusCodesEnum httpErrorCode;
	private String description;

	GeneralStatusEnum(String errorCode, HTTPStatusCodesEnum httpErrorCode, String description) {
		this.errorCode = errorCode;
		this.httpErrorCode = httpErrorCode;
		this.description = description;
	}

	public HTTPStatusCodesEnum getHttpErrorCode() {
		return httpErrorCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String getDescription() {
		return this.description;
	}

}
