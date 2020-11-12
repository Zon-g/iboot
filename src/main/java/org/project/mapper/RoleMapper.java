package org.project.mapper;

import org.project.entity.condition.RoleCondition;
import org.project.entity.RoleEntity;

import java.util.List;

public interface RoleMapper {

    int deleteByUserId(int id);

    List<RoleEntity> getRoles(RoleCondition condition);

    List<RoleEntity> getRoleList();

    int deleteOldRoles(int id, List<Integer> list);

    int addNewRoles(int id, List<Integer> list);

    int insert(RoleEntity roleEntity);

    int update(RoleEntity roleEntity);

    int delete(int id);

}
