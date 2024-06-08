package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDashboard extends JFrame {
    public DefaultTableModel model;
    public JComboBox<String> monthSelector;
    public JTextField yearSelector;
    public JButton searchButton;
    public LoginTool loginTool;
    public DoctorDashboard(LoginTool loginTool) {
        this.loginTool = loginTool;
        this.model = new DefaultTableModel();

        String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};

        String[] columNames = {"Appointment ID", "Patient First Name", "Patient Second Name", "Dr", "Date", "Notes"};

        model.setColumnIdentifiers(columNames);

        //setting up the JFrame
        setTitle("View your appointments");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("View Your Appointments");
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
        JLabel filterLabel = new JLabel("Month: ");
        topMainPanel.add(filterLabel); //add to panel

        monthSelector = new JComboBox<>(months);
        topMainPanel.add(monthSelector);//add to panel

        JLabel yearLabel = new JLabel("Year: ");
        topMainPanel.add(yearLabel); //add to panel

        yearSelector = new JTextField(20);
        topMainPanel.add(yearSelector);//add to panel

        //add search button
        this.searchButton = new JButton("Find");
        topMainPanel.add(searchButton);//add to panel

        searchButton.addActionListener(e -> {
            // Open the DoctorDetails.java file
            search(monthSelector.getSelectedIndex()+1, yearSelector.getText());
        });

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
    }

    private void search(int month, String year) {
        AppointmentTool appointmentTool = new AppointmentTool();
        ResultSet appointments = appointmentTool.getAppointmentByMonthYear(loginTool.UserID, month, Integer.parseInt(year));
        model.setRowCount(0); // Wipe previous results
        try {
            while (appointments.next()) {
                Object[] row = new Object[6];
                row[0] = appointments.getInt("AppointmentID");
                row[1] = appointments.getString("PatientUsers.FirstName");
                row[2] = appointments.getString("PatientUsers.SurName");
                row[3] = appointments.getString("DoctorUsers.Surname");
                row[4] = appointments.getString("AppointmentDate");
                row[5] = appointments.getString("Notes");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new DoctorDashboard(new LoginTool(true));
    }
}

