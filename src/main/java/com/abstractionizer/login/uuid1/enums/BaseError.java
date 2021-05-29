package com.abstractionizer.login.uuid1.enums;

import org.springframework.http.HttpStatus;

public interface BaseError {

    HttpStatus getHttpStatus();

    String getCode();

    String getMsg();
}
