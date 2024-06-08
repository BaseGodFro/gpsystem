package SourceFiles;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;


public class RegisterPage extends JFrame {

    public JTextField usernameField;
    public JPasswordField passwordField;
    public JButton loginButton;
    public JButton goToLoginButton;
    public JLabel messageLabel;
    public LoginTool loginTool;
    public JTextField firstNameField;
    public JTextField surnameField;
    public JTextField dateOfBirthField;
    public JTextField  emailField;
    public JTextField  ageField;
    public JTextField genderField;
    public JTextField phoneNumberField;

    public RegisterPage(LoginTool loginTool) {
        this.loginTool = loginTool;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Register Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        firstNameField = new JTextField(15);
        surnameField = new JTextField(15);
        dateOfBirthField = new JTextField(15);
        emailField = new JTextField(15);
        ageField = new JTextField(15);
        genderField = new JTextField(15);
        phoneNumberField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Register");
        goToLoginButton = new JButton("Go to Login");
        messageLabel = new JLabel();

        setLayout(new GridLayout(12, 2));

        add(new JLabel("<html><div style='width: 100px;'>First Name:</div></html>")); // Set width to 100px
        add(firstNameField);
        add(new JLabel("<html><div style='width: 100px;'>Surname:</div></html>")); // Set width to 100px
        add(surnameField);
        add(new JLabel("<html><div style='width: 100px;'>Date of Birth:</div></html>")); // Set width to 100px
        add(dateOfBirthField);
        add(new JLabel("<html><div style='width: 100px;'>Email:</div></html>")); // Set width to 100px
        add(emailField);
        add(new JLabel("<html><div style='width: 100px;'>Age:</div></html>")); // Set width to 100px
        add(ageField);
        add(new JLabel("<html><div style='width: 100px;'>Gender:</div></html>")); // Set width to 100px
        add(genderField);
        add(new JLabel("<html><div style='width: 100px;'>Phone Number:</div></html>")); // Set width to 100px
        add(phoneNumberField);
        add(new JLabel("<html><div style='width: 100px;'>Desired Password:</div></html>")); // Set width to 100px
        add(passwordField);
        add(new JLabel("")); // Empty label placeholder
        add(loginButton);
        add(goToLoginButton);
        add(messageLabel);



        loginButton.addActionListener(this::handleRegister);

        goToLoginButton.addActionListener(e -> {
            dispose();
            LoginPage loginPage = new LoginPage(loginTool);
            loginPage.setVisible(true);
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleRegister(ActionEvent e) {
        try {
            String successfulRegister = loginTool.register(
                    firstNameField.getText(),
                    surnameField.getText(),
                    passwordField.getText(),
                    dateOfBirthField.getText(),
                    emailField.getText(),
                    Integer.parseInt(ageField.getText()),
                    genderField.getText(),
                    phoneNumberField.getText()
            );
            if (successfulRegister.equals("")) {
                messageLabel.setText("There was an error registering you, please try again.");
            } else {
                messageLabel.setText("Successfully registered with username " + successfulRegister);
            }
        } catch (SQLException e2) {
            messageLabel.setText("There was an error registering you, please try again.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegisterPage(new LoginTool()));
    }
}
