package users;

import java.io.IOException;
import java.sql.*;
import  java.util.Scanner;
import java.util.SortedMap;

import static org.CS305.Main.st;
public class Admin extends User{
    private Connection conn = null;
    private static Statement st = null;
    public Admin(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }

    private void makeConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5433/";
        String username = "postgres";
        String password = "dbms";
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(url, username, password);
        st = conn.createStatement();
    }

    public String createAccount() throws SQLException, IOException, ClassNotFoundException {
        makeConnection();
        System.out.println("Enter Account Details:");
        Scanner sc = new Scanner(System.in);
        System.out.print("UserID: "); String ID = sc.nextLine();
        System.out.print("User Type(student/faculty): "); String type = sc.nextLine();
        System.out.print("Password: "); String pass = sc.nextLine();
        System.out.print("Enrollment Year: "); String enrollyear = sc.nextLine();

        String check_query = "Select * from Users;";
        ResultSet rs = st.executeQuery(check_query);
        while(rs.next()){
            String user = rs.getString(1);
            if(user.equals(ID)){
                return "User Already Registered.";
            }
        }

        if(type.equals("student")){
            String makeTable = "create table Student_"+ID+"(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, grade INTEGER NOT NULL, credit float NOT NULL, courseType varchar(200) NOT NULL, PRIMARY KEY(CourseId,year,sem), FOREIGN KEY(CourseId,year,sem) REFERENCES CourseOffering(CourseId,year,sem));";
            st.executeUpdate(makeTable);
        }

        else if(type.equals("faculty")){
            String makeTable = "create table Faculty_"+ID+"(CourseId varchar(50) NOT NULL, year INTEGER NOT NULL, sem INTEGER NOT NULL, credit float NOT NULL, PRIMARY KEY(CourseId,year,sem), FOREIGN KEY(CourseId,year,sem) REFERENCES CourseOffering(CourseId,year,sem));";
            st.execute(makeTable);
        }

        else {
            return "Not a Valid User Info.";
        }

        String query = "INSERT INTO Users VALUES ('"+ID+"','"+type+"','"+pass+"','"+enrollyear+"');";
        st.executeUpdate(query);

        return "User with ID = "+ID+" registered successfully.";
    }

    public String editCourseCatalog() throws IOException, ClassNotFoundException, SQLException {
        makeConnection();
        System.out.println("Following courses are available in the Course Catalog:-");
        viewCourseCatalog();
        System.out.println("-----------------------------");

        System.out.println("1. Add new course");
        System.out.println("2. Delete course");
        System.out.println("3. Edit existing course");
        System.out.print("Make choice: ");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice == 1) {
            return addcourse(sc);
        } else if (choice == 2) {
            return deletecourse(sc);
        } else if (choice == 3) {
            return editcourse(sc);
        } else {
            return "Please choose a valid choice!!";
        }
    }


//    public void changeAcademicYearSem(){
//        System.out.println("need to implement");
//    }

    // -----------Private functions----------------------//

    private void viewCourseCatalog() throws SQLException, IOException{
        String viewQuery = "select * from coursecatalog;";
        ResultSet rs = st.executeQuery(viewQuery);
        System.out.println("COURSEID \t L \t T \t P \t PREREQUISITE \t OFFERED AS CORE \t OFFERED AS ELECTIVE");
        while (rs.next()) {
            System.out.println(
                    rs.getString(1) + " \t " +
                    rs.getString(2) + " \t " +
                    rs.getString(3) + " \t " +
                    rs.getString(4) + " \t " +
                    rs.getString(5) + " \t " +
                    rs.getString(6) + " \t " +
                    rs.getString(7)
            );
        }
    }

    private String addcourse(Scanner sc) throws SQLException, IOException{
//        Scanner sc = new Scanner(System.in);
        System.out.println("Enter course details:-");
        System.out.print("Course ID: "); String course = sc.nextLine();
        System.out.print("L-T-P: "); String lpt = sc.nextLine();
        System.out.print("Pre Requisites: "); String prereq = sc.nextLine();
        System.out.print("Branches for which the course is core(comma separated): "); String core = sc.nextLine();
        System.out.print("Branches for which the course is elective(comma separated): "); String elec = sc.nextLine();

        String check = "select count(*) from coursecatalog where courseid = '"+course+"';";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if(rs.getInt(1)==1) return "The course already exist.";

        String[] tokens = lpt.split("-");
        Integer L = Integer.parseInt(tokens[0]);
        Integer T = Integer.parseInt(tokens[1]);
        Integer P = Integer.parseInt(tokens[2]);

        String addQuery = "insert into coursecatalog values('"+course+"',"+L+","+T+","+P+",'"+prereq+"','"+core+"','"+elec+"')";
        st.executeUpdate(addQuery);

        return "Course Added Successfully";
    }

    private String deletecourse(Scanner sc) throws SQLException, IOException {
//        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Course you want to delete:-");
        System.out.print("Course ID: ");
        String course = sc.nextLine();

        String check = "select count(*) from coursecatalog where courseid = '"+course+"';";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if(rs.getInt(1)==0) return "The Course does not exist. Please choose a valid course.";

        String delQuery = "delete from coursecatalog where courseid = '" + course + "';";
        st.executeUpdate(delQuery);
        return "Course Deleted Successfully";
    }

    private String editcourse(Scanner sc) throws SQLException, IOException{
        //give more edit access to admin
//        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Course ID which you want to edit:");
        System.out.print("Course ID: "); String course = sc.nextLine();

        String check = "select count(*) from coursecatalog where courseid = '"+course+"';";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if(rs.getInt(1)==0) return "The Course does not exist. Please choose a valid course.";

        System.out.println("Now enter updated core branches");
        System.out.print("Upadated Core Branches: "); String updatedcore = sc.nextLine();

        String updateQuery = "UPDATE coursecatalog SET core = '"+updatedcore+"' WHERE courseid = '"+course+"';";
        st.executeUpdate(updateQuery);
        return "Course Updated Successfully.";
    }
}
