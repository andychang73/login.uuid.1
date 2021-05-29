package com.abstractionizer.login.uuid1.login.services.impl;

import com.abstractionizer.login.uuid1.constants.RedisConstant;
import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.abstractionizer.login.uuid1.login.services.UserRegistrationService;
import com.abstractionizer.login.uuid1.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

    private final Long validationDuration = 5L;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public boolean isUsernameRegistered(String username) {
        return redisUtil.isKeyExists(RedisConstant.getUserRegisteredName(username));
    }

    @Override
    public void setUserRegisterInfo(String token, User user) {
        redisUtil.set(RedisConstant.getUserRegisterToken(token), user, validationDuration, TimeUnit.MINUTES);
    }

    @Override
    public void setUserRegisteredName(String username) {
        redisUtil.set(RedisConstant.getUserRegisteredName(username), username, validationDuration, TimeUnit.MINUTES);
    }

    @Override
    public void sendEmail(String to, String uuid) {
        javaMailSender.send(generateMessage(to, uuid));
    }

    private SimpleMailMessage generateMessage(String to, String uuid){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreplay@abstractionizer.com");
        message.setTo(to);
        message.setSubject("Account Validation");
        message.setText(generateValidationUrl(uuid));
        return message;
    }

    private String generateValidationUrl(String token){
        return "http://127.0.0.1:8080/api/user?token=" + token;
    }


}
