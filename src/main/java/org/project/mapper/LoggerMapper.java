package org.project.mapper;

import org.project.entity.LoggerEntity;
import org.project.entity.condition.LoggerCondition;

import java.util.List;

public interface LoggerMapper {

    int insert(LoggerEntity entity);

    List<LoggerEntity> getPage(LoggerCondition condition);

    int deleteAll();

}
