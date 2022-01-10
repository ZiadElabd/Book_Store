package software.project.backend.Database;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
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

	public void updateCart(String userName, String ISBN, int newCopies) {
		jdbcTemplate.update(Commands.updateCart(), userName, ISBN, newCopies);
	}

	public boolean isInCart(String userName, String ISBN) {
		try {
			Integer n = jdbcTemplate.queryForObject(Commands.isInCart(),
					Integer.class,
					userName, ISBN);
			return (n != null) && (n > 0);
		} catch (IncorrectResultSizeDataAccessException e) {
			System.out.println("Exception in checking userName");
			return false;
		}
	}

	public List<Book> searchWithISBN(String ISBN, String cateogry) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithISBN(ISBN),
				new BeanPropertyRowMapper(Book.class), cateogry);
		return res;
	}

	public List<Book> searchWithTitle(String title, String cateogry) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithTitle(title),
				new BeanPropertyRowMapper(Book.class), cateogry);
		return res;
	}

	public List<Book> searchWithpublisherName(String name, String cateogry) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithPublisherName(name),
				new BeanPropertyRowMapper(Book.class), cateogry);
		return res;
	}

	public List<Book> searchWithAuthorName(String name, String cateogry) {
		List<Book> res = (List<Book>) jdbcTemplate.query(Commands.searchWithAuthorName(name),
				new BeanPropertyRowMapper(Book.class), cateogry);
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

	public boolean deleteCheckOut(String userName) {
		jdbcTemplate.update(Commands.deleteFromCheckOutWithUserName(), userName);
		return true;
	}

	public boolean delelteFromCheckOut(String userName, String ISBN) {
		jdbcTemplate.update(Commands.deleteFromCheckOutWithUserISBN(), userName, ISBN);
		return true;
	}

	public List<Book> getCart(String userName) {
		return jdbcTemplate.query(Commands.getCart(), new BeanPropertyRowMapper(Book.class), userName);
	}
	
	public boolean updateQuantityInCart(String u , String isbn , int v) {

		int result = jdbcTemplate.update(Commands.incrementQuantity(),v,isbn,u);
		if (result > 0) {
			System.out.println("A new row has been updated.");
			return true;
		}
		return false;
	}
	
	

	public boolean insertToCart(String userName, String ISBN, int noOpCopies, String date) {
		 jdbcTemplate.update(Commands.insertToCart(), userName, ISBN, noOpCopies, date);
		 return true;
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
