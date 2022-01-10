package pl.com.britenet.javastepone.service.user;

import pl.com.britenet.javastepone.configuration.security.Role;
import pl.com.britenet.javastepone.service.dtos.user.GetUserDto;
import pl.com.britenet.javastepone.service.dtos.user.PostUserDto;

import java.util.List;

public interface IUserService {

    GetUserDto create(PostUserDto postUserDto);

    GetUserDto update(Long userId, String username, Role role);

    GetUserDto delete(Long userId);

    List<GetUserDto> get(Integer pageNumber, Integer pageSize);

    List<GetUserDto> getAndSort(Integer pageNumber, Integer pageSize, String sortProperty);

    List<GetUserDto> getByRole(Integer pageNumber, Integer pageSize, Role role);
}
