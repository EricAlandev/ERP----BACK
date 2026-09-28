package boletoGenreator.useCases.service.users;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import boletoGenreator.domain.model.limits.PreLimitsDTO;
import boletoGenreator.domain.model.users.UserLimitsDTO;
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
        
        UserLimitsDTO limitsDTO = userScoreRepository.findUserScore(idUser)
        .orElseThrow(() -> new RuntimeException("user or score not found"));

        LocalDateTime monthAgo = LocalDateTime.now().minusMonths(1);

        Boolean recalculateScore = limitsDTO.getScore().getLastChange().isBefore(monthAgo);

        if(recalculateScore){
            try {
                limitsDTO.setScore(CalculateClientScore(idUser, limitsDTO));
            } catch (Exception e) {
                throw new RuntimeException("exception on the limitsUseCase " + e.getMessage());
            } 
        }

        EntityUserScore score = limitsDTO.getScore();
        String paymentSituation = score.getPaymentSituation();

        PreLimitsDTO limits = calculateLoanAndInstallments(paymentSituation, limitsDTO.getSalary());

        return new OutPutValues(limits);
    }


    @Value 
    public static class InputValues implements  UseCase.InputValues{
        private Long idUser;
    }

    @Value 
    public static class OutPutValues implements  UseCase.OutPutValues{
        private PreLimitsDTO dto;
    }

    public EntityUserScore CalculateClientScore(Long idUser, UserLimitsDTO score) throws Exception{
         try {
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

                if(finalBalance.compareTo(BigDecimal.valueOf(0L)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(60L)) < 0){
                    paymentSituation = "BC";
                }

                else if(finalBalance.compareTo(BigDecimal.valueOf(60L)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(80L)) < 0){
                    paymentSituation = "AC";
                }

                else if(finalBalance.compareTo(BigDecimal.valueOf(80L)) >= 0 && finalBalance.compareTo(BigDecimal.valueOf(100L)) <= 0){
                    paymentSituation = "GC";
                }

                if(paymentSituation == null){
                    throw new RuntimeException("Fail on the payment simulation calculation");
                }

                EntityUserScore scoreEntity = score.getScore();

                scoreEntity.setLastChange(LocalDateTime.now()); 
                scoreEntity.setPaymentSituation(paymentSituation);
                scoreEntity.setScoreClient(finalBalance);
                
                userScoreRepository.save(scoreEntity);

                return scoreEntity;
            }

            throw new RuntimeException("user dosn't ");
         } catch (Exception e) {
            throw new Exception(e.getMessage());
         }
    }

    public PreLimitsDTO calculateLoanAndInstallments (String paymentSituation, BigDecimal salary){
        BigDecimal percentLoan = BigDecimal.ZERO;

        try {
            switch (paymentSituation) {
                case "NC":
                    percentLoan = BigDecimal.valueOf(0.10);
                    break;

                case "GC":
                    percentLoan = BigDecimal.valueOf(0.30);
                    break;

                case "BC":
                    percentLoan = BigDecimal.valueOf(0);
                    break;

                case "AC":
                    percentLoan = BigDecimal.valueOf(0.18);
                    break;
            
                default:
                    percentLoan = BigDecimal.valueOf(0);
                    break;
            }

            if(BigDecimal.ZERO.compareTo(percentLoan) == 0){
                throw new RuntimeException("User not allowed to make loans");
            }

            BigDecimal maxLoan = salary.add(salary.multiply(percentLoan));

            Long quantityInstallments = 2L;

            if(!"BC".equals(paymentSituation)){
                if(BigDecimal.valueOf(2000).compareTo(maxLoan) >= 0){
                    quantityInstallments = 12L;
                }

                else if(BigDecimal.valueOf(8000).compareTo(maxLoan) >= 0){
                    quantityInstallments = 24L;
                }
            }

            PreLimitsDTO limitsData = new PreLimitsDTO();
            limitsData.setMaxLoan(maxLoan);
            limitsData.setQuantityInstallments(quantityInstallments);

            return limitsData;
            

        } catch (Exception e) {
           throw new RuntimeException(e.getMessage());
        }
    }
}
