package boletoGenreator.infrastructure.repository.banks;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.banks.EntityBank;

public interface BankRepository extends JpaRepository<EntityBank, Long>{

    
} 
