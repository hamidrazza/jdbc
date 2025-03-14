import java.sql.*;

public class JdbcExample {
        public static void main(String[] args) {
            try {
                // storing into a variable 'url'
                String url = "jdbc:mysql://localhost:3306/demo_db";
                String username = "root";
                String password = "Hamraj@786";

                // Loading the driver of 'MySQL'
                Class.forName("com.mysql.cj.jdbc.Driver");

                // Making the connection, using the 'url', 'username' and 'password'
                Connection con = DriverManager.getConnection(url, username, password);

                // Creating the statement
                Statement stmt = con.createStatement();

                // Executing the SQL query.
//                String query = "INSERT INTO emp(empId, empName) VALUES(103,'Bill Paste')";
//                int row = stmt.executeUpdate(query);
//                System.out.println(row);

                ResultSet rs = stmt.executeQuery("SELECT * FROM emp");

                // next() : 'true' if the new current row is valid, else 'false'
                while (rs.next()) {
                    System.out.println(rs.getInt(1) + " " + rs.getString(2));
                }
//                // closing the Statement and Connection.
//                stmt.close();
//                con.close();

            }
            // catching the exception : SQLException
            catch (SQLException e1) {
                e1.printStackTrace();
            }
            // Class.forName : throws exception to ClassNotFoundException
            catch(ClassNotFoundException e2){
                e2.printStackTrace();
            }

        }
}