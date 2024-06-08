package SourceFiles;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Extension of DatabaseTool - used to allow other programs to easily interact with the database for logging in and registering
 */
public class LoginTool extends DatabaseTool{
    public static boolean isLoggedIn = false;
    public boolean userIsPatient = false;
    public boolean userIsDoctor = false;
    public int UserID;
    public String username;
    public LoginTool() {}
    public LoginTool(boolean simulateUser) {
        if (simulateUser) {
            isLoggedIn = true;
            userIsPatient = true;
            userIsDoctor = true;
            UserID = 1;
            username = "mworb0001";
        }
    }

    /**
     * Checks if credentials are correct.
     *
     * @param username intended username
     * @param password intended password (plaintext)
     * @return UserID if credentials are valid, else -1
     */
    public int login(String username, String password) {
        if (DatabaseTool.checkInput(username)) {
            return -1;
        }
        ResultSet resultSet = query("SELECT UserID FROM Users WHERE Username='" + username + "' AND password='" + password + "';"); // TODO implement encryption here

        try {
            if (resultSet.next()) {
                int id = resultSet.getInt("UserID");
                ResultSet patients = query("SELECT * FROM Patients WHERE UserID = " + id);
                userIsPatient = patients.next();

                ResultSet doctors = query("SELECT * FROM Doctors WHERE UserID = " + id);
                userIsDoctor = doctors.next();
                UserID = id;
                this.username = username;
                return id;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("ERROR HAS OCCURRED");
        }

        return -1;
    }

    /**
     * Registers a new user to the Users table in the form fssss####, where:
     * f = first name digit
     * s = surname digit
     * #### = unique id for that combination of names
     *
     * @param firstName user's first name
     * @param surName user's second name
     * @param plaintextPassword user's chosen password in plaintext
     * @param DateOfBirth user's date of birth in the form 'yyyy-mm-dd'
     * @return true if successful registration
     */
    public String register(String firstName, String surName, String plaintextPassword, String DateOfBirth, String email, int age, String gender, String phoneNumber) throws SQLException {
        // Validates the data is secure against injections
        if (checkInput(firstName) || checkInput(surName)) {
            return "";
        }

        // Translates the first name and surname into the required form
        char nameDigit = (!firstName.isEmpty()) ? Character.toLowerCase(firstName.charAt(0)) : ' ';
        String fourDigits = (surName.length() >= 4) ? surName.substring(0, 4).toLowerCase() : surName.toLowerCase();

        String username = nameDigit + fourDigits;

        // Gets all users from the database with the same first digits
        ResultSet results = query("SELECT Username," +
                "CAST(SUBSTRING(Username, LENGTH('" + username + "') + 1) AS UNSIGNED) AS extracted_integer " +
                "FROM Users " +
                "WHERE Username REGEXP '^" + username + "[0-9]+$' " +
                "ORDER BY extracted_integer;"); // Fetches all usernames which exactly match the username followed by integers

        // FInd the highest previous occurrence of unique digits after the name and add 1
        int newValue;

        try {
            results.last();
        } catch (SQLException e) { // This shouldn't happen
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        String lastUsername;

        try {
            lastUsername = results.getString("Username");
            String digits = lastUsername.replaceAll("[^0-9]+", "");
            newValue = Integer.parseInt(digits) + 1;
        } catch (SQLException e) {
            newValue = 1;
        }

        String IDdigits = String.format("%04d", newValue);

        String usernameFormatted = username + IDdigits;
        query("INSERT INTO Users (Username, Password, DateOfBirth, FirstName, SurName, email, age, phonenumber, gender) " +
                String.format("VALUES ('%s', '%s', '%s', '%s', '%s', '%s', %d, '%s', '%s');", usernameFormatted, plaintextPassword, DateOfBirth, firstName, surName, email, age, phoneNumber, gender));

        return usernameFormatted;
    }
}
