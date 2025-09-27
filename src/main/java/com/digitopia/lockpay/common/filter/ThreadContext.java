package com.digitopia.lockpay.common.filter;

import com.digitopia.lockpay.common.model.ServiceContext;

public class ThreadContext {
	private static final ThreadLocal<ServiceContext> THREAD_CONTEXT = new ThreadLocal<>();

	public static void setContext(ServiceContext serviceContext) {
		THREAD_CONTEXT.set(serviceContext);
	}

	public static void removeContext() {
		THREAD_CONTEXT.remove();
	}

	public static ServiceContext getContext() {
		return THREAD_CONTEXT.get();
	}

}
