package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.CS305.Main.st;

public class Student extends User{
    Student(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }

    public void courseRegister(){
        ArrayList<String> courses = new ArrayList<String>();

        String selectQuery = "Select * from courseOffering_";
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

//        Scanner sc=new Scanner(System.in);
//        System.out.print("Opt Course: ");
//        String optedCourse = sc.nextLine();
//        if(courses.contains(optedCourse)){
//            String addQuery = "insert into Student"+uID+" values('"+optedCourse+"','"+year+"','"+sem+"','-1');";
//            try {
//                st.executeQuery(addQuery);
//            }
//            catch (SQLException e) {
//                System.out.println(e);
//            }
//        }
//        else{
//            System.out.println("Please choose a valid course!!");
//        }
    }
}
