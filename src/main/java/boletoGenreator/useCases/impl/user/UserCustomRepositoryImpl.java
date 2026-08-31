package boletoGenreator.useCases.impl.user;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.reflect.TypeToken;

import boletoGenreator.domain.model.contracts.ContractData;
import boletoGenreator.domain.model.users.UserSearch;
import ch.qos.logback.core.util.StringUtil;

public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserCustomRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    //SQLS
    private static final String FIND_USER_CONTRACT_DATA = "select u.id, u.email, " + 
                "c.id as id_contract, " + 
                "c.typecontract as typecontract, " + 
                "c.datecontract as expirationdate, " + 
                "jsonb_agg(to_jsonb(bb.*)) as bankBillets " +
                "from contracts c " + 
                "left join users u on c.user_id = u.id " + 
                "left join contractbillets cb on cb.contract_id = c.id " +
                "left join bankbillets bb on bb.id = cb.bankbillet_id " +
                "where c.id = ? " +
                "group by u.id, " + 
                "u.email," +
                "c.id, " +
                "c.typecontract, " +
                "c.datecontract";

    @Override
    public List<UserSearch> findUsers(UserSearch userSearch){

        StringBuilder sql = new StringBuilder("");
        List<Object> params = new ArrayList<>();

        if(!userSearch.getEmail().equals("") || !userSearch.getIdUser().equals("")){
            sql.append("SELECT * FROM  USERS WHERE 1 = 1");
        }

        //make the querys
        if(StringUtil.notNullNorEmpty(userSearch.getEmail())){
            sql.append(" AND EMAIL LIKE %?% ");
            params.add(userSearch.getEmail());
        }

        if(StringUtil.notNullNorEmpty(userSearch.getIdUser())){
            sql.append(" AND id = ? ");
            params.add(Long.parseLong(userSearch.getIdUser()));
        }
        
        return jdbcTemplate.query(
            sql.toString(), 
            (rs, rowNum) -> UserSearch.builder()
            .IdUser(String.valueOf(rs.getLong("id")))
            .Email(rs.getString("email"))
            .build(),
            params.toArray()
        );
    }

    @Override
    public List<ContractData> findContractData(Long id){

        StringBuilder sql = new StringBuilder();
        List<Object> params = new ArrayList<>();

        sql.append(UserCustomRepositoryImpl.FIND_USER_CONTRACT_DATA);
        params.add(id);

        System.out.println("SQL " + sql + " id Contract: " + id);

        return jdbcTemplate.query(
            sql.toString(),
            (rs, rowNum) -> ContractData.builder()
            .idClient(rs.getLong("id"))
            .nameClient(rs.getString("email"))
            .idContract(rs.getLong("id_contract"))
            .typeContract(rs.getString("typeContract"))
            .datecontract(rs.getTimestamp("expirationdate"))
            .bankBillets(organizeBankBillet(rs.getString("bankBillets")))
            .build(), 
            params.toArray()
        );
    }


    public List<ContractData.BankBillet> organizeBankBillet(String array){

        if(array == null || array.isEmpty() || "[null]".equals(array)){
            List<ContractData.BankBillet> emptyArray =  new ArrayList<>();
            return emptyArray;
        }

        Gson objectMapper = new GsonBuilder()
        .registerTypeAdapter( LocalDateTime.class, (JsonDeserializer<LocalDateTime>)
            (json, type, context) -> LocalDateTime.parse(json.getAsString()))
        .create();

        try {
            Type listType = new TypeToken<List<ContractData.BankBillet>>() {}.getType();

            return objectMapper.fromJson(array, listType);
            
        } catch (Exception e) {
            throw new RuntimeException("Fail to pick the bank Billets of the contract;");
        }
    } 
}
