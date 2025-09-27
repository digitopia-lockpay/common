package com.digitopia.lockpay.common.builder;

import org.springframework.stereotype.Component;

import com.digitopia.lockpay.common.model.ServiceContext;
import com.digitopia.lockpay.common.model.response.ResponseMessage;

import lombok.Data;

@Component
@Data
public abstract class APIResponseBuilder<T, E> {

	protected ServiceResponseBuilder responseBuilder;

	public ResponseMessage<E> buildServiceOutput(T serviceResponse, String requestId) {

		ResponseMessage<E> resp = responseBuilder.buildResponse(serviceResponse, requestId);
		if (serviceResponse != null && !(serviceResponse.getClass().equals(java.lang.Boolean.class))) {

			resp.setData(transformMessage(serviceResponse));
		}

		return resp;
	}
	
	public ResponseMessage<E> buildServiceOutput(T serviceResponse, ServiceContext serviceContext) {

		ResponseMessage<E> resp = responseBuilder.buildResponse(serviceResponse, serviceContext);
		if (serviceResponse != null && !(serviceResponse.getClass().equals(java.lang.Boolean.class))) {

			resp.setData(transformMessage(serviceResponse));
		}
		return resp;

	}

	protected abstract E transformMessage(T serviceResponse);
	

}
