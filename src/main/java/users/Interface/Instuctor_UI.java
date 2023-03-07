package users.Interface;
import users.Instructor;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Instuctor_UI {
    public void callFloatCourse() throws SQLException, IOException, ClassNotFoundException {
        Instructor instructor = new Instructor ("TS1","faculty","iit",2020);
        instructor.viewCourseCatalog();
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course details:-");
        System.out.print("Course ID: "); String course = sc.nextLine();
        System.out.print("CGPA Requirement: "); Float cg = sc.nextFloat(); String garbage = sc.nextLine();
        System.out.print("Allowed Batches: "); String batch = sc.nextLine();
        instructor.floatCourse(course,cg,batch);
    }

    public void callDefloatCourse() throws SQLException, IOException, ClassNotFoundException {
        Instructor instructor = new Instructor ("TS1","faculty","iit",2020);
        instructor.viewCourseOffering();
        System.out.println("Enter details of the course you want to delete:-");
        Scanner sc = new Scanner(System.in);
        System.out.print("Course ID: "); String CID = sc.nextLine();
        System.out.print("Year: "); Integer y = sc.nextInt();
        System.out.print("Sem: "); Integer s = sc.nextInt();
        instructor.deFloatCourse(CID,y,s);
    }

    public void callUpdateGrades() throws SQLException, ClassNotFoundException, IOException {
        Instructor instructor = new Instructor ("TS1","faculty","iit",2020);
        instructor.viewCourseOffering();
        System.out.println("Enter Course ID:-");
        Scanner sc = new Scanner(System.in);
        System.out.print("Course ID: "); String CID = sc.nextLine();
        System.out.print("CSV File Name: "); String name = sc.nextLine();
        instructor.updateGrades(CID,name);
    }


}
