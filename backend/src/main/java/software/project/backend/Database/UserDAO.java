package software.project.backend.Database;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import software.project.backend.Database.CustomMappers.SignInMapper;
import software.project.backend.Model.Product;
import software.project.backend.Model.User;

import java.util.List;

public class UserDAO {

	private final JdbcTemplate jdbcTemplate = new JdbcTemplate(Source.getDataSource());

	public UserDAO() {
	}

	public boolean insertUser(User user) {

		int result = jdbcTemplate.update(Commands.INSERT_USER(),
				user.getUserName(),
				user.getPassword(),
				user.getFirstName(),
				user.getLastName(),
				user.getAddress(),
				user.getPhone(),
				user.getRole());

		if (result > 0) {
			System.out.println("A new row has been inserted.");
			return true ;
		}
		return false ;
	 }

	public boolean isUserNameExist(String userName) {
		try {
			Integer n = jdbcTemplate.queryForObject(Commands.SELECT_BY_USERNAME(),
					Integer.class,
					userName);

			return (n != null) && (n > 0);
		} catch (IncorrectResultSizeDataAccessException e) {
			System.out.println("Exception in checking userName");
			return false;
		}
	}
	
	public User checkSignIn(String userName,String Password) {
	    try {
			return jdbcTemplate.queryForObject(Commands.SELECT_BY_USER_PASS(),
					new SignInMapper(),
					userName ,
					Password);
	    } catch (IncorrectResultSizeDataAccessException e) {
			return null;
	    }
	}

	public boolean changePassword(String userName,String password){
		int result = jdbcTemplate.update(Commands.CHANGE_PASSWORD(), password, userName);

		if (result > 0) {
			System.out.println("Password is changed");
			return true ;
		}
		return false ;
	}

	public User getUserSettings(String userName){
		User user = (User) jdbcTemplate.queryForObject(Commands.GET_USER_DATA(),
				new BeanPropertyRowMapper(User.class),
				userName);
		if (user != null) {
			user.setPassword(null);
		}
		return user;
	}

	public boolean updateUserSettings(String userName, User user){

		int result = jdbcTemplate.update(Commands.UPDATE_USER_SETTINGS(),
				user.getFirstName(),
				user.getLastName(),
				user.getAddress(),
				user.getPhone(),
				userName);

		if (result > 0) {
			System.out.println("Password is changed");
			return true ;
		}
		return false ;
	}

	public List<String> getAdmins() {
		List<String> admins = jdbcTemplate.queryForList(Commands.GET_ADMINS(),String.class);
		return admins;
	}

	public boolean deleteAdmin(String userName){
		int result = jdbcTemplate.update(Commands.DELETE_ADMIN(), userName);
		if (result > 0) {
			System.out.println("Admin is removed");
			return true ;
		}
		return false ;
	}

	public boolean deleteAllCustomers(){
		jdbcTemplate.update(Commands.DELETE_ALL_CUSTOMERS());
		return true ;
	}

}
