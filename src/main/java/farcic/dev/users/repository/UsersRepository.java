package farcic.dev.users.repository;

import farcic.dev.users.entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    boolean existsByEmail(String email);

}
