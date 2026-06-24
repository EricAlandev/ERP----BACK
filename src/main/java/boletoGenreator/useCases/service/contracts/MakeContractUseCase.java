package boletoGenreator.useCases.service.contracts;

import java.time.LocalDateTime;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.infrastructure.repository.BankBilletsRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.EntityBankBillet;
import boletoGenreator.useCases.entity.user.EntityUser;
import lombok.Value;

public class MakeContractUseCase implements UseCase<MakeContractUseCase.InputValues, MakeContractUseCase.OutPutValues> {

    private final UserRepository userRepository;
    private final BankBilletsRepository bankBilletsRepository;

    public MakeContractUseCase(UserRepository userRepository, BankBilletsRepository bankBilletsRepository){
        this.userRepository = userRepository;
        this.bankBilletsRepository = bankBilletsRepository;
    }

    @Override
    public OutPutValues execute(InputValues input){

        DealContract contractData = input.getContratData();

        EntityUser clientUser = userRepository.findById(Long.parseLong(contractData.getIdClient()))
        .orElseThrow(() -> new RuntimeException("User dosn't exist"));

        for(int i = 1; i <= contractData.getQuantityInstallments(); i++){

            EntityBankBillet bankBillet = new EntityBankBillet();

            LocalDateTime expirationDate = LocalDateTime.now();
            expirationDate = expirationDate.plusDays(30 * i);

            bankBillet.setExpirationDate(expirationDate);
            bankBillet.setPrice(Long.parseLong(contractData.getPrice()));
            bankBillet.setStats("PD");
            bankBillet.setTypeContract(contractData.getBankBilletType());
            bankBillet.setUser(clientUser);

            bankBilletsRepository.save(bankBillet);
        }

        return new OutPutValues("The user " + clientUser.getEmail() + " maked the contract");
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        DealContract contratData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        private String returnValue;
    }
}
