package com.abstractionizer.login.uuid1.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode implements BaseError{
    DATA_INSERT_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10000", "Data insert failed"),
    DATA_UPDATE_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10001", "Data update failed"),
    DATA_DELETION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10002", "Failed to delete data"),
    DATA_NOT_FOUND(HttpStatus.NOT_FOUND, "10003", "Data not found"),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "10010", "User account does not exist"),
    USER_LOGGED_IN(HttpStatus.INTERNAL_SERVER_ERROR, "10011", "This account has already been logged in"),
    USER_LOGGED_OUT(HttpStatus.BAD_REQUEST, "100012", "User has been logged out"),
    USERNAME_EXISTS(HttpStatus.INTERNAL_SERVER_ERROR, "10013", "Username already exists"),
    TOKEN_GENERATION_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "10014", "Generate token failed"),

    ACCOUNT_FROZEN(HttpStatus.INTERNAL_SERVER_ERROR, "10020", "Your account has been frozen, please contact admin"),
    ACCOUNT_VALIDATION_EXPIRED(HttpStatus.NOT_FOUND, "10021", "Account validation is expired"),
    ACCOUNT_NOT_VALIDATED(HttpStatus.INTERNAL_SERVER_ERROR, "10022", "Account has not yet been validated"),

    NEW_OLD_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "10030", "New password and old password cannot be the same"),
    NEW_PASSWORD_INCONSISTENCY(HttpStatus.BAD_REQUEST, "10031", "New passwords must be the same"),

    INVALID_TOKEN(HttpStatus.INTERNAL_SERVER_ERROR, "10040", "Invalid Token"),
    INVALID_CREDENTIAL(HttpStatus.INTERNAL_SERVER_ERROR, "10041", "Invalid Credential"),
    INVALID_METHOD_ARGUMENT(HttpStatus.BAD_REQUEST, "10042", "Invalid method argument"),
    INVALID_HEADER_ARGUMENT(HttpStatus.BAD_REQUEST, "10043", "Invalid header argument")
    ;

    ErrorCode(HttpStatus httpStatus, String code, String msg){
        this.httpStatus = httpStatus;
        this.code = code;
        this.msg = msg;
    }

    private HttpStatus httpStatus;
    private String code;
    private String msg;

    @Override
    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
