package org.project.service.impl;

import org.project.entity.UserEntity;
import org.project.entity.ViewObject.UserMailVO;
import org.project.entity.ViewObject.UserVO;
import org.project.entity.condition.UserCondition;
import org.project.mapper.RoleMapper;
import org.project.mapper.UserMapper;
import org.project.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<UserVO> getPage(UserCondition condition) {
        return userMapper.getPage(condition);
    }

    @Override
    public void addUser(UserEntity userEntity) {
        userMapper.addUser(userEntity);
    }

    @Override
    public UserEntity getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public int updatePwd(UserEntity userEntity) {
        return userMapper.updatePwd(userEntity);
    }

    @Override
    public int updateUserInfo(UserEntity userEntity) {
        return userMapper.updateUserInfo(userEntity);
    }

    @Override
    public int update(UserEntity userEntity) {
        return userMapper.update(userEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int deleteUser(int id) {
        int user = userMapper.deleteUser(id);
        roleMapper.deleteByUserId(id);
        return user;
    }

    @Override
    public UserEntity getUserByName(String username) {
        return userMapper.getUserByName(username);
    }

    @Override
    public List<Integer> getRoleListById(int id) {
        return userMapper.getRoleListById(id);
    }

    @Override
    public List<UserMailVO> getUserMailList() {
        return userMapper.getUserMailList();
    }

}
