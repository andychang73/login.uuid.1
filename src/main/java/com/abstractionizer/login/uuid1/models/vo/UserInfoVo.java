package com.abstractionizer.login.uuid1.models.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoVo {
    private Integer userId;
    private String username;
    private String email;
    private String phone;
    private boolean status;
}
