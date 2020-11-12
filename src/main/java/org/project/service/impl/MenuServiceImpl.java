package org.project.service.impl;

import org.project.entity.ViewObject.MenuVO;
import org.project.entity.MenuEntity;
import org.project.mapper.MenuMapper;
import org.project.service.MenuService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    public static String[] getPerms(List<MenuEntity> menuEntities) {
        return menuEntities.stream()
                .filter(menu -> menu.getType() == 1)
                .map(MenuEntity::getPerms)
                .toArray(String[]::new);
    }

    public static List<MenuEntity> removeUselessMenu(List<MenuEntity> menuEntities) {
        Queue<MenuEntity> queue = new LinkedList<>();
        menuEntities.forEach(menu -> {
            if (menu.getStatus() == 0) queue.offer(menu);
        });
        while (!queue.isEmpty()) {
            MenuEntity menuEntity = queue.poll();
            menuEntities.remove(menuEntity);
            menuEntities.forEach(menu1 -> {
                if (menu1.getParentId() == menuEntity.getId()) queue.offer(menu1);
            });
        }
        return menuEntities;
    }

    public static List<Integer> getLeaves(List<MenuEntity> menuEntities) {
        List<Integer> list = new LinkedList<>();
        for (MenuEntity menuEntity : menuEntities) {
            boolean flag = true;
            for (MenuEntity m : menuEntities) {
                if (m.getParentId() == menuEntity.getId()) {
                    flag = false;
                    break;
                }
            }
            if (flag) list.add(menuEntity.getId());
        }
        return list;
    }

    public static MenuVO[] getMenuTree(List<MenuEntity> menuEntities) {
        return getMenuTree(menuEntities, true);
    }

    public static MenuVO[] getMenuTree(List<MenuEntity> menuEntities, boolean withButton) {
        List<MenuVO> list = menuEntities.stream()
                .map(MenuVO::of)
                .collect(Collectors.toList());
        Queue<MenuVO> queue = new LinkedList<>();
        MenuVO root = new MenuVO();
        root.setId(0);
        queue.offer(root);
        while (!queue.isEmpty()) {
            MenuVO object = queue.poll();
            MenuVO[] children = findChildren(list, object.getId(), withButton);
            object.setChildren(children);
            for (MenuVO child : children) queue.offer(child);
        }
        return root.getChildren().clone();
    }

    private static MenuVO[] findChildren(List<MenuVO> list, int parentId, boolean withButton) {
        Predicate<MenuVO> predicate;
        if (withButton) predicate = o -> o.getParentId() == parentId;
        else predicate = o -> o.getParentId() == parentId && o.getType() == 0;
        return list.stream()
                .filter(predicate)
                .toArray(MenuVO[]::new);
    }

    @Override
    public int deleteOldMenus(int id, List<Integer> list) {
        return menuMapper.deleteOldMenus(id, list);
    }

    @Override
    public int addNewMenus(int id, List<Integer> list) {
        return menuMapper.addNewMenus(id, list);
    }

    @Override
    public List<MenuEntity> getAll() {
        return menuMapper.getAll();
    }

    @Override
    public List<MenuEntity> getMenuByUserId(int id) {
        return menuMapper.getMenuByUserId(id);
    }

    @Override
    public List<MenuEntity> getMenuByRoleId(int id) {
        return menuMapper.getMenuByRoleId(id);
    }

    @Override
    public List<Integer> getRoleMenu(int id) {
        return menuMapper.getRoleMenu(id);
    }

    @Override
    public int delete(int id) {
        return menuMapper.delete(id);
    }

    @Override
    public int insert(MenuEntity menuEntity) {
        return menuMapper.insert(menuEntity);
    }

    @Override
    public int update(MenuEntity menuEntity) {
        return menuMapper.update(menuEntity);
    }

}
