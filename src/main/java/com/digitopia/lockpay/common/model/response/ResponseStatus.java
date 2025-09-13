package com.digitopia.lockpay.common.model.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represent status of the request which is part of the response
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "code","details" ,"subErrors"})
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseStatus {

   // Business code returned
	@JsonProperty("code")
    private String code;
    
    // details of error
    @JsonProperty("details")
    private String details;
   
    // to be set in HTTP header response
    @JsonIgnore
	private HttpStatus httpStatusCode;

    // List of suberrors 
    @JsonProperty("subErrors")
    private List<SubError> subErrors= new ArrayList<SubError>();

}
