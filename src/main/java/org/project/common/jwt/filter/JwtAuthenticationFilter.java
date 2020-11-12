package org.project.common.jwt.filter;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.project.entity.ViewObject.UserInfo;
import org.project.common.jwt.entity.JwtUser;
import org.project.common.jwt.utils.JwtTokenUtils;
import org.project.common.response.Res;
import org.project.common.security.constants.SecurityConstants;
import org.project.common.security.dto.LoginRequest;
import org.project.service.MenuService;
import org.project.service.impl.MenuServiceImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();

    private final AuthenticationManager manager;

    private static MenuService menuService = new MenuServiceImpl();

    public JwtAuthenticationFilter(AuthenticationManager manager) {
        this.manager = manager;
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            rememberMe.set(loginRequest.isRememberMe());
            UsernamePasswordAuthenticationToken authentication = new
                    UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword());
            return manager.authenticate(authentication);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) {
        JwtUser jwtUser = ((JwtUser) authResult.getPrincipal());
        Set<String> authorities = jwtUser.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), rememberMe.get(), authorities);
        rememberMe.remove();
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);

        try (PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(Res.ok().data("user", UserInfo.of(jwtUser))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, failed.getMessage());
    }

}
