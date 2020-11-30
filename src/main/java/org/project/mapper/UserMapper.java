package org.project.mapper;

import org.project.entity.UserEntity;
import org.project.entity.ViewObject.UserMailVO;
import org.project.entity.ViewObject.UserVO;
import org.project.entity.condition.UserCondition;
import org.springframework.cache.annotation.Cacheable;

import java.util.List;

public interface UserMapper {

    List<UserVO> getPage(UserCondition condition);

    void addUser(UserEntity userEntity);

    UserEntity getUserById(int id);

    int updatePwd(UserEntity userEntity);

    int updateUserInfo(UserEntity userEntity);

    int update(UserEntity userEntity);

    int deleteUser(int id);

    @Cacheable(value = "user", key = "#username")
    UserEntity getUserByName(String username);

    List<Integer> getRoleListById(int id);

    List<UserMailVO> getUserMailList();

}
