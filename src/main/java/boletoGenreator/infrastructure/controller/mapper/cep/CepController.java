package boletoGenreator.infrastructure.controller.mapper.cep;

import java.util.concurrent.CompletableFuture;

import boletoGenreator.domain.model.cep.CepRespondeDTO;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.cep.FindCepUseCase;

public class CepController implements CepResource {

    private final FindCepUseCase findCepUseCase;

    public CepController(FindCepUseCase findCepUseCase){
        this.findCepUseCase = findCepUseCase;
    }
    
    @Override 
    public CompletableFuture<CepRespondeDTO> findCEP(String cepNumber){

        return ServiceExecute.execute(
            findCepUseCase, 
            new FindCepUseCase.InputValues(cepNumber), 
            (output) -> output.getCepResponse()
        );
    }
}
