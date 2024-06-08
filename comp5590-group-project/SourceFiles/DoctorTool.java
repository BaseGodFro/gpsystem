package SourceFiles;

import java.sql.ResultSet;

public class DoctorTool extends DatabaseTool{
    public ResultSet getAllDoctors() {
        return query("SELECT * FROM Doctors INNER JOIN Users on Users.UserID = Doctors.UserID;");
    }
}
