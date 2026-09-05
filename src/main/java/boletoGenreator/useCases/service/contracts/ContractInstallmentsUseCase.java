package boletoGenreator.useCases.service.contracts;
import java.util.List;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.impl.user.UserCustomRepository;
import lombok.Value;

public class ContractInstallmentsUseCase implements
UseCase<ContractInstallmentsUseCase.InputValues, ContractInstallmentsUseCase.OutPutValues> {

    private final UserCustomRepository userCustomRepository;

    public ContractInstallmentsUseCase (UserCustomRepository userCustomRepository){
        this.userCustomRepository = userCustomRepository;
    }
    
    @Override
    public OutPutValues execute(InputValues input){
        
        List<ContractData.BankBillet> bankBillets = userCustomRepository.findInstallments(Long.parseLong(input.getIdContract()));

        return new OutPutValues(bankBillets);
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        public String idContract;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
      List<ContractData.BankBillet> installments;
    }
}
