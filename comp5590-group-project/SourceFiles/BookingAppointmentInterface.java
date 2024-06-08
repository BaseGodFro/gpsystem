package SourceFiles;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BookingAppointmentInterface extends JFrame {
    public JComboBox<String> doctorSelector;
    public JTextField dateSelector;
    public JTextField notesField;
    public LoginTool loginTool;
    public BookingAppointmentInterface(LoginTool loginTool) {
        this.loginTool = loginTool;

        if (!loginTool.userIsDoctor) {
            this.dispose(); // Close the DoctorDashboard
            new HomePage(loginTool).setVisible(true);
        }

        //setting up the JFrame
        setTitle("Book an appointment");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Creating the header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(Color.decode("#007bff"));
        JLabel headerLabel = new JLabel("Book an appointment");
        headerLabel.setForeground(Color.BLACK);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 50));
        headerPanel.add(headerLabel);
        add(headerPanel, BorderLayout.NORTH);

        //Creating the main panel
        JPanel mainPanel = new JPanel();
        JPanel topMainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        topMainPanel.setLayout( new FlowLayout());

        JLabel drLabel = new JLabel("Doctor: ");
        topMainPanel.add(drLabel); //add to panel

        ArrayList<String> doctorOptions = new ArrayList<>();
        DoctorTool doctorTool = new DoctorTool();
        ResultSet doctors = doctorTool.getAllDoctors();

        try {
            while (doctors.next()) {
                doctorOptions.add("Dr. " + doctors.getString("Surname"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        String[] doctorOption = doctorOptions.toArray(new String[doctorOptions.size()]);
        doctorSelector = new JComboBox<>(doctorOption);
        topMainPanel.add(doctorSelector);

        JLabel dateLabel = new JLabel("Date (yyyy-mm-dd): ");
        topMainPanel.add(dateLabel); //add to panel

        dateSelector = new JTextField(10);
        topMainPanel.add(dateSelector);

        mainPanel.add(topMainPanel , BorderLayout.NORTH);

        //new child panel
        JPanel secondPanel = new JPanel();
        secondPanel.setLayout( new FlowLayout());

        JLabel notesLabel = new JLabel("Notes: ");
        secondPanel.add(notesLabel); //add to panel

        notesField = new JTextField(20);
        secondPanel.add(notesField);

        //adding the child panel to the main panel
        mainPanel.add(secondPanel,BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JButton bookButton = new JButton("Book");
        bottomPanel.add(bookButton);

        bookButton.addActionListener(e -> {
            try {
                int doctorIndex = doctorSelector.getSelectedIndex();

                int count = -1;
                int doctorID = -1;
                while (doctors.next()) {
                    count++;
                    if (count == doctorIndex) {
                        doctorID = doctors.getInt("UserID");
                        break;
                    }
                }

                String notes = notesField.getText();

                AppointmentTool appointmentTool = new AppointmentTool();
                appointmentTool.createAppointment(loginTool.UserID, doctorID, dateSelector.getText(), notes);

                NotificationTool notificationTool = new NotificationTool();
                notificationTool.notify(loginTool.UserID, ("You have just booked an appointment with " + doctorSelector.getSelectedItem()) + " on " + dateSelector.getText());
                notificationTool.notify(doctorID, ("A new appointment has just been booked with you with " + loginTool.username));

                notesField.setText("");

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
        new BookingAppointmentInterface(new LoginTool(true));
    }
}

