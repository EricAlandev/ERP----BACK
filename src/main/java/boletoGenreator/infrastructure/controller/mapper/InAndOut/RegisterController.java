package boletoGenreator.infrastructure.controller.mapper.InAndOut;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.InAndOut.RegisterData;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.inAndOut.RegisterUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class RegisterController implements RegisterResource {

    private RegisterUseCase registerUseCase;

    public RegisterController(RegisterUseCase registerUseCase){
        this.registerUseCase = registerUseCase;
    }

    @Override
    public CompletableFuture<String> register(RegisterData data){
        
        return ServiceExecute.execute(registerUseCase,
        new RegisterUseCase.InputValues(data),
        (output) -> output.getMessage());
    };
    
}
