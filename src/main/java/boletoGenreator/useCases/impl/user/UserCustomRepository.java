package boletoGenreator.useCases.impl.user;

import java.util.List;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.domain.model.users.UserSearch;

public interface UserCustomRepository {
    
    List<UserSearch> findUsers(UserSearch userSearch); 
    
    List<ContractData> findContractData(Long id);

    List<ContractData.BankBillet> findInstallments(Long id);
}
