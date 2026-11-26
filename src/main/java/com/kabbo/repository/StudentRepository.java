package com.kabbo.repository;

import com.kabbo.model.Student;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentRepository {
    // S1: JDBC Connection
    String url = "jdbc:mysql://localhost:3306/student_db";
    String username = "root";
    String password = "password";


    // S2: CREATE Operation method
    public void createStudent(Student student) throws SQLException {

        // Dynamic Query
        String sql =    """ 
                        INSERT INTO students(name, email, age) 
                        VALUES (?, ?, ?)
                        """;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            // Smart interface for JDBC Works
            PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            // Parameter mapping: assigning Student properties to the PreparedStatement
            // Bind the Student object's data to the SQL query placeholders (?) (Note: Index starts at 1, not 0)
            // We will Set values in the Main.java file
            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());

                int rowAffected = preparedStatement.executeUpdate();

                if (rowAffected == 0) {
                    System.out.println( "Insertion Failed");
                }
                else {
                    System.out.println("Inserted Successfully");
                }
            }

        catch(SQLException e){
                System.out.println("Connection failed");
                e.printStackTrace();
            }
        }


    // S3: UPDATE Operation
    public void updateStudent(Student student, Long id){

        String sql = """
                     UPDATE students 
                     SET name = ?,
                         email = ?,
                         age = ?
                     WHERE id = ?
                     """;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getEmail());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setLong(4, id);

            int rowAffected = preparedStatement.executeUpdate();

            if(rowAffected == 0){
                System.out.println("Updation Failed");
            }
            else{
                System.out.println("Updated Successfully");
            }
        }

        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // S4: DELETE Operation
    public void deleteStudent(Long id){

        String sql = """
                     DELETE FROM students
                     WHERE id = ?
                     """;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){

            preparedStatement.setLong(1, id);

            int rowAffected = preparedStatement.executeUpdate();

            if(rowAffected == 0){
                System.out.println("Deletion Failed");
            }
            else{
                System.out.println("Deleted Successfully");
            }
        }

        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // S5: READ Operation
    public void getStudentById(Long id) throws SQLException {

        String sql = """
                     SELECT id, name, email, age FROM students
                     WHERE id = ?
                     """;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ) {

            preparedStatement.setLong(1, id);

            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    Student student = mapRow(resultSet);
                    System.out.println(student);
                }
            }
        }

        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // S8: All Students READ
    public void getAllStudent() throws SQLException {

        String sql = """
                     SELECT id, name, email, age FROM students
                     """;

        try(Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery()
        ) {
                List<Student> studentList = new ArrayList<>();

                while(resultSet.next()){
                    studentList.add(mapRow(resultSet));
                }

                // 2. Print the WHOLE list ONCE after the loop finishes
                System.out.println("--- All Students ---");
                for (Student s : studentList) {
                    System.out.println(s); // Prints each student on a new line
                }
                System.out.println("--------------------");
            }

        catch(SQLException e){
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // S7: Complete CRUD Operation (General Purpose)
    public void completeCRUD(String sql) {

        // Note: We pass the SQL query as a parameter so we can test different operations.

        try (Connection connection = DriverManager.getConnection(url, username, password);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            // The execute() method returns a boolean
            boolean isResultSet = preparedStatement.execute();

            if (isResultSet) {
                // TRUE -> It was a SELECT query
                System.out.println("--- Query returned a ResultSet ---");

                try (ResultSet resultSet = preparedStatement.getResultSet()) {
                    while (resultSet.next()) {
                        Student student = mapRow(resultSet);
                        System.out.println(student);
                    }
                }

            } else {
                // FALSE -> It was an INSERT, UPDATE, or DELETE
                int rowAffected = preparedStatement.getUpdateCount();
                System.out.println("--- Operation Successful ---");
                System.out.println("Rows affected: " + rowAffected);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed");
            e.printStackTrace();
        }
    }

    // S6: mapRow of the S5's READ Operation
    private Student mapRow(ResultSet resultSet) throws SQLException {
        Student student = new Student();

        student.setId(resultSet.getLong("id"));
        student.setName(resultSet.getString("name"));
        student.setEmail(resultSet.getString("email"));
        student.setAge(resultSet.getInt("age"));

        return student;
    }
}
