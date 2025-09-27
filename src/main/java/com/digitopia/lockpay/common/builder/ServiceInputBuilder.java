package com.digitopia.lockpay.common.builder;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.digitopia.lockpay.common.model.exception.HeaderNotFoundException;
import com.digitopia.lockpay.common.model.request.ServiceInput;

import lombok.Data;

/**
 * Builder utility for service input objects
 */
@Service
@Data
public abstract class ServiceInputBuilder<T,E extends ServiceInput> {

	protected ContextBuilder contextBuilder;
	
	/**
	 * Build service command from the headers map and the original request to the adapter
	 * @param requestInput The request to the in-bound adaptor
	 * @param headers The request headers
	 * @return The generated service input.
	 * @throws HeaderNotFoundException 
	 */
	public E buildServiceInput(Map<String, String> headers, T requestInput) throws HeaderNotFoundException {
		E serviceInput = transformMessage(requestInput);
		serviceInput.setContext(contextBuilder.buildServiceContext(headers));
		return serviceInput;
	}
	
	/**
	 * Transform the input request message from the adaptor to ServiceInput object.
	 * @param requestInput
	 * @return
	 */
	protected abstract E transformMessage(T requestInput);
	
}
