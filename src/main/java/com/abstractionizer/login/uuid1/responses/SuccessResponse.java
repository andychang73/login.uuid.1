package com.abstractionizer.login.uuid1.responses;

import lombok.Data;

@Data
public class SuccessResponse<T> {
    T data;

    public SuccessResponse(){
        this.data = (T)"Success";
    }

    public SuccessResponse(T data){
        this.data = data;
    }
}
