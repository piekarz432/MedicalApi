package pl.com.medicalApi.service.user;

import pl.com.medicalApi.configuration.security.Role;
import pl.com.medicalApi.service.dtos.user.GetUserDto;
import pl.com.medicalApi.service.dtos.user.PostUserDto;

import java.util.List;

public interface IUserService {

    GetUserDto create(PostUserDto postUserDto);

    GetUserDto update(Long userId, String username, Role role);

    GetUserDto delete(Long userId);

    List<GetUserDto> get(Integer pageNumber, Integer pageSize);

    List<GetUserDto> getAndSort(Integer pageNumber, Integer pageSize, String sortProperty);

    List<GetUserDto> getByRole(Integer pageNumber, Integer pageSize, Role role);
}
