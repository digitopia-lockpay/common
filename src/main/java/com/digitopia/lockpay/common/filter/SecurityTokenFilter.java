package com.digitopia.lockpay.common.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityTokenFilter implements Filter {

	@Value("${API_KEY_ENABLED:false}")
	private boolean isAPIKeyEnabled;
	
	private static final List<String> EXCLUDED_PREFIX = Arrays.asList("/swagger-ui", "/swagger-resources",
			"/v3/api-docs");
			
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;

		if (!isAPIKeyEnabled || isExcludedURL(httpRequest)) {
			chain.doFilter(request, response);
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User Unauthorized.");
		}
	}

	private static boolean isExcludedURL(HttpServletRequest httpRequest) {
		return EXCLUDED_PREFIX.stream().anyMatch(prefix-> httpRequest.getRequestURI().startsWith(prefix));
	}

}
