package boletoGenreator.useCases.service.inAndOut;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import boletoGenreator.domain.model.InAndOut.RegisterData;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.infrastructure.repository.adress.AdressRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityAdress;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import jakarta.transaction.Transactional;
import lombok.Value;

@Service
public class RegisterUseCase implements UseCase<RegisterUseCase.InputValues, RegisterUseCase.OutPutValues>{

    private final UserRepository userRepository;
    private final UserIntegrityRepository userIntegrityRepository;
    private final AdressRepository adressRepository;

    public RegisterUseCase(UserRepository userRepository, UserIntegrityRepository userIntegrityRepository, AdressRepository adressRepository){
        this.userRepository = userRepository;
        this.userIntegrityRepository = userIntegrityRepository;
        this.adressRepository = adressRepository;
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

        EntityAdress adress = new EntityAdress();

        adress.setCep(input.getData().getCep());
        adress.setState(input.getData().getState());
        adress.setNeighborhood(input.getData().getNeighborhood());
        adress.setAdress(input.getData().getAdress());
        adress.setAdressNumber(Long.parseLong(input.getData().getAdressNumber()));

        adressRepository.save(adress);

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
