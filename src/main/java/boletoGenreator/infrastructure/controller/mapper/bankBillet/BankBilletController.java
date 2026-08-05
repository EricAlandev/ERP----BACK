package boletoGenreator.infrastructure.controller.mapper.bankBillet;

import java.util.concurrent.CompletableFuture;

import boletoGenreator.infrastructure.controller.dto.boleto.BankBilletPickIntallmentsResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.bankBillet.ContractInstallmentsUseCase;

public class BankBilletController implements BankBilletResource{

    private final ContractInstallmentsUseCase  contractInstallmentsUseCase;

    public BankBilletController(ContractInstallmentsUseCase  contractInstallmentsUseCase){
        this.contractInstallmentsUseCase = contractInstallmentsUseCase;
    }

    @Override
    public CompletableFuture<BankBilletPickIntallmentsResponse> getInstallments(String idContract){

        return ServiceExecute.execute(
            contractInstallmentsUseCase,
            new ContractInstallmentsUseCase.InputValues(idContract),
            (output) -> BankBilletPickIntallmentsResponse.from(output.getPivodValue())
        );
    }
    
}
