package org.CS305;

import users.Interface.Admin_UI;
import users.Interface.Instuctor_UI;
import users.Interface.Student_UI;

import java.io.IOException;
import java.util.Scanner;

import java.sql.*;

public class Main {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        System.out.println("--Welcome to Academic Management Portal--");

        Scanner sc = new Scanner(System.in);
        System.out.println("Please Enter Your Role.");
        System.out.println("1. Admin");
        System.out.println("2. Instructor");
        System.out.println("3. Student");

        System.out.print("Enter choice: "); Integer loginChoice = sc.nextInt(); String garbage = sc.nextLine();
        if(loginChoice<=0 || loginChoice>=4)    {
            System.out.println("Not a valid choice");
        }
        else {
            System.out.println("Enter ID: "); String id = sc.nextLine();
            System.out.println("Enter Password: "); String pass = sc.nextLine();
            Login login = new Login();
            Integer l = login.enter(id,pass);
            if(l==0){
                System.out.println("Not a valid user");
                return;
            }
            else {

                if (loginChoice==1){
                    System.out.println("Choose What Do You Want To Do");
                    System.out.println("1. Create Account");
                    System.out.println("2. Edit Course Catalog");
                    System.out.println("3. Change Academic Year");
                    System.out.println("4. View Grades");
                    Admin_UI ui = new Admin_UI();
                    Integer adminChoice = sc.nextInt(); garbage = sc.nextLine();
                    if(adminChoice==1){
                        ui.callCreateAccount();
                    }
                    else if(adminChoice==2){
                        ui.callEditCourseCatalog();
                    }
                    else if(adminChoice==3){
                        ui.callChangeAcademicYearSem();
                    }
                    else if(adminChoice==4){
                        ui.callViewGrades();
                    }
                    else {
                        System.out.println("Not a valid choice");
                    }
                }
                else if (loginChoice==2){
                    System.out.println("Choose What Do You Want To Do");
                    System.out.println("1. Float Course");
                    System.out.println("2. Remove Course");
                    System.out.println("3. Update Grades");
                    System.out.println("4. View Grades");
                    Instuctor_UI ui = new Instuctor_UI();
                    Integer instructorChoice = sc.nextInt(); garbage = sc.nextLine();
                    if(instructorChoice==1){
                        ui.callFloatCourse(id,pass,l);
                    }
                    else if(instructorChoice==2){
                        ui.callDefloatCourse(id,pass,l);
                    }
                    else if(instructorChoice==3){
                        ui.callUpdateGrades(id,pass,l);
                    }
                    else if(instructorChoice==4){
                        ui.callViewGrades(id,pass,l);
                    }
                    else {
                        System.out.println("Not a valid choice");
                    }
                }
                else if(loginChoice==3){
                    System.out.println("Choose What Do You Want To Do");
                    System.out.println("1. Course Registration");
                    System.out.println("2. Course Withdraw");
                    System.out.println("3. Compute CGPA");
                    System.out.println("4. View Grades");
                    System.out.println("5. Generate Transcript");
                    System.out.println("6. Degree Check");
                    Student_UI ui = new Student_UI();
                    Integer studentChoice = sc.nextInt(); garbage = sc.nextLine();
                    if(studentChoice==1){
                        ui.callCourseRegister(id,pass,l);
                    }
                    else if(studentChoice==2){
                        ui.callCourseWithdraw(id,pass,l);
                    }
                    else if(studentChoice==3){
                        ui.callComputerCGPA(id,pass,l);
                    }
                    else if(studentChoice==4){
                        ui.callViewGrades(id,pass,l);
                    }
                    else if(studentChoice==5){
                        ui.callGenerateTranscript(id,pass,l);
                    }
                    else if(studentChoice==6){
                        ui.callCheckDegree(id,pass,l);
                    }
                    else {
                        System.out.println("Not a valid choice");
                    }
                }

            }
        }

    }
}