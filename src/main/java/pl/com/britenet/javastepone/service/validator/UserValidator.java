package pl.com.britenet.javastepone.service.validator;

import org.springframework.stereotype.Component;
import pl.com.britenet.javastepone.configuration.security.Role;
import pl.com.britenet.javastepone.exception.ResourceFoundException;
import pl.com.britenet.javastepone.exception.ResourceNotFoundException;
import pl.com.britenet.javastepone.exception.ValidationError;
import pl.com.britenet.javastepone.exception.ValidationException;
import pl.com.britenet.javastepone.repository.UserRepository;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.service.dtos.user.PostUserDto;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class UserValidator {

    private static final int MIN_USERNAME_LENGTH = 3;
    private static final int MAX_USERNAME_LENGTH = 15;
    private static final int MIN_PASSWORD_LENGTH = 8;
    private static final int MAX_PASSWORD_LENGTH = 15;

    private static final String FIELD_USERNAME = "Username";
    private static final String FIELD_PASSWORD = "Password";
    private static final String FIELD_ROLE = "Role";
    private static final String FIELD_STATUS = "Status";

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void createOnValidate(PostUserDto postUserDto) {
        var fieldErrorList = isValid(postUserDto);

        if (fieldErrorList.isEmpty()) {
            int count = userRepository.countByUsername(postUserDto.getUsername());

            if (count != 0) {
                throw new ResourceFoundException("User with username: " + postUserDto.getUsername() + " exists");
            }
        } else {
            throw new ValidationException("Validation Error", fieldErrorList);
        }
    }

    public void updateOnValidate(Long id, String username, Role role) {

        if (username == null && role == null) {
            throw new ValidationException("The data you want to edit has not been specified", emptyList());
        }

        var fieldErrorList = new ArrayList<ValidationError>();

        var userEntityOptional = userRepository.findById(id);

        if (username != null) {
            fieldErrorList.addAll(validUsername(username));

            if (fieldErrorList.isEmpty()) {
                if (userEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("User with id: "
                            + userEntityOptional.orElseThrow().getId() + " does not exists");
                }
            } else {
                throw new ValidationException("Validation Error", fieldErrorList);
            }
        }

        if (role != null) {
            fieldErrorList.addAll(validRole(role));

            if (fieldErrorList.isEmpty()) {

                if (userEntityOptional.isEmpty()) {
                    throw new ResourceNotFoundException("User with id: "
                            + userEntityOptional.orElseThrow().getId() + " does not exists");
                }
            } else {
                throw new ValidationException("Validation Error", fieldErrorList);
            }
        }
    }

    public void getOnValidate(Integer pageNumber, Integer pageSize) {
        validPageNumber(pageNumber);
        validPageSize(pageSize);
    }

    public void getAndSortOnValidate(Integer pageNumber, Integer pageSize, String sortProperty) {
        validPageNumber(pageNumber);
        validPageSize(pageSize);

        if (sortProperty == null) {
            throw new IllegalArgumentException("Sort property cannot be empty");
        }
    }

    public void getByRoleOnValidate(Integer pageNumber, Integer pageSize, Role role) {
        validPageNumber(pageNumber);
        validPageSize(pageSize);

        if (role == null) {
            throw new IllegalArgumentException("User role cannot be empty");
        }
    }

    public void deleteOnValidate(Long userId) {
        userExists(userId);
    }

    private List<ValidationError> isValid(PostUserDto postUserDto) {
        var fieldErrorList = new ArrayList<ValidationError>();

        fieldErrorList.addAll(validUsername(postUserDto.getUsername()));
        fieldErrorList.addAll(validPassword(postUserDto.getPassword()));
        fieldErrorList.addAll(validRole(postUserDto.getRole()));
        fieldErrorList.addAll(validStatus(postUserDto.getStatus()));

        return fieldErrorList;
    }

    private List<ValidationError> validUsername(String username) {

        if (username == null || username.equals("")) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_USERNAME + " is blank", FIELD_USERNAME + " cannot be empty"));
        }
        if (username.length() < MIN_USERNAME_LENGTH) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_USERNAME + " is too short", FIELD_USERNAME + " must be at least " + MIN_USERNAME_LENGTH + " characters"));
        }
        if (username.length() > MAX_USERNAME_LENGTH) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_USERNAME + " is too long", FIELD_USERNAME + " can be up to " + MAX_USERNAME_LENGTH + " characters"));
        }

        return emptyList();
    }

    private List<ValidationError> validPassword(String password) {

        if (password == null || password.equals("")) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_PASSWORD + " is blank", FIELD_PASSWORD + " cannot be empty"));
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_PASSWORD + " is too short", FIELD_PASSWORD + " must be at least" + MIN_PASSWORD_LENGTH + " characters"));
        }
        if (password.length() > MAX_PASSWORD_LENGTH) {
            return List.of(new ValidationError(FIELD_USERNAME, FIELD_PASSWORD + " is too long", FIELD_PASSWORD + " can be up to" + MAX_PASSWORD_LENGTH + " characters"));
        }

        return emptyList();
    }

    private List<ValidationError> validRole(Role role) {
        if (role == null) {
            return List.of((new ValidationError(FIELD_ROLE, FIELD_ROLE + " is blank", FIELD_ROLE + " cannot be empty")));
        }
        return emptyList();
    }

    private List<ValidationError> validStatus(Status status) {
        if (status == null) {
            return List.of((new ValidationError(FIELD_STATUS, FIELD_STATUS + " is blank", FIELD_STATUS + " cannot be empty")));
        }
        if (status == Status.INACTIVE) {
            return List.of(new ValidationError(FIELD_STATUS, FIELD_STATUS + " is inactive", "A new user can not be status inactive"));
        }
        if (status == Status.DELETED) {
            return List.of(new ValidationError(FIELD_STATUS, FIELD_STATUS + " is deleted", "A new user can not be status deleted"));
        }
        return emptyList();
    }

    private void validPageNumber(Integer pageNumber) {
        if (pageNumber == null) {
            throw new IllegalArgumentException("Page number cannot be empty");
        }
        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page number cannot be less then 0");
        }
    }

    private void validPageSize(Integer pageSize) {
        if (pageSize == null) {
            throw new IllegalArgumentException("Page size cannot be empty");
        }
        if (pageSize == 0) {
            throw new IllegalArgumentException("Page size cannot be 0");
        }
    }

    private void userExists(Long userId) {
        var count = userRepository.countById(userId);

        if (count == 0) {
            throw new ResourceNotFoundException("User with id " + userId + " does not exists");
        }
    }
}
