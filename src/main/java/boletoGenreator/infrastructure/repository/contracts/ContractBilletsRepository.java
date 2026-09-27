package boletoGenreator.infrastructure.repository.contracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.contracts.EntityContractBillet;
import boletoGenreator.useCases.entity.contracts.EntityContracts;

public interface ContractBilletsRepository  extends JpaRepository<EntityContractBillet, Long>{

    List<EntityContractBillet> findByContracts(EntityContracts contract);

    @Query("SELECT cb.bankBillets FROM EntityContractBillet cb WHERE cb.contracts IN (:contractsIds)")
    List<Long> findByContractIds(List<Long> contractsIds);
}
