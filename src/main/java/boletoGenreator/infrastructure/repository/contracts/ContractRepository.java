package boletoGenreator.infrastructure.repository.contracts;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.contracts.EntityContracts;

public interface ContractRepository  extends JpaRepository<EntityContracts, Long>{


    @Query("SELECT e FROM EntityContracts e WHERE e.contractsUser =: userId AND e.dateContract <= : monthsCap")
    List<EntityContracts> findContractsWithCap(Long userId, LocalDateTime monthsCap);
}
