package SourceFiles;

import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;

public class HomePage extends JFrame {
    private final LoginTool loginTool;
    public HomePage(LoginTool loginTool) {
        super("System");
        this.loginTool = loginTool;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout()); // Use BorderLayout

        // Top menu
        JPanel panel = createTopMenu();
        add(panel, BorderLayout.CENTER);

        // Log Out button
        JButton logoutButton = new JButton("Log Out");
        logoutButton.addActionListener(e -> {
            this.dispose(); // Close the HomePage
            new LoginPage(new LoginTool()).setVisible(true); // Open the LoginPage
        });

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(logoutButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }



    private JPanel createTopMenu() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(15, 12, 15, 12));

        if (loginTool.userIsPatient) {
            // Button to navigate to BookingAppointmentPage
            JButton appointmentButton = new JButton("Book Appointment");
            appointmentButton.addActionListener(e -> {
                dispose();
                this.setVisible(false); // Hide the HomePage
                new BookingAppointmentInterface(loginTool).setVisible(true); // Open the BookingAppointmentPage
            });

            panel.add(appointmentButton);
        }

        // Button to navigate to BookingAppointmentPage
        JButton viewAppointmentButton = new JButton("View Appointments");
        viewAppointmentButton.addActionListener(e -> {
            this.setVisible(false); // Hide the HomePage
            // Assuming BookingAppointmentPage is a JFrame or a class that extends JFrame
            new AppointmentsInterface(loginTool).setVisible(true); // Open the AppointmentInterface
        });

        panel.add(viewAppointmentButton);

        JButton notifsButton = new JButton("View Notifications");
        notifsButton.addActionListener(e -> {
            this.setVisible(false); // Hide the HomePage
            // Assuming BookingAppointmentPage is a JFrame or a class that extends JFrame
            new NotificationCentre(loginTool).setVisible(true); // Open the AppointmentInterface
        });

        panel.add(notifsButton);

        if (loginTool.userIsDoctor) {
            JButton patientButton = new JButton("Patients");
            // Add action handlers for buttons to navigate to different sections
            patientButton.addActionListener(e -> {
                this.setVisible(false); // Hide the HomePage
                new PatientDetailsInterface(loginTool).setVisible(true); // Show the PatientDetailsInterface
            });

            panel.add(patientButton);

            // Dashboard button setup
            JButton dashboardButton = new JButton("Dashboard");
            dashboardButton.addActionListener(e -> {
                this.dispose(); // Close the HomePage
                new DoctorDashboard(loginTool).setVisible(true); // Open the DoctorDashboard
            });

            panel.add(dashboardButton);

            JButton visitButton = new JButton("View Visits");
            visitButton.addActionListener(e -> {
                this.dispose(); // Close the HomePage
                new VisitDetailsInterface(loginTool).setVisible(true); // Open the DoctorDashboard
            });

            panel.add(visitButton);

            JButton messageButton = new JButton("Message Admins");
            messageButton.addActionListener(e -> {
                this.dispose(); // Close the HomePage
                new MessageAdminInterface(loginTool).setVisible(true); // Open the DoctorDashboard
            });

            panel.add(messageButton);
        }

        return panel;
    }

public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new HomePage(new LoginTool(true)).setVisible(true));
}


}
