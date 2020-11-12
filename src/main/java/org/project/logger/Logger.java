package org.project.logger;

import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * Defines am annotation for logger.
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
