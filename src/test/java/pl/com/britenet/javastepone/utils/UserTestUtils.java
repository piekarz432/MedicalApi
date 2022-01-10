package pl.com.britenet.javastepone.utils;

import pl.com.britenet.javastepone.configuration.security.Role;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.service.dtos.user.PostUserDto;

public class UserTestUtils {

    public static final String userPath = "/user";

    public static PostUserDto createValidPostUserDto() {
        PostUserDto postUserDto = new PostUserDto();
        postUserDto.setUsername("Test");
        postUserDto.setPassword("Admin12!");
        postUserDto.setRole(Role.PSYCHOLOGIST);
        postUserDto.setStatus(Status.ACTIVE);

        return postUserDto;
    }

}
