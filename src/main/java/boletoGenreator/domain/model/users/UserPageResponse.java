package boletoGenreator.domain.model.users;

import java.time.LocalDateTime;
import java.util.List;

import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.useCases.entity.contracts.EntityContracts;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPageResponse {
    private Long id;
    private String email;
    private String birthday;
    private List<UserIntegrity> integritys;
    private List<Contracts> contracts;

    @Getter
    @Setter
    @Builder
    public static class UserIntegrity{
        private String integrity;

        public static UserIntegrity from(EntityUserIntegrity integry){
            return UserIntegrity.builder()
            .integrity(integry.getStats())
            .build();
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Contracts{
        private Long idContract;
        private String typeContract;
        private LocalDateTime date;

        public static Contracts from(EntityContracts contract){
            return Contracts.builder()
            .idContract(contract.getId())
            .typeContract(contract.getTypeContract())
            .date(contract.getDateContract())
            .build();
        }
    }

    public static UserPageResponse from(EntityUser client, List<EntityUserIntegrity> integritys, List<EntityContracts> contracts){

        List<UserIntegrity> parsedIntegritys = integritys.stream()
        .map(i -> UserPageResponse.UserIntegrity.from(i))
        .toList();

        List<Contracts> parsedContracts = contracts.stream()
        .map(c -> UserPageResponse.Contracts.from(c))
        .toList();


        return UserPageResponse.builder()
        .id(client.getId())
        .email(client.getEmail())
        .birthday(ParseTime.parseTime(client.getBirthday()))
        .integritys(parsedIntegritys)
        .contracts(parsedContracts)
        .build();
    }
}
