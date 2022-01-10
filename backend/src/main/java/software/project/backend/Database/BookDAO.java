package software.project.backend.Database;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import software.project.backend.Model.Book;
import software.project.backend.Model.builder.order;

import java.util.List;

public class BookDAO {

	private JdbcTemplate jdbcTemplate = new JdbcTemplate(Source.getDataSource());

	public BookDAO() {

	}

	public boolean insertProduct(Book p) {

		int result = jdbcTemplate.update(Commands.INSERT_PRODUCT(),
				p.getIsbn(),
				p.getTitle(),
				p.getNoOfCopies(),
				p.getPrice(),
				p.getPublicationYear(),
				p.getThreshold(),
				p.getCategoryName(),
				p.getPublisherName(),
				p.getImage());

		if (result > 0) {
			System.out.println("A new product has been inserted.");
			return true;
		}
		return false;
	}

	public List<Book> searchWithISBN(String ISBN) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithISBN(),
				new BeanPropertyRowMapper(Book.class), ISBN);
		return res;
	}

	public List<Book> searchWithTitle(String title) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithTitle(),
				new BeanPropertyRowMapper(Book.class), title);
		return res;
	}

	public List<Book> searchWithpublisherName(String name) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithPublisherName(),
				new BeanPropertyRowMapper(Book.class), name);
		return res;
	}

	public List<Book> searchWithAuthorName(String name) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithAuthorName(),
				new BeanPropertyRowMapper(Book.class), name);
		return res;
	}

	public boolean updateProduct(Book p) {

		int result = jdbcTemplate.update(Commands.UPDATE_PRODUCT(),
				p.getTitle(),
				p.getNoOfCopies(),
				p.getPrice(),
				p.getPublicationYear(),
				p.getThreshold(),
				p.getCategoryName(),
				p.getPublisherName(),
				p.getImage(),
				p.getIsbn());
		if (result > 0) {
			System.out.println("A new row has been updated.");
			return true;
		}
		return false;
	}

	public void delelteFromCheckOutWithUserName(String userName) {
		jdbcTemplate.update(Commands.deleteFromCheckOutWithUserName(), userName);
	}

	public void delelteFromCheckOut(String userName, String ISBN) {
		jdbcTemplate.update(Commands.deleteFromCheckOutWithUserISBN(), userName, ISBN);
	}

	public List<Book> getBooksFromCheckOut(String userName) {
		List<Book> books = (List<Book>) jdbcTemplate.query(Commands.getBooks(), new BeanPropertyRowMapper(Book.class),
				userName);
		return books;
	}

	public Book getProductByID(String ID) {
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
	
	public List<order> getAllOrders() {
		List<order> products = jdbcTemplate.query(Commands.allOrders(),
				new BeanPropertyRowMapper(order.class));
		return products;
	}

	public boolean insertOrder(String isbn, int noOfCopies) {

		int result = jdbcTemplate.update(Commands.insertOrder(),
				isbn, noOfCopies);

		if (result > 0) {
			System.out.println("A new product has been inserted.");
			return true;
		}
		return false;
	}
	
	public boolean deleteOrder(int orderID) {

		int result = jdbcTemplate.update(Commands.deleteOrder(), orderID);

		if (result > 0) {
			System.out.println("A new has been deleted");
			return true;
		}
		return false;
	}

	public boolean deleteProduct(int productId) {

		int result = jdbcTemplate.update(Commands.DELETE_PRODUCT(), productId);

		if (result > 0) {
			System.out.println("A new has been deleted");
			return true;
		}
		return false;
	}

}
