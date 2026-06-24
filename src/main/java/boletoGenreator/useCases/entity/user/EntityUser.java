package boletoGenreator.useCases.entity.user;

import java.util.List;

import boletoGenreator.useCases.entity.EntityBankBillet;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "users")
public class EntityUser {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column()
    private String email;

    @Column()
    private String password;

    @Column()
    private String birthday;

    @Column()
    private String token;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<EntityBankBillet> billets;

    @OneToOne(mappedBy = "userByIntegrity", cascade = CascadeType.ALL)
    private EntityUserIntegrity integrity;
}
