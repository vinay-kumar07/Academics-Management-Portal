package users;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class InstructorTest {

    Connection TestConnection = null;
    Statement TestStatement = null;
    void makeConnetion() throws ClassNotFoundException, SQLException {
        String TestURL = "jdbc:postgresql://localhost:5433/";
        String TestUserName = "postgres";
        String TestPassword = "dbms";
        Class.forName("org.postgresql.Driver");
        TestConnection = DriverManager.getConnection(TestURL, TestUserName, TestPassword);
        TestStatement = TestConnection.createStatement();
    }

    @Test
    void floatCourse() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.addcourse("TC1","3-0-0","TC2","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        String r1 = instructor.floatCourse("TC1",6.5f,"2022,2021");
        assertEquals("Course Offered Successfully.",r1);
        String r2 = instructor.floatCourse("TC1",6.5f,"2022,2021");
        assertEquals("The course has been already offered.",r2);

        instructor.deFloatCourse("TC1",2020,1);
        admin.deletecourse("TC1");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();

    }

    @Test
    void deFloatCourse() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.addcourse("TC1","3-0-0","TC2","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        String r1 = instructor.deFloatCourse("TC1",2020,1);
        assertEquals("Choose a valid course to delete.",r1);
        instructor.floatCourse("TC1",6.5f,"2022,2021");
        String r2 = instructor.deFloatCourse("TC1",2020,1);
        assertEquals("Course Removed Successfully from Offerings.",r2);

        admin.deletecourse("TC1");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();

    }

    @Test
    void updateGrades() {



    }
}