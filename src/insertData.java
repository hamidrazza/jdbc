import java.sql.*;

public class insertData {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/my_db";
        String username = "root";
        String password = "Hamraj@786";

//        String query = "INSERT INTO students(roll, name, course, fee) VALUES (38, 'Mamun', 'BTech', 6500.0)";

        // Deleting from database
//        String query = "DELETE from students WHERE roll = 36";

        // Updating from database
        String query = "UPDATE students SET course = 'MCA', fee = '7500' WHERE roll = 11";

        // DRIVER
        System.out.println("Driver load SUCCESSFULLY !!!");

        // CONNECTION
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connected SUCCESSFULLY !!!");
            // STATEMENT
            Statement st = con.createStatement();
            int rowsAffected = st.executeUpdate(query);
            if(rowsAffected > 0){
//                System.out.println("Insert Successfully " + rowsAffected + " row(s) affected.");
//                System.out.println("Deletion Successfully " + rowsAffected + " row(s) affected.");
                System.out.println("Updated Successfully " + rowsAffected + " row(s) affected.");
            }else{
//                System.out.println("Insertion Failed !!!");
//                System.out.println("Deletion Failed !!!");
                System.out.println("Update Failed !!!");
            }

            st.close();
            con.close();
            System.out.println("Connection Closed SUCCESSFULLY !!!");
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

    }
}
