
package boletoGenreator.infrastructure.repository.contracts;


import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.contracts.EntityContracts;

public interface ContractRepository  extends JpaRepository<EntityContracts, Long>{
}
