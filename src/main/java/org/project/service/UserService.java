package org.project.service;

import org.project.entity.ViewObject.UserMailVO;
import org.project.entity.ViewObject.UserVO;
import org.project.entity.condition.UserCondition;
import org.project.entity.UserEntity;

import java.util.List;

public interface UserService {

    List<UserVO> getPage(UserCondition condition);

    void addUser(UserEntity userEntity);

    UserEntity getUserById(int id);

    int updatePwd(UserEntity userEntity);

    int updateUserInfo(UserEntity userEntity);

    int update(UserEntity userEntity);

    int deleteUser(int id);

    UserEntity getUserByName(String username);

    List<Integer> getRoleListById(int id);

    List<UserMailVO> getUserMailList();

}
