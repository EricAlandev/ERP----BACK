package boletoGenreator.useCases.service.inAndOut;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import boletoGenreator.domain.model.InAndOut.RegisterData;
import boletoGenreator.infrastructure.controller.dto.generic.ParseTime;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.adress.AdressRepository;
import boletoGenreator.infrastructure.repository.user.UserRepository;
import boletoGenreator.infrastructure.repository.user.UserScoreRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityAdress;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import boletoGenreator.useCases.entity.user.EntityUserScore;
import boletoGenreator.useCases.service.Text.TextFunctions;
import ch.qos.logback.core.util.StringUtil;
import jakarta.transaction.Transactional;
import lombok.Value;

@Service
public class RegisterUseCase implements UseCase<RegisterUseCase.InputValues, RegisterUseCase.OutPutValues>{

    private final UserRepository userRepository;
    private final UserIntegrityRepository userIntegrityRepository;
    private final AdressRepository adressRepository;
    private final UserScoreRepository userScoreRepository;

    public RegisterUseCase(UserRepository userRepository, UserIntegrityRepository userIntegrityRepository, AdressRepository adressRepository, UserScoreRepository userScoreRepository){
        this.userRepository = userRepository;
        this.userIntegrityRepository = userIntegrityRepository;
        this.adressRepository = adressRepository;
        this.userScoreRepository = userScoreRepository;
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
        user.setTypeUser(input.getData().getTypeUser());
        user.setCic(TextFunctions.formatCic(input.getData().getCic()));
        defineGender(user, input);

        userRepository.save(user);

        EntityAdress adress = new EntityAdress();
        RegisterData.AdressData adressData = input.getData().getAdressData();

        adress.setCep(TextFunctions.formatCic(adressData.getCep()));
        adress.setState(adressData.getState());
        adress.setNeighborhood(adressData.getNeighborhood());
        adress.setAdress(adressData.getAdress());
        adress.setAdressNumber(Long.valueOf(adressData.getAdressNumber()));
        adress.setUserAdress(user);

        adressRepository.save(adress);

        EntityUserIntegrity integrity = new EntityUserIntegrity();

        integrity.setStats("COM");
        integrity.setUserByIntegrity(user);

        userIntegrityRepository.save(integrity);

        EntityUserScore score = new EntityUserScore();
        score.setScoreClient(BigDecimal.valueOf(100L));
        score.setPaymentSituation("NC");
        score.setLastChange(LocalDateTime.now());

        userScoreRepository.save(score);

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

    public void defineGender(EntityUser user, InputValues input) throws RuntimeException{
        if("C".equals(input.getData().getTypeUser())){
            if(StringUtil.isNullOrEmpty(input.getData().getGender())){
                user.setGender("N");
            }
        }

        else{
            user.setGender(input.getData().getGender());
        }

        //final verification
        if(StringUtil.isNullOrEmpty(user.getGender())){
            throw new RuntimeException("Gender Error");
        }
    }
}
