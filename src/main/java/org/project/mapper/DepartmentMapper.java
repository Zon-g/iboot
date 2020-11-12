package org.project.mapper;

import org.project.entity.ViewObject.DepartmentVO;
import org.project.entity.condition.DepartmentCondition;
import org.project.entity.DepartmentEntity;

import java.util.List;

public interface DepartmentMapper {

    List<DepartmentVO> getDeptList();

    List<DepartmentEntity> getPage(DepartmentCondition condition);

    int deleteById(int id);

    int insert(DepartmentEntity department);

    int update(DepartmentEntity department);

}
