package com.digitopia.lockpay.common.enums;

public enum HttpHeadersEnum {
	
	SESSION_ID("x-session-id"),
	PROFILE_ID("x-profile-id"),
	LATITUDE("x-latitude"), 
	LONGITUDE("x-longitude");
		
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
