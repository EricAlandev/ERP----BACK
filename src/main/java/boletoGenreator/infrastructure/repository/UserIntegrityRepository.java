package boletoGenreator.infrastructure.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;


public interface UserIntegrityRepository  extends JpaRepository<EntityUserIntegrity, Long>{

}
