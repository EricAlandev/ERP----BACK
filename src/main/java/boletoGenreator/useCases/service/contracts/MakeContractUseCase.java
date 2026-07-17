package boletoGenreator.useCases.service.contracts;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
        System.out.println(input);
        DealContract contractData = input.getContratData();

        EntityUser clientUser = userRepository.findById(contractData.getIdClient())
        .orElseThrow(() -> new RuntimeException("User dosn't exist"));

        List<EntityBankBillet> bankBillets = searchBankBillets(clientUser);

        clientAvaibleToLoan(bankBillets);

        for(int i = 1; i <= contractData.getQuantityInstallments(); i++){

            EntityBankBillet bankBillet = new EntityBankBillet();

            LocalDateTime expirationDate = LocalDateTime.now();
            expirationDate = expirationDate.plusDays(30 * i);

            Timestamp parsedExpiredDate = Timestamp.valueOf(expirationDate);

            bankBillet.setExpirationDate(parsedExpiredDate);
            bankBillet.setPrice(contractData.getPriceInstallments());
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

    public List<EntityBankBillet> searchBankBillets(EntityUser client) throws RuntimeException{
        List<EntityBankBillet> bankBillets = bankBilletsRepository.findByUser(client);

        if(bankBillets.size() > 12){
            throw new RuntimeException("Client already have a new loan");
        }

        return bankBillets;
    }

    public List<EntityBankBillet> clientAvaibleToLoan(List<EntityBankBillet> bankBillets) throws RuntimeException{

        if(bankBillets.size() > 12){
            throw new RuntimeException("Client already have a new loan");
        }

        return bankBillets;
    }
}
