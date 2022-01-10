package pl.com.britenet.javastepone.service.user;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.com.britenet.javastepone.configuration.security.Role;
import pl.com.britenet.javastepone.configuration.security.annotation.AdminAuthorization;
import pl.com.britenet.javastepone.repository.UserRepository;
import pl.com.britenet.javastepone.repository.model.Status;
import pl.com.britenet.javastepone.repository.model.user.UserEntity;
import pl.com.britenet.javastepone.service.dtos.user.GetUserDto;
import pl.com.britenet.javastepone.service.dtos.user.PostUserDto;
import pl.com.britenet.javastepone.service.validator.UserValidator;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.data.domain.PageRequest.of;

@Service
@Transactional
@AdminAuthorization
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, UserValidator userValidator, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userValidator = userValidator;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public GetUserDto create(PostUserDto postUserDto) {
        userValidator.createOnValidate(postUserDto);

        postUserDto.setPassword(passwordEncoder.encode(postUserDto.getPassword()));

        var userEntity = modelMapper.map(postUserDto, UserEntity.class);

        var savedUser = userRepository.save(userEntity);

        return modelMapper.map(savedUser, GetUserDto.class);
    }

    @Override
    public GetUserDto update(Long userId, String username, Role role) {
        userValidator.updateOnValidate(userId, username, role);

        var userEntityOptional = userRepository.findById(userId);
        userEntityOptional.orElseThrow().setUsername(username);
        userEntityOptional.orElseThrow().setRole(role);

        return modelMapper.map(userEntityOptional.orElseThrow(), GetUserDto.class);
    }

    @Override
    public List<GetUserDto> get(Integer pageNumber, Integer pageSize) {
        userValidator.getOnValidate(pageNumber, pageSize);

        Pageable pageable = of(pageNumber, pageSize, Sort.by("id"));
        var users = userRepository.findAll(pageable);

        return users
                .stream().map(userEntity -> modelMapper.map(userEntity, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserDto> getAndSort(Integer pageNumber, Integer pageSize, String sortProperty) {
        userValidator.getAndSortOnValidate(pageNumber, pageSize, sortProperty);

        Pageable pageable = of(pageNumber, pageSize, Sort.Direction.ASC, sortProperty);
        var users = userRepository.findAll(pageable);

        return users
                .stream().map(userEntity -> modelMapper.map(userEntity, GetUserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<GetUserDto> getByRole(Integer pageNumber, Integer pageSize, Role role) {
        userValidator.getByRoleOnValidate(pageNumber, pageSize, role);

        Pageable pageable = of(pageNumber, pageSize, Sort.by("id"));
        Page<UserEntity> result = userRepository.findByRole(role, pageable);

        return result
                .stream().map(userEntity -> modelMapper.map(userEntity, GetUserDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public GetUserDto delete(Long userId) {
        userValidator.deleteOnValidate(userId);

        var user = userRepository.findById(userId);
        user.orElseThrow().setStatus(Status.INACTIVE);

        return modelMapper.map(user.orElseThrow(), GetUserDto.class);
    }
}
