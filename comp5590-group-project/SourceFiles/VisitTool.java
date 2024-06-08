package SourceFiles;

import java.sql.ResultSet;

public class VisitTool extends DatabaseTool{
    public void createVisit(int AppointmentID, String details, String prescription) {
        query("INSERT INTO Visits(AppointmentID, Details, Prescriptions) " +
                "VALUES (" + AppointmentID + ", '" + details + "', '" + prescription + "');");
        System.out.println("INSERT INTO Visits(AppointmentID, Details, Prescriptions) " +
                "VALUES (" + AppointmentID + ", '" + details + "', '" + prescription + "');");
    }

    public void editVisit(int VisitID, String details, String prescription) {
        query("UPDATE Visits " +
                "SET Details = '" + details + "', Prescription = '" + prescription + "' " +
                "WHERE VisitID=" + VisitID + ";");
    }

    public ResultSet getUserVisits(int UserID) {
        return query("SELECT * FROM Visits " +
                "INNER JOIN Appointments on Appointments.AppointmentID = Visits.AppointmentID " +
                "INNER JOIN Patients on Patients.UserID = Appointments.PatientID " +
                "INNER JOIN Doctors on Doctors.UserID = Appointments.DoctorID " +
                "INNER JOIN Users as PatientUsers on PatientUsers.UserID = Patients.UserID " +
                "INNER JOIN Users as DoctorUsers on DoctorUsers.UserID = Doctors.UserID " +
                "WHERE PatientUsers.UserID = " + UserID + " OR " +
                "DoctorUsers.UserID = " + UserID + ";");
    }

    public ResultSet getUserVisitsBy(int UserID, String columnName, String criteria) {
        switch (columnName) {
            case "Patient First Name" -> columnName = "PatientUsers.FirstName";
            case "Patient Surname" -> columnName = "PatientUsers.Surname";
            case "Date of Visit" -> columnName = "Appointments.AppointmentDate";
        }
        return query("SELECT * FROM Visits " +
                "INNER JOIN Appointments on Appointments.AppointmentID = Visits.AppointmentID " +
                "INNER JOIN Patients on Patients.UserID = Appointments.PatientID " +
                "INNER JOIN Doctors on Doctors.UserID = Appointments.DoctorID " +
                "INNER JOIN Users as PatientUsers on PatientUsers.UserID = Patients.UserID " +
                "INNER JOIN Users as DoctorUsers on DoctorUsers.UserID = Doctors.UserID " +
                "WHERE " + columnName + " = '" + criteria + "' " +
                "AND (PatientUsers.UserID = " + UserID + " OR " +
                "DoctorUsers.UserID = " + UserID + ");");
    }
}
