package boletoGenreator.useCases.entity.user;

import java.time.LocalDateTime;
import java.util.List;

import boletoGenreator.useCases.entity.contracts.EntityContracts;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @Column(length = 255)
    private String email;

    @Column(length = 100)
    private String password;

    @Column()
    private LocalDateTime birthday;

    @Column(length = 500)
    private String token;

    @Column(length = 1)
    private String gender;

    @Column(length = 1, name="type_user")
    private String typeUser;

    @Column(length = 14, name="cic")
    private String cic;

    @OneToMany(mappedBy = "userByIntegrity", cascade = CascadeType.ALL)
    private List<EntityUserIntegrity> integrity;

    @OneToMany(mappedBy = "contractsUser", cascade = CascadeType.ALL)
    private List<EntityContracts> userContracts;

    @OneToMany(mappedBy = "userAdress", cascade = CascadeType.ALL)
    private List<EntityAdress> adress;
}
