package pl.com.medicalApi.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.*;
import pl.com.medicalApi.configuration.security.Role;
import pl.com.medicalApi.service.dtos.user.GetUserDto;
import pl.com.medicalApi.service.dtos.user.PostUserDto;
import pl.com.medicalApi.service.user.IUserService;

import java.util.List;

@RestController
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Create a new user")
    @PostMapping("/britemed/user")
    public GetUserDto create(@RequestBody PostUserDto postUserDto) {
        return userService.create(postUserDto);
    }

    @Operation(summary = "Update user")
    @PatchMapping("/britemed/user/{id}")
    public GetUserDto update(
            @PathVariable Long id,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) Role role) {
        return userService.update(id, username, role);
    }

    @Operation(summary = "Get users by pages")
    @GetMapping("/britemed/user/{pageNumber}/{pageSize}")
    public List<GetUserDto> get(@PathVariable Integer pageNumber, @PathVariable Integer pageSize) {
        return userService.get(pageNumber, pageSize);
    }

    @Operation(summary = "Get users by role, pages and sort")
    @GetMapping("/britemed/user/{pageNumber}/{pageSize}/role")
    public List<GetUserDto> getByRole(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @RequestParam Role role) {
        return userService.getByRole(pageNumber, pageSize, role);
    }

    @Operation(summary = "Get users by pages and sort")
    @GetMapping("/britemed/user/{pageNumber}/{pageSize}/{sortProperty}")
    public List<GetUserDto> getAndSort(@PathVariable Integer pageNumber, @PathVariable Integer pageSize, @PathVariable String sortProperty) {
        return userService.getAndSort(pageNumber, pageSize, sortProperty);
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/britemed/user/{id}")
    public GetUserDto delete(@PathVariable Long id) {
        return userService.delete(id);
    }
}
