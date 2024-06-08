package SourceFiles;

import java.sql.ResultSet;

public class AdminTool extends DatabaseTool{
    public ResultSet getAllAdmins() {
        return query("SELECT * FROM Admins " +
                "INNER JOIN Users On Users.UserID = Admins.UserID;");
    }
}
