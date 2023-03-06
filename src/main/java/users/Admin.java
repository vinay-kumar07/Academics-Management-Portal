package users;

import java.io.IOException;
import java.sql.*;

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

    public String createAccount(String ID, String type, String pass, Integer enrollyear) throws SQLException, IOException, ClassNotFoundException {
        makeConnection();

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

        String query = "INSERT INTO Users VALUES ('"+ID+"','"+type+"','"+pass+"',"+enrollyear+");";
        st.executeUpdate(query);

        st.close();
        conn.close();
        return "User with ID = "+ID+" registered successfully.";
    }


    public String changeAcademicYearSem(Integer y, Integer s, Integer o) throws SQLException, ClassNotFoundException {
        makeConnection();
        String clear = "truncate table info;";
        st.executeUpdate(clear);
        String add = "insert into info values("+y+","+s+","+o+");";
        st.executeUpdate(add);
        st.close();
        conn.close();
        return "Updated";
    }

    public String addcourse(String course, String lpt, String prereq, String core, String elec) throws SQLException, IOException, ClassNotFoundException {
        makeConnection();
        System.out.println("Following courses are available in the Course Catalog:-");
        viewCourseCatalog();
        System.out.println("-----------------------------");

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

        st.close();
        conn.close();
        return "Course Added Successfully";
    }

    public String deletecourse(String course) throws SQLException, IOException, ClassNotFoundException {
        makeConnection();
        System.out.println("Following courses are available in the Course Catalog:-");
        viewCourseCatalog();
        System.out.println("-----------------------------");

        String check = "select count(*) from coursecatalog where courseid = '"+course+"';";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if(rs.getInt(1)==0) return "The Course does not exist. Please choose a valid course.";

        String delQuery = "delete from coursecatalog where courseid = '" + course + "';";
        st.executeUpdate(delQuery);

        st.close();
        conn.close();

        return "Course Deleted Successfully";
    }

    public String editcourse(String course, String updatedcore) throws SQLException, IOException, ClassNotFoundException {
        //give more edit access to admin
        makeConnection();
        System.out.println("Following courses are available in the Course Catalog:-");
        viewCourseCatalog();
        System.out.println("-----------------------------");

        String check = "select count(*) from coursecatalog where courseid = '"+course+"';";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if(rs.getInt(1)==0) return "The Course does not exist. Please choose a valid course.";

        String updateQuery = "UPDATE coursecatalog SET core = '"+updatedcore+"' WHERE courseid = '"+course+"';";
        st.executeUpdate(updateQuery);

        st.close();
        conn.close();

        return "Course Updated Successfully.";
    }

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
}
