import java.io.*;
import java.sql.*;

public class Image {
    public static void main(String[] args){
        String url = "jdbc:mysql://localhost:3306/my_db";
        String username = "root";
        String password = "Hamraj@786";
        // For file to sql
//        String image_path = "D:\\Photos\\Logo Design.jpg";
//        String query = "INSERT INTO image_table(image_data) VALUES(?)";
        // For sql to file
        String folder_path = "D:\\Photos\\";
        String query = "SELECT image_data FROM image_table WHERE image_id = (?)";

        try{
            Connection con = DriverManager.getConnection(url, username, password);
            System.out.println("Connection Established Successfully !!");

            // For inserting image into sql.
            /* FileInputStream fileInputStream = new FileInputStream(image_path);
            byte[] imageData = new byte[fileInputStream.available()];
            fileInputStream.read(imageData);

            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setBytes(1, imageData);

            int affectedRows = preparedStatement.executeUpdate();
            if(affectedRows > 0){
                System.out.println("Image INSERTED Successfully.");
            }else{
                System.out.println("Image Insertion Failed !!");
            }*/

            // For retrieving the image from sql.
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,1);
            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                byte[] image_data = resultSet.getBytes("image_data");
                String image_path = folder_path + "extractedImage.jpg";
                OutputStream outputStream = new FileOutputStream(image_path);
                outputStream.write(image_data);

                System.out.println("Image ADDED Successfully.");
            }else{
                System.out.println("Image NOT FOUND !!");
            }

            preparedStatement.close();
            con.close();

        }catch(SQLException e){
            System.out.println(e.getMessage());
        } catch(IOException e){
            throw new RuntimeException(e);
        }
    }
}
