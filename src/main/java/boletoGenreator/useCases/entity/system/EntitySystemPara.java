package boletoGenreator.useCases.entity.system;

import java.math.BigDecimal;

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
@Table(name = "system_parameters")
public class EntitySystemPara {
    
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "iof_tax")
    private BigDecimal iofMax;

    @Column(name = "iof_day_value")
    private BigDecimal iofPerDay;

    @Column(name = "max_days")
    private Long maxDays;

    @Column(name = "cod_do")
    private Long codigoDo;

    @Column(name = "sg_do", length = 3)
    private String siglaDo;

    @Column(name = "tac")
    private BigDecimal tac;
}
