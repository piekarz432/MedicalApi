package pl.com.britenet.javastepone.service.dtos.user;

import pl.com.britenet.javastepone.repository.model.Status;

public class PostUserDto extends UserDto {

    private Status status;
    private String password;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
