package SourceFiles;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;


public class LoginPage extends JFrame {

    JTextField usernameField;
    JPasswordField passwordField;
    JButton loginButton;
    JLabel messageLabel;
    LoginTool loginTool;

    public LoginPage(LoginTool loginTool) {
        this.loginTool = loginTool;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Simple Login Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        messageLabel = new JLabel();

        setLayout(new GridLayout(4, 2));

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(messageLabel);

        loginButton.addActionListener(this::handleLogin);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleLogin(ActionEvent e) {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        int userID = loginTool.login(username, password);

        if (userID != -1) {
            LoginTool.isLoggedIn = true;
            messageLabel.setText("Login Successful");
            dispose();
            HomePage home = new HomePage(loginTool);
            home.setVisible(true);
        } else {
            messageLabel.setText("Invalid username or password");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginPage(new LoginTool()));
    }
}
