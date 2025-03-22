import java.sql.*;
import java.util.Random;
import java.util.Scanner;

public class SignUp {
    Connect obj = new Connect();
    Connection con;
    SignUp(){
        try{
            con = DriverManager.getConnection(obj.url, obj.username, obj.password);
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    Scanner sc = new Scanner(System.in);

    public void Signing(){
        String login = "INSERT INTO login (username, password) VALUES (?,?)";
        String account = "UPDATE INTO customers SET acc_no = ? AND amount = ? WHERE username = ? AND password = ?";
        try{
            con.setAutoCommit(false);
            System.out.println("+------------------+");
            System.out.println("|   SIGN UP PAGE   |");
            System.out.println("+------------------+");
            System.out.print("Enter your username: ");
            String user = sc.next();
            System.out.print("Enter your password: ");
            String pass = sc.next();

            PreparedStatement insertLogin = con.prepareStatement(login);
            PreparedStatement insertAcc = con.prepareStatement(account);
            insertLogin.setString(1, user);
            insertLogin.setString(2, pass);

            int rowsAffected = insertLogin.executeUpdate();
            if (rowsAffected > 0){

                System.out.println("SIGNED UP SUCCESSFULLY !!.");
                insertAcc.setString(1, generateAccountNo());
                insertAcc.setDouble(2,0.0);
                insertAcc.setString(3,user);
                insertAcc.setString(4,pass);
                insertAcc.executeUpdate();
            }else{
                con.rollback();
                System.out.println("USERNAME ALREADY EXISTED");
            }
            insertLogin.close();
            insertAcc.close();
            con.commit();
            sc.close();
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    // Generating the random accountNo
    String generateAccountNo(){
        String keys = "0123456789";
        StringBuilder account = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < 11; i++){
            account.append(keys.charAt(random.nextInt(keys.length())));
        }
        return account.toString();
    }


    public void Loging(){
        try{
            System.out.println("Connection Established Successful");
            con.setAutoCommit(false);

            System.out.println("+------------------+");
            System.out.println("|   LOGIN  PAGE    |");
            System.out.println("+------------------+");
            System.out.print("Enter your username: ");
            String user = sc.next();
            System.out.print("Enter your password: ");
            String pass = sc.next();

            String withdraw = "UPDATE customers SET amount = amount - ? WHERE acc_no = ?";
            String credit = "UPDATE customers SET amount = amount + ? WHERE acc_no = ?";
            PreparedStatement withdrawStatement = con.prepareStatement(withdraw);
            PreparedStatement creditStatement = con.prepareStatement(credit);


            // Checking the authentication : If data is available or not.
            if(checkData(user, pass)){
                System.out.println("+-----------------------+");
                System.out.println("|       LOGGED IN       |");
                System.out.println("+-----------------------+");
                System.out.println("|    1. Withdraw        |");
                System.out.println("|    2. Credit          |");
                System.out.println("|    3. Check Balance   |");
                System.out.println("|    4. Bank Transfer   |");
//                System.out.println("|    5. Generate MPIN   |");
                System.out.println("|    0. LOG OUT         |");
                System.out.println("+-----------------------+");

                boolean repeat = true;
                while(repeat){

                    System.out.print("Choose an option : ");
                    int choice = sc.nextInt();
                    switch (choice){
                        // WITHDRAW the amount
                        case 1->{
                            System.out.print("Enter the amount : ");
                            int amount = sc.nextInt();
                            if(amount < getBalance(user)){
                                withdrawStatement.setInt(1,amount);
                                withdrawStatement.setString(2,getAccount(user));
                                int affectedRow = withdrawStatement.executeUpdate();

                                if (affectedRow > 0){
                                    con.commit();
                                    System.out.println("WITHDRAW SUCCESSFUL !!");
                                    System.out.println("Current Balance : " + getBalance(user));
                                }else{
                                    con.rollback();
                                    System.out.println("Transaction Failed !!");
                                }
                            }
                            else{
                                System.out.println("INSUFFICIENT BALANCE");
                                System.out.println("Current Balance : " + getBalance(user));
                            }
                        }
                        // CREDIT the amount
                        case 2 -> {
                            System.out.print("Enter the amount : ");
                            int amount = sc.nextInt();
                            creditStatement.setInt(1,amount);
                            creditStatement.setString(2,getAccount(user));
                            int affectedRow = creditStatement.executeUpdate();

                            if (affectedRow > 0){
                                con.commit();
                                System.out.println("CREDIT SUCCESSFUL !!");
                                System.out.println("Current Balance : " + getBalance(user));
                            }else{
                                con.rollback();
                                System.out.println("Transaction Failed !!");
                            }
                        }
                        // CHECKING THE BALANCE
                        case 3->{
                            System.out.println("Current Balance : " + getBalance(user));
                        }
                        // BANK TRANSFER
                        case 4->{
                            System.out.println("Enter The Account to Transfer: ");
                            String ac_no = sc.next();
                            System.out.println("Enter the amount: ");
                            int amount = sc.nextInt();
                            withdrawStatement.setInt(1,amount);
                            withdrawStatement.setString(2, getAccount(user));

                            creditStatement.setInt(1, amount);
                            creditStatement.setString(2, ac_no);
                            int affectedRow1 = withdrawStatement.executeUpdate();
                            int affectedRow2 = creditStatement.executeUpdate();

                            if (affectedRow1 > 0 && affectedRow2 > 0){
                                con.commit();
                                System.out.println("Transaction Successful");
                                System.out.println("Current Balance : " + getBalance(user));
                            }else{
                                con.rollback();
                                System.out.println("Transaction Failed !!");
                            }
                        }
                        case 0-> {
                            repeat = false;
                            withdrawStatement.close();
                            creditStatement.close();
                        }
                    }
                }
            }else{
                System.out.println("Incorrect Username or Password !!");
            }
            // withdraw, credit, and checkBalance
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
    }
    String getAccount(String user){
        String findingAc = "SELECT acc_no FROM customers WHERE username = ?";
        String accNo = null;
        try{
            PreparedStatement account = con.prepareStatement(findingAc);
            account.setString(1,user);

            ResultSet rs = account.executeQuery();
            if(rs.next()){
                accNo = rs.getString("acc_no");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return accNo;
    }
    int getBalance(String user){
        String findingAc = "SELECT amount FROM customers WHERE username = ?";
        int balance = 0;
        try{
            PreparedStatement account = con.prepareStatement(findingAc);
            account.setString(1,user);

            ResultSet rs = account.executeQuery();
            if(rs.next()){
                balance = rs.getInt("amount");
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
        return balance;
    }
    private boolean checkData(String user, String pass){
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
    public void closeConnection(){
        try{
            con.close();
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
