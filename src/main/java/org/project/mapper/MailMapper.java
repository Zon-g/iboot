package org.project.mapper;

import org.project.entity.MailEntity;
import org.project.entity.condition.MailCondition;

import java.util.List;

public interface MailMapper {

    List<MailEntity> findPage(MailCondition condition);

    int add(MailEntity entity);

    int delete(long id);

}
