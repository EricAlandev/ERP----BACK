package boletoGenreator.domain.model.contracts;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MakeContractResponse {
    
    private String message;
    private Long idContract;


    public static MakeContractResponse from(String message, Long idContract){

        return MakeContractResponse.builder()
        .message(message)
        .idContract(idContract)
        .build();
    }
}
