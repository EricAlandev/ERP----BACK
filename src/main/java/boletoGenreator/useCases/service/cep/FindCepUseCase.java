package boletoGenreator.useCases.service.cep;

import org.springframework.web.client.RestClient;

import boletoGenreator.domain.model.cep.CepRespondeDTO;
import boletoGenreator.infrastructure.controller.mapper.cep.CepEndpoints;
import boletoGenreator.useCases.UseCase;
import lombok.Value;

public class FindCepUseCase implements UseCase<FindCepUseCase.InputValues, FindCepUseCase.OutPutValues> {

    
    private final RestClient restClient;

    public FindCepUseCase(RestClient restClient){
        this.restClient = restClient;
    }

    @Override 
    public OutPutValues execute(InputValues input){

        String cepEndpoint = CepEndpoints.CEP_CALL + input.getCepNumber() + "/json";

        CepRespondeDTO response = restClient.get()
        .uri(cepEndpoint)
        .retrieve()
        .body(CepRespondeDTO.class);
        
        return new OutPutValues(response);
    }

    @Value 
    public static class InputValues implements UseCase.InputValues{
        public String cepNumber;
    }

    @Value 
    public static class OutPutValues implements UseCase.OutPutValues{
        public CepRespondeDTO cepResponse;
    }
}
