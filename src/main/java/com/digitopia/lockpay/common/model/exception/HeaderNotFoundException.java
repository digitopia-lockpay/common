package com.digitopia.lockpay.common.model.exception;

/**
 *  Header Mandatory Elements not found 
 */
public class HeaderNotFoundException  extends ApplicationException {


	public HeaderNotFoundException(BasicErrorsEnum errorsEnum) {
		super(errorsEnum);
	}
}
