package users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static org.CS305.Main.st;

public class Student extends User{

    public Student(String UserID, String Type, String Password, Integer enrollYear) {
        super(UserID, Type, Password, enrollYear);
    }

    Float CGPA = null;
    Integer currYear = null;
    Integer currSem = null;

    public void courseRegister(){

        initiate();

        ArrayList<String> courses = new ArrayList<String>();

        String selectQuery = "Select * from courseOffering where year = "+currYear+" and sem = "+currSem+";";
        System.out.println("The following courses are available for enrollment: ");
        ResultSet rs = null;
        try {
            rs = st.executeQuery(selectQuery);
            while(rs.next()){
                String course = rs.getString(1);
                System.out.println(course);
                courses.add(rs.getString(1));
            }
        }
        catch (SQLException e) {
            System.out.println(e);
        }

        Float creditLimit = 21*1f;
        Integer sem = 2*(currYear-enrollYear) + currSem;
        if(sem>2){
            creditLimit = avgOfPrevSems();
        }

        Float regCredit = calRegCredit();

        Scanner sc=new Scanner(System.in);
        System.out.print("Opt Course: ");
        String optedCourse = sc.nextLine();

        Float cgreq = 0f;
        if(sem>1){
            String cgquery = "select cgcriteria from courseoffering where courseid = '"+optedCourse+"' and year = "+currYear+" and sem = "+currSem+";";
            try {
                rs = st.executeQuery(cgquery);
                rs.next();
                cgreq = rs.getFloat(1);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        Float credit = calculateCredit(optedCourse);

        if(courses.contains(optedCourse) && regCredit + credit <= creditLimit){
            if((sem>1 && CGPA>=cgreq) || sem==1) {
                String courseType = getCourseType(optedCourse);

                String addQuery = "insert into Student_" + UserID + " values('" + optedCourse + "'," + currYear + "," + currSem + ",-1," + credit + ",'" + courseType + "');";
                try {
                    st.executeUpdate(addQuery);
                } catch (SQLException e) {
                    System.out.println(e);
                }

                addQuery = "insert into " + optedCourse + "_" + currYear + "_" + currSem + " values('" + UserID + "'," + enrollYear + ",-1);";
                try {
                    st.executeUpdate(addQuery);
                } catch (SQLException e) {
                    System.out.println(e);
                }

                System.out.println("Registered Successfully!!");
            }
        }
        else{
            System.out.println("Can not add course!!");
        }
    }

    public void courseWithdraw(){
        initiate();
        System.out.println("For the following courses you have registered:-");
        ArrayList<String> courses = new ArrayList<String>();

        String fetchCourse = "Select courseid from student_"+UserID+" where year = "+currYear+" and sem = "+currSem+";";
        try {
            ResultSet rs = st.executeQuery(fetchCourse);
            while(rs.next()){
                System.out.println(rs.getString(1));
                courses.add(rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        System.out.println("Select the course");
        Scanner sc = new Scanner(System.in);
        String selectedCourse = sc.nextLine();

        if(courses.contains(selectedCourse)){
            String delFromCourseTable = "delete from " + selectedCourse + "_" + currYear + "_" + currSem + " where studentid = '"+UserID+"';";
            try {
                st.executeUpdate(delFromCourseTable);
            } catch (SQLException e) {
                System.out.println(e);
            }

            String delFromStudentTable = "delete from student_"+UserID+" where courseid = '"+selectedCourse+"' and year = "+currYear+" and sem = "+currSem+";";
            try {
                st.executeUpdate(delFromStudentTable);
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        else{
            System.out.println("Please Choose a valid course");
        }
    }

    private Float computeCPGA(){
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
        float ans = 0f;
        if(totalcredit!=0) ans = cg/totalcredit;
        return ans;
    }

    private Float calRegCredit(){

        Float redcr = 0*1.f;

        String getCredit = "Select credit from student_"+UserID+" where year ="+currYear+" and sem = "+currSem+";";
        try {
            ResultSet r = st.executeQuery(getCredit);
            while(r.next()){
                redcr += r.getFloat(1);
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

    private Float avgOfPrevSems(){
        Integer pyear = null;
        Integer ppyear = null;
        Integer psem = null;
        Integer ppsem = null;
        if(currSem==1){
            psem = 2;
            ppsem = 1;
            pyear = currYear-1;
            ppyear = currYear-1;
        }
        else if(currSem==2){
            psem = 1;
            ppsem = 2;
            pyear = currYear;
            ppyear = currYear-1;
        }

        Float credit = null;
        Integer n = null;

        String getQuery = "select credit,year,sem from student_"+UserID+";";
        try {
            ResultSet rs =st.executeQuery(getQuery);
            while (rs.next()){
                if((rs.getInt(2)==pyear && rs.getInt(3)==psem) || (rs.getInt(2)==ppyear && rs.getInt(3)==ppsem)){
                    credit+=(rs.getInt(1));
                    n++;
                }
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        return 1.25f*(credit/n);

    }

    private String getCourseType(String cid){
        String type = null;
        String fetchCourse = "select core,elective from coursecatalog where courseid = '"+cid+"';";
        try {
            ResultSet rs = st.executeQuery(fetchCourse);
            rs.next();
            String[] core = rs.getString(1).split(",");
            String[] elec = rs.getString(2).split(",");
            String branch = UserID.substring(0,2);
            if(Arrays.asList(core).contains(branch)){
                type = "core";
            }
            else if(Arrays.asList(elec).contains(branch)){
                type = "elective";
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
        return type;
    }

    private Integer yearInfo(){

        String info = "select curryear from info;";
        Integer y = null;
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            y = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return y;

    }

    private Integer semInfo(){

        String info = "select currsem from info;";
        Integer s = null;
        try {
            ResultSet rs = st.executeQuery(info);
            rs.next();
            s = rs.getInt(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
        return s;

    }

    private void initiate(){
        CGPA = computeCPGA();
        currYear = yearInfo();
        currSem = semInfo();
    }

}


