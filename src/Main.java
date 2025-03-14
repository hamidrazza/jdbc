import java.sql.*;

public class Main {
    public static void main(String[] args){

        String url = "jdbc:mysql://localhost:3306/my_db";
        String username = "root";
        String password = "Hamraj@786";

        String query = "SELECT * from students";

        // LOADING DRIVER
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver Loaded SUCCESSFULLY !!!");
//        }
//        catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
//        }

        // In NEW VERSION of JDBC we don't need to load DRIVER
        System.out.println("Driver Loaded SUCCESSFULLY !!!");

        // CONNECTION
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established SUCCESSFULLY !!!");

            // STATEMENT
            Statement st = con.createStatement();

            // QUERY
            ResultSet rs = st.executeQuery(query);

            // This loop will run until the 'rs' has some data
            while(rs.next()){
                int stu_roll = rs.getInt("roll");
                String stu_name = rs.getString("name");
                String stu_course = rs.getString("course");
                double stu_fees = rs.getDouble("fee");
                System.out.println();
                System.out.println("===========================");
                System.out.println("Roll NO : " + stu_roll);
                System.out.println("Name : " + stu_name);
                System.out.println("Course : " + stu_course);
                System.out.println("Fees : " + stu_fees);
            }

            // CLOSING all "Result Set", "Statement", and "Connection"
            rs.close();
            st.close();
            con.close();
            System.out.println("Connection Closed SUCCESSFULLY !!!");

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}