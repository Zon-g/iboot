package org.project.common.logger;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Self-defined class for system log. This annotation is used in
 * controller layer to record the operation current user does.
 * Usually only field <code>value</code> and <code>operation</code>
 * is required.
 *
 * @author Zon-g
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logger {

    /**
     * Alias for {@link #description}
     */
    @AliasFor("description")
    String value() default "";

    /**
     * The description of to bind to the logger
     */
    String description() default "";

    /**
     * Type of operation, default <code>NULL</code>
     */
    Operation operation() default Operation.NULL;

}
