package student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.SimpleTimeZone;

import static org.CS305.Main.conn;
import static org.CS305.Main.st;

public class CourseRegistration {
    public static void courseRegister(String uID, Integer sem, Integer year){
        ArrayList<String> courses = new ArrayList<String>();

        String selectQuery = "Select * from courseOffering";
        System.out.println("The following courses are available for enrollment: ");

        try {
            ResultSet rs = st.executeQuery(selectQuery);
            while(rs.next()){
                System.out.println(rs.getString(3));
                courses.add(rs.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();
        if(courses.contains(optedCourse)){
            String addQuery = "insert into Student"+uID+" values('"+optedCourse+"','"+year+"','"+sem+"','-1');";
            try {
                st.executeQuery(addQuery);
            }
            catch (SQLException e) {
                System.out.println(e);
            }
        }
        else{
            System.out.println("Please choose a valid course!!");
        }
    }
}
