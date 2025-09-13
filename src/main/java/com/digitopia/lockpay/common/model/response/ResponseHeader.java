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
@JsonPropertyOrder({ "requestId", "status" })
@AllArgsConstructor
@Data
@NoArgsConstructor
public class ResponseHeader {

	// status Business Error
	@JsonProperty("status")
	private ResponseStatus status;

	// Request Header sent into request
	@JsonProperty("requestId")
	private String requestId;

}