package com.abstractionizer.login.uuid1.models.bo;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
public class ChangePasswordBo {

    @NotEmpty(message = "must not be empty")
    private String oldPassword;

    @NotEmpty(message = "must not be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#%&*_+=])(?=\\S+$).{8,}$", message = "invalid format")
    private String newPassword;

    @NotEmpty(message = "must not be empty")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[~!@#%&*_+=])(?=\\S+$).{8,}$", message = "invalid format")
    private String confirmPassword;
}
