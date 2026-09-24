package boletoGenreator.useCases.service.contracts;

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

        TaxesInstallments taxesInstallments = definePriceAndInstallments(price, typeContract, Vip);

        int QuantityInstallments = taxesInstallments.getQuantityInstallments();
        Long maxPriceAllowed = taxesInstallments.getMaxPriceAllowed();

        //clean the stats to the front-end
        List<String> statsToFront = cleanStats(userStats);

        return new OutPutValues(QuantityInstallments, Client, statsToFront, maxPriceAllowed); 
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        MakeContract contractData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        int QuantityInstallments;
        EntityUser clientData;
        List<String> statsToFront;
        Long maxPriceAllowed;
    }

    public TaxesInstallments definePriceAndInstallments(Long price, String typeContract, Boolean Vip){

        Long maxPrice = (Vip) ? 30000L : 2000L;
        int installments = 0;

        if(price <= 0){
            throw new RuntimeException("Price can't be lower or equal to 0");        
        }

        if ("LESS TAXES".equals(typeContract) && price > maxPrice) {
            throw new RuntimeException("The price excedes the cap");
        }
        
        if ("MORE TAXES".equals(typeContract) && price > maxPrice) {
            throw new RuntimeException("The price excedes the cap");
        }

        if (price < 50) { 
            installments = 1;
        } 

        else if (price >= 50 && price <= 300) {
            installments = 3;
        } 

        else if (price > 300 && price <= 1000) {
            installments = 7;
        } 
        else if (price > 1000 && price <= 5000) {
            installments = 10;
        } 
        else if (price > 5000 && price <= 10000) {
            installments = 12;
        } 
        else if (price > 10000) {
            installments = 12;
        }

        TaxesInstallments taxesInstallments = new TaxesInstallments();

        taxesInstallments.setMaxPriceAllowed(maxPrice);
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
