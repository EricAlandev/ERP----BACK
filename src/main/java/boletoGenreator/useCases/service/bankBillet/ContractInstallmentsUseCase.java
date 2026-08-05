package boletoGenreator.useCases.service.bankBillet;

import java.util.List;

import boletoGenreator.infrastructure.repository.contracts.ContractBilletsRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.contracts.EntityContractBillet;
import boletoGenreator.useCases.entity.contracts.EntityContracts;
import jakarta.transaction.Transactional;
import lombok.Value;

public class ContractInstallmentsUseCase implements UseCase<ContractInstallmentsUseCase.InputValues, ContractInstallmentsUseCase.OutPutValues> {

    private final ContractRepository contractRepository;
    private final ContractBilletsRepository contractBilletsRepository;

    public ContractInstallmentsUseCase(ContractRepository contractRepository, ContractBilletsRepository contractBilletsRepository){
        this.contractRepository = contractRepository;
        this.contractBilletsRepository = contractBilletsRepository;
    }

    @Override
    @Transactional
    public OutPutValues execute(InputValues input){

        EntityContracts contract = contractRepository.findById(Long.parseLong(input.getIdContract()))
        .orElseThrow(() -> new RuntimeException("Contract not found"));

        List<EntityContractBillet> pivoValues = contractBilletsRepository.findByContracts(contract);

        return new OutPutValues(pivoValues);
    }


    @Value
    public static class InputValues implements UseCase.InputValues{
        private String idContract;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        private List<EntityContractBillet> pivodValue;
    }
}
