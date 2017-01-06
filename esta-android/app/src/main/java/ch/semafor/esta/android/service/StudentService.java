package ch.semafor.esta.android.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import ch.semafor.esta.android.domain.Student;

/**
 * This is a singleton class for interacting with the Database
 * <p>
 * If no connection to the Database could be established a local list of exampleStudents is used instead
 */
public class StudentService {

    // Singleton pattern
    private static StudentService ourInstance = new StudentService();

    // The list of example students which gets used in case no connection to the data could be established
    private List<Student> exampleStudents = new ArrayList<Student>(Arrays.asList(
            new Student("Max", "Muster", null),
            new Student("Matt", "Stauch", null),
            new Student("Adam", "Woods", null),
            new Student("Walter", "White", null),
            new Student("Valerie", "Holly", null),
            new Student("David", "Hall", null),
            new Student("Bettie", "Tyler", null)
    ));

    private StudentService() {
        // Gives each student a Birthdate and an Id
        long i = 0;
        for (Student s : exampleStudents) {
            s.setId(i);
            try {
                // Sets the Birthdate to the 1Xth of May 1994
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
                s.setBirthdate(dateFormat.parse("1994-04-1" + i));
            } catch (ParseException e) {
                e.printStackTrace();
                // Should never happen
            }
            i++;
        }
    }

    public static StudentService getInstance() {
        return ourInstance;
    }

    /**
     * Checks if a connection to the Database can be established
     *
     * @return If the connection could be established, true is returned. If it fails it returns false
     */
    private boolean checkDatabaseConnection() {
        // TODO: Implement this method
        return false;
    }

    /**
     * Returns a list of all students
     *
     * @return The list of students
     */
    public List<Student> getAllStudents() {
        if (checkDatabaseConnection()) {
            // TODO: Implement functionality for getting students from Database
        } else {
            return exampleStudents;
        }
        return null;
    }

    /**
     * Returns one Student by its Id
     *
     * @param id The id of the wanted Student
     * @return The Student of the Id or null if none is found
     */
    public Student getStudentById(long id) {
        if (checkDatabaseConnection()) {
            // TODO: Implement functionality for getting students from Database
        } else {
            for (Student s : exampleStudents) {
                if (s.getId() == id) {
                    return s;
                }
            }
        }
        return null;
    }

    /**
     * Adds a new Student to the database
     *
     * @param s The student to add
     */
    public void addStudents(Student s) {
        if (checkDatabaseConnection()) {
            // TODO: Implement functionality for adding students to the Database
        } else {
            exampleStudents.add(s);
        }
    }

    /**
     * Removes a student from the database
     *
     * @param id Id of the student to remove
     */
    public void removeStudent(long id) {
        if (checkDatabaseConnection()) {
            // TODO: Implement functionality for removing students from Database
        } else {
            exampleStudents.remove(getStudentById(id));
        }
    }
}
