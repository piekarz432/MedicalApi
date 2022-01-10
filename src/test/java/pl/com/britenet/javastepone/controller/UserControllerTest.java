package pl.com.britenet.javastepone.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import pl.com.britenet.javastepone.AbstractEndpointIntegrationTest;
import pl.com.britenet.javastepone.service.dtos.user.GetUserDto;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.*;
import static pl.com.britenet.javastepone.utils.UserTestUtils.createValidPostUserDto;
import static pl.com.britenet.javastepone.utils.UserTestUtils.userPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class UserControllerTest extends AbstractEndpointIntegrationTest {


    @Test
    @DisplayName("should create user with valid fields")
    void create_WhenUserFieldsIsValid_ShouldCreateUser() {

        // given
        var postUserDto = createValidPostUserDto();

        // when
        var response = testRestTemplate
                .withBasicAuth("test", "test")
                .postForEntity(url(port) + userPath, postUserDto, GetUserDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        var id = Objects.requireNonNull(response.getBody()).getId();

        //then
        assertEquals(OK, httpStatus);

        //cleanup
        clear(id, "APPLICATION_USER");
    }

    @Test
    @DisplayName("should not create user ")
    void create_WhenUserFieldsIsValidButUserHasNoAccess_ShouldNotCreateUser() {

        // given
        var postUserDto = createValidPostUserDto();

        // when
        var response = testRestTemplate
                .withBasicAuth("test2", "test")
                .postForEntity(url(port) + userPath, postUserDto, GetUserDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(FORBIDDEN, httpStatus);
    }

    @Test
    @DisplayName("should create user with valid fields and user does not exist")
    void create_WhenUserFieldsIsValidAndUserDoesNotExist_ShouldNotCreateUser() {
        // given
        var postUserDto = createValidPostUserDto();

        // when
        var response = testRestTemplate
                .withBasicAuth("test3", "test")
                .postForEntity(url(port) + userPath, postUserDto, GetUserDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(UNAUTHORIZED, httpStatus);
    }

    @Test
    @DisplayName("should get user by pages")
    void get_WhenPageNumberAndPageSizeIsValid_ShouldGetUserList() {
        // given
        int pageNumber = 0;
        int pageSize = 5;
        int numberOfUser = 5;

        // when
        var response = testRestTemplate
                .withBasicAuth("test", "test")
                .getForEntity(url(port) + userPath + "/" + pageNumber + "/" + pageSize, GetUserDto[].class);
        HttpStatus httpStatus = response.getStatusCode();

        int visitCount = Objects.requireNonNull(response.getBody()).length;

        //then
        assertEquals(OK, httpStatus);
        assertEquals(numberOfUser, visitCount);
    }

    @Test
    @DisplayName("should not get user by pages")
    void get_WhenPageNumberAndPageSizeIsValidButUserHasNoAccess_ShouldNotGetUserList() {
        // given
        int pageNumber = 0;
        int pageSize = 5;

        // when
        var response = testRestTemplate
                .withBasicAuth("test2", "test")
                .getForEntity(url(port) + userPath + "/" + pageNumber + "/" + pageSize, GetUserDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(FORBIDDEN, httpStatus);
    }

    @DisplayName("Should delete user")
    @Test
    void delete_WhenUserIsValid_ShouldDeleteUser() {

        //given
        long userId = 1000001;

        //when
        var response = testRestTemplate
                .withBasicAuth("test", "test")
                .exchange(url(port) + userPath + "/" + userId, DELETE, null, String.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(OK, httpStatus);

        //clean
        updateStatus(userId, "APPLICATION_USER");
    }

    @DisplayName("Should not delete user")
    @Test
    void delete_WhenUserIsValidButUserHasNoAccess_ShouldNotDeleteUser() {

        //given
        long userId = 1000001;

        //when
        var response = testRestTemplate
                .withBasicAuth("test2", "test")
                .exchange(url(port) + userPath + "/" + userId, DELETE, null, String.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(FORBIDDEN, httpStatus);

    }
}