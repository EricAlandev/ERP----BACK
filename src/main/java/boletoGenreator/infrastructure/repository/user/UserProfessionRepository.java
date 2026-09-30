package boletoGenreator.infrastructure.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.user.EntityUserProfession;

public interface UserProfessionRepository extends JpaRepository<EntityUserProfession, Long>{

    @Query("SELECT up FROM EntityUserProfession up WHERE EXISTS ( SELECT u FROM EntityUser u WHERE u.id =:id)")
    Optional<EntityUserProfession> findByUserByProfession(Long id);
}
