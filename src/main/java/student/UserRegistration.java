package student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import static org.CS305.Main.st;

public class UserRegistration {
    public void register(String ID, String userType, String Password){
        String check_query = "Select * from Users;";
        try {
            ResultSet rs = st.executeQuery(check_query);
            while(rs.next()){
                String user = rs.getString(1);
                if(user.equals(ID)){
                    System.out.println("User Already Registered. Try to login");
                    return;
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO Users (userID,userType,Password) VALUES ('"+ID+"','"+userType+"','"+Password+"');";
        try{
            st.executeQuery(query);
        }
        catch (Exception e){
            System.out.println(e);
        }

        if(userType!="Admin"){
            String makeTable = "create table Student"+ID+"(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, grade INTEGER NOT NULL, PRIMARY KEY(CourseId,year,sem));";
            try {
                st.execute(makeTable);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
}
