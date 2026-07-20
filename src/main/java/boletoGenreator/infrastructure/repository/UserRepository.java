package boletoGenreator.infrastructure.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.user.EntityUser;

public interface UserRepository  extends JpaRepository<EntityUser, Long>{

    Optional<EntityUser> findByEmail(String email);
}
