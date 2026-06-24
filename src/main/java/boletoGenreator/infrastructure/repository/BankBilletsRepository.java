package boletoGenreator.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import boletoGenreator.useCases.entity.EntityBankBillet;

@Repository
public interface BankBilletsRepository extends JpaRepository<EntityBankBillet, Long>{
    
}
