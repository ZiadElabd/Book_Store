package software.project.backend.Database;

public class Commands {

    public static String INSERT_USER(){
        return "INSERT INTO USER " +
                "(userName, password, firstName, lastName, address, email, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public static String UPDATE_USER_SETTINGS(){
        return "UPDATE USER SET firstName = ?, lastName = ?, address = ?, email = ? " +
                " WHERE userName = ? ";
    }


    public static String SELECT_BY_USERNAME(){
        return "SELECT COUNT(*) FROM USER WHERE userName = ? ";
    }

    public static String GET_USER_DATA(){
        return "SELECT * FROM USER WHERE userName = ? ";
    }


    public static String SELECT_BY_USER_PASS(){
        return "SELECT * FROM USER WHERE userName = ? AND password = ?";
    }

    public static String CHANGE_PASSWORD(){
        return "UPDATE USER SET password = ? WHERE userName = ?";
    }

    public static String GET_ADMINS(){
        return "SELECT userName FROM USER WHERE role != 0 AND role != 1";
    }

    public static String DELETE_ADMIN(){
        return "DELETE FROM USER WHERE role != 1  AND userName = ?; ";
    }

    public static String DELETE_ALL_CUSTOMERS(){
        return "DELETE FROM USER WHERE role != 1; ";
    }

    public static String INSERT_PRODUCT(){
        return "INSERT INTO BOOK " +
                "(ISBN,title, noOfCopies, price, publicationYear, threshold, categoryId, publisherId,image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)";
    }

    public static String UPDATE_PRODUCT(){
        return "UPDATE BOOK " +
                "SET ISBN = ?, title = ?, noOfCopies = ?, price = ?, publicationYear = ?, threshold = ?, categoryId = ? ,publisherId=? , image" +
                "WHERE ISBN = ?";
    }


    public static String GET_PRODUCT_BY_ID(){
        return "SELECT * FROM BOOK WHERE ISBN = ?" ;
    }

    public static String GET_PRODUCTS_BY_CATEGORY(){
        return "SELECT * FROM BOOK where categoryId = ?" ;
    }

    public static String GET_ALL_PRODUCTS(){
        return "SELECT * FROM BOOK" ;
    }

    public static String DELETE_PRODUCT(){
        return "DELETE FROM BOOK " +
                "WHERE ISBN = ?";
    }

}
