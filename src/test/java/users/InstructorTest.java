//package users;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.ByteArrayInputStream;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.PrintStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class InstructorTest {
//
//    @Test
//    void floatCourse() throws ClassNotFoundException, SQLException, IOException {
//        String TestURL = "jdbc:postgresql://localhost:5433/";
//        String TestUserName = "postgres";
//        String TestPassword = "dbms";
//        Class.forName("org.postgresql.Driver");
//        Connection TestConnection = DriverManager.getConnection(TestURL, TestUserName, TestPassword);
//        Statement TestStatement = TestConnection.createStatement();
//
//        String s1 = "update info set addwithdraw = 1,curryear = 2020,currsem = 1 where addwithdraw = 0 or addwithdraw =1;";
//        TestStatement.executeUpdate(s1);
//
//        Admin admin = new Admin ("Admin","Admin","admin",2008);
//
//        String input = "TS1\nfaculty\niit\n2020\n";
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        PrintStream ps = new PrintStream(byteArrayOutputStream);
//        System.setOut(ps);
//
//        admin.createAccount(ID, type, pass, enrollyear);
//
//        Instructor instructor = new Instructor ("TS1","faculty","iit",2020);
//
//        input = "TC1\n6.5\n2020,2021";
//        inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        ps = new PrintStream(byteArrayOutputStream);
//        System.setOut(ps);
//
//        String r1 = instructor.floatCourse();
//        assertEquals("Course Offered Successfully.",r1);
//
//        input = "TC1\n6.5\n2020,2021";
//        inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        ps = new PrintStream(byteArrayOutputStream);
//        System.setOut(ps);
//
//        String r2 = instructor.floatCourse();
//        assertEquals("The course has been already offered.",r2);
//
//        input = "TC1\n2020\n1";
//        inputStream = new ByteArrayInputStream(input.getBytes());
//        System.setIn(inputStream);
//
//        byteArrayOutputStream = new ByteArrayOutputStream();
//        ps = new PrintStream(byteArrayOutputStream);
//        System.setOut(ps);
//
//        instructor.floatCourse();
//
//        String s2 = "Drop table faculty_TS1;";
//        String s3 = "Delete from users where userid = 'TS1';";
//        TestStatement.executeUpdate(s2);
//        TestStatement.executeUpdate(s3);
//
//        TestStatement.close();
//        TestConnection.close();
//    }
//
//    @Test
//    void deFloatCourse() {
//    }
//
//    @Test
//    void updateGrades() {
//    }
//}