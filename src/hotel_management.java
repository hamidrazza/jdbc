import java.sql.*;
import java.util.Scanner;

public class hotel_management {

    private static final String url = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Hamraj@786";


    public static void main(String[] args) throws RuntimeException{
        try{
            Connection con = DriverManager.getConnection(url, username, password);
            while(true){
                System.out.println();
                System.out.println("HOTEL MANAGEMENT SYSTEM");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2. View Reservation");
                System.out.println("3. Get Room no.");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");

                System.out.print("Choose an option: ");
                int choice = sc.nextInt();

                switch (choice){
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        viewReservation(con);
                        break;
                    case 3:
                        getRoomNo(con, sc);
                        break;
                    case 4:
                        updateReservation(con,sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0:
                        exit();
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice!! Try Again !!");
                }
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }catch(InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    private static void reserveRoom(Connection con, Scanner sc){
        try{
            System.out.print("Enter guest name: ");
            String guestName = sc.next();
            sc.nextLine();
            System.out.print("Enter room number: ");
            int roomNumber = sc.nextInt();
            System.out.print("Enter contact number: ");
            String contactNo = sc.next();

            String sql = "INSERT INTO reservations(guest_name, room_no, contact_no)" + "VALUES ('" + guestName + "', '" + roomNumber + "' , '" + contactNo + "')";

            try(Statement st = con.createStatement()){

                int rowsAffected = st.executeUpdate(sql);
                if(rowsAffected > 0){
                    System.out.println("Reservation Successfully");
                }else{
                    System.out.println("Reservation Failed");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void viewReservation(Connection con) throws SQLException{
        String sql = "SELECT reservation_id, guest_name, room_no, contact_no, reservation_date FROM reservations";

        try(Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)){
            System.out.println("Current Reservations");
            System.out.println("+----------------+----------------+------------+----------------+----------------");
            System.out.println("| Reservation ID | Guest Name     | Room No    | Contact No     | Check In Time  |");
            System.out.println("+----------------+----------------+------------+----------------+----------------");

            while(rs.next()){
                int reservationId = rs.getInt("reservation_id");
                String guest_name = rs.getString("guest_name");
                int room_no = rs.getInt("room_no");
                String contact_no = rs.getString("contact_no");
                String reservation_date = rs.getTimestamp("reservation_date").toString();

                // Formatting values.
                System.out.printf("%-16d %-11s %-13d %-6s %s\n", reservationId, guest_name, room_no, contact_no, reservation_date);
            }
            System.out.println("+----------------+----------------+------------+----------------+----------------");
        }
    }
    private static void getRoomNo(Connection con, Scanner sc){
           try{
               System.out.println("Enter reservation ID: ");
               int reservationID = sc.nextInt();
               System.out.println("Enter guest name: ");
               String guestName = sc.next();

               String sql = "SELECT room_no FROM reservations WHERE reservation_id = " + reservationID + " AND guest_name = "+ guestName + ")";
               try(Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)){
                    if (rs.next()){
                        int roomNO = rs.getInt("room_no");
                        System.out.println("Room no for reservation ID: " + reservationID + " and guest name: " + guestName + " is " + roomNO);
                    }else{
                        System.out.println("Reservation NOT FOUND for reservation ID: " + reservationID + " and guest name: " + guestName);
                    }
               }
           }catch(SQLException e){
               e.printStackTrace();
           }
    }

    private static void updateReservation(Connection con, Scanner sc){
        try{
            System.out.println("Enter reservation ID to update");
            int reservationID = sc.nextInt();
            sc.nextLine();

            if(!reservationExists(con, reservationID)){
                System.out.println("Reservation is not available for the given ID");
                return;
            }

            System.out.println("Enter Guest Name: ");
            String newGuestName = sc.next();
            System.out.println("Enter Room No: ");
            int newRoomNO = sc.nextInt();
            System.out.println("Enter Contact NO: ");
            String newContact = sc.next();

            String sql = "UPDATE reservations SET guest_name = '"+ newGuestName + "', " + "room_no = '" + newRoomNO + "' ," + "contact_no = '" + newContact + "' WHERE reservation_id = '" + reservationID + "'";
            try(Statement st = con.createStatement()){
                int rowsAffected = st.executeUpdate(sql);

                if(rowsAffected > 0){
                    System.out.println("Reservation Updated successfully");
                }else{
                    System.out.println("Reservation Update Failed !!!");
                }
            }

        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static void deleteReservation(Connection con, Scanner sc){
        try{
            System.out.println("Enter Reservation ID: ");
            int reservationID = sc.nextInt();

            if(!reservationExists(con, reservationID)){
                System.out.println("Reservation is not available for the given ID");
                return;
            }
            String sql = "DELETE from reservations WHERE reservation_id = " + reservationID;

            try(Statement st = con.createStatement()){
                int rowsAffected = st.executeUpdate(sql);

                if(rowsAffected > 0){
                    System.out.println("Reservation Deleted Successfully");
                }else{
                    System.out.println("Reservation Deletion Failed !!!");
                }
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    private static boolean reservationExists(Connection con, int reservationID){
        try{
            String sql = "SELECT reservation_id FROM reservations WHERE reservation_id = " + reservationID;

            try(Statement st = con.createStatement(); ResultSet rs = st.executeQuery(sql)){
                return rs.next();
            }
        }catch(SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    private static void exit() throws InterruptedException{
        System.out.println("Exiting System");
        int i = 5;

        while(i != 0){
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("THANK YOU for using HOTEL MANAGEMENT SYSTEM");
    }
}