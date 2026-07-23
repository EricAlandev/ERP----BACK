package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.infrastructure.controller.dto.contract.SimulationResponse;
import boletoGenreator.infrastructure.controller.dto.pdfs.PdfResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.contracts.MakeContractUseCase;
import boletoGenreator.useCases.service.contracts.SimulationUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ContractsController implements ContractsResource{

    private final MakeContractUseCase makeContractUseCase;
    private final SimulationUseCase simulationUseCase;
    private final ContractPdfUseCase contractPdfUseCase;
 
    public ContractsController(MakeContractUseCase makeContractUseCase, SimulationUseCase simulationUseCase, ContractPdfUseCase contractPdfUseCase){
        this.makeContractUseCase = makeContractUseCase;
        this.simulationUseCase = simulationUseCase;
        this.contractPdfUseCase = contractPdfUseCase;
    }
    
    @Override
    public CompletableFuture<ResponseEntity<byte[]>> makeContract(DealContract contratData, String token){

        return ServiceExecute.execute(
            makeContractUseCase, 
            new MakeContractUseCase.InputValues(contratData, token), 
            (output) -> PdfResponse.from(output.getPdfBytes(), null)
        );
    }

    @Override
    public CompletableFuture<byte[]> contractPDF(DealContract contractPDFdata){

        return ServiceExecute.execute(
            contractPdfUseCase, 
            new ContractPdfUseCase.InputValues(contractPDFdata), 
            (output) -> output.getPdf()
        );
    }

    @Override
    public CompletableFuture<SimulationResponse> Simulation(MakeContract contratData){

        return ServiceExecute.execute(
            simulationUseCase, 
            new SimulationUseCase.InputValues(contratData), 
            (output) -> SimulationResponse.from(output.getTaxes(), output.getQuantityInstallments(), output.getClientData(), output.getStatsToFront(), output.getPrice(), output.getBankBilletType())
        );
    }
}
