package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import  java.util.Scanner;

import static org.CS305.Main.st;
public class Admin extends User{
    public Admin(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }
//make accounts of users

    public void createAccount(){
        Scanner sc = new Scanner(System.in);
        String ID = sc.nextLine();
        String type = sc.nextLine();
        String pass = sc.nextLine();
        String enrollyear = sc.nextLine();

        String check_query = "Select * from Users;";
        try {
            ResultSet rs = st.executeQuery(check_query);
            while(rs.next()){
                String user = rs.getString(1);
                if(user.equals(ID)){
                    System.out.println("User Already Registered. Try to login");
                    return;
                }
            }
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO Users VALUES ('"+ID+"','"+type+"','"+pass+"','"+enrollyear+"');";
        try{
            st.executeQuery(query);
        }
        catch (Exception e){
            System.out.println(e);
        }

        if(type.equals("student")){
            String makeTable = "create table Student"+ID+"(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, grade INTEGER NOT NULL, credit float NOT NULL, PRIMARY KEY(CourseId,year,sem));";
            try {
                st.execute(makeTable);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

        else if(type.equals("faculty")){
            String makeTable = "create table Faculty"+ID+"(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, credit float NOT NULL PRIMARY KEY(CourseId,year,sem));";
            try {
                st.execute(makeTable);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

    private void viewCourseCatalog(){
        String viewQuery = "select * from coursecatalog;";
        try {
            ResultSet rs = st.executeQuery(viewQuery);
            while (rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(5) + " " +rs.getString(6) + " " + rs.getString(7));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private void addcourse(){
        //check if a course already exist
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course details:-");
        String course = sc.nextLine();
        String lpt = sc.nextLine();
        String prereq = sc.nextLine();
        String core = sc.nextLine();
        String elec = sc.nextLine();
        Integer year = sc.nextInt();

        String[] tokens = lpt.split("-");
        Integer L = Integer.parseInt(tokens[0]);
        Integer T = Integer.parseInt(tokens[1]);
        Integer P = Integer.parseInt(tokens[2]);

        String addQuery = "insert into coursecatalog values('"+course+"',"+L+","+T+","+P+",'"+prereq+"','"+core+"','"+elec+"',"+year+")";
//        System.out.println(addQuery);
        try {
            st.executeQuery(addQuery);
        } catch (SQLException e) {
            System.out.println(e);
        }
        String makeTable = "create table "+course+"_"+Integer.toString(year)+"(StudentId varchar(20) NOT NULL, enrollyear INTEGER NOT NULL, grade float NOT NULL, PRIMARY KEY(StudentId));";
        try {
            st.executeQuery(makeTable);
        } catch (SQLException e) {
            System.out.println(e);
        }

    }

    private void deletecourse(){
        //check if the course details entered exit
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter details of the course you want to delete:-");
        String course = sc.nextLine();
        Integer year = sc.nextInt();

        String delQuery = "delete from coursecatalog where courseid = '"+course+"' and year = "+Integer.toString(year)+";";
        String dropTable = "drop table "+course+"_"+Integer.toString(year)+";";
        System.out.println(delQuery);
        System.out.println(dropTable);
        try {
            st.executeUpdate(delQuery);
            st.executeUpdate(dropTable);
            System.out.println("course deleted successfully");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    public void editCourseCatalog(){
        Integer currYear = null;
        String info = "Select currYear from info;";
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            currYear = rs.getInt(1);
            System.out.println("The current academic year is "+currYear);
            System.out.println("Following courses are available in the Course Catalog:-");
            viewCourseCatalog();
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Make choice:-");
        System.out.println("1. Add new course");
        System.out.println("2. Delete course");

        Scanner sc = new Scanner(System.in);
        Integer choice  = sc.nextInt();
        if(choice==1){
            addcourse();
        }
        else if(choice==2){
            deletecourse();
        }
        else{
            System.out.println("Please choose a valid choice!!");
        }
    }
}
