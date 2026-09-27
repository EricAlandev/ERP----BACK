package boletoGenreator.useCases.service.users;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import boletoGenreator.infrastructure.repository.BankBilletsRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractBilletsRepository;
import boletoGenreator.infrastructure.repository.contracts.ContractRepository;
import boletoGenreator.infrastructure.repository.user.UserScoreRepository;
import boletoGenreator.useCases.UseCase;
import boletoGenreator.useCases.entity.EntityBankBillet;
import boletoGenreator.useCases.entity.contracts.EntityContracts;
import boletoGenreator.useCases.entity.user.EntityUserScore;
import jakarta.transaction.Transactional;
import lombok.Value;

public class UserLimitsUseCase implements UseCase<UserLimitsUseCase.InputValues, UserLimitsUseCase.OutPutValues> {
    
    private final UserScoreRepository userScoreRepository;
    private final ContractRepository contractRepository;
    private final ContractBilletsRepository contractBilletsRepository;
    private final BankBilletsRepository bankBilletsRepository;

    public UserLimitsUseCase(UserScoreRepository userScoreRepository, BankBilletsRepository bankBilletsRepository, ContractBilletsRepository contractBilletsRepository,  ContractRepository contractRepository){
        this.userScoreRepository = userScoreRepository;
        this.bankBilletsRepository = bankBilletsRepository;
        this.contractBilletsRepository = contractBilletsRepository;
        this.contractRepository = contractRepository;
    }

    @Override 
    @Transactional 
    public OutPutValues execute(InputValues input){

        Long idUser = input.getIdUser();
        
        EntityUserScore score = userScoreRepository.findUserScore(idUser)
        .orElseThrow(() -> new RuntimeException("user or score not found"));

        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);

        Boolean recalculateScore = (score.getLastChange().isBefore(monthAgo)) ? true : false;

        if(recalculateScore){
            CalculateClientScore(idUser, score);
        }

        //Need to make the rest of the logical. 
        //fetch the user and verify his salary
        //make the max Loan price being 30% of his salary
        //
        //Then, create the quantity installments;
        

        return new OutPutValues();
    }


    @Value 
    public static class InputValues implements  UseCase.InputValues{
        private Long idUser;
    }

    @Value 
    public static class OutPutValues implements  UseCase.OutPutValues{
        private BigDecimal maxLoanPrice;
        private Long quantityInstallments;
    }

    public void CalculateClientScore(Long idUser, EntityUserScore score){
         LocalDateTime timeCap = LocalDateTime.now().minusMonths(6);
            List<EntityContracts> contracts = contractRepository.findContractsWithCap(idUser, timeCap);

            if(contracts != null && contracts.size() > 0){
                List<Long> idContracts = new ArrayList<>();

                for(int i = 0; i < contracts.size(); i++){
                    idContracts.add(contracts.get(i).getId());
                }

                //find the Contract Billets to pick the bankBIllets;
                List<Long> bankBilletsIds = contractBilletsRepository.findByContractIds(idContracts);

                List<EntityBankBillet> bankBillets = bankBilletsRepository.findByIds(bankBilletsIds);

                Long quantityBB = Long.valueOf(bankBillets.size());
                Long payedOnTime = 0L;
                Long late = 0L;

                for(int i = 0; i < quantityBB; i++){
                    EntityBankBillet bankBillet = bankBillets.get(i);

                    if("P".equals(bankBillet.getStats())){
                        payedOnTime = payedOnTime + 1L;
                    }

                    else if("L".equals(bankBillet.getStats())){
                        late = late + 1L;
                    }

                    else{
                        throw new RuntimeException("Error during the verifycation of stats");
                    }
                }

                BigDecimal finalBalance = BigDecimal.valueOf(((payedOnTime / quantityBB)* 100)).setScale(2, RoundingMode.CEILING);

                String paymentSituation = null;

                if(finalBalance.compareTo(BigDecimal.valueOf(0F)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(60F)) < 0){
                    paymentSituation = "BC";
                }

                else if(finalBalance.compareTo(BigDecimal.valueOf(60F)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(80F)) < 0){
                    paymentSituation = "AC";
                }

                else if(finalBalance.compareTo(BigDecimal.valueOf(80F)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(100F)) <= 0){
                    paymentSituation = "GC";
                }

                if(paymentSituation == null){
                    throw new RuntimeException("Fail on the payment simulation calculation");
                }

                score.setLastChange(LocalDateTime.now()); 
                score.setPaymentSituation(paymentSituation);
                score.setScoreClient(finalBalance);
                
                userScoreRepository.save(score);
        
            }
    }

}
