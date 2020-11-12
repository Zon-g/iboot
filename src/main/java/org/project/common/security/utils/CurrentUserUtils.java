package org.project.common.security.utils;

import lombok.RequiredArgsConstructor;
import org.project.entity.UserEntity;
import org.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CurrentUserUtils {

    @Autowired
    private UserService userService;

    private static String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != null) {
            return ((String) authentication.getPrincipal());
        }
        return null;
    }

    public UserEntity getCurrentUser() {
        return userService.getUserByName(getCurrentUsername());
    }

}
