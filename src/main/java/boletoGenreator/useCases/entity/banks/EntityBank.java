package boletoGenreator.useCases.entity.banks;

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
@Table (name = "banks")
public class EntityBank {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10 , name = "abreviation")
    private String Abreviation;

    @Column(length = 55 , name = "bankname")
    private String BankName;

    @Column(length = 255 , name = "location")
    private String Location;

    @Column( name = "bankcode")
    private Long BankCode;

    @Column( name = "check_digit_bank_code")
    private Long CheckId;

    @Column( name = "ispb")
    private Long ispbs;
    
}
