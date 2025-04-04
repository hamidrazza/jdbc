import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class JavaTask {
    String url = "jdbc:mysql://localhost:3306/demo_db";
    String username = "root";
    String password = "Hamraj@786";

    static Connection con;
    JavaTask(){
        try{
            con = DriverManager.getConnection(url, username, password);
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }

    public static void main(String[] args){
        new JavaTask();

        Scanner sc = new Scanner(System.in);
        System.out.println("+---------------------------+");
        System.out.println("|       EMPLOYEE DATA       |");
        System.out.println("+---------------------------+");
        System.out.println("|   1. ADD EMPLOYEE         |");
        System.out.println("|   2. DELETE EMPLOYEE      |");
        System.out.println("|   3. SEARCH EMPLOYEE      |");
        System.out.println("|   4. UPDATE NAME          |");
        System.out.println("|   5. UPDATE SALARY        |");
        System.out.println("|   6. SHOW EMPLOYEES       |");
        System.out.println("+---------------------------+");
        System.out.println("|   0. EXIT                 |");
        System.out.println("+---------------------------+");

        boolean repeat = true;



        String addEmp = "INSERT INTO employees(id, name, salary) VALUES (?,?,?)";
        String deleteEmp = "DELETE FROM employees WHERE id = ?";
        String searchEmp = "SELECT * FROM employees WHERE id = ?";
        String showEmp = "SELECT * FROM employees";
        String updateName = "UPDATE employees SET name = ? WHERE id = ?";
        String updateSalary = "UPDATE employees SET salary = ? WHERE id = ?";
        String getName = "SELECT name FROM employees WHERE id = ?";
        String getSalary = "SELECT salary FROM employees WHERE id = ?";

        try{
            PreparedStatement addStatement = con.prepareStatement(addEmp);
            PreparedStatement deleteStatement = con.prepareStatement(deleteEmp);
            PreparedStatement searchStatement = con.prepareStatement(searchEmp);
            PreparedStatement showStatement = con.prepareStatement(showEmp);
            PreparedStatement nameStatement = con.prepareStatement(updateName);
            PreparedStatement salaryStatement = con.prepareStatement(updateSalary);
            PreparedStatement getNameStatement = con.prepareStatement(getName);
            PreparedStatement getSalaryStatement = con.prepareStatement(getSalary);

            con.setAutoCommit(false);

            while(repeat){
                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch(choice){

                    case 1 ->{
                        sc.nextLine();
                        System.out.print("Enter the employee ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter the employee name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter the salary: ");
                        Double salary = sc.nextDouble();

                        addStatement.setInt(1, id);
                        addStatement.setString(2, name);
                        addStatement.setDouble(3, salary);

                        int rowsAffected = addStatement.executeUpdate();

                        if(rowsAffected > 0){
                            System.out.println(name + " is added.");
                            con.commit();
                        }
                        else{
                            System.out.println(name + " doesn't added.");
                            con.rollback();
                        }
                    }
                    case 2 ->{
                        System.out.print("Enter the employee id: ");
                        int id = sc.nextInt();

                        deleteStatement.setInt(1,id);
                        getNameStatement.setInt(1,id);

                        int rowsAffected = deleteStatement.executeUpdate();

                        if(rowsAffected > 0){
                            ResultSet rs = getNameStatement.executeQuery();
                            String name = rs.getString("name");

                            System.out.println(name + " is deleted.");
                            deleteStatement.executeUpdate();
                            con.commit();
                        }
                        else{
                            System.out.println("Employee ID Not Found !!");
                            con.rollback();
                        }
                    }
                    case 3 ->{
                        System.out.print("Enter the employee ID: ");
                        int id = sc.nextInt();
                        searchStatement.setInt(1, id);

                        ResultSet rs = searchStatement.executeQuery();
                        System.out.println("+-----------------------------------------------+");
                        System.out.println("| ID\t\tName\t\t\tSalary    |");
                        System.out.println("+-----------------------------------------------+");
                        if(rs.next()){
                            System.out.println("| " + rs.getInt("id") + "\t" + rs.getString("name")+"\t\t" + rs.getDouble("salary") + " |");
                        }
                        else{
                            System.out.println("Employee ID Not Found !!");
                        }
                        System.out.println("+---------------------------+");

                    }
                    // Changing the name
                    case 4->{
                        System.out.print("Enter the employee ID: ");
                        int id = sc.nextInt();
                        System.out.print("Enter the new name: ");
                        String newName = sc.next();

                        ResultSet rs = getNameStatement.executeQuery();
                        nameStatement.setString(1, newName);
                        nameStatement.setInt(2, id);

                        String oldName = rs.getString("name");
                        int rowsAffected = nameStatement.executeUpdate();
                        if(rowsAffected > 0){
                            System.out.println("Name changed " + oldName + " to " + newName);
                            con.commit();
                        }
                        else{
                            System.out.println("Employee ID Not Found !!");
                            con.rollback();
                        }
                    }
                    // Changing the salary
                    case 5->{
                        System.out.print("Enter the employee ID: ");
                        int id = sc.nextInt();
                        nameStatement.setInt(2, id);

                        System.out.print("Enter the new salary: ");
                        Double newSal = sc.nextDouble();
                        nameStatement.setDouble(1, newSal);

                        ResultSet rs = getSalaryStatement.executeQuery();
                        Double oldSal = rs.getDouble("salary");

                        int rowsAffected = nameStatement.executeUpdate();
                        if(rowsAffected > 0){
                            System.out.println("Salary changed " + oldSal + " to " + newSal);
                            con.commit();
                        }
                        else{
                            System.out.println("Employee ID Not Found !!");
                            con.rollback();
                        }
                    }
                    case 6 ->{
                        ResultSet rs = showStatement.executeQuery();
                        System.out.println("+-------------------------------------+");
                        System.out.println("| ID\tName\t\t\tSalary        |");
                        System.out.println("+-------------------------------------+");
                        while(rs.next()){
                            System.out.println("  " + rs.getInt("id") + "\t" + rs.getString("name")+"\t\t" + rs.getDouble("salary"));
                        }
                        System.out.println("+-------------------------------------+");
                    }
                    case 0 ->{
                        repeat = false;
                        addStatement.close();
                        deleteStatement.close();
                        searchStatement.close();
                        nameStatement.close();
                        salaryStatement.close();
                        showStatement.close();
                        getNameStatement.close();
                        getSalaryStatement.close();
                        sc.close();
                        con.close();
                    }
                }
            }
        }
        catch(SQLException ex){
            ex.printStackTrace();
        }
    }
}
