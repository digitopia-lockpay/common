package com.digitopia.lockpay.common.builder;

import org.springframework.stereotype.Service;

import com.digitopia.lockpay.common.enums.GeneralStatusEnum;
import com.digitopia.lockpay.common.model.ServiceContext;
import com.digitopia.lockpay.common.model.response.ResponseHeader;
import com.digitopia.lockpay.common.model.response.ResponseMessage;
import com.digitopia.lockpay.common.model.response.ResponseStatus;

/**
 *Respone builder to get consumer response using response received from the service
 */
@Service
public class ServiceResponseBuilder {
	
	
	/**
	 *generic function to handle response whatever it is type is boolean or response object.
	 * 
	 * @param Service Response
	 * @param request ID.
	 * @return responseMessage Message returned to consumer.
	 */
	
	public <T> ResponseMessage<T> buildResponse(Object serviceResponse, String sessionId) {

		ResponseMessage<T> finalResponse = new ResponseMessage<T>();
		ResponseHeader header = new ResponseHeader();
		ResponseStatus status = new ResponseStatus();
		header.setSessionId(sessionId);
		
		if (serviceResponse == null
				|| (serviceResponse.getClass().equals(java.lang.Boolean.class) && serviceResponse.equals(false))) {
			status.setCode(GeneralStatusEnum.GENERAL_ERROR.getErrorCode());
		} else {
			status.setCode(GeneralStatusEnum.SUCCESS.getErrorCode());
		}
		
		header.setStatus(status);
		finalResponse.setHeader(header);
		
		return finalResponse;

	}
	
	public <T> ResponseMessage<T> buildResponse(Object serviceResponse, ServiceContext serviceContext) {

		ResponseMessage<T> finalResponse = new ResponseMessage<T>();
		ResponseHeader header = new ResponseHeader();
		ResponseStatus status = new ResponseStatus();
		header.setSessionId(serviceContext.getSessionId());
		
		if (serviceResponse == null
				|| (serviceResponse.getClass().equals(java.lang.Boolean.class) && serviceResponse.equals(false))) {
			status.setCode(GeneralStatusEnum.GENERAL_ERROR.getErrorCode());
		} else {
			status.setCode(GeneralStatusEnum.SUCCESS.getErrorCode());
		}
		
		header.setStatus(status);
		finalResponse.setHeader(header);
		
		return finalResponse;

	}


}
