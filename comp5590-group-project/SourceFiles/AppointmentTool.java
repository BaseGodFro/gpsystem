package SourceFiles;

import java.sql.ResultSet;

/**
 * Tool for getting appointments
 */
public class AppointmentTool extends DatabaseTool{
    /**
     * For when you know exactly which appointment you want
     *
     * @param ID ID criteria
     * @return ResultSet all appointments with that ID
     */
    public ResultSet getAppointmentByID(int ID) {
        return query("SELECT * FROM Appointments WHERE AppointmentID=" + ID + ";");
    }

    public ResultSet getAppointmentByMonthYear(int UserID, int month, int year) {
        String SQLquery = String.format(("SELECT * " +
                "FROM Appointments " +
                "INNER JOIN Users as PatientUsers ON PatientUsers.UserID = Appointments.PatientID " +
                "INNER JOIN Users as DoctorUsers ON DoctorUsers.UserID = Appointments.DoctorID " +
                "WHERE YEAR(Appointments.AppointmentDate) = %d AND MONTH(Appointments.AppointmentDate) = %d AND " +
                "DoctorUsers.UserID = %d"), year, month, UserID);
        return query(SQLquery);
    }

    public ResultSet getAppointmentsBy(String columnName, String criteria) {
        switch (columnName) {
            case "Appointment ID" -> columnName = "AppointmentID";
            case "Patient ID" -> columnName = "PatientID";
            case "Doctor ID" -> columnName = "DoctorID";
            case "Appointment Date" -> columnName = "AppointmentDate";
        }
        return query(String.format("SELECT * FROM Appointments WHERE %s = %s;", columnName, criteria));
    }

    public ResultSet getUserAppointmentsBy(int UserID, String columnName, String criteria) {
        switch (columnName) {
            case "Appointment ID" -> columnName = "AppointmentID";
            case "Patient ID" -> columnName = "PatientID";
            case "Doctor ID" -> columnName = "DoctorID";
            case "Appointment Date" -> columnName = "AppointmentDate";
        }
        System.out.println(UserID);
        return query("SELECT * FROM Appointments WHERE " + columnName + " = " + criteria + " AND (DoctorID = " + UserID + " OR PatientID = " + UserID + ");");
    }

    public ResultSet getDoctorAppointments(int DoctorID) {
        return query("SELECT * FROM Appointments " +
                "INNER JOIN Patients ON Patients.UserID = Appointments.PatientID " +
                "INNER JOIN Users ON Users.UserID = Patients.UserID " +
                "WHERE Appointments.DoctorID = " + DoctorID + ";");
    }

    public void createAppointment(int PatientID, int DoctorID, String appointmentDate, String Notes) {
        query("INSERT INTO Appointments(PatientID, DoctorID, AppointmentDate, Notes) " +
                String.format("VALUES (%d, %d, '%s', '%s');", PatientID, DoctorID, appointmentDate, Notes));
    }
}
