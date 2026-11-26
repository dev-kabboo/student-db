package com.kabbo;

import com.kabbo.model.Student; // Make sure this import matches your Student class location
import com.kabbo.repository.StudentRepository;

import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        
        // 1. Set up Scanner for user input and the Repository
        Scanner scanner = new Scanner(System.in);
        StudentRepository studentRepository = new StudentRepository();
        boolean running = true;

        // 2. The Application Loop
        while (running) {
            System.out.println("\nStudent Management");
            System.out.println("=========================");
            System.out.println("1. Add/Create Student");
            System.out.println("2. Update");
            System.out.println("3. Delete");
            System.out.println("4. View Students");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");


            // 3. Handle the choice
            try {
                // Move 'nextInt()' inside the try block
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character (important!)

                switch (choice) {
                    case 1:
                        System.out.print("Enter Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Enter Email: ");
                        String email = scanner.nextLine();
                        System.out.print("Enter Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        studentRepository.createStudent(new Student(name, email, age));
                        break;

                    case 2:
                        System.out.print("Enter Student ID to Update: ");
                        Long updateId = scanner.nextLong();
                        scanner.nextLine(); // consume newline
                        
                        System.out.print("Enter New Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter New Email: ");
                        String newEmail = scanner.nextLine();
                        System.out.print("Enter New Age: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine(); // consume newline

                        studentRepository.updateStudent(new Student(newName, newEmail, newAge), updateId);
                        break;

                    case 3:
                        System.out.print("Enter Student ID to Delete: ");
                        Long deleteId = scanner.nextLong();
                        scanner.nextLine(); // consume newline

                        studentRepository.deleteStudent(deleteId);
                        break;

                    case 4:
                        // Returns data of all students
                        System.out.println("\n");
                        studentRepository.getAllStudent();
                        break;

                    case 5:
                        System.out.print("Enter Student ID to Search: ");
                        Long searchId = scanner.nextLong();
                        scanner.nextLine(); // consume newline

                        studentRepository.getStudentById(searchId);
                        break;

                    case 6:
                        System.out.println("Exiting System. Goodbye!");
                        running = false; // Breaks the while loop
                        break;

                    default:
                        System.out.println("Invalid choice! Please enter a number between 1 and 6.");
                }
            } catch(InputMismatchException e){
                // 4. Catch NON-NUMBER Inputs
                System.out.println("Error Input! Please Enter a Valid Number, Not Characters");
                scanner.nextLine(); // VERY IMPORTANT: Clear the bad input from the scanner
            }

            catch (SQLException e) {
                System.out.println("Database error occurred: \n" + e.getMessage());
            }
        }
        
        scanner.close(); // Good practice to close the scanner
    }
}
