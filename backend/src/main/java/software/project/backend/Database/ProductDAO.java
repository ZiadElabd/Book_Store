package software.project.backend.Database;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import software.project.backend.Model.Product;

import java.util.List;

public class ProductDAO {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(Source.getDataSource());

	public ProductDAO() {

	}

	public boolean insertProduct(Product p) {

		int result = jdbcTemplate.update(Commands.INSERT_PRODUCT(),
				p.getCategoryName(),
				p.getName(),
				p.getDescription(),
				p.getPrice() ,
				p.getQuantity(),
				p.getDiscount(),
				p.getImage());

		if (result > 0) {
			System.out.println("A new product has been inserted.");
			return true ;
		}
		return false ;
	}

	public boolean updateProduct(Product p, int productId) {

		int result = jdbcTemplate.update(Commands.UPDATE_PRODUCT(),
				p.getCategoryName(),
				p.getName(),
				p.getDescription(),
				p.getPrice() ,
				p.getQuantity(),
				p.getDiscount(),
				p.getImage(),
				productId);
		if (result > 0) {
			System.out.println("A new row has been updated.");
			return true ;
		}
		return false ;
	}

	public Product getProductByID(int ID) {
		Product product = (Product) jdbcTemplate.queryForObject(Commands.GET_PRODUCT_BY_ID(),
				new BeanPropertyRowMapper(Product.class), ID);
		return product;
	}

	public List<Product> getProductByCategory(String categoryName) {
		List<Product> products = jdbcTemplate.query(Commands.GET_PRODUCTS_BY_CATEGORY(),
				new BeanPropertyRowMapper(Product.class),
				categoryName);
		return products;
	}

	public List<Product> getAllProduct() {
		List<Product> products = jdbcTemplate.query(Commands.GET_ALL_PRODUCTS(),
				new BeanPropertyRowMapper(Product.class));
		return products;
	}

	public boolean deleteProduct(int productId) {

		int result = jdbcTemplate.update(Commands.DELETE_PRODUCT(), productId);

		if (result > 0) {
			System.out.println("A new has been deleted");
			return true ;
		}
		return false ;
	}

}
