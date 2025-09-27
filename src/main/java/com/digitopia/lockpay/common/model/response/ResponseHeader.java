package com.digitopia.lockpay.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent header object contained into response
 *
 */
@JsonPropertyOrder({ "sessionId", "status" })
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseHeader {

	// status Business Error
	@JsonProperty("status")
	private ResponseStatus status;

	// Request Header sent into request
	@JsonProperty("sessionId")
	private String sessionId;

}