import java.sql.*;
import java.util.Scanner;

public class preparedStatement {
    public static void main(String[] args){

        String url = "jdbc:mysql://localhost:3306/my_db";
        String username = "root";
        String password = "Hamraj@786";
//        String query = "SELECT * FROM students WHERE roll = ?";
        String query = "INSERT INTO students(roll, name, course, fees) VALUES (?, ?, ?, ?)";

     try{
         Connection con = DriverManager.getConnection(url, username, password);

         Scanner scanner = new Scanner(System.in);
         System.out.print("Enter ROLL: ");
         int roll = scanner.nextInt();
         scanner.nextLine();
         System.out.print("Enter NAME: ");
         String name = scanner.nextLine();
         System.out.print("Enter COURSE: ");
         String course = scanner.nextLine();
         System.out.print("Enter FEES: ");
         Double fees = scanner.nextDouble();

         PreparedStatement preparedStatement = con.prepareStatement(query);
         preparedStatement.setInt(1, roll);
         preparedStatement.setString(2, name);
         preparedStatement.setString(3, course);
         preparedStatement.setDouble(4, fees);

         int rowsAffected = preparedStatement.executeUpdate();
         if(rowsAffected > 0){
             System.out.println("New Student Added Successfully.");
         }else{
             System.out.println("Student Adding Failed !!!");
         }

         preparedStatement.close();
         con.close();
         System.out.println("Connection CLOSED Successfully !!!");

     }catch(SQLException e){
         System.out.println(e.getMessage());
     }
    }
}
