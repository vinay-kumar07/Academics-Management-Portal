package users;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AdminTest {
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
    void createAccountStudent() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String r1 = admin.createAccount("TS1","student","iit",2020);
        assertEquals("User with ID = TS1 registered successfully.",r1);

        String r2 = admin.createAccount("TS1","student","iit",2020);
        assertEquals("User Already Registered.",r2);

        String s1 = "Drop table student_TS1;";
        String s2 = "Delete from users where userid = 'TS1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void createAccountFaculty() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String r1 = admin.createAccount("TF1","faculty","iit",2020);
        assertEquals("User with ID = TF1 registered successfully.",r1);

        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();

    }

    @Test
    void createAccountForInValidUser() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String r1 = admin.createAccount("TS1", "svd", "admin",2022);
        assertEquals("Not a Valid User Info.",r1);

    }

    @Test
    void addcourse() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        String r1 = admin.addcourse("TC101","3-0-0","TC102","cs,mc","ee,me");
        assertEquals("Course Added Successfully",r1);
        String r2 = admin.addcourse("TC101","3-0-0","TC102","cs,mc","ee,me");
        assertEquals("The course already exist.",r2);
        admin.deletecourse("TC101");
    }

    @Test
    void deletecourse() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        String r1 = admin.deletecourse("TC101");
        assertEquals("The Course does not exist. Please choose a valid course.",r1);
        admin.addcourse("TC101","3-0-0","TC102","cs,mc","ee,me");
        String r2 = admin.deletecourse("TC101");
        assertEquals("Course Deleted Successfully",r2);
    }
    @Test
    void editCourseCatalogForInvalidChoice() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        String r1 = admin.editcourse("TC101","cs,ee");
        assertEquals("The Course does not exist. Please choose a valid course.",r1);
        admin.addcourse("TC101","3-0-0","TC102","cs,mc","ee,me");
        String r2 = admin.editcourse("TC101","cs,ep");
        assertEquals("Course Updated Successfully.",r2);
        admin.deletecourse("TC101");
    }
    @Test
    void changeAcademicYearSem() throws SQLException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        String s1 = admin.changeAcademicYearSem(2022,1,0);
        assertEquals("Updated",s1);
        admin.changeAcademicYearSem(2022,2,1);
    }
}