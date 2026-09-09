package boletoGenreator.infrastructure.controller.mapper.contracts;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.domain.model.contracts.MakeContractResponse;
import boletoGenreator.infrastructure.controller.dto.contract.SimulationResponse;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@CrossOrigin(origins = "http://localhost:5173/")
@RequestMapping(ContractsEndpoints.CONTRACT) 
public interface ContractsResource {
    
    @PostMapping(ContractsEndpoints.DEAL)
    public CompletableFuture<MakeContractResponse> makeContract(@RequestBody DealContract contratData);

    @PostMapping(ContractsEndpoints.CONTRACTPDF)
    public CompletableFuture<ResponseEntity<byte[]>> contractPDF(@PathVariable("id") String idContract);

    @PostMapping(ContractsEndpoints.SIMULATION)
    public CompletableFuture<SimulationResponse>Simulation(@RequestBody MakeContract contratData);

    @GetMapping(ContractsEndpoints.INSTALLMENTS)
    public CompletableFuture<List<ContractData.BankBillet>> InstallmentsContract(@PathVariable("id") String idContract);
    
    @GetMapping(ContractsEndpoints.INSTALLMENT_PDF)
    public CompletableFuture<ResponseEntity<byte[]>> installmentPdf(@PathVariable("id") String idInstallment);
    
}
