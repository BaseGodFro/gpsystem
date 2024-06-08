package SourceFiles;

import javax.swing.*;
import java.awt.*;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CreateVisitInterface extends JFrame {
    public JComboBox<String> appointmentSelector;
    public JTextField detailsField;
    public JTextField prescriptionField;
    public LoginTool loginTool;
    public CreateVisitInterface(LoginTool loginTool) {
        this.loginTool = loginTool;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        //setting up the JFrame
        setTitle("Record a visit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Record a visit");
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
        JLabel patientLabel = new JLabel("Appointment: ");
        topMainPanel.add(patientLabel); //add to panel

        ArrayList<String> appointmentOptions = new ArrayList<>();
        AppointmentTool appointmentTool = new AppointmentTool();
        ResultSet appointments = appointmentTool.getDoctorAppointments(loginTool.UserID);

        try {
            while (appointments.next()) {
                String stringBuilder = appointments.getString("FirstName") +
                        " " +
                        appointments.getString("Surname") +
                        " (on " +
                        appointments.getDate("AppointmentDate") +
                        ")";
                appointmentOptions.add(stringBuilder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] appointmentOption = appointmentOptions.toArray(new String[appointmentOptions.size()]);
        this.appointmentSelector = new JComboBox<>(appointmentOption);
        topMainPanel.add(appointmentSelector);

        mainPanel.add(topMainPanel , BorderLayout.NORTH);

        //new child panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout( new FlowLayout());

        JLabel detailsLabel = new JLabel("Details: ");
        secondPanel.add(detailsLabel); //add to panel

        detailsField = new JTextField(20);
        secondPanel.add(detailsField);

        JLabel prescriptionsLabel = new JLabel("Prescriptions: ");
        secondPanel.add(prescriptionsLabel); //add to panel

        prescriptionField = new JTextField(20);
        secondPanel.add(prescriptionField);

        //adding the child panel to the main panel
        mainPanel.add(secondPanel,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton createButton = new JButton("Create");
        bottomPanel.add(createButton);

        createButton.addActionListener(e -> {
            try {
                int appointmentIndex = appointmentSelector.getSelectedIndex();
                ResultSet appointments2 = appointmentTool.getDoctorAppointments(loginTool.UserID);

                int count = -1;
                int appointmentID = -1;
                while (appointments2.next()) {
                    count++;
                    if (count == appointmentIndex) {
                        appointmentID = appointments2.getInt("AppointmentID");
                        break;
                    }
                }

                System.out.println("Appointment Index" + appointmentIndex);

                String details = detailsField.getText();
                String prescription = prescriptionField.getText();

                VisitTool visitTool = new VisitTool();
                visitTool.createVisit(appointmentID, details, prescription);

                detailsField.setText("");
                prescriptionField.setText("");

                CreateVisitInterface.this.notify("Recorded a visit: " + appointmentSelector.getSelectedItem());

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
            new VisitDetailsInterface(loginTool).setVisible(true); // Open the HomePage
        });
        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backButtonPanel.add(backButton);
        bottomPanel.add(backButtonPanel, BorderLayout.WEST);

        add(bottomPanel, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void notify(String message) {
        JOptionPane.showMessageDialog(this, message);
        NotificationTool notificationTool = new NotificationTool();
        notificationTool.notify(loginTool.UserID, message);
    }

    public static void main(String[] args) {
        new CreateVisitInterface(new LoginTool(true));
    }
}

