package users.Interface;

import users.Student;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Student_UI {
    public void callCourseRegister(String id, String pass, Integer l) throws SQLException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        vinay.viewCourseOfferings();

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();
        System.out.println(vinay.courseRegister(optedCourse));
    }
    public void callCourseWithdraw(String id, String pass, Integer l) throws SQLException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        vinay.viewCourseOfferings();

        System.out.print("Select the course: ");
        Scanner sc = new Scanner(System.in);
        String selectedCourse = sc.nextLine();
        System.out.println(vinay.courseWithdraw(selectedCourse));
    }
    public void callComputerCGPA(String id, String pass, Integer l) throws SQLException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        System.out.println("Your current CGPA is: "+ vinay.computeCPGA());
    }
    public void callViewGrades(String id, String pass, Integer l) throws SQLException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        vinay.viewGrades();
    }
    public void callGenerateTranscript(String id, String pass, Integer l) throws SQLException, IOException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        vinay.generateTranscript();
    }
    public void callCheckDegree(String id, String pass, Integer l) throws SQLException, ClassNotFoundException {
        Student vinay = new Student (id,"student",pass,l);
        System.out.println(vinay.checkDegree());
    }
}
