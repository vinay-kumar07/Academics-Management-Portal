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
    public void editCourseCatalog(){
        Integer currYear = null;
        String info = "Select currYear from info;";
        try {
            ResultSet rs = st.executeQuery(info);
            currYear = rs.getInt(1);
            System.out.println("The current academic year is "+currYear);
            viewCourseCatalog();
        } catch (SQLException e) {
            System.out.println(e);
        }

//        String checkTable = "Select exists (Select * From information_schema.tables where table_schema = 'public' and table_name = 'courseoffering"+currYear+"');";
    }
}
