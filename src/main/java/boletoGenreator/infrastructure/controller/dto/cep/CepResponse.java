package boletoGenreator.infrastructure.controller.dto.cep;

import boletoGenreator.domain.model.cep.CepRespondeDTO;
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
public class CepResponse {
    private String cep;
    private String neighborhood;
    private String state;
    private String adress;

    public static CepResponse from(CepRespondeDTO apiResponse){
        return CepResponse.builder()
        .cep(apiResponse.getCep())
        .neighborhood(apiResponse.getBairro())
        .state(apiResponse.getLocalidade())
        .adress(apiResponse.getLogradouro())
        .build();
    }
}
