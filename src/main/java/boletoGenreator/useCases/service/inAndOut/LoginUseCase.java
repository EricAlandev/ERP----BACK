package boletoGenreator.useCases.service.inAndOut;

import org.springframework.stereotype.Service;

import boletoGenreator.domain.model.InAndOut.LoginData;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.service.jwt.JwtAuthorization;
import lombok.Value;

@Service
public class LoginUseCase implements UseCase<LoginUseCase.InputValues, LoginUseCase.OutPutValues>{

    private final UserRepository userRepository;
     
    private final JwtAuthorization jwtAuthorization;

    public LoginUseCase(UserRepository userRepository, JwtAuthorization jwtAuthorization){
        this.userRepository = userRepository;
        this.jwtAuthorization = jwtAuthorization;
    }

    @Override
    public OutPutValues execute(InputValues input){

        String email = input.getData().getEmail().trim();
        String password = input.getData().getPassword().trim();

        System.out.println("DATA IS" + email + password);

        EntityUser user = userRepository.findByEmail(email)
        .orElseThrow(() -> new RuntimeException("Wrong email"));

        if(user.getEmail().isEmpty()){
            throw new RuntimeException("wrong email");
        }

        if(!password.equals(user.getPassword())){
            throw new RuntimeException("wrong password");
        }

        String token = jwtAuthorization.createToken(user.getId());

        user.setToken(token);

        userRepository.save(user);

        return new OutPutValues(user);
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        LoginData data;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        EntityUser user;
    }
}
