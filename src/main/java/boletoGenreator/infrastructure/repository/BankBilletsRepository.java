package boletoGenreator.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.EntityBankBillet;
import boletoGenreator.useCases.entity.user.EntityUser;

public interface BankBilletsRepository extends JpaRepository<EntityBankBillet, Long>{
    
    List<EntityBankBillet> findByUser(EntityUser user);
}
