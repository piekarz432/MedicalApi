package pl.com.medicalApi.utils;

import pl.com.medicalApi.configuration.security.Role;
import pl.com.medicalApi.repository.model.Status;
import pl.com.medicalApi.service.dtos.user.PostUserDto;

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
