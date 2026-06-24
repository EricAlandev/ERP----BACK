package boletoGenreator.useCases.service.inAndOut;

import org.springframework.stereotype.Service;

import boletoGenreator.domain.model.InAndOut.RegisterData;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import lombok.Value;

@Service
public class RegisterUseCase implements UseCase<RegisterUseCase.InputValues, RegisterUseCase.OutPutValues>{

    private final UserRepository userRepository;
    private final UserIntegrityRepository userIntegrityRepository;

    public RegisterUseCase(UserRepository userRepository, UserIntegrityRepository userIntegrityRepository){
        this.userRepository = userRepository;
        this.userIntegrityRepository = userIntegrityRepository;
    }

    @Override
    public OutPutValues execute(InputValues input){

        String email = input.getData().getEmail();
        String password = input.getData().getPassword();
        String birthday = input.getData().getBirthday();

        EntityUser user = new EntityUser();
        user.setEmail(email);
        user.setPassword(password);
        user.setBirthday(birthday);

        userRepository.save(user);

        EntityUserIntegrity integrity = new EntityUserIntegrity();

        integrity.setStats("COM");
        integrity.setUserByIntegrity(user);

        userIntegrityRepository.save(integrity);

        return new OutPutValues("user saved");
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        RegisterData data;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        String message;
    }
}
