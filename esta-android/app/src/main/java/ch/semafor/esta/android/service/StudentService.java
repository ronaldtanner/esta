package ch.semafor.esta.android.service;

import android.content.Context;

import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.semafor.esta.android.domain.NetworkOperationParameter;
import ch.semafor.esta.android.domain.Student;

/**
 * This is a singleton class for interacting with the Database
 * <p>
 * If no connection to the Database could be established a local list of exampleStudents is used instead
 */
public class StudentService {

    // Singleton pattern
    private static StudentService ourInstance = new StudentService();

    private StudentService() {
    }

    public static StudentService getInstance() {
        return ourInstance;
    }

    /**
     * Returns a list of all students
     *
     * @return The list of students
     */
    public List<Student> getAllStudents(Context context) {
        try {

            NetworkOperation operation = new NetworkOperation(context);
            return operation.execute(new NetworkOperationParameter(NetworkOperationParameter.GET,
                    null)).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Adds a new Student to the database
     *
     * @param s The student to add
     */
    public void addStudent(Student s, Context context) {
        // Creates and executes the operation
        NetworkOperation operation = new NetworkOperation(context);
        operation.execute(new NetworkOperationParameter(NetworkOperationParameter.POST, s));
    }

    /**
     * Updates a student
     *
     * @param s The student to update
     */
    public void updateStudent(Student s, Context context) {
        // Creates and executes the operation
        NetworkOperation operation = new NetworkOperation(context);
        operation.execute(new NetworkOperationParameter(NetworkOperationParameter.PUT, s));
    }
}
