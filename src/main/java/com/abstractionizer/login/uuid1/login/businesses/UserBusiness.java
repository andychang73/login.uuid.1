package com.abstractionizer.login.uuid1.login.businesses;

import com.abstractionizer.login.uuid1.models.bo.ChangePasswordBo;
import com.abstractionizer.login.uuid1.models.bo.UpdateInfoBo;
import com.abstractionizer.login.uuid1.models.bo.UserLoginBo;
import com.abstractionizer.login.uuid1.models.bo.UserRegisterBo;
import com.abstractionizer.login.uuid1.models.vo.LoginSuccessfulVo;
import com.abstractionizer.login.uuid1.models.dto.UserInfo;

public interface UserBusiness {

    void register(UserRegisterBo bo);

    UserInfo validate(String token);

    LoginSuccessfulVo login(UserLoginBo bo);

    void changePassword(Integer userId, ChangePasswordBo bo);

    UserInfo updateInfo(Integer userId, UpdateInfoBo bo);

    void logout(Integer userId, String token);
}
