import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/my_db";
        String username = "root";
        String password = "Hamraj@786";
        String withdraw = "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String credit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try{
            Class.forName("com.mysql.cj.jdbc.Driver");e
            System.out.println("Driver Loaded Successfully.");
        }catch(ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established Successful");
            con.setAutoCommit(false);
            PreparedStatement withdrawStatement = con.prepareStatement(withdraw);
            PreparedStatement creditStatement = con.prepareStatement(credit);

            withdrawStatement.setDouble(1, 500.00);
            withdrawStatement.setString(2, "account456");

            creditStatement.setDouble(1, 500.00);
            creditStatement.setString(2, "account123");

            int affectedRow1 = withdrawStatement.executeUpdate();
            int affectedRow2 = creditStatement.executeUpdate();

            if (affectedRow1 > 0 && affectedRow2 > 0){
                con.commit();
                System.out.println("Transaction Successful");
            }else{
                con.rollback();
                System.out.println("Transaction Failed !!");
            }
            creditStatement.close();
            withdrawStatement.close();
            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
