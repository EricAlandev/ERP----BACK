package boletoGenreator.infrastructure.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.user.EntityUserScore;

public interface UserScoreRepository extends JpaRepository<EntityUserScore, Long>  {

    @Query("SELECT us FROM EntityUserScore us WHERE EXISTS (SELECT u FROM EntityUser u WHERE u.id =:id)")
    Optional<EntityUserScore> findByUserFromScore(Long id);

}
