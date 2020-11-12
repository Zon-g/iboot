package org.project.service;

import org.project.entity.LoggerEntity;
import org.project.entity.condition.LoggerCondition;

import java.util.List;

public interface LoggerService {

    int insert(LoggerEntity entity);

    List<LoggerEntity> getPage(LoggerCondition condition);

    int deleteAll();

}
