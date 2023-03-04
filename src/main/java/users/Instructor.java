package users;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.CS305.Main.st;

public class Instructor extends User{
    public Instructor(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }

    private void getInfo(){
        Integer currYear = null;
        Integer currSem = null;
        Integer bool = null;
        String info = "Select * from info;";
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            currYear = rs.getInt(1);
            currSem = rs.getInt(2);
            bool = rs.getInt(3);
            System.out.println(currYear+" "+currSem+" "+bool);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void floatCourse(){

    }

    public void deFloatCourse(){

    }
}
