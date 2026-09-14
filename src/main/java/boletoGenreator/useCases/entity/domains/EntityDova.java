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
@Table(name = "dova")
public class EntityDova {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sg_do")
    private Long code;

    @Column(name = "do_value", length = 55)
    private String value;

}
