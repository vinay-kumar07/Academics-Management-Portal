package org.CS305;

import student.CourseRegistration;
import student.UserRegistration;
import student.viewGrades;
import users.Admin;
import users.Instructor;
import users.User;

import java.util.Scanner;

import java.sql.*;

public class Main {
    public static Connection conn = null;
    public static Statement st = null;
    public static Integer currentSem = 2;
    public static Integer currentYear = 2022;

    public static void main(String[] args) throws SQLException {
        try{
            System.out.println("Trying to connect..");
            String url = "jdbc:postgresql://localhost:5433/";
            String username = "postgres";
            String password = "dbms";
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(url, username, password);
            st = conn.createStatement();
            System.out.println("Connected To Database :)");
        }
        catch (Exception e){
            System.out.println(e);
        }

//        UserRegistration u = new UserRegistration();
//        u.register("2020csb1141","Student","123");

//        CourseRegistration cr = new CourseRegistration();
//        cr.courseRegister("2020csb1141",2,3);

//        viewGrades v = new viewGrades();
//        v.view("2020csb1141");

        {
            Instructor balwinder = new Instructor ("cs001","faculty","iit",2015);
            balwinder.deFloatCourse();
            return;
        }

//        Scanner sc = new Scanner(System.in);
//        System.out.println("Please what is your role: ");
//        Integer loginChoice = sc.nextInt(); String garbage = sc.nextLine();
//        if(loginChoice==1){
//            System.out.println("give id");
//            String inputID = sc.nextLine();
//            System.out.println("give password");
//            String inputPass = sc.nextLine();
//
//            String check_query = "Select * from Users;";
//            try {
//                ResultSet rs = st.executeQuery(check_query);
//                while(rs.next()){
//                    String user = rs.getString(1);
//                    String code = rs.getString(3);
//                    if(user.equals(inputID) && code.equals(inputPass)){
//                        System.out.println("Logged in Successfully :)");
//                        break;
//                    }
//                    else{
//                        return;
//                    }
//                }
//            }
//            catch (SQLException e) {
//                throw new RuntimeException(e);
//            }
//
//            System.out.println("What do you want to do?");
//            System.out.println("1. Create users account");
//            System.out.println("2. Edit Course Catalog");
//            System.out.println("3. View Grades");
//            System.out.println("4. Generate Transcript");
//            System.out.println("5. LogOut");
//
//            Admin admin = new Admin (inputID,"Admin","Admin",2008);
//            Integer Choice = sc.nextInt(); garbage = sc.nextLine();
//            if(Choice==1){
//                admin.createAccount();
//                //hello
//            }
//            else if(Choice==2){
//                admin.editCourseCatalog();
//            }
//            else if(Choice==5){
//                return;
//            }
//            else{
//                System.out.println("Please Choose a valid choice!!");
//            }
//
//        }
//        else if(loginChoice==2){
//            //faculty
//        }
//        else if(loginChoice==3){
//            //student
//        }
//        else{
//            System.out.println("Please enter a valid choice!!");
//        }

    }
}