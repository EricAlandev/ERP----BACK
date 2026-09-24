package boletoGenreator.infrastructure.controller.mapper.contracts;

import boletoGenreator.useCases.service.jwt.Authentication;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.domain.model.contracts.MakeContractResponse;
import boletoGenreator.infrastructure.controller.dto.contract.SimulationResponse;
import boletoGenreator.infrastructure.controller.dto.pdfs.PdfResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.contracts.ContractInstallmentsUseCase;
import boletoGenreator.useCases.service.contracts.InstallmentPdfUseCase;
import boletoGenreator.useCases.service.contracts.MakeContractUseCase;
import boletoGenreator.useCases.service.contracts.SimulationUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ContractsController implements ContractsResource{

    private final MakeContractUseCase makeContractUseCase;
    private final SimulationUseCase simulationUseCase;
    private final ContractPdfUseCase contractPdfUseCase;
    private final ContractInstallmentsUseCase contractInstallmentsUseCase;
    private final InstallmentPdfUseCase installmentPdfUseCase;

    public ContractsController(MakeContractUseCase makeContractUseCase, SimulationUseCase simulationUseCase, ContractPdfUseCase contractPdfUseCase, Authentication authentication,  ContractInstallmentsUseCase contractInstallmentsUseCase, InstallmentPdfUseCase installmentPdfUseCase){
        this.makeContractUseCase = makeContractUseCase;
        this.simulationUseCase = simulationUseCase;
        this.contractPdfUseCase = contractPdfUseCase;
        this.contractInstallmentsUseCase = contractInstallmentsUseCase;
        this.installmentPdfUseCase = installmentPdfUseCase;
    }
    
    @Override
    public CompletableFuture<MakeContractResponse> makeContract(DealContract contratData){

        return ServiceExecute.execute(
            makeContractUseCase, 
            new MakeContractUseCase.InputValues(contratData), 
            (output) -> MakeContractResponse.from(output.getMessage(), output.getIdContract())
        );
    }

    @Override
    public CompletableFuture<ResponseEntity<byte[]>> contractPDF(String idContract){

        return ServiceExecute.execute(
            contractPdfUseCase, 
            new ContractPdfUseCase.InputValues(idContract), 
            (output) -> PdfResponse.from(output.getPdf(), null)
        );
    }

    @Override
    public CompletableFuture<SimulationResponse> Simulation(MakeContract contratData){

        return ServiceExecute.execute(
            simulationUseCase, 
            new SimulationUseCase.InputValues(contratData), 
            (output) -> SimulationResponse.from(output.getQuantityInstallments(), output.getClientData(), output.getStatsToFront(), output.getMaxPriceAllowed())
        );
    }

    @Override
    public CompletableFuture<List<ContractData.BankBillet>> InstallmentsContract(String idContract){

        return ServiceExecute.execute(
            contractInstallmentsUseCase, 
            new ContractInstallmentsUseCase.InputValues(idContract), 
            (output) -> output.getInstallments()
        );
    }

    @Override
    public CompletableFuture<ResponseEntity<byte[]>> installmentPdf(String idContract){

        return ServiceExecute.execute(
            installmentPdfUseCase, 
            new InstallmentPdfUseCase.InputValues(idContract), 
            (output) -> PdfResponse.from(output.getReturnV(), null)
        );
    }
}
