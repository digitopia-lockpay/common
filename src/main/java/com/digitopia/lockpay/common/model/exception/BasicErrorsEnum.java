package com.digitopia.lockpay.common.model.exception;

import com.digitopia.lockpay.common.enums.HTTPStatusCodesEnum;

public interface BasicErrorsEnum {

    String getErrorCode();
    HTTPStatusCodesEnum getHttpErrorCode();
    String getDescription();
}
