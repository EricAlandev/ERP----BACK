package boletoGenreator.infrastructure.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.user.EntityUserScore;

public interface UserScoreRepository extends JpaRepository<EntityUserScore, Long>  {
    
    @Query(" SELECT us FROM EntityUserScore us where us.user_id =: id AND EXISTS ( SELECT u.id from users u where u.id =: id AND u.id = us.user_id)")
    Optional<EntityUserScore> findUserScore(Long id);

}
