package boletoGenreator.infrastructure.repository.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.domain.model.users.UserLimitsDTO;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserProfession;
import boletoGenreator.useCases.entity.user.EntityUserScore;

public interface UserScoreRepository extends JpaRepository<EntityUserScore, Long>  {
    
    @Query("SELECT u.id, p.job, p.salary, us FROM EntityUser u LEFT JOIN EntityUserProfession p ON p.userByProfession = u LEFT JOIN EntityUserScore us ON us.userFromScore = u WHERE u.id = :id")
    Optional<UserLimitsDTO> findUserScore(Long id);

}
