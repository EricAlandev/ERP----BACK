package boletoGenreator.infrastructure.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.EntityBankBillet;

public interface BankBilletsRepository extends JpaRepository<EntityBankBillet, Long>{
    
}
