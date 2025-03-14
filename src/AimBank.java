import java.util.Scanner;

public class AimBank {
    static Connect obj = new Connect();

    public static void main(String[] args){

        Scanner sc = new Scanner(System.in);
//        try{
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            System.out.println("Driver Loaded Successfully.");
//        }catch(ClassNotFoundException e){
//            System.out.println(e.getMessage());
//        }

        boolean repeat = true;
        System.out.println("+--------------------------+");
        System.out.println("|   WELCOME TO AIM BANK    |");
        System.out.println("+--------------------------+");
        System.out.println("|   1. SIGN UP             |");
        System.out.println("|   2. LOGIN               |");
        System.out.println("|   0. EXIT                |");
        System.out.println("+--------------------------+");

        while(repeat){
            System.out.print("Enter your choice : ");
            int choice = sc.nextInt();

            switch(choice){
                case 0 -> repeat = false;
                // Here, we're using anonymous object
                case 1 -> {new SignUp(choice);}
                case 2 -> {new SignUp(choice);}
            }
        }
//            withdrawStatement.setDouble(1, 500.00);
//            withdrawStatement.setString(2, "account456");
//
//            creditStatement.setDouble(1, 500.00);
//            creditStatement.setString(2, "account123");
    }
}
