package boletoGenreator.infrastructure.repository.system;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.system.EntitySystemPara;

public interface SystemRepository extends JpaRepository<EntitySystemPara, Long> {

    @Query("SELECT para.iofMax, para.iofPerDay, para.maxDays, para.tac FROM EntitySystemPara para WHERE para.siglaDo = :siglaDo")
    EntitySystemPara findByIofMax(String siglaDo);
} 
