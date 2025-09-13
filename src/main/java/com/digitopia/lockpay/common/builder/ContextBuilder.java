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

		if (headers.containsKey(HttpHeadersEnum.REQUEST_ID.getCode())) {
			serviceContext.setRequestId(headers.get(HttpHeadersEnum.REQUEST_ID.getCode()));
		} else {
			throw new HeaderNotFoundException(FrameworkErrorsEnum.REQUEST_ID_NOT_FOUND);
		}

		if (headers.containsKey(HttpHeadersEnum.USER_ID.getCode())) {
			serviceContext.setUserId(headers.get(HttpHeadersEnum.USER_ID.getCode()));
		}

		if (headers.containsKey(HttpHeadersEnum.SESSION_ID.getCode())) {
			serviceContext.setSessionId(headers.get(HttpHeadersEnum.SESSION_ID.getCode()));
		}
		
		if (headers.containsKey(HttpHeadersEnum.PREFERED_LANG.getCode())) {
			serviceContext.setPeferredLang(headers.get(HttpHeadersEnum.PREFERED_LANG.getCode()));
		} else {
			throw new HeaderNotFoundException(FrameworkErrorsEnum.PREFERED_LANGUAGE_NOT_FOUND);
		}

		if (headers.containsKey(HttpHeadersEnum.CLIENT_ID.getCode())) {
			serviceContext.setClientId(headers.get(HttpHeadersEnum.CLIENT_ID.getCode()));
		}

		if (headers.containsKey(HttpHeadersEnum.CLIENT_SECRET.getCode())) {
			serviceContext.setClientSecret(headers.get(HttpHeadersEnum.CLIENT_SECRET.getCode()));
		}

		if (headers.containsKey(HttpHeadersEnum.DEVICE_ID.getCode())) {
			serviceContext.setDeviceId(headers.get(HttpHeadersEnum.DEVICE_ID.getCode()));
		}

		if (headers.containsKey(HttpHeadersEnum.HOST_IP.getCode())) {
			serviceContext.setHostIp(headers.get(HttpHeadersEnum.HOST_IP.getCode()));
		}
		
		if (headers.containsKey(HttpHeadersEnum.CLIENT_IP.getCode())) {
			serviceContext.setClientIp(headers.get(HttpHeadersEnum.CLIENT_IP.getCode()));
		}
		
		if (headers.containsKey(HttpHeadersEnum.LONGITUDE.getCode())) {
			serviceContext.setLongitude(headers.get(HttpHeadersEnum.LONGITUDE.getCode()));
		}

		if (headers.containsKey(HttpHeadersEnum.LATITUDE.getCode())) {
			serviceContext.setLatitude(headers.get(HttpHeadersEnum.LATITUDE.getCode()));
		}
		
		if (headers.containsKey(HttpHeadersEnum.APPLICATION_VERSION.getCode())) {
			serviceContext.setApplicationVersion(headers.get(HttpHeadersEnum.APPLICATION_VERSION.getCode()));
		}
		
		if (headers.containsKey(HttpHeadersEnum.PRINCIPLE_TYPE.getCode())) {
			serviceContext.setPrincipleType(headers.get(HttpHeadersEnum.PRINCIPLE_TYPE.getCode()));
		}
		
		if(headers.containsKey(HttpHeadersEnum.TWO_FA_VALIDATION.getCode())) {
			serviceContext.setTwoFAValidation(headers.get(HttpHeadersEnum.TWO_FA_VALIDATION.getCode()));
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