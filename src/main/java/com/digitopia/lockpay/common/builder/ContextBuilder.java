package com.digitopia.lockpay.common.builder;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.digitopia.lockpay.common.enums.FrameworkErrorsEnum;
import com.digitopia.lockpay.common.enums.HttpHeadersEnum;
import com.digitopia.lockpay.common.model.ServiceContext;
import com.digitopia.lockpay.common.model.exception.HeaderNotFoundException;

/**
 * used to build context object from headers Map
 * 
 */
@Service
public class ContextBuilder {

	/**
	 * used to build context object from headers Map
	 * 
	 * @param headers: Maps of the header
	 * @return ServiceContext: service context which hold important info sent via
	 *         header
	 */
	public ServiceContext buildServiceContext(Map<String, String> headers) throws HeaderNotFoundException {

		ServiceContext serviceContext = new ServiceContext();
		headers = manipulateHeader(headers);

		if (headers.containsKey(HttpHeadersEnum.SESSION_ID.getCode())) {
			serviceContext.setSessionId(headers.get(HttpHeadersEnum.SESSION_ID.getCode()));
		} else {
			throw new HeaderNotFoundException(FrameworkErrorsEnum.SESSION_ID_NOT_FOUND);
		} 

		if (headers.containsKey(HttpHeadersEnum.PROFILE_ID.getCode())) {
			serviceContext.setProfileId(headers.get(HttpHeadersEnum.PROFILE_ID.getCode()));
		} 
		
		if (headers.containsKey(HttpHeadersEnum.LONGITUDE.getCode())) {
			serviceContext.setLongitude(headers.get(HttpHeadersEnum.LONGITUDE.getCode()));
		} else {
			throw new HeaderNotFoundException(FrameworkErrorsEnum.LONGITUDE_NOT_FOUND);
		}

		if (headers.containsKey(HttpHeadersEnum.LATITUDE.getCode())) {
			serviceContext.setLatitude(headers.get(HttpHeadersEnum.LATITUDE.getCode()));
		} else {
			throw new HeaderNotFoundException(FrameworkErrorsEnum.LATITUDE_NOT_FOUND);
		}
		
		return serviceContext;
	}

	public Map<String, String> manipulateHeader(Map<String, String> headers) {

		Map<String, String> newHeaders = new HashMap<>();

		for (Entry<String, String> entry : headers.entrySet()) {
			String key = entry.getKey();
			String newKey = key.toLowerCase();
			newHeaders.put(newKey, entry.getValue());
		}
		return newHeaders;
	}
}