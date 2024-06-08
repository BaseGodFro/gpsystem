package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDetailsInterface extends JFrame {
    public DefaultTableModel model;
    public JComboBox<String> filterComboBox;
    public JTextField inputField;
    public JButton searchButton;
    public LoginTool loginTool;
    public boolean limitToUserResults;
    public JButton toggler;
    public PatientDetailsInterface(LoginTool loginTool) {
        this.loginTool = loginTool;
        this.model = new DefaultTableModel();
        this.limitToUserResults = true;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        String[] columnNames = {"Patient ID", "Primary Care Physician ID", "FirstName", "Surname", "DateOfBirth", "Email", "Age", "Gender", "PhoneNumber"};

        model.setColumnIdentifiers(columnNames);

        //setting up the JFrame
        setTitle("Patients");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Patients");
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

        if (loginTool.userIsDoctor) {
            toggler = new JButton("Getting only your patients");
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

        JButton changeDoctorsButton = new JButton("Change the doctor of a patient");
        changeDoctorsButton.addActionListener(e -> {
            dispose(); // Close this window
            new AssignNewPatientInterface(loginTool).setVisible(true); // Open the HomePage
        });
        backButtonPanel.add(changeDoctorsButton, BorderLayout.EAST);

        bottomPanel.add(backButtonPanel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        PatientTool patientTool = new PatientTool();
        ResultSet patients = patientTool.getPatientsOfDoctor(loginTool.UserID);

        try {
            while (patients.next()) {
                Object[] row = new Object[9];
                row[0] = patients.getInt("UserID");
                row[1] = patients.getInt("PrimaryCarePhysicianID");
                row[2] = patients.getString("FirstName");
                row[3] = patients.getString("Surname");
                row[4] = patients.getDate("DateOfBirth");
                row[5] = patients.getString("email");
                row[6] = patients.getInt("age");
                row[7] = patients.getString("gender");
                row[8] = patients.getString("phonenumber");
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void search(String mode, String criteria) {
        System.out.println(limitToUserResults);
        PatientTool patientTool = new PatientTool();
        ResultSet patients;
        if (limitToUserResults) {
            patients = patientTool.getUserPatientBy(loginTool.UserID, mode, criteria);
        } else {
            patients = patientTool.getPatientBy(mode, criteria);
        }
        model.setRowCount(0); // Wipe previous results
        try {
            while (patients.next()) {
                Object[] row = new Object[9];
                row[0] = patients.getInt("UserID");
                row[1] = patients.getInt("PrimaryCarePhysicianID");
                row[2] = patients.getString("FirstName");
                row[3] = patients.getString("Surname");
                row[4] = patients.getDate("DateOfBirth");
                row[5] = patients.getString("email");
                row[6] = patients.getInt("age");
                row[7] = patients.getString("gender");
                row[8] = patients.getString("phonenumber");
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
            toggler.setText("Getting only your patients");
        } else {
            toggler.setBackground(Color.decode("#e75472"));
            toggler.setText("Getting all patients");
        }
    }

    public static void main(String[] args) {
        new PatientDetailsInterface(new LoginTool(true));
    }
}

