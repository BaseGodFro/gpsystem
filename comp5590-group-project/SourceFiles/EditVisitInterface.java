package SourceFiles;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

public class EditVisitInterface extends JFrame {
    public JComboBox<String> visitSelector;
    public JTextField detailsField;
    public JTextField prescriptionField;
    public LoginTool loginTool;
    public EditVisitInterface(LoginTool loginTool) {
        this.loginTool = loginTool;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        //setting up the JFrame
        setTitle("Change a visit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Change a visit");
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
        JLabel patientLabel = new JLabel("Visit to change: ");
        topMainPanel.add(patientLabel); //add to panel

        ArrayList<String> visitOptions = new ArrayList<>();
        VisitTool visitTool = new VisitTool();
        ResultSet visits = visitTool.getUserVisits(loginTool.UserID);

        try {
            while (visits.next()) {
                String stringBuilder = visits.getString("FirstName") +
                        " " +
                        visits.getString("Surname") +
                        " (on " +
                        visits.getDate("AppointmentDate") +
                        ")";
                visitOptions.add(stringBuilder);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] appointmentOption = visitOptions.toArray(new String[visitOptions.size()]);
        this.visitSelector = new JComboBox<>(appointmentOption);
        topMainPanel.add(visitSelector);

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

        JButton createButton = new JButton("Change");
        bottomPanel.add(createButton);

        createButton.addActionListener(e -> {
            try {
                int patientIndex = visitSelector.getSelectedIndex();

                ResultSet appointments = visitTool.getUserVisits(loginTool.UserID);

                int count = 0;
                int visitID = -1;
                while (appointments.next()) {
                    count++;
                    if (count == patientIndex) {
                        visitID = appointments.getInt("VisitID");
                        break;
                    }
                }

                String details = detailsField.getText();
                String prescription = prescriptionField.getText();

                VisitTool visitTool1 = new VisitTool();
                visitTool1.editVisit(visitID, details, prescription);

                detailsField.setText("");
                prescriptionField.setText("");

                EditVisitInterface.this.notify("Edited a visit: " + Objects.requireNonNull(visitSelector.getSelectedItem()));

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
        new EditVisitInterface(new LoginTool(true));
    }
}

