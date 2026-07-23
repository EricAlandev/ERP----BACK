package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.infrastructure.controller.dto.contract.SimulationResponse;

@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(ContractsEndpoints.CONTRACT) 
public interface ContractsResource {
    
    @PostMapping(ContractsEndpoints.DEAL)
    public CompletableFuture<ResponseEntity<byte[]>> makeContract(@RequestBody DealContract contratData, @RequestHeader("Authorization") String token);

    @PostMapping(ContractsEndpoints.CONTRACTPDF)
    public CompletableFuture<byte[]> contractPDF(@RequestBody DealContract contractPDFdata);

    @PostMapping(ContractsEndpoints.SIMULATION)
    public CompletableFuture<SimulationResponse>Simulation(@RequestBody MakeContract contratData);
}
