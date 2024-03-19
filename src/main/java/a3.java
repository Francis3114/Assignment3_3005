import java.sql.*;
import java.util.Scanner;

public class a3 {

    // JDBC URL, username, and password for connecting to the database
    private static final String url = "jdbc:postgresql://localhost:5432/Assignment3";
    private static final String user = "postgres";
    private static final String password = "peter";

    // Method to establish a connection to the database
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Method to retrieve and display all records from the students table
    public static void getAllStudents(){
        try(Connection connection = connect();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM STUDENTS");

        ){
            System.out.println("Displaying all records from the students table: ");
            while(resultSet.next()){
                // Printing each record's fields
                System.out.print(resultSet.getInt("student_id")+"\t");
                System.out.print(resultSet.getString("first_name")+"\t");
                System.out.print(resultSet.getString("last_name")+"\t");
                System.out.print(resultSet.getString("email")+"\t");
                System.out.println(resultSet.getDate("enrollment_date"));

            }
            System.out.println();
        }
        catch(SQLException e){
            System.out.println("error returned: "+ e.getMessage());
        }

    }

    // Method to add a new student record into the students table
    public static void addStudent(String first_name, String last_name, String email, Date enrollment_date) {
        try (Connection connection = connect();
             PreparedStatement statement = connection.prepareStatement("INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES (?, ?, ?, ?)")) {
            // Setting values for the prepared statement
            statement.setString(1, first_name);
            statement.setString(2, last_name);
            statement.setString(3, email);
            statement.setDate(4, enrollment_date);
            // Executing the insertion query
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("error returned: "+ e.getMessage());
        }
    }

    // Method to update the email address for a student with the specified student_id
    public static void updateStudentEmail(int student_id,String new_email){
        try(Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("Update students set email = ? where student_id = ?");
        ){
            // Setting values for the prepared statement
            statement.setString(1,new_email);
            statement.setInt(2,student_id);
            // Executing the update query
            statement.executeUpdate();

        }
        catch (SQLException e) {
            System.out.println("error returned: "+ e.getMessage());
        }
    }

    // Method to delete the record of the student with the specified student_id
    public static void deleteStudent(int student_id){
        try(Connection connection = connect();
            PreparedStatement statement = connection.prepareStatement("DELETE FROM STUDENTS WHERE STUDENT_ID = ?");
        ){
            // Setting value for the prepared statement
            statement.setInt(1,student_id);
            // Executing the delete query
            statement.executeUpdate();
        }
        catch (SQLException e){
            System.out.println("error returned: "+ e.getMessage());
        }
    }

    // Main method to run the program
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        while (true){
            // Displaying menu options
            System.out.println("CHOOSE FROM THE BELOW FUNCTIONS: ");
            System.out.println("1. Retrieves and displays all records from the students table.");
            System.out.println("2. Inserts a new student record into the students table.");
            System.out.println("3. Updates the email address for a student with the specified student_id");
            System.out.println("4. Deletes the record of the student with the specified student_id.");
            System.out.println("5. Exit the program");
            System.out.println();
            System.out.println("Enter your choice: ");

            // Reading user choice
            int choice = scanner.nextInt();
            scanner.nextLine();

            // Executing corresponding actions based on user choice
            if (choice == 1){
                getAllStudents();
            }

            else if (choice == 2){
                // Adding a new student
                System.out.println("Enter the first name: ");
                String fname = scanner.nextLine();
                System.out.println("Enter the last name: ");
                String lname = scanner.nextLine();
                System.out.println("Enter the email id: ");
                String email = scanner.nextLine();
                System.out.println("Enter the enrollment date (yyyy-mm-dd): ");
                Date enrollmentDate = Date.valueOf(scanner.nextLine());

                addStudent(fname,lname,email,enrollmentDate);
            }

            else if(choice == 3){
                // Updating email of a student
                System.out.println("Enter the student_id whose email you want to update: ");
                int student_id = scanner.nextInt();
                scanner.nextLine();
                System.out.println("Enter the new email id: ");
                String email = scanner.nextLine();

                updateStudentEmail(student_id,email);
            }


            else if(choice == 4){
                // Deleting a student
                System.out.println("Enter the student_id which you want to delete: ");
                int student_id = scanner.nextInt();

                deleteStudent(student_id);
            }

            else if(choice == 5){
                // Exiting the program
                System.out.println("Exiting the program :)");
                break;
            }

            else{
                System.out.println("Incorrect option. Try again.");

            }
        }
    }
}
