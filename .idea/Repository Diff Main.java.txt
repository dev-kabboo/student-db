package com.kabbo;

import com.kabbo.repository.StudentRepository;

import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {

        // S1: Create StudentRepository Object
        StudentRepository studentRepository = new StudentRepository();

        // S2: INSERTION
//        studentRepository.createStudent(new Student("madhob", "madhob@hkstore.com", 40));

        // S3: UPDATION
//        studentRepository.updateStudent(new Student("poli", "poli@gmail.com", 28), 8L);
//        studentRepository.updateStudent(new Student("kabbo", "kabbodebnath5@gmail.com", 23), 1L);

        // S4: DELETION
//        studentRepository.deleteStudent(6L);

        // S5: READ
//        studentRepository.getStudentById(8L);

        // S5: All Students
//        studentRepository.getAllStudent();

        // S6: Complete CRUD
// TEST 1: General Purpose SELECT
        String selectSql = "SELECT id, name, email, age FROM students WHERE id = 7";
        studentRepository.completeCRUD(selectSql);

// TEST 2: General Purpose UPDATE
//        String updateSql = "UPDATE students SET age = 33 WHERE id = 5";
//        studentRepository.completeCRUD(updateSql);


    }
}
