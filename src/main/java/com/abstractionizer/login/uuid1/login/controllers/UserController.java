package com.abstractionizer.login.uuid1.login.controllers;

import com.abstractionizer.login.uuid1.login.businesses.UserBusiness;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.UserInfoVo;
import com.abstractionizer.login.uuid1.responses.SuccessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserBusiness userBusiness;

    @PostMapping
    public SuccessResponse register(@RequestBody @Valid UserRegisterBo bo){
        userBusiness.register(bo);
        return new SuccessResponse();
    }

    @GetMapping
    public SuccessResponse<UserInfoVo> validate(@RequestParam("token") String token){
        return new SuccessResponse<>(userBusiness.validate(token));
    }
}
