package org.project.common.quartz.anotation;

import java.lang.annotation.*;

/**
 * Indicates self-defined scheduled job.
 *
 * @author Lin Tang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ScheduledJob {

    /**
     * value (or description) of scheduled job.
     */
    String value() default "";

    /**
     * ignore current class annotated by {@link org.project.common.quartz.anotation.ScheduledJob}.
     */
    boolean ignoreClass() default false;

}
