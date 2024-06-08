package SourceFiles;

import java.sql.ResultSet;

public class NotificationTool extends DatabaseTool{
    public void notify(int UserID, String message) {
        query("INSERT INTO Notifications(UserID, Message, NotificationDate, NotificationRead) " +
                "VALUES (" + UserID + ", '" + message + "', CURDATE(), 0);");
    }

    public ResultSet getUserNotifications(int UserID) {
        return query("SELECT * FROM Notifications " +
                "WHERE UserID = " + UserID + " AND NotificationRead = 0;");
    }

    public void readMessages(int UserID) {
        query("UPDATE Notifications " +
                "SET NotificationRead = 1 " +
                "WHERE UserID = " + UserID + ";");
    }

    public static void main(String[] args) {
        NotificationTool notificationTool = new NotificationTool();
        notificationTool.notify(7, "Hello!");
    }
}
