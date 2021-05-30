package com.abstractionizer.login.uuid1.login.controllers;

import com.abstractionizer.login.uuid1.login.businesses.UserBusiness;
import com.abstractionizer.login.uuid1.models.bo.ChangePasswordBo;
import com.abstractionizer.login.uuid1.models.bo.UserLoginBo;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.LoginSuccessfulVo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;
import com.abstractionizer.login.uuid1.responses.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController{

    @Autowired
    private UserBusiness userBusiness;

    @PostMapping
    public SuccessResponse register(@RequestBody @Valid UserRegisterBo bo){
        userBusiness.register(bo);
        return new SuccessResponse();
    }

    @GetMapping
    public SuccessResponse<UserInfo> validate(@RequestParam("token") String token){
        return new SuccessResponse<>(userBusiness.validate(token));
    }

    @PostMapping("/login")
    public SuccessResponse<LoginSuccessfulVo> login (@RequestBody @Valid UserLoginBo bo){
        return new SuccessResponse<>(userBusiness.login(bo));
    }

    @PutMapping("/changePassword")
    public SuccessResponse changePassword(@RequestAttribute("userInfo") UserInfo userInfo,
                                          @RequestBody @Valid ChangePasswordBo bo){
        userBusiness.changePassword(userInfo.getUserId(), bo);
        return new SuccessResponse();
    }

    @GetMapping("/logout")
    public SuccessResponse logout(@RequestHeader("token") String token,
                                  @RequestAttribute("userInfo") UserInfo userInfo){
        userBusiness.logout(userInfo.getUserId(), token);
        return new SuccessResponse();
    }
}
