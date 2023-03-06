package users.Interface;

import users.Student;

import java.sql.SQLException;
import java.util.Scanner;

public class Student_UI {

    public void callCourseRegister() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.viewCourseOfferings();

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();
        vinay.courseRegister(optedCourse);
    }

    public void callCourseWithdraw() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.viewCourseOfferings();

        System.out.println("Select the course");
        Scanner sc = new Scanner(System.in);
        String selectedCourse = sc.nextLine();
        vinay.courseWithdraw(selectedCourse);
    }


}
