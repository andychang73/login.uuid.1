package com.abstractionizer.login.uuid1.models.bo;

import com.abstractionizer.login.uuid1.annotations.NullOrNotBlank;
import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class UpdateInfoBo {

    @NullOrNotBlank(message = "can be null but nor empty")
    private String username;

    @NullOrNotBlank(message = "can be null but nor empty")
    @Pattern(regexp = "^(.*)@(,*)$")
    private String email;

    @NullOrNotBlank(message = "can be null but nor empty")
    @Pattern(regexp = "^09\\d{8}&")
    private String phone;
}
