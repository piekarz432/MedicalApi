package pl.com.britenet.javastepone.service.dtos.user;

import pl.com.britenet.javastepone.configuration.security.Role;

public abstract class UserDto {

    private String username;
    private Role role;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
