package users;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.imageio.IIOException;
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

    @Test
    void createAccountStudent() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String input = "TS1\nstudent\niit\n2020\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r1 = admin.createAccount();
        assertEquals("User with ID = TS1 registered successfully.",r1);

        input = "TS1\nstudent\niit\n2020\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r2 = admin.createAccount();
        assertEquals("User Already Registered.",r2);

        String s1 = "Drop table student_TS1;";
        String s2 = "Delete from users where userid = 'TS1';";

        String TestURL = "jdbc:postgresql://localhost:5433/";
        String TestUserName = "postgres";
        String TestPassword = "dbms";
        Class.forName("org.postgresql.Driver");
        Connection TestConnection = DriverManager.getConnection(TestURL, TestUserName, TestPassword);
        Statement TestStatement = TestConnection.createStatement();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void createAccountFaculty() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String input = "TS1\nfaculty\niit\n2020\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r1 = admin.createAccount();
        assertEquals("User with ID = TS1 registered successfully.",r1);

        String s1 = "Drop table faculty_TS1;";
        String s2 = "Delete from users where userid = 'TS1';";

        String TestURL = "jdbc:postgresql://localhost:5433/";
        String TestUserName = "postgres";
        String TestPassword = "dbms";
        Class.forName("org.postgresql.Driver");
        Connection TestConnection = DriverManager.getConnection(TestURL, TestUserName, TestPassword);
        Statement TestStatement = TestConnection.createStatement();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();

    }

    @Test
    void createAccountForInValidUser() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String input = "TS1\ngarbage\niit\n2020\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r1 = admin.createAccount();
        assertEquals("Not a Valid User Info.",r1);

    }

    @Test
    void editCourseCatalog() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String input = "2 \nTC1\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r1 = admin.editCourseCatalog();
        assertEquals("The Course does not exist. Please choose a valid course.",r1);


        input = "1\nTC1\n3-0-0\nPR1\ncs,mc\nee,ep\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r2 = admin.editCourseCatalog();
        assertEquals("Course Added Successfully",r2);


        input = "1\nTC1\n3-0-0\nPR1\ncs,mc\nee,ep\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r3 = admin.editCourseCatalog();
        assertEquals("The course already exist.",r3);


        input = "3\nTC2\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r4 = admin.editCourseCatalog();
        assertEquals("The Course does not exist. Please choose a valid course.",r4);


        input = "3\nTC1\ncs,mc,me\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r5 = admin.editCourseCatalog();
        assertEquals("Course Updated Successfully.",r5);


        input = "2\nTC1\n";
        inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        byteArrayOutputStream = new ByteArrayOutputStream();
        ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r6 = admin.editCourseCatalog();
        assertEquals("Course Deleted Successfully",r6);
    }

    @Test
    void editCourseCatalogForInvalidChoice() throws SQLException, IOException, ClassNotFoundException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);

        String input = "4\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(byteArrayOutputStream);
        System.setOut(ps);

        String r1 = admin.editCourseCatalog();
        assertEquals("Please choose a valid choice!!",r1);
    }
//
//    @Test
//    void changeAcademicYearSem() {
//    }
}