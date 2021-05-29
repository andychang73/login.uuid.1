package com.abstractionizer.login.uuid1.models.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class UserLoginBo {

    @NotEmpty(message = "must not be null nor empty")
    private String username;

    @NotEmpty(message = "must not be null nor empty")
    private String password;
}
