package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.CS305.Main.st;

public class User {
    public String UserID;
    public String Type;
    public Integer enrollYear;
    private String Password;

    public User(String UserID, String Type, String Password, Integer enrollYear){
        this.UserID = UserID;
        this.Type = Type;
        this.Password = Password;
        this.enrollYear = enrollYear;
    }
}
