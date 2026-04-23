package farcic.dev.users.repository;

import farcic.dev.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    boolean existsByEmail(String email);

    Optional<UsersEntity> findByEmail(String email);

    boolean existsByEmailAndIdNot(String email, Long id);

}
