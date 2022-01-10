package pl.com.britenet.javastepone.service.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import pl.com.britenet.javastepone.configuration.security.Role;
import pl.com.britenet.javastepone.exception.ResourceFoundException;
import pl.com.britenet.javastepone.exception.ValidationException;
import pl.com.britenet.javastepone.repository.UserRepository;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.service.dtos.user.PostUserDto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserValidatorTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserValidator userValidator;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @DisplayName("Should create valid user")
    @Test
    void createOnValidate_WhenUserIsValid_ShouldExceptionWillBeNotThrown() {

        //given
        var postUserDto = createValidPostUserDto();

        //when
        userValidator.createOnValidate(postUserDto);

        //then
        assertDoesNotThrow(() -> {
        });
    }

    @DisplayName("Create user with invalid username")
    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"A", "qweqwrqwrqrwasdasdwqrqqwasfazxcawrqwsaf"})
    void createOnValidate_WhenUserNameIsInvalid_ShouldExceptionWillBeThrown(String username) {

        //given
        var postUserDto = createValidPostUserDto();
        postUserDto.setUsername(username);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> userValidator.createOnValidate(postUserDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create user with invalid role")
    @Test
    void createOnValidate_WhenUserRoleIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var postUserDto = createValidPostUserDto();
        postUserDto.setRole(null);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> userValidator.createOnValidate(postUserDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @NullAndEmptySource
    @ParameterizedTest
    @ValueSource(strings = {"A", "qweqwrqwrqrwasdasdwqrqqwasfazxcawrqwsaf"})
    @DisplayName("Create user with invalid password")
    void createOnValidate_WhenUserPasswordIsInvalid_ShouldExceptionWillBeThrown(String password) {

        //given
        var postUserDto = createValidPostUserDto();
        postUserDto.setPassword(password);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> userValidator.createOnValidate(postUserDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create user with invalid status")
    @Test
    void createOnValidate_WhenUserStatusIsInvalid_ShouldExceptionWillBeThrown() {

        //given
        var postUserDto = createValidPostUserDto();
        postUserDto.setStatus(null);

        //when
        ValidationException exception = assertThrows(ValidationException.class, () -> userValidator.createOnValidate(postUserDto));

        //then
        assertEquals("Validation Error", exception.getMessage());
    }

    @DisplayName("Create user when user exists")
    @Test
    void createOnValidate_WhenUserIsExists_ShouldExceptionWillBeThrown() {

        //given
        var postUserDto = createValidPostUserDto();

        //when
        when(userRepository.countByUsername(postUserDto.getUsername())).thenReturn(1);
        ResourceFoundException exception = assertThrows(ResourceFoundException.class, () -> userValidator.createOnValidate(postUserDto));


        //then
        assertEquals("User with username: " + postUserDto.getUsername() + " exists", exception.getMessage());
    }


    private PostUserDto createValidPostUserDto() {
        PostUserDto postUserDto = new PostUserDto();

        postUserDto.setUsername("test");
        postUserDto.setPassword("Admin12!");
        postUserDto.setRole(Role.PSYCHIATRIST);
        postUserDto.setStatus(Status.ACTIVE);

        return postUserDto;
    }

}