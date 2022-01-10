package pl.com.medicalApi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.com.medicalApi.configuration.security.Role;
import pl.com.medicalApi.repository.model.user.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    int countByUsername(String username);

    int countById(Long id);

    Page<UserEntity> findByRole(Role role, Pageable pageable);
}
