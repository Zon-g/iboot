package org.project.service.impl;

import org.project.entity.ViewObject.DepartmentVO;
import org.project.entity.condition.DepartmentCondition;
import org.project.entity.DepartmentEntity;
import org.project.mapper.DepartmentMapper;
import org.project.service.DepartmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public List<DepartmentVO> getDeptList() {
        return departmentMapper.getDeptList();
    }

    @Override
    public List<DepartmentEntity> getPage(DepartmentCondition condition) {
        return departmentMapper.getPage(condition);
    }

    @Override
    public int deleteById(int id) {
        return departmentMapper.deleteById(id);
    }

    @Override
    public int insert(DepartmentEntity department) {
        return departmentMapper.insert(department);
    }

    @Override
    public int update(DepartmentEntity department) {
        return departmentMapper.update(department);
    }

}
