package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationCentre extends JFrame {
    public DefaultTableModel model;
    public LoginTool loginTool;
    public NotificationCentre(LoginTool loginTool) {
        this.loginTool = loginTool;
        this.model = new DefaultTableModel();

        String[] columnNames = {"Messages"};

        model.setColumnIdentifiers(columnNames);

        //setting up the JFrame
        setTitle("Notifications");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Notifications");
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        //Creating the main panel
        JPanel mainPanel = new JPanel();
        JPanel topMainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        topMainPanel.setLayout( new FlowLayout());

        //add top pane to main
        mainPanel.add(topMainPanel , BorderLayout.NORTH);

        //new child panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout( new FlowLayout());

        //adding the child panel to the main panel
        mainPanel.add(secondPanel,BorderLayout.CENTER);

        //adding all contents to the container
        add(mainPanel, BorderLayout.CENTER);

        JTable table = new JTable(model);//create table with both attributes and null data
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);

        // Using JSplitPane to separate the main panel and the table
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, mainPanel, scrollPane);
        splitPane.setResizeWeight(0.3); // Adjust this value as needed
        add(splitPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
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

        NotificationTool notificationTool = new NotificationTool();
        ResultSet notifications = notificationTool.getUserNotifications(loginTool.UserID);

        try {
            while (notifications.next()) {
                Object[] row = new Object[1];
                row[0] = notifications.getString("Message");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        notificationTool.readMessages(loginTool.UserID);
    }

    public static void main(String[] args) {
        new NotificationCentre(new LoginTool(true));
    }
}

