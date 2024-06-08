package SourceFiles;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class MessageAdminInterface extends JFrame {
    public JComboBox<String> adminSelector;
    public JTextField messageField;
    public LoginTool loginTool;
    public MessageAdminInterface(LoginTool loginTool) {
        this.loginTool = loginTool;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        //setting up the JFrame
        setTitle("Message Admin Team");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Message Admin Team");
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        //Creating the main panel
        JPanel mainPanel = new JPanel();
        JPanel topMainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        topMainPanel.setLayout( new FlowLayout());

        ArrayList<String> adminOptions = new ArrayList<>();
        AdminTool adminTool = new AdminTool();
        ResultSet admins = adminTool.getAllAdmins();

        try {
            while (admins.next()) {
                String stringBuilder = admins.getString("FirstName") +
                        " " +
                        admins.getString("Surname");
                adminOptions.add(stringBuilder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] adminOption = adminOptions.toArray(new String[adminOptions.size()]);
        this.adminSelector = new JComboBox<>(adminOption);
        topMainPanel.add(adminSelector);

        mainPanel.add(topMainPanel , BorderLayout.NORTH);

        //new child panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout( new FlowLayout());

        JLabel messageLabel = new JLabel("Message: ");
        secondPanel.add(messageLabel); //add to panel

        messageField = new JTextField(20);
        secondPanel.add(messageField);

        //adding the child panel to the main panel
        mainPanel.add(secondPanel,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton sendButton = new JButton("Send");
        bottomPanel.add(sendButton);

        sendButton.addActionListener(e -> {
            try {
                int adminIndex = adminSelector.getSelectedIndex();
                ResultSet admins1 = adminTool.getAllAdmins();

                int count = -1;
                int adminID = -1;
                while (admins1.next()) {
                    count++;
                    if (count == adminIndex) {
                        adminID = admins1.getInt("UserID");
                        break;
                    }
                }

                String message = messageField.getText();

                NotificationTool notificationTool = new NotificationTool();
                notificationTool.notify(adminID, message);

                messageField.setText("");

            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        });

        //adding all contents to the container
        add(mainPanel, BorderLayout.CENTER);

        // New bottom panel for the Back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new HomePage(loginTool).setVisible(true); // Open the HomePage
        });
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);
        bottomPanel.add(backButtonPanel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new MessageAdminInterface(new LoginTool(true));
    }
}

