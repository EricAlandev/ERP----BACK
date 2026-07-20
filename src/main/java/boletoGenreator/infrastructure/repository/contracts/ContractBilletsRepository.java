package boletoGenreator.infrastructure.repository.contracts;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.contracts.EntityContractBillet;

public interface ContractBilletsRepository  extends JpaRepository<EntityContractBillet, Long>{
}
