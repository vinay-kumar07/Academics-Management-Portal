package users.Interface;

import users.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Student_UI {
    public void callCourseRegister() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.viewCourseOfferings();

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();
        System.out.println(vinay.courseRegister(optedCourse));
    }
    public void callCourseWithdraw() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.viewCourseOfferings();

        System.out.print("Select the course: ");
        Scanner sc = new Scanner(System.in);
        String selectedCourse = sc.nextLine();
        System.out.println(vinay.courseWithdraw(selectedCourse));
    }
    public void callComputerCGPA() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        System.out.println("Your current CGPA is: "+ vinay.computeCPGA());
    }
    public void callViewGrades() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.viewGrades();
    }
    public void callGenerateTranscript() throws SQLException, IOException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        vinay.generateTranscript();
    }
    public void callCheckDegree() throws SQLException, ClassNotFoundException {
        Student vinay = new Student ("cs1141","student","iit",2022);
        System.out.println(vinay.checkDegree());
    }
}
