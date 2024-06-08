package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VisitDetailsInterface extends JFrame {
    public DefaultTableModel model;
    public JComboBox<String> filterComboBox;
    public JTextField inputField;
    public JButton searchButton;
    public LoginTool loginTool;
    public VisitDetailsInterface(LoginTool loginTool) {
        this.loginTool = loginTool;
        this.model = new DefaultTableModel();

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        String[] columnNames = {"Visit ID", "Patient First Name", "Patient Surname", "Date of Visit", "Details", "Prescription"};

        model.setColumnIdentifiers(columnNames);

        //setting up the JFrame
        setTitle("Visits");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Visits");
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

        searchButton.addActionListener(e -> {
            // Open the DoctorDetails.java file
            search((String) filterComboBox.getSelectedItem(), inputField.getText());
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

        JButton createVisitButton = new JButton("Create a new Visit");
        createVisitButton.addActionListener(e -> {
            dispose(); // Close this window
            new CreateVisitInterface(loginTool).setVisible(true);
        });

        bottomPanel.add(createVisitButton, BorderLayout.EAST);

        JButton editVisitButton = new JButton("Edit a Visit");
        editVisitButton.addActionListener(e -> {
            dispose(); // Close this window
            new EditVisitInterface(loginTool).setVisible(true);
        });

        bottomPanel.add(editVisitButton, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        VisitTool visitTool = new VisitTool();
        ResultSet visits = visitTool.getUserVisits(loginTool.UserID);

        try {
            while (visits.next()) {
                Object[] row = new Object[6];
                row[0] = visits.getInt("Visits.VisitID");
                row[1] = visits.getString("PatientUsers.FirstName");
                row[2] = visits.getString("PatientUsers.Surname");
                row[3] = visits.getDate("Appointments.AppointmentDate");
                row[4] = visits.getString("Visits.Details");
                row[5] = visits.getString("Visits.Prescriptions");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void search(String mode, String criteria) {
        VisitTool visitTool = new VisitTool();
        ResultSet visits = visitTool.getUserVisitsBy(loginTool.UserID, mode, criteria);
        model.setRowCount(0); // Wipe previous results
        try {
            while (visits.next()) {
                Object[] row = new Object[6];
                row[0] = visits.getInt("Visits.VisitID");
                row[1] = visits.getString("PatientUsers.FirstName");
                row[2] = visits.getString("PatientUsers.Surname");
                row[3] = visits.getDate("Appointments.AppointmentDate");
                row[4] = visits.getString("Visits.Details");
                row[5] = visits.getString("Visits.Prescriptions");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        new VisitDetailsInterface(new LoginTool(true));
    }
}

