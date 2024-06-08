package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AppointmentsInterface extends JFrame {
    public DefaultTableModel model;
    public JComboBox<String> filterComboBox;
    public JTextField inputField;
    public JButton searchButton;
    public LoginTool loginTool;
    public boolean limitToUserResults;
    public JButton toggler;
    public AppointmentsInterface(LoginTool loginTool) {
        this.loginTool = loginTool;
        this.model = new DefaultTableModel();
        this.limitToUserResults = true;

        String[] columnNames = {"Appointment ID", "Patient ID", "Doctor ID", "Appointment Date", "Notes"};

        model.setColumnIdentifiers(columnNames);

        //setting up the JFrame
        setTitle("Appointments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Appointments");
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        //Creating the main panel
        JPanel mainPanel = new JPanel();
        JPanel topMainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        topMainPanel.setLayout( new FlowLayout());

        //Add filter label and combo box
        JLabel filterLabel = new JLabel("Filter By");
        topMainPanel.add(filterLabel); //add to panel

        this.filterComboBox = new JComboBox<>(columnNames);
        topMainPanel.add(filterComboBox);//add to panel

        this.inputField = new JTextField(20);
        topMainPanel.add(inputField);//add to panel

        //add search button
        this.searchButton = new JButton("Search");
        topMainPanel.add(searchButton);//add to panel

        searchButton.addActionListener(e -> search((String) filterComboBox.getSelectedItem(), inputField.getText()));

        if (loginTool.userIsDoctor) {
            toggler = new JButton("Getting only your appointments");
            toggler.setBackground(Color.decode("#1fbf44"));
            toggler.addActionListener(e -> toggleLimiting());

            topMainPanel.add(toggler);
        }

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

        AppointmentTool appointmentTool = new AppointmentTool();
        ResultSet appointments = appointmentTool.getAppointmentByID(loginTool.UserID);

        try {
            while (appointments.next()) {
                Object[] row = new Object[5];
                row[0] = appointments.getInt("AppointmentID");
                row[1] = appointments.getString("PatientID");
                row[2] = appointments.getString("DoctorID");
                row[3] = appointments.getString("AppointmentDate");
                row[4] = appointments.getString("Notes");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void search(String mode, String criteria) {
        System.out.println(limitToUserResults);
        AppointmentTool appointmentTool = new AppointmentTool();
        ResultSet appointments;
        if (limitToUserResults) {
            appointments = appointmentTool.getUserAppointmentsBy(loginTool.UserID, mode, criteria);
        } else {
            appointments = appointmentTool.getAppointmentsBy(mode, criteria);
        }
        model.setRowCount(0); // Wipe previous results
        try {
            while (appointments.next()) {
                Object[] row = new Object[5];
                row[0] = appointments.getInt("AppointmentID");
                row[1] = appointments.getString("PatientID");
                row[2] = appointments.getString("DoctorID");
                row[3] = appointments.getString("AppointmentDate");
                row[4] = appointments.getString("Notes");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void toggleLimiting() {
        limitToUserResults = !limitToUserResults;
        if (limitToUserResults) {
            toggler.setBackground(Color.decode("#1fbf44"));
            toggler.setText("Getting only your appointments");
        } else {
            toggler.setBackground(Color.decode("#e75472"));
            toggler.setText("Getting all appointments");
        }
    }

    public static void main(String[] args) {
        new AppointmentsInterface(new LoginTool(true));
    }
}

