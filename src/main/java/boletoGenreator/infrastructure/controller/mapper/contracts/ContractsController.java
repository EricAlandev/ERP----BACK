package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.util.concurrent.CompletableFuture;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.infrastructure.controller.dto.contract.SimulationResponse;
import boletoGenreator.useCases.ServiceExecute;
import boletoGenreator.useCases.service.contracts.MakeContractUseCase;
import boletoGenreator.useCases.service.contracts.SimulationUseCase;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
public class ContractsController implements ContractsResource{

    private final MakeContractUseCase makeContractUseCase;
    private final SimulationUseCase simulationUseCase;

    public ContractsController(MakeContractUseCase makeContractUseCase, SimulationUseCase simulationUseCase){
        this.makeContractUseCase = makeContractUseCase;
        this.simulationUseCase = simulationUseCase;
    }
    
    @Override
    public CompletableFuture<String> makeContract(DealContract contratData){

        return ServiceExecute.execute(
            makeContractUseCase, 
            new MakeContractUseCase.InputValues(contratData), 
            (output) -> output.getReturnValue()
        );
    }

    @Override
    public CompletableFuture<SimulationResponse> Simulation(MakeContract contratData){

        return ServiceExecute.execute(
            simulationUseCase, 
            new SimulationUseCase.InputValues(contratData), 
            (output) -> SimulationResponse.from(output.getTaxes(), output.getQuantityInstallments(), output.getClientData(), output.getStatsToFront(), output.getPrice())
        );
    }
}
