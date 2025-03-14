import java.sql.*;
import java.util.Scanner;

public class SignUp {
    SignUp(int option){
        if(option == 1){
            Signing();
        }
        else if(option == 2){
            Loging();
        }
    }

    static Connect obj = new Connect();
    Scanner sc = new Scanner(System.in);

    public void Signing(){
        String query = "INSERT INTO login (username, password) VALUES (?,?)";
        try{
            Connection con = DriverManager.getConnection(obj.url, obj.username, obj.password);
            con.setAutoCommit(false);
            System.out.println("+------------------+");
            System.out.println("|   SIGN UP PAGE   |");
            System.out.println("+------------------+");
            System.out.print("Enter your username: ");
            String user = sc.next();
            System.out.print("Enter your password: ");
            String pass = sc.next();

            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, user);
            ps.setString(2, pass);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0){
                con.commit();
                System.out.println("SIGNED UP SUCCESSFULLY !!.");
            }else{
                con.rollback();
                System.out.println("FAILED !!");
            }
            ps.close();
            con.commit();
            sc.close();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    public void Loging(){
        String withdraw = "UPDATE customers SET balance = balance - ? WHERE ac_no = ?";
        String credit = "UPDATE customers SET balance = balance + ? WHERE ac_no = ?";
        try{
            Connection con = DriverManager.getConnection(obj.url, obj.username, obj.password);
            System.out.println("Connection Established Successful");
            con.setAutoCommit(false);
            PreparedStatement withdrawStatement = con.prepareStatement(withdraw);
            PreparedStatement creditStatement = con.prepareStatement(credit);

            System.out.println("+------------------+");
            System.out.println("|   LOGIN  PAGE    |");
            System.out.println("+------------------+");
            System.out.print("Enter your username: ");
            String user = sc.next();
            System.out.print("Enter your password: ");
            String pass = sc.next();

            // Checking the authentication : If data is available or not.
            if(checkData(user, pass)){
                System.out.println("LOGIN SUCCESSFULLY");
            }else{
                System.out.println("Incorrect Username or Password !!");
            }
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
    private static boolean checkData(String user, String pass){
        String check = "SELECT username, password FROM login WHERE username = ? AND password = ?";

        try(Connection con = DriverManager.getConnection(obj.url, obj.username, obj.password);
            PreparedStatement ps = con.prepareStatement(check)){
            ps.setString(1, user);
            ps.setString(2, pass);

            try(ResultSet rs = ps.executeQuery()){
                // Checking if the records is available or not
                if(rs.next()){
                    return true;
                }
            }

        }catch(SQLException e){
            System.out.println(e.getMessage());
        }

        return false;
    }
}
