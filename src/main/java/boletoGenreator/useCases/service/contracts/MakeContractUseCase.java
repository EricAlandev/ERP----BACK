package boletoGenreator.useCases.service.contracts;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import boletoGenreator.domain.model.contracts.DealContract;
import boletoGenreator.infrastructure.repository.BankBilletsRepository;
import boletoGenreator.infrastructure.repository.UserIntegrityRepository;
import boletoGenreator.infrastructure.repository.UserRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractBilletsRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.EntityBankBillet;
import boletoGenreator.useCases.entity.contracts.EntityContractBillet;
import boletoGenreator.useCases.entity.contracts.EntityContracts;
import boletoGenreator.useCases.entity.user.EntityUser;
import boletoGenreator.useCases.entity.user.EntityUserIntegrity;
import lombok.Value;

public class MakeContractUseCase implements UseCase<MakeContractUseCase.InputValues, MakeContractUseCase.OutPutValues> {

    private final UserRepository userRepository;
    private final BankBilletsRepository bankBilletsRepository;
    private final UserIntegrityRepository userIntegrityRepository;
    private final ContractRepository contractRepository;
    private final ContractBilletsRepository contractBilletsRepository;

    public MakeContractUseCase(UserRepository userRepository, BankBilletsRepository bankBilletsRepository, UserIntegrityRepository userIntegrityRepository, ContractBilletsRepository contractBilletsRepository, ContractRepository contractRepository){
        this.userRepository = userRepository;
        this.bankBilletsRepository = bankBilletsRepository;
        this.userIntegrityRepository = userIntegrityRepository;
        this.contractBilletsRepository = contractBilletsRepository;
        this.contractRepository = contractRepository;
    }

    @Override
    public OutPutValues execute(InputValues input){
        
        DealContract contractData = input.getContratData();

        EntityUser clientUser = userRepository.findById(contractData.getIdClient())
        .orElseThrow(() -> new RuntimeException("User dosn't exist"));

        List<EntityUserIntegrity> integrity = userIntegrityRepository.findByUserByIntegrity(clientUser);
        Boolean vipClient = isVip(integrity);

        //Verify if the user is avaible to make a new contract;
        List<EntityContracts> contracts = searchContracts(clientUser);

        Boolean userCanLoan = clientAvaibleToLoan(contracts, vipClient);

        if(userCanLoan){
            //create the list to receive all of the banks and pivos;
            List<EntityBankBillet> bankBilletsList = new ArrayList<>();
            List<EntityContractBillet> pivoList = new ArrayList<>();

            //create contract
            EntityContracts contract = createContract(contractData, clientUser);

            //save bankBillets on DB;
            for(int i = 1; i <= contractData.getQuantityInstallments(); i++){
                EntityBankBillet bankBillet = buildBankBillet(contractData, i);
                bankBilletsList.add(bankBillet);
            }

            bankBilletsRepository.saveAll(bankBilletsList);
            
            //save pivo on DB;
            for(EntityBankBillet actualBankBillet : bankBilletsList){
                EntityContractBillet pivo = buildPivo(contract, actualBankBillet);
                pivoList.add(pivo);
            }

            contractBilletsRepository.saveAll(pivoList);
        }

        return new OutPutValues( "The user " + clientUser.getEmail() + " maked the contract");
    }

    @Value
    public static class InputValues implements UseCase.InputValues{
        DealContract contratData;
    }

    @Value
    public static class OutPutValues implements UseCase.OutPutValues{
        private String returnValue;
    }

    public List<EntityContracts> searchContracts(EntityUser client) throws RuntimeException{
        List<EntityContracts> contracts = client.getUserContracts();
        List<EntityContracts> loanContracts = new ArrayList<>();

        //pick loan contracts
        for(EntityContracts contract: contracts){
            if(contract.getTypeContract().equals(ContractProps.LT) 
                || contract.getTypeContract().equals(ContractProps.MT) 
              ){
                loanContracts.add(contract);
            }
        }

        return loanContracts;
    }

    public Boolean clientAvaibleToLoan(List<EntityContracts> contracts, Boolean isVip) throws RuntimeException{

        BigDecimal totalValue = BigDecimal.ZERO;
        
        //gonna verify the billets of each contract;
        for(int i = 0; i < contracts.size(); i++){
            List<EntityContractBillet> pivoBillets = contracts.get(i).getContractBilletPivo(); 

            //verifications
            if(pivoBillets.size() > 12){
                throw new RuntimeException("Client already have a recent loan");
            }

            for(int y = 0; y < pivoBillets.size(); y++){
                EntityBankBillet bankBillet = pivoBillets.get(y).getBankBillets();
                    totalValue = totalValue.add(bankBillet.getPrice());
                }

                if(!isVip && totalValue.compareTo(new BigDecimal(5000)) >= 0){
                    return false;
                }

                else if(isVip && totalValue.compareTo(new BigDecimal(25000)) >= 0){
                    return false;
                }
        }

        return true;
    }

    public Boolean isVip(List<EntityUserIntegrity> integritys){
        for(int i = 0; i < integritys.size(); i++){
            String actualIntegrity = integritys.get(i).getStats();

            if(ContractProps.VIP.equals(actualIntegrity)){
                return true;
            }
        }

        return false;
    }

    public EntityContracts createContract(DealContract contractData,EntityUser clientUser){
            EntityContracts contract = new EntityContracts();

            contract.setTypeContract(contractData.getBankBilletType());
            contract.setContractsUser(clientUser);
                
            contractRepository.save(contract);

            return contract;
    }

    public EntityBankBillet buildBankBillet(DealContract contractData, int i){
        EntityBankBillet bankBillet = new EntityBankBillet();

        LocalDateTime expirationDate = LocalDateTime.now();
        expirationDate = expirationDate.plusDays(30 * i);

        Timestamp parsedExpiredDate = Timestamp.valueOf(expirationDate);

        bankBillet.setExpirationDate(parsedExpiredDate);
        bankBillet.setPrice(contractData.getPriceInstallments());
        bankBillet.setStats(ContractProps.PENDING);
        bankBillet.setTypeContract(contractData.getBankBilletType());

        return bankBillet;
    }

    public EntityContractBillet buildPivo(EntityContracts contract, EntityBankBillet bankBillet){
        EntityContractBillet contractBillet = new EntityContractBillet();

        contractBillet.setBankBillets(bankBillet);
        contractBillet.setContracts(contract);

        return contractBillet;
    }
}
