package SourceFiles;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AssignNewPatientInterface extends JFrame {
    public JComboBox<String> patientSelector;
    public JComboBox<String> doctorSelector;
    public LoginTool loginTool;
    public AssignNewPatientInterface(LoginTool loginTool) {
        this.loginTool = loginTool;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        //setting up the JFrame
        setTitle("Assign a new doctor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Assign a new doctor");
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
        JLabel patientLabel = new JLabel("Patient: ");
        topMainPanel.add(patientLabel); //add to panel

        ArrayList<String> patientOptions = new ArrayList<>();
        PatientTool patientTool = new PatientTool();
        ResultSet patients = patientTool.getAllPatients();

        try {
            while (patients.next()) {
                String stringBuilder = patients.getString("FirstName") +
                        " " +
                        patients.getString("Surname") +
                        " (ID: " +
                        patients.getInt("UserID") +
                        ")";
                patientOptions.add(stringBuilder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] patientOption = patientOptions.toArray(new String[patientOptions.size()]);
        this.patientSelector = new JComboBox<>(patientOption);
        topMainPanel.add(patientSelector);

        JLabel doctorLabel = new JLabel("Doctor: ");
        topMainPanel.add(doctorLabel); //add to panel

        ArrayList<String> doctorOptions = new ArrayList<>();
        DoctorTool doctorTool = new DoctorTool();
        ResultSet doctors = doctorTool.getAllDoctors();

        try {
            while (doctors.next()) {
                String stringBuilder = doctors.getString("FirstName") +
                        " " +
                        doctors.getString("Surname") +
                        " (ID: " +
                        doctors.getInt("UserID") +
                        ")";
                doctorOptions.add(stringBuilder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] doctorOption = doctorOptions.toArray(new String[doctorOptions.size()]);
        this.doctorSelector = new JComboBox<>(doctorOption);
        topMainPanel.add(doctorSelector);

        //add top pane to main
        mainPanel.add(topMainPanel , BorderLayout.NORTH);

        //new child panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout( new FlowLayout());

        //adding the child panel to the main panel
        mainPanel.add(secondPanel,BorderLayout.CENTER);

        JButton assignButton = new JButton("Assign");
        secondPanel.add(assignButton);

        assignButton.addActionListener(e -> {
            try {
                int patientIndex = patientSelector.getSelectedIndex();
                int doctorIndex = doctorSelector.getSelectedIndex();

                ResultSet patients2 = patientTool.getAllPatients();
                ResultSet doctors2 = doctorTool.getAllDoctors();

                int count = -1;
                int patientID = -1;
                while (patients2.next()) {
                    count++;
                    if (count == patientIndex) {
                        patientID = patients2.getInt("UserID");
                        break;
                    }
                }

                count = -1;
                int doctorID = -1;
                String doctorSurname = "";
                while (doctors2.next()) {
                    count++;
                    if (count == doctorIndex) {
                        doctorID = doctors2.getInt("UserID");
                        doctorSurname = doctors2.getString("Surname");
                        break;
                    }
                }

                patientTool.changeDoctor(patientID, doctorID);
                System.out.println("Patient Index " + patientIndex);
                NotificationTool notificationTool = new NotificationTool();
                notificationTool.notify(patientID, "You have been reassigned to Dr. " + doctorSurname + ".");

            } catch (SQLException e2) {
                e2.printStackTrace();
            }
        });

        //adding all contents to the container
        add(mainPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            dispose(); // Close this window
            new PatientDetailsInterface(loginTool).setVisible(true); // Open the HomePage
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
        new AssignNewPatientInterface(new LoginTool(true));
    }
}

