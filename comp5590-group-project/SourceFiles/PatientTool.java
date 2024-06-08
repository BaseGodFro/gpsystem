package SourceFiles;
import com.mysql.cj.xdevapi.Column;

import java.sql.ResultSet;

/**
 * Tool for creating and updating patients
 */
public class PatientTool extends DatabaseTool{
    /**
     * Gets Patient by the given ID
     * @param ID ID of patient
     * @return ResultSet of patients
     */
    public ResultSet getPatientByUserID(int ID) {
        String SQLQuery = String.format("SELECT * FROM Patients INNER JOIN Users ON Patients.UserID=Users.UserID WHERE Users.UserID=%d", ID);
        return query(SQLQuery);
    }

    /**
     * Changes the assigned doctor of a patient
     * @param PatientID Patient to be changed
     * @param newDoctorID New doctor ID
     */
    public void changeDoctor(int PatientID, int newDoctorID) {
        String SQLQuery = String.format("UPDATE Patients SET PrimaryCarePhysicianID=%d WHERE UserID=%d;", newDoctorID, PatientID);
        query(SQLQuery);
    }

    /**
     * Gets an existing User and marks them as a patient
     * @param UserID Existing User ID
     * @param PrimaryCarePhysicianID ID of Patient's doctor
     */
    public void createPatient(int UserID, int PrimaryCarePhysicianID) {
        String SQLQuery = String.format("INSERT INTO Patients(UserID, PrimaryCarePhysicianID) VALUES (%d, %d);", UserID, PrimaryCarePhysicianID);
        query(SQLQuery);
    }

    public ResultSet getPatientBy(String columnName, String criteria) {
        if (columnName.equals("Patient ID")) {
            columnName = "Patients.UserID";
        } else if (columnName.equals("Primary Care Physician ID")) {
            columnName = "Patients.PrimaryCarePhysicianID";
        } else {
            columnName = "Users." + columnName;
        }
        return query(String.format("SELECT * FROM Patients INNER JOIN Users on Users.UserID = Patients.UserID WHERE %s = %s;", columnName, criteria));
    }

    public ResultSet getUserPatientBy(int UserID, String columnName, String criteria) {
        if (columnName.equals("Patient ID")) {
            columnName = "Patients.UserID";
        } else if (columnName.equals("Primary Care Physician ID")) {
            columnName = "Patients.PrimaryCarePhysicianID";
        } else {
            columnName = "Users." + columnName;
        }
        return query("SELECT * FROM Patients INNER JOIN Users on Users.UserID = Patients.UserID WHERE " + columnName + " = " + criteria + " AND Patients.UserID = " + UserID + ";");
    }

    public ResultSet getAllPatients() {
        return query("SELECT * FROM PATIENTS INNER JOIN Users ON Users.UserID = Patients.UserID;");
    }

    public ResultSet getPatientsOfDoctor(int DoctorID) {
        return query("SELECT * FROM Patients " +
                "INNER JOIN Users ON Users.UserID = Patients.UserID " +
                "WHERE Patients.PrimaryCarePhysicianID=" + DoctorID + ";");
    }
}
