package software.project.backend.Database;

public class Commands {

    public static String INSERT_USER() {
        return "INSERT INTO USER " +
                "(userName, password, firstName, lastName, address, email, role) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    public static String Search(String searchText, String type) {
        if (type == "ISBN" || type == "title" || type == "Publisher")
            return "SELECT * FROM Book WHERE " + type.toLowerCase() + "LIKE " + "% " + searchText.toLowerCase() + "%";
        else
            return "SELECT * FROM Book AS B WHERE B.ISBN IN (SELECT ISBN FROM BookAuthors WHERE authorName = "
                    + searchText + ")";
    }

    public static String insertCheckOut() {
        return "INSERT INTO CheckOut (userName,ISBN,noOfCopies,data) VALUES (?,?,?,?)";
    }

    public static String allOrders() {
        return "SELECT * FROM Orders";
    }

    public static String deleteOrder() {
        return "DELETE FROM Orders WHERE orderId = ?" ;
    }
    
    public static String insertOrder() {
        return "INSERT INTO Orders (ISBN,noOfCopies) VALUES(?,?)";
    }

    public static String deleteCheckOut(String userName, String ISBN) {
        return "DELETE FROM CheckOut AS C WHERE C.userName = " + userName + " AND C.ISBN = " + ISBN;
    }

    public static String deleteBook(String ISBN) {
        return "DELETE FROM Book AS B WHERE B.ISBN = " + ISBN;
    }

    public static String UPDATE_USER_SETTINGS() {
        return "UPDATE USER SET firstName = ?, lastName = ?, address = ?, email = ? " +
                " WHERE userName = ? ";
    }

    public static String SELECT_BY_USERNAME() {
        return "SELECT COUNT(*) FROM USER WHERE userName = ? ";
    }

    public static String GET_USER_DATA() {
        return "SELECT * FROM USER WHERE userName = ? ";
    }

    public static String SELECT_BY_USER_PASS() {
        return "SELECT * FROM USER WHERE userName = ? AND password = ?";
    }

    public static String CHANGE_PASSWORD() {
        return "UPDATE USER SET password = ? WHERE userName = ?";
    }

    public static String GET_ADMINS() {
        return "SELECT userName FROM USER WHERE role != 0 AND role != 1";
    }

    public static String DELETE_ADMIN() {
        return "DELETE FROM USER WHERE role != 1  AND userName = ?; ";
    }

    public static String DELETE_ALL_CUSTOMERS() {
        return "DELETE FROM USER WHERE role != 1; ";
    }

    public static String INSERT_PRODUCT() {
        return "INSERT INTO BOOK " +
                "(ISBN,title, noOfCopies, price, publicationYear, threshold, categoryName, publisherName,image) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ? ,?)";
    }

    public static String UPDATE_PRODUCT() {
        return "UPDATE BOOK " +
                "SET title = ?, noOfCopies = ?, price = ?, publicationYear = ?, threshold = ?, categoryName = ? ,publisherName=? , image=?"
                +
                "WHERE ISBN = ?";
    }

    public static String GET_PRODUCT_BY_ID() {
        return "SELECT * FROM BOOK WHERE ISBN = ?";
    }

    public static String GET_PRODUCTS_BY_CATEGORY(){
        return "SELECT * FROM BOOK where categoryName = ?" ;
    }

    public static String GET_ALL_PRODUCTS() {
        return "SELECT * FROM BOOK";
    }

    public static String DELETE_PRODUCT() {
        return "DELETE FROM BOOK " +
                "WHERE ISBN = ?";
    }

    /*
        
    */

}
