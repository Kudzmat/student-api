package com.example.demo.student;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());

        // student validation by email
        if (studentOptional.isPresent()) {
            throw new IllegalStateException("email taken");
        }

        else {
            studentRepository.save(student);
        }
    }

    public void deleteStudent(Long studentId) {

        boolean student = studentRepository.existsById(studentId);

        // if student does not exist
        if (!student) {
            throw new IllegalStateException("Student with ID " + studentId + " does not exist");
        }

        else {
            studentRepository.deleteById(studentId);
        }
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email) {

        // checking if student exists
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalStateException("Student with ID " + studentId + " does not exist"));

        // if name is not empty and old name and new name are not equal
        if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)){
            student.setName(name);  // set new name
        }

        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)){

            // checking that the email has not been taken
            Optional<Student> studentOptional = studentRepository.findStudentByEmail(email);
            if (studentOptional.isPresent()) {
                throw new IllegalStateException("This email is taken");
            }
            student.setEmail(email);
        }
    }
}
