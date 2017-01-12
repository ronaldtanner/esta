package ch.semafor.esta;

import android.content.Context;
import android.os.AsyncTask;
import android.test.mock.MockContext;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ch.semafor.esta.android.domain.NetworkOperationParameter;
import ch.semafor.esta.android.domain.Student;
import ch.semafor.esta.android.service.NetworkOperation;
import ch.semafor.esta.android.service.StudentService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(NetworkOperation.class)
public class StudentsTest {

    @Mock
    private NetworkOperation operationMock;

    @Mock
    private AsyncTask<NetworkOperationParameter, Integer, List<Student>> taskMock;

    private List<Student> testStudents = new ArrayList<>();

    @Before
    public void setUp() throws ExecutionException, InterruptedException {
        // Create a test Student
        Student student = new Student("Test", "Test", null);
        student.setHref("testHref");
        testStudents.add(student);


        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(NetworkOperation.class);


        // Define the way the NetworkOperation responds
        PowerMockito.when(operationMock.execute(Mockito.any(NetworkOperationParameter[].class))).thenAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                // Get the Parameter
                NetworkOperationParameter param = (NetworkOperationParameter) invocation.getArguments()[0];

                // Respond depending on the requestType
                switch (param.getRequestType()) {
                    case NetworkOperationParameter.GET:
                        return taskMock;
                    case NetworkOperationParameter.POST:
                        testStudents.add(param.getStudent());
                        break;
                    case NetworkOperationParameter.PUT:
                        // Adds the new student and removes the old version of it
                        testStudents.add(param.getStudent());
                        for (Student s : testStudents) {
                            if (s.getHref().equals(param.getStudent().getHref())) {
                                testStudents.remove(s);
                                break;
                            }
                        }
                        break;
                }
                return null;
            }
        });

        PowerMockito.when(taskMock.get()).thenReturn(testStudents);

        PowerMockito.when(NetworkOperation.create(Mockito.any(Context.class))).thenReturn(operationMock);
    }

    @Test
    public void getAllStudentsTest() {
        List<Student> studs = StudentService.getInstance().getAllStudents(new MockContext());
        Assert.assertEquals(testStudents.size(), studs.size());
    }

    @Test
    public void addStudentTest() {
        // Get the initial size of the student list
        int studentListSize = StudentService.getInstance().getAllStudents(new MockContext()).size();

        // Creates a new Student and adds it
        Student student = new Student("Chuck", "Norris", null);
        StudentService.getInstance().addStudent(student, new MockContext());

        // Get the new size of the student list
        int newStudentListSize = StudentService.getInstance().getAllStudents(new MockContext()).size();

        // Checks if the new list is bigger by one
        Assert.assertEquals(studentListSize + 1, newStudentListSize);
    }

    @Test
    public void updateStudentTest() {
        List<Student> studentList = StudentService.getInstance().getAllStudents(new MockContext());

        // Get the first student and the initial size of the student list
        int studentListSize = studentList.size();
        Student studentToUpdate = studentList.get(0);

        // Create a new date and add it to the student
        Date date = new Date();
        date.setTime(9001);
        studentToUpdate.setBirthdate(date);


        StudentService.getInstance().updateStudent(studentToUpdate, new MockContext());

        // Get the new Student list size and check if it remained unchanged
        List<Student> newStudentList = StudentService.getInstance().getAllStudents(new MockContext());
        int newStudentListSize = newStudentList.size();
        Assert.assertEquals(studentListSize, newStudentListSize);

        // Gets the student by its href
        boolean found = false;
        Student stud = null;
        for (Student s : newStudentList) {
            if (s.getHref().equals(studentToUpdate.getHref())) {
                found = true;
                stud = s;
                break;
            }
        }

        // Checks if it got found, if yes it compares the birthdates
        Assert.assertTrue(found);
        Assert.assertEquals(date, stud.getBirthdate());

    }

}
