package boletoGenreator.infrastructure.repository.adress;

import org.springframework.data.jpa.repository.JpaRepository;

import boletoGenreator.useCases.entity.user.EntityAdress;

public interface AdressRepository extends JpaRepository<EntityAdress, Long> {
    
}
