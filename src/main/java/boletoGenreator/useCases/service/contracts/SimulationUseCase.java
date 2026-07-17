package boletoGenreator.useCases.service.contracts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import boletoGenreator.domain.model.contracts.MakeContract;
import boletoGenreator.domain.model.contracts.TaxesInstallments;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
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

        List<EntityUserIntegrity> userStats = Client.getIntegrity();
        Boolean Vip = verifyVip(userStats);

        Long price = Long.parseLong(input.getContractData().getPrice());
        String typeContract = input.getContractData().getBankBilletType();

        TaxesInstallments taxesInstallments = defineTaxesAndInstallments(price, typeContract, Vip);

        BigDecimal taxes = taxesInstallments.getTaxes();
        int QuantityInstallments = taxesInstallments.getQuantityInstallments();

        //clean the stats to the front-end
        List<String> statsToFront = cleanStats(userStats);

        return new OutPutValues(taxes, QuantityInstallments, Client, statsToFront, price, input.getContractData().getBankBilletType()); 
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
        List<String> statsToFront;
        Long price;
        String bankBilletType;
    }

    public TaxesInstallments defineTaxesAndInstallments(Long price, String typeContract, Boolean Vip){

        BigDecimal taxes = BigDecimal.ZERO;
        int installments = 0;

        if(price <= 0){
            throw new RuntimeException("Price can't be lower or equal to 0");        
        }

        if ("LESS TAXES".equals(typeContract) && price > 10000 && !Vip) {
            throw new RuntimeException("Price exceeds the 10,000 cap for LESS TAXES contracts");
        }
        
        if ("MORE TAXES".equals(typeContract) && price > 200000 && !Vip) {
            throw new RuntimeException("Price exceeds the 200,000 cap for MORE TAXES contracts");
        }

        if (price < 50) { 
            if (!Vip) {
                taxes = BigDecimal.valueOf(4.5);
            } else {
                taxes = BigDecimal.valueOf(5);
            }

            installments = 1;
        } 

        else if (price >= 50 && price <= 300) {
            if (!Vip) {
                taxes = BigDecimal.valueOf(6.5);
            } else {
                taxes = BigDecimal.valueOf(7);
            }

            installments = 3;
        } 

        else if (price > 300 && price <= 1000) {
            if (!Vip) {
                taxes = BigDecimal.valueOf(8);
            } else {
                taxes = BigDecimal.valueOf(9);
            }

            installments = 7;
        } 
        else if (price > 1000 && price <= 5000) {
            if (!Vip) {
                taxes = BigDecimal.valueOf(11);
            } else {
                taxes = BigDecimal.valueOf(12);
            }

            installments = 10;
        } 
        else if (price > 5000 && price <= 10000) {
            if (!Vip) {
                taxes = BigDecimal.valueOf(13.5);
            } else {
                taxes = BigDecimal.valueOf(15);
            }
            installments = 12;
        } 
        else if (price > 10000) {
            if (!Vip) {
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

    public List<String> cleanStats(List<EntityUserIntegrity> stats){

        List<String> userStats = new ArrayList<>();

        for(int i = 0; i < stats.size(); i++){
            String actualStats = stats.get(i).getStats();
            String parsedStats = cleanActualState(actualStats);
            userStats.add(parsedStats);
        }

        return userStats;
    }

    public String cleanActualState (String stat){
            switch (stat) {
                case "VIP":
                    return "VIP";

                case "COM":
                    return "Common";
            
                default:
                    return "Common";
            }   
    }

    public Boolean verifyVip(List<EntityUserIntegrity> stats){
        for(int i = 0; i < stats.size(); i++){
            if(stats.get(i).getStats() == "VIP"){
                return true;
            }
        }
        
        return false;
    }
}
