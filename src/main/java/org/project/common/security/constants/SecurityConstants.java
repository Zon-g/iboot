package org.project.common.security.constants;

public class SecurityConstants {

    /* Authentication login url */
    public static final String AUTH_LOGIN_URL = "/auth/login";

    /* Key for roles */
    public static final String ROLE_CLAIMS = "rol";

    /* Expiration time when rememberMe equals false */
    public static final long EXPIRATION = 60 * 60L;

    /* Expiration time when rememberMe equals true */
    public static final long EXPIRATION_REMEMBER = 60 * 60 * 24 * 7L;

    /* JWT token defaults */
    public static final String TOKEN_HEADER = "Authorization";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String TOKEN_TYPE = "JWT";

    public static final String JWT_SECRET_KEY = "C*F-JaNdRgUkXn2r5u8x/A?D(G+KbPeShVmYq3s6v9y$B&E)H@McQfTjWnZr4u7w";

    private SecurityConstants() {
    }

}
