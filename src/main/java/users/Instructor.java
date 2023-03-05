package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.CS305.Main.st;

public class Instructor extends User{
    public Instructor(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }
    public Integer currYear = null;
    public Integer currSem = null;

    public void floatCourse(){
        if(getInfo()==0){
            System.out.println("Course add is off. Contact Admin.");
        }

        else {
            viewCourseCatalog();
            System.out.println("\n\n");
            //check if a course already exist
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter course details:-");
            String course = sc.nextLine();
            Float cg = sc.nextFloat(); String garbage = sc.nextLine();
            String batch = sc.nextLine();

            String addQuery = "insert into courseoffering values('"+course+"','"+UserID+"',"+cg+","+currYear+","+currSem+",'"+batch+"');";
//            System.out.println(addQuery);
            try {
                st.executeUpdate(addQuery);
            } catch (SQLException e) {
                System.out.println(e);
            }
            Float credit = calculateCredit(course);
//            System.out.println(credit);
            addQuery = "insert into faculty_"+UserID+" values('"+course+"',"+currYear+","+currSem+","+credit+");";
//        System.out.println(addQuery);
            try {
                st.executeUpdate(addQuery);
            } catch (SQLException e) {
                System.out.println(e);
            }
            String makeTable = "create table "+course+"_"+Integer.toString(currYear)+"_"+Integer.toString(currSem)+"(StudentId varchar(20) NOT NULL, enrollyear INTEGER NOT NULL, grade float NOT NULL, PRIMARY KEY(StudentId), FOREIGN KEY(StudentID) REFERENCES Users(userID));";
            try {
                st.executeUpdate(makeTable);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }

    public void deFloatCourse(){
        getInfo();
        System.out.println("These are the courses you have floated:-");

        String fetchQuery = "Select courseid from courseoffering where facultyid = '"+UserID+"';";
        try {
            ResultSet rs = st.executeQuery(fetchQuery);
            while (rs.next()){
                System.out.println(rs.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Enter details of the course you want to delete");
        Scanner sc = new Scanner(System.in);
        String CID = sc.nextLine();
        Integer y = sc.nextInt();
        Integer s = sc.nextInt();

        String deleteTable = "drop table "+CID+"_"+Integer.toString(currYear)+"_"+Integer.toString(currSem)+";";
        try {
            st.executeUpdate(deleteTable);
        } catch (SQLException e) {
            System.out.println(e);
        }

        String delFromFacultyTable = "delete from faculty_"+UserID+" where courseid = '"+CID+"' and year = "+y+" and sem = "+s+";";
        try {
            st.executeUpdate(delFromFacultyTable);
        } catch (SQLException e) {
            System.out.println(e);
        }

        String delFromOffering = "delete from courseoffering where courseid = '"+CID+"' and year = "+y+" and sem = "+s+";";
        try {
            st.executeUpdate(delFromOffering);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private Integer getInfo(){
        Integer bool = null;
        String info = "Select * from info;";
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            currYear = rs.getInt(1);
            currSem = rs.getInt(2);
            bool = rs.getInt(3);
            System.out.println(currYear+" "+currSem+" "+bool);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return bool;
    }

    private void viewCourseCatalog(){
        String viewQuery = "select * from coursecatalog;";
        try {
            ResultSet rs = st.executeQuery(viewQuery);
            System.out.println("Following courses are available in the Course Catalog:-");
            while (rs.next()){
                System.out.println(rs.getString(1) + " " + rs.getString(5) + " " +rs.getString(6) + " " + rs.getString(7));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    private Float calculateCredit(String cId){
        Float credit = null;

        String fetchQuery = "select l,p from coursecatalog where courseid = '"+cId+"';";

        try {
            ResultSet rs = st.executeQuery(fetchQuery);
            rs.next();
            Integer l = rs.getInt(1);
            Integer p = rs.getInt(2);
            credit = l + (p/(2*1f));
        } catch (SQLException e) {
            System.out.println(e);
        }

        return credit;
    }
}
