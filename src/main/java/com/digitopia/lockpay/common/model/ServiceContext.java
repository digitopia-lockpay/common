package com.digitopia.lockpay.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Stores the context for the service call
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceContext {
	
	private String sessionId;//x-session-id
	private String profileId;//x-prinicipal-id
	private String latitude; //x-latitude
	private String longitude; // x-longitude

}
