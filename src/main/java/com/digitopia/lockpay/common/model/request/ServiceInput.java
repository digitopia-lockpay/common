package com.digitopia.lockpay.common.model.request;

import com.digitopia.lockpay.common.model.ServiceContext;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class ServiceInput {

	private ServiceContext context;
	
}
