package boletoGenreator.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import boletoGenreator.useCases.entity.EntityBankBillet;

public interface BankBilletsRepository extends JpaRepository<EntityBankBillet, Long>{
    
    @Query("SELECT bb FROM EntityBankBillet bb WHERE bb.id in (:bankBilletsIds) AND b.stats IN ('P', 'L')")
    List<EntityBankBillet> findByIds(List<Long> bankBilletsIds);
}
