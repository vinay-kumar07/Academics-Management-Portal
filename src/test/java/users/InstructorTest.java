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
    void viewGradesInvalidCourse() throws SQLException, ClassNotFoundException, IOException {
        makeConnetion();
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.createAccount("TF1","faculty","iit",2020);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Integer r1 = instructor.viewGrades("TC1",2022,1);
        assertEquals(0,r1);

        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";

        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void  viewGradesValidCourse() throws SQLException, IOException, ClassNotFoundException {
        makeConnetion();
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);
        admin.createAccount("TS2","student","iit",2022);
        admin.createAccount("TS3","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Student student1 = new Student("TS1","student","iit",2022);
        Student student2 = new Student("TS2","student","iit",2022);
        Student student3 = new Student("TS3","student","iit",2022);

        instructor.floatCourse("TC1",6f,"2022");
        student1.courseRegister("TC1");
        student2.courseRegister("TC1");
        student3.courseRegister("TC1");

        String s5 = "Update TC1_2022_1 set grade = 8;";
        TestStatement.executeUpdate(s5);

        Integer r1 = instructor.viewGrades("TC1",2022,1);
        assertEquals(1,r1);

        String s1 = "Drop table student_TS1, student_TS2, student_TS3;";
        TestStatement.executeUpdate(s1);
        instructor.deFloatCourse("TC1",2022,1);
        admin.deletecourse("TC1");
        String s2 = "Drop table faculty_TF1;";
        TestStatement.executeUpdate(s2);
        String s3 = "Delete from users where userid = 'TF1' or userid = 'TS1' or userid = 'TS2' or userid = 'TS3';";
        TestStatement.executeUpdate(s3);

        TestStatement.close();
        TestConnection.close();
    }
    @Test
    void updateGrades() {



    }
}