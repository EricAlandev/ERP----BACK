package boletoGenreator.useCases.service.inAndOut;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import boletoGenreator.domain.model.InAndOut.RegisterData;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import jakarta.transaction.Transactional;
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
    @Transactional
    public OutPutValues execute(InputValues input){

        String email = input.getData().getEmail();
        String password = input.getData().getPassword();
        String birthday = input.getData().getBirthday();

        EntityUser user = new EntityUser();
        user.setEmail(email);
        user.setPassword(password);

        LocalDateTime now = ParseTime.parseTolocal(birthday);
        user.setBirthday(now);

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
