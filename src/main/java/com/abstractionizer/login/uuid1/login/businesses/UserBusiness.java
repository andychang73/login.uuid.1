package com.abstractionizer.login.uuid1.login.businesses;

import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.UserInfoVo;

public interface UserBusiness {

    void register(UserRegisterBo bo);

    UserInfoVo validate(String token);
}
