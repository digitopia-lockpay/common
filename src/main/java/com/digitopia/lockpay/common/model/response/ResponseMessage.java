package com.digitopia.lockpay.common.model.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent Message Response returned to the caller
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "header","data"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessage<T> {
	
	// Header of the response
	@JsonProperty("header")
	ResponseHeader header;

	// generic object to be returned in data field
	@JsonProperty("data")
	T data;
	
}


