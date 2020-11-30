package org.project.service;

import org.project.entity.MailEntity;
import org.project.entity.condition.MailCondition;

import java.util.List;

public interface MailService {

    List<MailEntity> findPage(MailCondition condition);

    int add(MailEntity entity, String... attachments);

    int delete(long id);

}
