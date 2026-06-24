package boletoGenreator.useCases.service.contracts;

import java.math.BigDecimal;

import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.domain.model.contracts.TaxesInstallments;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import lombok.Value;

public class SimulationUseCase implements UseCase<SimulationUseCase.InputValues, SimulationUseCase.OutPutValues> {

    private final UserRepository userRepository;

    public SimulationUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public OutPutValues execute(InputValues input){

        EntityUser Client = userRepository.findById(Long.parseLong(input.getContractData().getIdClient()))
        .orElseThrow(() -> new RuntimeException("Client not found"));

        String userStats = Client.getIntegrity().getStats();

        Long price = Long.parseLong(input.getContractData().getPrice());
        String typeContract = input.getContractData().getBankBilletType();

        TaxesInstallments taxesInstallments = defineTaxesAndInstallments(price, typeContract ,userStats);

        BigDecimal taxes = taxesInstallments.getTaxes();
        int QuantityInstallments = taxesInstallments.getQuantityInstallments();

        //clean the stats to the front-end
        String statsToFront = cleanStats(userStats);

        return new OutPutValues(taxes, QuantityInstallments, Client, statsToFront, price); 
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        MakeContract contractData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        BigDecimal taxes;
        int QuantityInstallments;
        EntityUser clientData;
        String statsToFront;
        Long price;
    }

    public TaxesInstallments defineTaxesAndInstallments(Long price, String typeContract, String stats){

        BigDecimal taxes = BigDecimal.ZERO;
        int installments = 0;

        if(price <= 0){
            throw new RuntimeException("Price can't be lower or equal to 0");        
        }

        if ("LESS TAXES".equals(typeContract) && price > 10000 && !"VIP".equals(stats)) {
            throw new RuntimeException("Price exceeds the 10,000 cap for LESS TAXES contracts");
        }
        
        if ("MORE TAXES".equals(typeContract) && price > 200000 && !"VIP".equals(stats)) {
            throw new RuntimeException("Price exceeds the 200,000 cap for MORE TAXES contracts");
        }

        if (price < 50) { 
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(4.5);
            } else {
                taxes = BigDecimal.valueOf(5);
            }

            installments = 1;
        } 

        else if (price >= 50 && price <= 300) {
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(6.5);
            } else {
                taxes = BigDecimal.valueOf(7);
            }

            installments = 3;
        } 

        else if (price > 300 && price <= 1000) {
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(8);
            } else {
                taxes = BigDecimal.valueOf(9);
            }

            installments = 7;
        } 
        else if (price > 1000 && price <= 5000) {
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(11);
            } else {
                taxes = BigDecimal.valueOf(12);
            }

            installments = 10;
        } 
        else if (price > 5000 && price <= 10000) {
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(13.5);
            } else {
                taxes = BigDecimal.valueOf(15);
            }
            installments = 12;
        } 
        else if (price > 10000) {
            if ("VIP".equals(stats)) {
                taxes = BigDecimal.valueOf(15);
            } else {
                taxes = BigDecimal.valueOf(17);
            }
            installments = 12;
        }

        if("LESS TAXES".equals(typeContract)){
            taxes = taxes.subtract(BigDecimal.ONE);
        }

        TaxesInstallments taxesInstallments = new TaxesInstallments();

        taxesInstallments.setTaxes(taxes);
        taxesInstallments.setQuantityInstallments(installments);

        return taxesInstallments;
    }

    public String cleanStats(String stats){

        String cleanedStats;

        switch (stats) {
            case "VIP":
                cleanedStats = "VIP";
                break;

            case "COM":
                cleanedStats = "Common";
                break;
        
            default:
                cleanedStats = "Common";
                break;
        }   

        return cleanedStats;

    }
}
