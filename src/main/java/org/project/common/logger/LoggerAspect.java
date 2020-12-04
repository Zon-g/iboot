package org.project.common.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.project.entity.LoggerEntity;
import org.project.service.LoggerService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggerAspect {

    private static final String OP = "OPERATION";

    private static final String DES = "DESCRIPTION";

    private static final String PARAMS = "PARAMETERS";

    @Resource
    private LoggerService loggerService;

    @Pointcut(value = "@annotation(org.project.common.logger.Logger)")
    public void loggerAspect() {
    }

    @AfterReturning(value = "loggerAspect()")
    public void afterReturning(JoinPoint point) {
        Map<String, Object> info = getMethodInfo(point);
        String user = getCurrentUser(), method = getCurrentMethod(point);
        String operation = ((String) info.get(OP)), description = ((String) info.get(DES));
        @SuppressWarnings("unchecked")
        Map<Class<?>, Object> params = ((Map<Class<?>, Object>) info.get(PARAMS));
        LoggerEntity logger = LoggerEntity.builder()
                .setUser(user)
                .setMethod(method)
                .setOperation(operation)
                .setDescription(description)
                .setParams(params.toString())
                .build();
        loggerService.insert(logger);
    }

    /**
     * Gets the basic info of current invoked method.
     *
     * @param point Join point to interleave
     * @return basic info of current invoked method
     */
    private Map<String, Object> getMethodInfo(JoinPoint point) {
        Map<Parameter, Object> params = new HashMap<>();
        String operation = null, description = null;
        try {
            String target = point.getTarget().getClass().getName(),
                    methodName = point.getSignature().getName();
            Object[] args = point.getArgs();
            Class<?> targetClass = Class.forName(target);
            Method[] methods = targetClass.getMethods();
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Parameter[] parameters = method.getParameters();
                    if (parameters.length == args.length) {
                        for (int i = 0; i < parameters.length; i++) {
                            params.put(parameters[i], args[i]);
                        }
                        Logger logger = method.getAnnotation(Logger.class);
                        operation = logger.operation().name();
                        description = logger.value();
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Map<String, Object> map = new HashMap<>();
        map.put(PARAMS, params);
        map.put(OP, operation);
        map.put(DES, description);
        return map;
    }

    /**
     * Gets the name of current method.
     *
     * @param point Join point
     * @return current method's name
     */
    private String getCurrentMethod(JoinPoint point) {
        return point.getTarget().getClass().getName() + "." + point.getSignature().getName();
    }

    /**
     * Gets the username of current user.
     *
     * @return username of current user
     */
    private String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((String) authentication.getPrincipal());
        }
        return null;
    }

}
