package org.project.service;

import org.project.entity.MenuEntity;

import java.util.List;

public interface MenuService {

    int deleteOldMenus(int id, List<Integer> list);

    int addNewMenus(int id, List<Integer> list);

    List<MenuEntity> getAll();

    List<MenuEntity> getMenuByUserId(int id);

    List<MenuEntity> getMenuByRoleId(int id);

    List<Integer> getRoleMenu(int id);

    int delete(int id);

    int insert(MenuEntity menuEntity);

    int update(MenuEntity menuEntity);

}
