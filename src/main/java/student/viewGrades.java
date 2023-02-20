package student;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.CS305.Main.st;

public class viewGrades {
    public static void view(String uID){
        String viewQuery = "Select * from student"+uID;
        try {
            ResultSet rs =  st.executeQuery(viewQuery);
            while(rs.next()){
                System.out.println(rs.getString(1) +" "+ rs.getString(4));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
