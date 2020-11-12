package org.project.common.jwt.entity;

import org.project.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class JwtUser implements UserDetails {

    private int id;

    private String username;

    private String password;

    private String avatar;

    private boolean enabled;

    private Set<SimpleGrantedAuthority> authorities;

    private boolean isAdmin;

    public JwtUser() {
    }

    public static JwtUser of(UserEntity userEntity) {
        JwtUser jwtUser = new JwtUser();
        jwtUser.id = userEntity.getId();
        jwtUser.username = userEntity.getUsername();
        jwtUser.password = userEntity.getPassword();
        jwtUser.avatar = userEntity.getAvatar();
        jwtUser.enabled = userEntity.getStatus() == 1;
        jwtUser.isAdmin = userEntity.getType() == 0;
        jwtUser.authorities = new HashSet<>();
        jwtUser.authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (userEntity.getType() == 0) jwtUser.authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return jwtUser;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public int getId() {
        return id;
    }

    public String getAvatar() {
        return this.avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", authorities=" + authorities +
                '}';
    }

}
