package SourceFiles;
import java.sql.*;

/**
 * This class allows other classes to interact with the database and its structure
 * This class should **not** be interacted with directly; only use its subclasses
 */
public class DatabaseTool {
    // Initialises data required for accessing the file - if you have any questions about hwo to set this up on your PC ask Max
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/comp5590_assessment_2_group_12b";
    private static final String JDBC_USER = "DatabaseTool";
    private static final String JDBC_PASSWORD = "password_comp5590";
    private Connection connection;

    /**
     * Creates the connection for the query method
     */
    public DatabaseTool() {
        if (!connect()) {
            System.out.println("Error Connecting to the Database.");
        }
    }

    /**
     * Connects to the database, called automatically in the constructor
     *
     * @return true if successful connection
     */
    public boolean connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error");
            return false;
        }

        try {
            connection = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    /**
     * Directly runs an SQL query to the database - be very careful if you are directly calling this
     * Make sure to pass any unfiltered data though DatabaseTool.checkInput()
     * If false, the data is not safe.
     *
     * @param SQLQuery Query to send to the database
     * @return ResultSet containing results from the query - if any.
     */
    protected ResultSet query(String SQLQuery) {
        try {
            PreparedStatement statement = connection.prepareStatement(SQLQuery, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            try {
                return statement.executeQuery();
            } catch (SQLException e) {
                try {
                    statement.executeUpdate();
                } catch (SQLException ee) {
                    System.out.println(SQLQuery);
                    e.printStackTrace();
                    return null;
                }
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error Executing SQL Code - '" + SQLQuery + "' :");
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkInput(String SQLInput) {
        return SQLInput.contains("'") || SQLInput.contains(String.valueOf('"'));
    }


}
