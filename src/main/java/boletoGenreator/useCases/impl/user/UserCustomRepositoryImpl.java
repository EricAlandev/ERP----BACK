package boletoGenreator.useCases.impl.user;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import boletoGenreator.domain.model.users.UserSearch;
import ch.qos.logback.core.util.StringUtil;

public class UserCustomRepositoryImpl implements UserCustomRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserCustomRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

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
            params.add(userSearch.getIdUser());
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
}
