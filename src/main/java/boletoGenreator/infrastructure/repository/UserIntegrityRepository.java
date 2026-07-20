package boletoGenreator.infrastructure.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;


public interface UserIntegrityRepository  extends JpaRepository<EntityUserIntegrity, Long>{

    
    List<EntityUserIntegrity> findByUserByIntegrity(EntityUser userByIntegrity);
}
