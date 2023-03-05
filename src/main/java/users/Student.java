package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.CS305.Main.st;

public class Student extends User{
    public Student(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
        Float CGPA = computeCPGA();
    }

    public void courseRegister(){
        ArrayList<String> courses = new ArrayList<String>();

        String selectQuery = "Select * from courseOffering";
        System.out.println("The following courses are available for enrollment: ");

        try {
            ResultSet rs = st.executeQuery(selectQuery);
            while(rs.next()){
                System.out.println(rs.getString(3));
                courses.add(rs.getString(3));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        Float creditLimit = 21*1f;
        Integer sem = findSem();

//        if(sem == 1 || sem ==2){
//
//        }

        Float regCredit = calRegCredit();

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();

        Float optedCourseCredit = calculateCredit(optedCourse);

        if(courses.contains(optedCourse) &&  && regCredit + optedCourseCredit <= 24*1.f){


            String addQuery = "insert into Student"+uID+" values('"+optedCourse+"','"+year+"','"+sem+"','-1');";
            try {
                st.executeQuery(addQuery);
            }
            catch (SQLException e) {
                System.out.println(e);
            }


        }
        else{
            System.out.println("Please choose a valid course!!");
        }
    }

    public Float computeCPGA(){
        Float cg = 0*1.f;
        Float totalcredit = 0*1.f;
        String getCredit = "Select credit,grade from student_"+UserID+";";
        try {
            ResultSet rs = st.executeQuery(getCredit);
            while(rs.next()){
                Float c = rs.getFloat(1);
                Integer g = rs.getInt(2);
                if(g!=-1){
                    totalcredit += c;
                    cg += (c*g);
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return cg/totalcredit;
    }

    private Integer findSem(){

        String info = "select curryear,currsem from info;";

        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            Integer curryear = rs.getInt(1);
            Integer currsem = rs.getInt(2);

            return 2*(curryear-enrollYear) + currsem;

        } catch (SQLException e) {
            System.out.println(e);
        }

        return 0;

    }

    private Float calRegCredit(){

        String info = "select curryear,currsem from info;";
        Integer curryear = null;
        Integer currsem = null;
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            curryear = rs.getInt(1);
            currsem = rs.getInt(2);
        } catch (SQLException e) {
            System.out.println(e);
        }

        Float redcr = 0*1.f;

        String getCredit = "Select credit from student_"+UserID+" where year ="+curryear+" and sem = "+currsem+";";
        try {
            ResultSet rs = st.executeQuery(getCredit);
            while(rs.next()){
                redcr += rs.getFloat(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return redcr;
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


