package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.util.List;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.impl.user.UserCustomRepository;
import jakarta.transaction.Transactional;
import lombok.Value;

public class ReprintContractPdfUseCase implements UseCase<ReprintContractPdfUseCase.InputValues, ReprintContractPdfUseCase.OutPutValues> {

    private final UserCustomRepository userCustomRepository;

    public ReprintContractPdfUseCase(UserCustomRepository userCustomRepository){
        this.userCustomRepository = userCustomRepository;
    }
    
    @Transactional
    @Override
    public OutPutValues execute(InputValues input){

        List<ContractData> contractData = userCustomRepository.findContractData(Long.parseLong(input.getIdContract()));



        return new OutPutValues();
    }


    @Value
    public static class InputValues implements UseCase.InputValues{
        private String idContract;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        private byte[] pdf;
    }
}
