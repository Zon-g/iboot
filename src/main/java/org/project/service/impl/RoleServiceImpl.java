package org.project.service.impl;

import org.project.entity.condition.RoleCondition;
import org.project.entity.RoleEntity;
import org.project.mapper.MenuMapper;
import org.project.mapper.RoleMapper;
import org.project.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<RoleEntity> getRoles(RoleCondition condition) {
        return roleMapper.getRoles(condition);
    }

    @Override
    public List<RoleEntity> getRoleList() {
        return roleMapper.getRoleList();
    }

    @Override
    public int deleteOldRoles(int id, List<Integer> list) {
        return roleMapper.deleteOldRoles(id, list);
    }

    @Override
    public int addNewRoles(int id, List<Integer> list) {
        return roleMapper.addNewRoles(id, list);
    }

    @Override
    public int insert(RoleEntity roleEntity) {
        return roleMapper.insert(roleEntity);
    }

    @Override
    public int update(RoleEntity roleEntity) {
        return roleMapper.update(roleEntity);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public int delete(int id) {
        int role = roleMapper.delete(id);
        menuMapper.deleteByRoleId(id);
        return role;
    }

}
