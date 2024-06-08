package SourceFiles;
import org.junit.jupiter.api.Test;

import javax.swing.table.DefaultTableModel;

import static org.junit.Assert.assertEquals;

class TesterClass {
    public static void main(String[] args) {
        System.out.println(":)");
    }

    @Test
    public void testSuccessfulLogin() {
        // Mocked LoginTool always returns 1 for UserID, indicating a successful login
        LoginTool loginTool = new LoginTool() {
            @Override
            public int login(String username, String password) {
                return 1; // Mock successful login
            }
        };

        // Create the LoginPage with the mocked LoginTool
        LoginPage loginPage = new LoginPage(loginTool);

        // Simulate user input
        loginPage.usernameField.setText("mworb0001");
        loginPage.passwordField.setText("password123");

        // Simulate button click
        loginPage.loginButton.doClick();

        // Verify the result
        assertEquals("Login Successful", loginPage.messageLabel.getText());
    }

    @Test
    public void testUnsuccessfulLogin() {
        // Mocked LoginTool returns -1 for UserID, indicating an unsuccessful login
        LoginTool loginTool = new LoginTool() {
            @Override
            public int login(String username, String password) {
                return -1; // Mock unsuccessful login
            }
        };

        // Create the LoginPage with the mocked LoginTool
        LoginPage loginPage = new LoginPage(loginTool);

        // Simulate user input
        loginPage.usernameField.setText("wronguser");
        loginPage.passwordField.setText("wrongpassword");

        // Simulate button click
        loginPage.loginButton.doClick();

        // Verify the result
        assertEquals("Invalid username or password", loginPage.messageLabel.getText());
    }

    @Test
    public void testPatientSearch() {
        PatientDetailsInterface pdi = new PatientDetailsInterface(new LoginTool(true)); // Simulate logging in as user 1
        pdi.filterComboBox.setSelectedItem("Patient ID");
        pdi.inputField.setText("1");
        // Simulate entering details that will search for a patient with ID 1
        pdi.searchButton.doClick();

        DefaultTableModel model = pdi.model;

        Object[][] expectedData = {
                {1, 1, "Max", "Worby", 19, "m", "mw737@kent.ac.uk", "011111 1111"}
        };

        boolean dataMatches = true;

        for (int row = 0; row < model.getRowCount(); row++) {
            for (int col = 0; col < model.getColumnCount(); col++) {
                Object value = model.getValueAt(row, col);
                Object expectedValue = expectedData[row][col];
                if (!value.equals(expectedValue)) {
                    dataMatches = false;
                    System.out.println(String.format("No match in position %d,%d.\nExpected Value: %s\nGot: %s", row, col, expectedValue.toString(), value.toString()));
                }
            }
        }
        assertEquals(true, dataMatches);
    }
}
