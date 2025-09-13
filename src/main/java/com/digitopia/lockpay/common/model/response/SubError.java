package com.digitopia.lockpay.common.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent list of sub errors
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubError {

	// field of error
	@JsonProperty("code")
    private String field;
    
	// details of error
    @JsonProperty("details")
    private String details;
    
}
