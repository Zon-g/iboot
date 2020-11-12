package org.project.service.impl;

import org.project.entity.LoggerEntity;
import org.project.entity.condition.LoggerCondition;
import org.project.mapper.LoggerMapper;
import org.project.service.LoggerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LoggerServiceImpl implements LoggerService {

    @Resource
    private LoggerMapper loggerMapper;

    @Override
    public int insert(LoggerEntity entity) {
        return loggerMapper.insert(entity);
    }

    @Override
    public List<LoggerEntity> getPage(LoggerCondition condition) {
        return loggerMapper.getPage(condition);
    }

    @Override
    public int deleteAll() {
        return loggerMapper.deleteAll();
    }

}
