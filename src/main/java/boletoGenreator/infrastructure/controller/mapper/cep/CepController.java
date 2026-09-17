package boletoGenreator.infrastructure.controller.mapper.cep;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.infrastructure.controller.dto.cep.CepResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.cep.FindCepUseCase;

@RestController  
@CrossOrigin (origins = "http://localhost:5173/")
public class CepController implements CepResource {

    private final FindCepUseCase findCepUseCase;

    public CepController(FindCepUseCase findCepUseCase){
        this.findCepUseCase = findCepUseCase;
    }
    
    @Override 
    public CompletableFuture<CepResponse> findCEP(String cepNumber){

        return ServiceExecute.execute(
            findCepUseCase, 
            new FindCepUseCase.InputValues(cepNumber), 
            (output) -> CepResponse.from(output.getCepResponse())
        );
    }
}
