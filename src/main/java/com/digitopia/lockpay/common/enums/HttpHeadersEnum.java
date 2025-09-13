package com.digitopia.lockpay.common.enums;

public enum HttpHeadersEnum {
	
	REQUEST_ID("x-request-id"),
	SESSION_ID("x-session-id"),
	USER_ID("x-principle-id"),
	PREFERED_LANG("x-session-language"),
	CLIENT_ID("x-client-id"),
	CLIENT_SECRET("x-client-secret"),
	DEVICE_ID("x-device-id"),
	HOST_IP("x-host-ip"),
	CLIENT_IP("x-client-ip"),
	LATITUDE("x-latitude"), 
	LONGITUDE("x-longitude"),
	APPLICATION_VERSION("x-app-version"),
	PRINCIPLE_TYPE("x-principle-type"),
	TWO_FA_VALIDATION("x-2fa-validation"),
	API_KEY("x-api-key");
		
	private String code;

	HttpHeadersEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String toString() {
        return this.getCode();
    }

    public static HttpHeadersEnum getEnum(String value) {
        if(value == null)
            throw new IllegalArgumentException();
        for(HttpHeadersEnum v : values())
            if(value.equalsIgnoreCase(v.getCode())) return v;
        throw new IllegalArgumentException();
    }
}
