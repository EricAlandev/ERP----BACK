package boletoGenreator.infrastructure.repository.combos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.domain.model.combos.ComboStateDTO;
import boletoGenreator.useCases.entity.domains.EntityDomain;
import boletoGenreator.useCases.entity.domains.EntityDova;

public interface ComboStateRepository extends JpaRepository<EntityDova, Long> {

    @Query("SELECT dov.value FROM EntityDova dov WHERE EXISTS (SELECT d.code FROM EntityDomain d WHERE d.sigle = 'EST' AND dov.code = d.code)")
    public List<ComboStateDTO> findStates();
}
