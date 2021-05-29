package com.abstractionizer.login.uuid1.models.bo;

import com.abstractionizer.login.uuid1.annotations.NullOrNotBlank;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class UserRegisterBo {

    @NotEmpty(message = "not must be null or empty")
    private String username;

    @NotEmpty(message = "not must be null or empty")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z)])(?=.*[A-Z])(?=.*[~!@#$%^&*()_=+])(?=\\S+$).{8,}$", message = "invalid password format")
    private String password;

    @NotEmpty(message = "not must be null or empty")
    @Pattern(regexp = "^(.*)@(.*$)", message = "invalid format")
    private String email;

    @NullOrNotBlank(message = "can be null but not empty")
    @Pattern(regexp = "^09\\d{8}$", message = "invalid format")
    private String phone;
}
