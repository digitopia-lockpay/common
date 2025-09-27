package com.digitopia.lockpay.common.filter;

import java.io.IOException;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import com.digitopia.lockpay.common.enums.HttpHeadersEnum;
import com.digitopia.lockpay.common.model.ServiceContext;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

/**
 * Custom Filter to Capture service context
 *
 */

@Configuration
@Order(Ordered.LOWEST_PRECEDENCE)
@AllArgsConstructor
@Log4j2
public class ContextFilter implements Filter {


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		log.info("########## Initiating Context filter ##########");
	}

	/**
	 * Handle Request and do filter
	 *
	 * @param servletRequest  Servlet Request
	 * @param servletResponse Servlet Response
	 * @param filterChain     filter chain
	 */

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		ServiceContext serviceContext = new ServiceContext();
		String profileId = httpServletRequest.getHeader(HttpHeadersEnum.PROFILE_ID.getCode());
		if (profileId != null && !profileId.equals("")) {
			serviceContext.setProfileId(profileId);
		} else {
			serviceContext.setProfileId("system");
		}
		
		serviceContext.setSessionId(httpServletRequest.getHeader(HttpHeadersEnum.SESSION_ID.getCode()));
		serviceContext.setLatitude(httpServletRequest.getHeader(HttpHeadersEnum.LATITUDE.getCode()));
		serviceContext.setLongitude(httpServletRequest.getHeader(HttpHeadersEnum.LONGITUDE.getCode()));

		ThreadContext.setContext(serviceContext);
		filterChain.doFilter(servletRequest, servletResponse);
		ThreadContext.removeContext();

	}


	@Override
	public void destroy() {
	}


}