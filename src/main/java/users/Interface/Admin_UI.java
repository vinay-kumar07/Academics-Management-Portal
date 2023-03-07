package users.Interface;

import users.Admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin_UI {

    public void callCreateAccount() throws SQLException, IOException, ClassNotFoundException {
        System.out.println("Enter Account Details:");
        Scanner sc = new Scanner(System.in);
        System.out.print("UserID: "); String ID = sc.nextLine();
        System.out.print("User Type(student/faculty): "); String type = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        System.out.print("Enrollment Year: "); Integer enrollyear = sc.nextInt();

        Admin admin = new Admin ("Admin","Admin","admin",2008);
        System.out.println(admin.createAccount(ID,type,pass,enrollyear));
    }

    public void callEditCourseCatalog() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);

        System.out.println("Following courses are available in the Course Catalog:-");
        admin.viewCourseCatalog();
        System.out.println("-----------------------------");

        System.out.println("1. Add new course");
        System.out.println("2. Delete course");
        System.out.println("3. Edit existing course");
        System.out.print("Make choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            System.out.println("Enter course details:-");
            System.out.print("Course ID: "); String course = sc.nextLine();
            System.out.print("L-T-P: "); String lpt = sc.nextLine();
            System.out.print("Pre Requisites: "); String prereq = sc.nextLine();
            System.out.print("Branches for which the course is core(comma separated): "); String core = sc.nextLine();
            System.out.print("Branches for which the course is elective(comma separated): "); String elec = sc.nextLine();
            System.out.println(admin.addcourse(course,lpt,prereq,core,elec));
        } else if (choice == 2) {
            System.out.println("Enter Course you want to delete:-");
            System.out.print("Course ID: ");
            String course = sc.nextLine();
            System.out.println(admin.deletecourse(course));
        } else if (choice == 3) {
            System.out.println("Enter Course ID which you want to edit:");
            System.out.print("Course ID: "); String course = sc.nextLine();
            System.out.println("Now enter updated core branches");
            System.out.print("Upadated Core Branches: "); String updatedcore = sc.nextLine();
            System.out.println(admin.editcourse(course,updatedcore));
        } else {
            System.out.println("Please choose a valid choice!!");
        }
    }

    public void callChangeAcademicYearSem() throws SQLException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        Scanner sc = new Scanner(System.in);
        System.out.print("What is the current academic year?"); Integer y = sc.nextInt();
        System.out.print("What is the current sem?"); Integer s = sc.nextInt();
        System.out.print("Is course add/drop allowed?(0/1)"); Integer o = sc.nextInt();
        System.out.println(admin.changeAcademicYearSem(y,s,o));
    }

    public void callViewGrades() throws SQLException, ClassNotFoundException, IOException {
        System.out.println("You want to see grades of a Course or a specific Student??");
        System.out.println("1. For Student");
        System.out.println("2. For Course");
        Scanner sc = new Scanner(System.in);
        Integer choice = sc.nextInt();
        if(choice==1){
            Admin admin = new Admin ("Admin","Admin","admin",2008);
            System.out.print("Enter Student Entry No:"); String sid = sc.nextLine();
            admin.viewGradesStudent(sid);
        } else if (choice==2) {
            Admin admin = new Admin ("Admin","Admin","admin",2008);
            System.out.println("The Following courses are currently offered:-");
            admin.viewCourseOffering();
            System.out.print("Enter Course ID:"); String cid = sc.nextLine();
            System.out.print("Enter Year:"); Integer y = sc.nextInt();
            System.out.print("Enter Sem:"); Integer s = sc.nextInt();
            admin.viewGradesCourse(cid,y,s);
        } else {
            System.out.println("Not a valid choice!!");
        }
    }
}
