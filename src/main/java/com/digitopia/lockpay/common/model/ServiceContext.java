package com.digitopia.lockpay.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores the context for the service call
 * @author wtayea
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceContext {
	
	private String requestId;//x-request-d
	private String sessionId;//x-session-id
	private String userId;//x-prinicipal-id
	private String peferredLang; //x-session-language
	private String clientId; //x-client-id
	private String clientSecret; //x-client-secret
	private String deviceId; //x-device-id
	private String hostIp; //x-host-ip
	private String clientIp; //x-client-ip
	private String latitude; //x-latitude
	private String longitude; // x-longitude
	private String applicationVersion; //x-app-version
	private String principleType; // x-principle-type
	private String twoFAValidation  ; // 2 FA Validation
	

}
