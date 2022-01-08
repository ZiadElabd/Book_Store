package software.project.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import software.project.backend.Database.ProductDAO;
import software.project.backend.Database.UserDAO;
import software.project.backend.Model.Product;
import software.project.backend.Model.User;

@SpringBootApplication
public class BackendApplication  {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
