package software.project.backend.Database;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import software.project.backend.Model.Book;

import java.util.List;

public class BookDAO {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(Source.getDataSource());

	public BookDAO() {

	}

	public boolean insertProduct(Book p) {

		int result = jdbcTemplate.update(Commands.INSERT_PRODUCT(),
				p.getISBN(),
				p.getTitle(),
				p.getNoOfCopies(),
				p.getPrice() ,
				p.getPublicationYear(),
				p.getThreshold(),
				p.getCategoryId(),
				p.getPublisherId(),
				p.getImage());

		if (result > 0) {
			System.out.println("A new product has been inserted.");
			return true ;
		}
		return false ;
	}

	public boolean updateProduct(Book p, int productId) {

		int result = jdbcTemplate.update(Commands.UPDATE_PRODUCT(),
				p.getISBN(),
				p.getTitle(),
				p.getNoOfCopies(),
				p.getPrice() ,
				p.getPublicationYear(),
				p.getThreshold(),
				p.getCategoryId(),
				p.getPublisherId(),
				p.getImage(),
				productId);
		if (result > 0) {
			System.out.println("A new row has been updated.");
			return true ;
		}
		return false ;
	}

	public Book getProductByID(int ID) {
		Book product = (Book) jdbcTemplate.queryForObject(Commands.GET_PRODUCT_BY_ID(),
				new BeanPropertyRowMapper(Book.class), ID);
		return product;
	}

	public List<Book> getProductByCategory(String categoryName) {
		List<Book> products = jdbcTemplate.query(Commands.GET_PRODUCTS_BY_CATEGORY(),
				new BeanPropertyRowMapper(Book.class),
				categoryName);
		return products;
	}

	public List<Book> getAllProduct() {
		List<Book> products = jdbcTemplate.query(Commands.GET_ALL_PRODUCTS(),
				new BeanPropertyRowMapper(Book.class));
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
