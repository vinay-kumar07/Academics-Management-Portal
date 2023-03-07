package users;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class StudentTest {
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
    void InvalidCourseRegister() throws SQLException, ClassNotFoundException, IOException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.createAccount("TS1","student","iit",2022);

        Student student = new Student("TS1","student","iit",2022);
        String r1 = student.courseRegister("TC1");
        assertEquals("Choose a valid course.",r1);

        String s1 = "Drop table student_TS1;";
        String s2 = "Delete from users where userid = 'TS1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);

        TestStatement.close();
        TestConnection.close();
    }
    @Test
    void creditLimitExceeds21() throws SQLException, ClassNotFoundException, IOException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","21-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC2","3-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        instructor.floatCourse("TC1",0f,"2022");
        instructor.floatCourse("TC2",0f,"2022");

        Student student = new Student("TS1","student","iit",2022);
        student.courseRegister("TC1");
        String r1 = student.courseRegister("TC2");
        assertEquals("Credit Limit exceeds.",r1);
        student.courseWithdraw("TC1");

        instructor.deFloatCourse("TC1",2022,1);
        instructor.deFloatCourse("TC2",2022,1);
        admin.deletecourse("TC1");
        admin.deletecourse("TC2");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void creditLimitExceedsAvg() throws SQLException, ClassNotFoundException, IOException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC2","4-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC3","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC4","4-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Student student = new Student("TS1","student","iit",2022);

        instructor.floatCourse("TC1",0f,"2022");
        student.courseRegister("TC1");
        admin.changeAcademicYearSem(2022,2,1);
        instructor.floatCourse("TC2",0f,"2022");
        student.courseRegister("TC2");
        admin.changeAcademicYearSem(2023,1,1);
        instructor.floatCourse("TC3",0f,"2022");
        instructor.floatCourse("TC4",0f,"2022");
        student.courseRegister("TC3");
        String r1 = student.courseRegister("TC4");
        assertEquals("Credit Limit exceeds.",r1);

        student.courseWithdraw("TC3");
        admin.changeAcademicYearSem(2022,2,1);
        student.courseWithdraw("TC2");
        admin.changeAcademicYearSem(2022,1,1);
        student.courseWithdraw("TC1");
        instructor.deFloatCourse("TC1",2022,1);
        instructor.deFloatCourse("TC2",2022,2);
        instructor.deFloatCourse("TC3",2023,1);
        instructor.deFloatCourse("TC4",2023,1);
        admin.deletecourse("TC1");
        admin.deletecourse("TC2");
        admin.deletecourse("TC3");
        admin.deletecourse("TC4");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void cgReqNotSatisfied() throws SQLException, ClassNotFoundException, IOException {
        makeConnetion();
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC2","4-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC3","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC4","4-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Student student = new Student("TS1","student","iit",2022);

        instructor.floatCourse("TC1",6f,"2022");
        student.courseRegister("TC1");
        instructor.floatCourse("TC2",6.5f,"2022");
        student.courseRegister("TC2");

        String s5 = "Update student_TS1 set grade = 8 where courseid = 'TC1' and year = 2022 and sem = 1;";
        String s6 = "Update student_TS1 set grade = 7 where courseid = 'TC2' and year = 2022 and sem = 1;";
        TestStatement.executeUpdate(s5);
        TestStatement.executeUpdate(s6);

        admin.changeAcademicYearSem(2022,2,1);
        instructor.floatCourse("TC3",7f,"2022");
        instructor.floatCourse("TC4",8f,"2022");
        String r1 = student.courseRegister("TC3");
        assertEquals("Registered Successfully!!",r1);
        String r2 = student.courseRegister("TC4");
        assertEquals("Cg criteria not matched.",r2);

        student.courseWithdraw("TC3");
        admin.changeAcademicYearSem(2022,1,1);
        student.courseWithdraw("TC2");
        student.courseWithdraw("TC1");
        instructor.deFloatCourse("TC1",2022,1);
        instructor.deFloatCourse("TC2",2022,1);
        instructor.deFloatCourse("TC3",2022,2);
        instructor.deFloatCourse("TC4",2022,2);
        admin.deletecourse("TC1");
        admin.deletecourse("TC2");
        admin.deletecourse("TC3");
        admin.deletecourse("TC4");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }
    @Test
    void preReqNotSatisfied() throws SQLException, IOException, ClassNotFoundException {
        makeConnetion();
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","PR1","cs,mc","ee,me");
        admin.addcourse("TC2","4-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC3","3-0-0","TC1,TC2","cs,mc","ee,me");
        admin.addcourse("TC4","4-0-0","TC5","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Student student = new Student("TS1","student","iit",2022);

        instructor.floatCourse("TC1",6f,"2022");
        student.courseRegister("TC1");
        instructor.floatCourse("TC2",6.5f,"2022");
        student.courseRegister("TC2");

        String s5 = "Update student_TS1 set grade = 10 where courseid = 'TC1' and year = 2022 and sem = 1;";
        String s6 = "Update student_TS1 set grade = 10 where courseid = 'TC2' and year = 2022 and sem = 1;";
        TestStatement.executeUpdate(s5);
        TestStatement.executeUpdate(s6);

        admin.changeAcademicYearSem(2022,2,1);
        instructor.floatCourse("TC3",7f,"2022");
        instructor.floatCourse("TC4",8f,"2022");
        String r1 = student.courseRegister("TC3");
        assertEquals("Registered Successfully!!",r1);
        String r2 = student.courseRegister("TC4");
        assertEquals("Pre Requisite Not Satisfied.",r2);

        student.courseWithdraw("TC3");
        admin.changeAcademicYearSem(2022,1,1);
        student.courseWithdraw("TC2");
        student.courseWithdraw("TC1");
        instructor.deFloatCourse("TC1",2022,1);
        instructor.deFloatCourse("TC2",2022,1);
        instructor.deFloatCourse("TC3",2022,2);
        instructor.deFloatCourse("TC4",2022,2);
        admin.deletecourse("TC1");
        admin.deletecourse("TC2");
        admin.deletecourse("TC3");
        admin.deletecourse("TC4");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }
    @Test
    void registerCourseAsCoreOrELective() throws SQLException, ClassNotFoundException, IOException {
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("cs301","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("cs302","3-0-0","-","ee,mc","cs,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("cs1134","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        instructor.floatCourse("cs301",0f,"2022");
        instructor.floatCourse("cs302",0f,"2022");

        Student student = new Student("cs1134","student","iit",2022);
        String r1 = student.courseRegister("cs301");
        assertEquals("Registered as core course.",r1);
        String r2 = student.courseRegister("cs302");
        assertEquals("Registered as elective course.",r2);
        student.courseWithdraw("cs301");
        student.courseWithdraw("cs302");

        instructor.deFloatCourse("cs301",2022,1);
        instructor.deFloatCourse("cs302",2022,1);
        admin.deletecourse("cs301");
        admin.deletecourse("cs302");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_cs1134;";
        String s4 = "Delete from users where userid = 'cs1134';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void viewgrades() throws SQLException, ClassNotFoundException, IOException {
        makeConnetion();
        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC2","4-0-0","-","cs,mc","ee,me");
        admin.addcourse("TC3","3-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        Student student = new Student("TS1","student","iit",2022);

        instructor.floatCourse("TC1",6f,"2022");
        student.courseRegister("TC1");
        instructor.floatCourse("TC2",6.5f,"2022");
        student.courseRegister("TC2");
        instructor.floatCourse("TC3",7f,"2022");
        student.courseRegister("TC3");

        String s5 = "Update student_TS1 set grade = 8 where courseid = 'TC1' and year = 2022 and sem = 1;";
        String s6 = "Update student_TS1 set grade = 7 where courseid = 'TC2' and year = 2022 and sem = 1;";
        TestStatement.executeUpdate(s5);
        TestStatement.executeUpdate(s6);

        Integer r1 = student.viewGrades();
        assertEquals(1,r1);

        student.courseWithdraw("TC3");
        student.courseWithdraw("TC2");
        student.courseWithdraw("TC1");
        instructor.deFloatCourse("TC1",2022,1);
        instructor.deFloatCourse("TC2",2022,1);
        instructor.deFloatCourse("TC3",2022,1);
        admin.deletecourse("TC1");
        admin.deletecourse("TC2");
        admin.deletecourse("TC3");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();
    }

    @Test
    void generateTranscript(){

    }
    @Test
    void courseWithdraw() throws SQLException, IOException, ClassNotFoundException {

        Admin admin = new Admin ("Admin","Admin","admin",2008);
        admin.changeAcademicYearSem(2022,1,1);
        admin.addcourse("TC1","3-0-0","-","cs,mc","ee,me");
        admin.createAccount("TF1","faculty","iit",2020);
        admin.createAccount("TS1","student","iit",2022);

        Instructor instructor = new Instructor ("TF1","faculty","iit",2020);
        instructor.floatCourse("TC1",0f,"2022");

        Student student = new Student("TS1","student","iit",2022);
        String r1 = student.courseWithdraw("TC1");
        assertEquals("Please Choose a valid course",r1);
        student.courseRegister("TC1");
        String r2 = student.courseWithdraw("TC1");
        assertEquals("Withdraw Successful",r2);

        instructor.deFloatCourse("TC1",2022,1);
        admin.deletecourse("TC1");
        String s1 = "Drop table faculty_TF1;";
        String s2 = "Delete from users where userid = 'TF1';";
        String s3 = "Drop table student_TS1;";
        String s4 = "Delete from users where userid = 'TS1';";

        makeConnetion();
        TestStatement.executeUpdate(s1);
        TestStatement.executeUpdate(s2);
        TestStatement.executeUpdate(s3);
        TestStatement.executeUpdate(s4);

        TestStatement.close();
        TestConnection.close();

    }

}