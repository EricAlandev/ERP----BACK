package boletoGenreator.infrastructure.controller.mapper.InAndOut;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.InAndOut.LoginData;
import boletoGenreator.domain.model.InAndOut.LoginResponse;
import boletoGenreator.infrastructure.controller.dto.inAndOut.LoginDTO;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.inAndOut.LoginUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class LoginController implements LoginResource {

    private LoginUseCase loginUseCase;

    public LoginController(LoginUseCase loginUseCase){
        this.loginUseCase = loginUseCase;
    }

    @Override
    public CompletableFuture<LoginDTO> login(LoginData data){
        return ServiceExecute.execute(
            loginUseCase, 
            new LoginUseCase.InputValues(data), 
            (out) -> LoginResponse.from(out.getUser())
        );
    }
}