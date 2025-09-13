package com.digitopia.lockpay.common.enums;

public enum HTTPStatusCodesEnum {
	OK("200"), BAD_REQUEST("400"), UNAUTHORIZED("401"), FORBIDDEN("403"), INTERNAL_SERVER_ERROR("500"),
	NOT_FOUND("404");

	private String httpStatusCode;

	HTTPStatusCodesEnum(String httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

	public static HTTPStatusCodesEnum getEnum(String value) {
		if (value == null)
			throw new IllegalArgumentException();
		for (HTTPStatusCodesEnum v : values())
			if (value.equalsIgnoreCase(v.getHttpStatusCode()))
				return v;
		throw new IllegalArgumentException();
	}

}
