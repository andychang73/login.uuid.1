package com.abstractionizer.login.uuid1.db.rmdb.mappers;

import com.abstractionizer.login.uuid1.db.rmdb.entities.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<User> {

    int countByIdOrUsername(@Param("id") Integer id, @Param("username") String username);

    User getByIdOrUsername(@Param("id") Integer id, @Param("username") String username);

    int updateStatus(@Param("id") Integer id, @Param("status") boolean status);

    int updateUserInfo(@Param("user") User user);
}
