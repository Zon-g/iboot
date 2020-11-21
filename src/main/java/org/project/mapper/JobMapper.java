package org.project.mapper;

import org.project.common.quartz.entity.QuartzJob;

import java.util.List;

public interface JobMapper {

    List<QuartzJob> list(String jobName);

}
