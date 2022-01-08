package software.project.backend.DatabaseTests;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;
import software.project.backend.Database.ProductDAO;
import software.project.backend.Model.Product;
import software.project.backend.Model.builder.Director;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductDAO_Test {

    private final Director director = new Director();
    private final ProductDAO productDAO = new ProductDAO();

    @Order(1)
    @Test
    void insertProductTest() {
        String dataSent = "{\n" +
                "   \"name\":\"apple\",\n" +
                "    \"categoryName\":\"Clothing\",\n" +
                "    \"description\":\"apple is healthy\",\n" +
                "    \"price\":\"12\",\n" +
                "    \"quantity\":\"10\",\n" +
                "    \"discount\":\"3\",\n" +
                "    \"image\":\"null\"\n" +
                "}";
        System.out.println(dataSent);
        Product product = (Product) director.composeModel("product",dataSent);
        System.out.println(product);
        assertTrue(productDAO.insertProduct(product));

    }

    @Test
    void getProductByIDTest() {
        Product product = productDAO.getProductByID(8);
        System.out.println(product.getCategoryName() + "  " + product.getName());
    }

    @Order(2)
    @Test
    void getProductByCategoryTest() {
        List<Product> products = productDAO.getProductByCategory("Fruits");
        for (Product product : products) {
            System.out.println(product.getCategoryName() + "  " + product.getName());
        }

    }

    @Order(2)
    @Test
    void getAllProductsTest() {
        List<Product> products = productDAO.getAllProduct();
        for (Product product : products) {
            System.out.println(product.getCategoryName() + "  " + product.getName());
        }
    }

    @Order(3)
    @Test
    void deleteProductTest() {
        assertTrue(productDAO.deleteProduct(0));

    }

}
