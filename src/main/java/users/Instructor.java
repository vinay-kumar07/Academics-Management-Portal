package users;

import java.sql.*;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import java.io.FileReader;
import java.io.IOException;

public class Instructor extends User{

    private Connection conn = null;
    private Statement st = null;
    public Instructor(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }
    public Integer currYear = null;
    public Integer currSem = null;

    private void makeConnection() throws ClassNotFoundException, SQLException {
        String url = "jdbc:postgresql://localhost:5433/";
        String username = "postgres";
        String password = "dbms";
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(url, username, password);
        st = conn.createStatement();
    }

    public String floatCourse(String course, Float cg, String batch) throws SQLException, IOException, ClassNotFoundException {
        if(getInfo()==0){
            return "Course add is off. Contact Admin.";
        }

        else {
            makeConnection();

            String check = "select count(*) from courseoffering where courseid = '"+course+"' and year = "+currYear+" and sem = "+currSem+";";
            ResultSet rs = st.executeQuery(check);
            rs.next();
            if(rs.getInt(1)==1) return "The course has been already offered.";

            String addQuery = "insert into courseoffering values('"+course+"','"+UserID+"',"+cg+","+currYear+","+currSem+",'"+batch+"');";
            st.executeUpdate(addQuery);

            Float credit = calculateCredit(course);
            addQuery = "insert into faculty_"+UserID+" values('"+course+"',"+currYear+","+currSem+","+credit+");";
            st.executeUpdate(addQuery);

            String makeTable = "create table "+course+"_"+Integer.toString(currYear)+"_"+Integer.toString(currSem)+"(StudentId varchar(20) NOT NULL, enrollyear INTEGER NOT NULL, grade float NOT NULL, PRIMARY KEY(StudentId), FOREIGN KEY(StudentID) REFERENCES Users(userID));";
            st.executeUpdate(makeTable);

            st.close();
            conn.close();

            return "Course Offered Successfully.";
        }
    }

    public String deFloatCourse(String CID, Integer y, Integer s) throws SQLException, ClassNotFoundException {
        if(getInfo()==0){
            return "Course delete is off. Contact Admin.";
        }
        else {
            makeConnection();
            String check = "select count(*) from courseoffering where courseid = '" + CID + "' and year = " + y + " and sem = " + s + ";";
            ResultSet rs = st.executeQuery(check);
            rs.next();
            if (rs.getInt(1) == 0) return "Choose a valid course to delete.";

            String deleteTable = "drop table " + CID + "_" + y + "_" + s + ";";
            st.executeUpdate(deleteTable);

            String delFromFacultyTable = "delete from faculty_" + UserID + " where courseid = '" + CID + "' and year = " + y + " and sem = " + s + ";";
            st.executeUpdate(delFromFacultyTable);

            String delFromOffering = "delete from courseoffering where courseid = '" + CID + "' and year = " + y + " and sem = " + s + ";";
            st.executeUpdate(delFromOffering);

            st.close();
            conn.close();

            return "Course Removed Successfully from Offerings.";
        }
    }

    public String updateGrades(String CID, String name) throws SQLException, ClassNotFoundException {
        getInfo();
        makeConnection();

        String check = "select count(*) from courseoffering where courseid = '" + CID + "' and year = " + currYear + " and sem = " + currSem + ";";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if (rs.getInt(1) == 0) return "Choose a valid course.";

        String path = "D:/Java/AIMS_Portal_CS305/Files/CSV Files/"+name;

        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(path));
            String[] line;
            while ((line = csvReader.readNext()) != null) {
                String tokens[] = line[0].split("\t");

                String student = "Update student_"+tokens[0]+" set grade = "+tokens[1]+" where courseid = '"+CID+"' and year = "+currYear+" and sem = "+currSem+";";
                String course = "Update "+CID+"_"+currYear+"_"+currSem+" set grade = "+tokens[1]+" where studentid = '"+tokens[0]+"';";
                try {
                    st.executeUpdate(student);
                    st.executeUpdate(course);
                } catch (SQLException e) {
                    System.out.println(e);
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return "Grades Updated Successfully";
    }

    public Integer viewGrades(String CID, Integer y, Integer s) throws SQLException, ClassNotFoundException, IOException {
        makeConnection();

        String check = "select count(*) from courseoffering where courseid = '" + CID + "' and year = " + y + " and sem = " + s + ";";
        ResultSet rs = st.executeQuery(check);
        rs.next();
        if (rs.getInt(1) == 0){
            System.out.println("Choose a valid course.");
            return 0;
        }

        String fetchGrades = "Select * from "+CID+"_"+y+"_"+s+";";
        rs = st.executeQuery(fetchGrades);
        System.out.println("(Grade -1 means the course has not been graded yet)");
        System.out.println("COURSE \t ENROLLMENT YEAR \t GRADE ");
        while (rs.next()){
            System.out.println(
                    rs.getString(1) + " \t " +
                    rs.getString(2) + " \t " +
                    rs.getString(3)
            );
        }
        return 1;
    }

    private Integer getInfo() throws SQLException, ClassNotFoundException {
        makeConnection();
        Integer bool = null;
        String info = "Select * from info;";
        ResultSet rs = st.executeQuery(info);
        rs.next();
        currYear = rs.getInt(1);
        currSem = rs.getInt(2);
        bool = rs.getInt(3);
        st.close();
        conn.close();
        return bool;
    }

    public void viewCourseCatalog() throws SQLException, IOException{
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
    public void viewCourseOffering() throws SQLException, IOException{
        String viewQuery = "select courseid,cgcriteria,year,sem from courseoffering where facultyid = '"+UserID+"';";
        ResultSet rs = st.executeQuery(viewQuery);
        System.out.println("COURSEID \t CG REQ \t YEAR \t SEM ");
        while (rs.next()) {
            System.out.println(
                rs.getString(1) + " \t " +
                rs.getString(2) + " \t " +
                rs.getString(3) + " \t " +
                rs.getString(4)
            );
        }
    }

    private Float calculateCredit(String cId) throws SQLException {
        Float credit = null;

        String fetchQuery = "select l,p from coursecatalog where courseid = '"+cId+"';";

        ResultSet rs = st.executeQuery(fetchQuery);
        rs.next();
        Integer l = rs.getInt(1);
        Integer p = rs.getInt(2);
        credit = l + (p/(2*1f));

        return credit;
    }
}
