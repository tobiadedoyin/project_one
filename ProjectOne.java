import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Scanner;

public class ProjectOne {
   private static  final String JDBC_URL ="jdbc:mysql://localhost:3306/projectOne";
   private static final String PASSWORD = "Ol@117844";
   private static  final String USERNAME = "root";
   private  static  final String DB_NAME = "projectOne";
   Connection connection = null;
    public static void main(String[] args) {
        try(Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD); Statement statement = connection.createStatement()){
            Class.forName("com.mysql.cj.jdbc.Driver");
            statement.executeUpdate("CREATE DATABASE IF NOT EXISTS " + DB_NAME );
            System.out.println("Database connected!");

            ProjectOne userTable = new ProjectOne();
            userTable.createTable(connection);

            int count = userTable.populateTable(connection);
            System.out.println(count);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error in main method");
            System.out.println(e.getMessage());
        }
    }
    void createTable(Connection connection){
        String createUser =  "CREATE TABLE IF NOT EXISTS user (" + "Name TEXT NOT NULL ," + "Email TEXT NOT NULL," + "Age INT NOT NULL ," + "Location TEXT NOT NULL ," + "Designation TEXT NOT NULL " + ")";
        ;
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(createUser);
           System.out.println("user created");
        } catch(Exception e){
            System.out.println("error in create table method");
            System.out.println(e.getMessage());
        }
        
    }

    int populateTable(Connection connection){
        String insertIntoUser = "INSERT INTO user (Name, Email, Age, Location, Designation) VALUES (?, ?, ?, ?, ?)";
        int rowCount = 0;
        try(PreparedStatement preparedStatement = connection.prepareStatement(insertIntoUser); Scanner input = new Scanner(System.in)){

            int i = 0;
            while (i < 2){
                System.out.printf("Data for row %d \t", (i + 1));
                System.out.println("Enter your name:");
                String Name = input.nextLine();
                System.out.println("Enter your email:");
                String Email = input.nextLine();
                System.out.println("Enter your Age:");
                int Age = input.nextInt();
                input.nextLine();
                System.out.println("Enter your Location");
                String Location = input.nextLine();
                System.out.println("Enter your Designation");
                String Designation = input.nextLine();

                preparedStatement.setString(1, Name);
                preparedStatement.setString(2,Email);
                preparedStatement.setInt(3, Age);
                preparedStatement.setString(4, Location);
                preparedStatement.setString(5,Designation);

                i++;
                int rowInserted = preparedStatement.executeUpdate();
                rowCount += rowInserted;
            }
        }catch(Exception e){
            System.out.println("error in populate table method");
            System.out.println(e.getMessage());
        }
        return rowCount;
    }
}
