package boletoGenreator.useCases.entity.domains;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity  
@Getter 
@Setter 
@Table(name = "domains")
public class EntityDomain {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sg_do", length = 3)
    private String sigle;

    @Column(name = "cod_do")
    private Long code;

    @Column(length = 55)
    private String description;


}
