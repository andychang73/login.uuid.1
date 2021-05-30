package com.abstractionizer.login.uuid1.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfo {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private boolean status;

}
