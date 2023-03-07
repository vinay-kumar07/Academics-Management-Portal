package users.Interface;
import users.Instructor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Instuctor_UI {
    public void callFloatCourse(String id, String pass, Integer l) throws SQLException, IOException, ClassNotFoundException {
        Instructor instructor = new Instructor (id,"faculty",pass,l);
        instructor.viewCourseCatalog();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course details:-");
        System.out.print("Course ID: "); String course = sc.nextLine();
        System.out.print("CGPA Requirement: "); Float cg = sc.nextFloat(); String garbage = sc.nextLine();
        System.out.print("Allowed Batches: "); String batch = sc.nextLine();
        System.out.println(instructor.floatCourse(course,cg,batch));
    }

    public void callDefloatCourse(String id, String pass, Integer l) throws SQLException, IOException, ClassNotFoundException {
        Instructor instructor = new Instructor (id,"faculty",pass,l);
        instructor.viewCourseOffering();
        System.out.println("Enter details of the course you want to delete:-");
        Scanner sc = new Scanner(System.in);
        System.out.print("Course ID: "); String CID = sc.nextLine();
        System.out.print("Year: "); Integer y = sc.nextInt();
        System.out.print("Sem: "); Integer s = sc.nextInt();
        System.out.println(instructor.deFloatCourse(CID,y,s));
    }

    public void callUpdateGrades(String id, String pass, Integer l) throws SQLException, ClassNotFoundException, IOException {
        Instructor instructor = new Instructor (id,"faculty",pass,l);
        instructor.viewCourseOffering();
        System.out.println("Enter Course ID:-");
        Scanner sc = new Scanner(System.in);
        System.out.print("Course ID: "); String CID = sc.nextLine();
        System.out.print("CSV File Name: "); String name = sc.nextLine();
        System.out.println(instructor.updateGrades(CID,name));
    }

    public void callViewGrades(String id, String pass, Integer l) throws SQLException, IOException, ClassNotFoundException {
        Instructor instructor = new Instructor (id,"faculty",pass,l);
        instructor.viewCourseOffering();
        System.out.println("Enter Course ID:-");
        Scanner sc = new Scanner(System.in);
        System.out.print("Course ID: "); String CID = sc.nextLine();
        System.out.print("Year: "); Integer y = sc.nextInt();
        System.out.println("Sem: "); Integer s = sc.nextInt();
        instructor.viewGrades(CID,y,s);
    }
}
