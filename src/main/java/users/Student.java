package users;

import java.sql.*;
import java.util.*;

public class Student extends User{
    private Connection conn = null;
    private Statement st = null;
    Float CGPA = null;
    Integer currYear = null;
    Integer currSem = null;
    public Student(String UserID, String Type, String Password, Integer enrollYear) {
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

    public String courseRegister(String optedCourse) throws SQLException, ClassNotFoundException {

        makeConnection();
        initiate();

        viewCourseOfferings();

        ArrayList<String> courses = new ArrayList<String>();

        String selectQuery = "Select * from courseOffering where year = "+currYear+" and sem = "+currSem+";";
        ResultSet rs = st.executeQuery(selectQuery);
        while(rs.next()){
            String course = rs.getString(1);
            courses.add(rs.getString(1));
        }

        Float creditLimit = 21*1f;
        Integer sem = 2*(currYear-enrollYear) + currSem;
        if(sem>2){
            creditLimit = avgOfPrevSems();
        }

        Float regCredit = calRegCredit();

        Float cgreq = 0f;
        if(sem>1){
            String cgquery = "select cgcriteria from courseoffering where courseid = '"+optedCourse+"' and year = "+currYear+" and sem = "+currSem+";";
            rs = st.executeQuery(cgquery);
            rs.next();
            cgreq = rs.getFloat(1);
        }

        if(courses.contains(optedCourse)) {
            Float credit = calculateCredit(optedCourse);
            if(regCredit + credit <= creditLimit) {

                if((sem>1 && CGPA>=cgreq)) {

                    if(preReqSatisfy(optedCourse)){
                        String courseType = getCourseType(optedCourse);

                        String addQuery = "insert into Student_" + UserID + " values('" + optedCourse + "'," + currYear + "," + currSem + ",-1," + credit + ",'" + courseType + "');";
                        st.executeUpdate(addQuery);

                        addQuery = "insert into " + optedCourse + "_" + currYear + "_" + currSem + " values('" + UserID + "'," + enrollYear + ",-1);";
                        st.executeUpdate(addQuery);

                        if(courseType.equals("core")){
                            return "Registered as core course.";
                        }
                        else if (courseType.equals("elective")){
                            return "Registered as elective course.";
                        }
                        else {
                            return "Registered Successfully!!";
                        }
                    }

                    else {
                        return "Pre Requisite Not Satisfied.";
                    }

                } else if (sem==1) {
                    String courseType = getCourseType(optedCourse);

                    String addQuery = "insert into Student_" + UserID + " values('" + optedCourse + "'," + currYear + "," + currSem + ",-1," + credit + ",'" + courseType + "');";
                    st.executeUpdate(addQuery);

                    addQuery = "insert into " + optedCourse + "_" + currYear + "_" + currSem + " values('" + UserID + "'," + enrollYear + ",-1);";
                    st.executeUpdate(addQuery);

                    if(courseType.equals("core")){
                        return "Registered as core course.";
                    }
                    else if (courseType.equals("elective")){
                        return "Registered as elective course.";
                    }
                    else {
                        return "Registered Successfully!!";
                    }
                } else{
                    return "Cg criteria not matched.";
                }

            }else {
                return "Credit Limit exceeds.";
            }

        }else {
            return "Choose a valid course.";
        }
    }

    public String courseWithdraw(String selectedCourse) throws SQLException, ClassNotFoundException {
        makeConnection();
        initiate();
        ArrayList<String> courses = new ArrayList<String>();

        String fetchCourse = "Select courseid from student_"+UserID+" where year = "+currYear+" and sem = "+currSem+";";
        ResultSet rs = st.executeQuery(fetchCourse);
        while(rs.next()){
            courses.add(rs.getString(1));
        }

        if(courses.contains(selectedCourse)){
            String delFromCourseTable = "delete from " + selectedCourse + "_" + currYear + "_" + currSem + " where studentid = '"+UserID+"';";
            st.executeUpdate(delFromCourseTable);

            String delFromStudentTable = "delete from student_"+UserID+" where courseid = '"+selectedCourse+"' and year = "+currYear+" and sem = "+currSem+";";
            st.executeUpdate(delFromStudentTable);

            st.close();
            conn.close();
            return "Withdraw Successful";
        }
        else{
            st.close();
            conn.close();
            return "Please Choose a valid course";
        }
    }

    public void viewCourseOfferings() throws SQLException {
        String selectQuery = "Select * from courseOffering where year = "+currYear+" and sem = "+currSem+";";
        System.out.println("The following courses are available for enrollment: ");
        ResultSet rs = st.executeQuery(selectQuery);
        while(rs.next()){
            String course = rs.getString(1);
            System.out.println(course);
        }
    }

    public Float computeCPGA() throws SQLException, ClassNotFoundException {
        Float cg = 0*1.f;
        Float totalcredit = 0*1.f;
        String getCredit = "Select credit,grade from student_"+UserID+";";
        ResultSet rs = st.executeQuery(getCredit);
        while(rs.next()){
            Float c = rs.getFloat(1);
            Integer g = rs.getInt(2);
            if(g!=-1){
                totalcredit += c;
                cg += (c*g);
            }
        }
        float ans = 0f;
        if(totalcredit!=0) ans = cg/totalcredit;
        return ans;
    }

    public Integer viewGrades() throws SQLException, ClassNotFoundException {
        makeConnection();
        String fetchGrades = "Select * from student_"+UserID+";";
        ResultSet rs = st.executeQuery(fetchGrades);
        System.out.println("(Grade -1 means the course has not been graded yet)");
        System.out.println("COURSE \t YEAR \t SEM \t GRADE \t CREDIT \t REGISTERED AS");
        while (rs.next()){
            System.out.println(
                    rs.getString(1) + " \t " +
                    rs.getString(2) + " \t " +
                    rs.getString(3) + " \t " +
                    rs.getString(4) + " \t " +
                    rs.getString(5) + " \t " +
                    rs.getString(6)
            );
        }
        return 1;
    }

    private Float calRegCredit() throws SQLException {

        Float redcr = 0*1.f;

        String getCredit = "Select credit from student_"+UserID+" where year ="+currYear+" and sem = "+currSem+";";
        ResultSet r = st.executeQuery(getCredit);
        while(r.next()){
            redcr += r.getFloat(1);
        }

        return redcr;
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

    private Float avgOfPrevSems() throws SQLException {
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

        Float credit = 0f;
        Integer n = 0;

        String getQuery = "select credit,year,sem from student_"+UserID+";";
        ResultSet rs =st.executeQuery(getQuery);
        while (rs.next()){
            if((rs.getInt(2)==pyear && rs.getInt(3)==psem) || (rs.getInt(2)==ppyear && rs.getInt(3)==ppsem)){
                credit+=(rs.getInt(1));
                n++;
            }
        }

        return 1.25f*(credit/n);

    }

    private String getCourseType(String cid) throws SQLException {
        String type = "";
        String fetchCourse = "select core,elective from coursecatalog where courseid = '"+cid+"';";
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
        return type;
    }

    private Integer yearInfo() throws SQLException {

        String info = "select curryear from info;";
        Integer y = null;
        ResultSet rs = st.executeQuery(info);
        rs.next();
        y = rs.getInt(1);
        return y;

    }

    private Integer semInfo() throws SQLException {

        String info = "select currsem from info;";
        Integer s = null;
        ResultSet rs = st.executeQuery(info);
        rs.next();
        s = rs.getInt(1);
        return s;

    }

    private Boolean preReqSatisfy(String cid) throws SQLException, ClassNotFoundException {
        String getPreReq = "select prereq from coursecatalog where courseid = '"+cid+"';";
        ResultSet rs = st.executeQuery(getPreReq);
        rs.next();
        String prereq = rs.getString(1);
        if (prereq.equals("-")){
            return true;
        }
        else {
            String tokens[] = prereq.split(",");
            ArrayList<String> tokens_aslist= new ArrayList<String>(Arrays.asList(tokens));
            ArrayList<String> done = new ArrayList<String>();
            String coursesDone = "select courseid from student_"+UserID+" where grade>0;";
            rs = st.executeQuery(coursesDone);
            while (rs.next()){
                done.add(rs.getString(1));
            }
            for(int i=0;i<tokens_aslist.size();i++){
                if(!done.contains(tokens_aslist.get(i))){
                    return false;
                }
            }
            return true;
        }
    }

    private void initiate() throws SQLException, ClassNotFoundException {
        CGPA = computeCPGA();
        currYear = yearInfo();
        currSem = semInfo();
    }

}


