package boletoGenreator.infrastructure.repository.combos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.domain.model.combos.ComboStateDTO;
import boletoGenreator.useCases.entity.domains.EntityDova;

public interface ComboStateRepository extends JpaRepository<EntityDova, Long> {
    
    @Query("SELECT EntityDova.do_value FROM EntityDova dov WHERE EXISTS(SELECT d.cod_do from  d WHERE d.sg_do = 'EST' and dov.sg_do = d.cod_do )")
    public List<ComboStateDTO> findStates();
}
