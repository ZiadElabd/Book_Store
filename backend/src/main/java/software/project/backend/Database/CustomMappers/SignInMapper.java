package software.project.backend.Database.CustomMappers;

import org.springframework.jdbc.core.RowMapper;
import software.project.backend.Model.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SignInMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User person = new User();
        person.setUserName(rs.getString("userName"));
        person.setRole(rs.getInt("role"));
        return person;
    }
}
