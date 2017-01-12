package ch.semafor.esta;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.service.StudentService;
import io.github.bonigarcia.wdm.PhantomJsDriverManager;
import org.junit.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

// Run it with The Runner from Spring
@RunWith(SpringJUnit4ClassRunner.class)
// Make it use a random port on strat,
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        // Our main class
        classes = JqarApplication.class)
public class SeleniumWebTest {

    private WebDriver driver;

    @Autowired
    StudentService studentService;

    @LocalServerPort
    private int port;

    private String baseUrl;

    @BeforeClass
    public static void setupClass() {
        // Setup the PhantomJS Headless Browser
        PhantomJsDriverManager.getInstance().setup("2.1.1");
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Before
    public void setup() throws Exception {
        // Instantiate the driver and set the MaxTimeout to 10 seconds
        driver = new PhantomJSDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        baseUrl = "http://localhost:" + port + "/";
    }

    @Test
    public void addStudentTest() throws InterruptedException {
        // Get the initial size of the student list
        int studentListSize = studentService.findAll().size();
        driver.get(baseUrl + "students");

        // Find the input fields and send enter the appropriate keys
        driver.findElement(By.id("firstname")).sendKeys("Chuck");
        driver.findElement(By.id("lastname")).sendKeys("Norris");
        driver.findElement(By.id("birthdate")).sendKeys("1940-03-10");

        // Click the submit button and wait half a second for the request to process
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(500);
        driver.close();

        // Compare the size of the new student list
        Assert.assertEquals(studentListSize + 1, studentService.findAll().size());

        // Check if we can find our new Student
        for(Student s : studentService.findAll()) {
            if(s.getFirstname().equals("Chuck") && s.getLastname().equals("Norris") &&
                    String.valueOf(s.getBirthdate()).equals("1940-03-10")) {
                // If the student was found then we are done with this test and can return
                return;
            }
        }

        // If the test is not done at this point, the student wasn't found because something happened
        Assert.fail("Student 'Chuck Norris' not found");

    }

    @Test
    public void editStudentTest() throws InterruptedException {
        // Get the initial size of the student list
        int studentListSize = studentService.findAll().size();
        driver.get(baseUrl + "students");

        // Click the first student's edit button
        driver.findElement(By.xpath("//a[@href='/students/1']")).click();
        Thread.sleep(100);

        // Find the field with the firstname
        WebElement element = driver.findElement(By.id("firstname"));

        // Clear the field and enter the new name
        element.clear();
        element.sendKeys("TestStudent1234");

        // Click the submit button and wait half a second for the request to process
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        Thread.sleep(500);
        driver.close();

        // Check to see if the list is still the same size
        Assert.assertEquals(studentListSize, studentService.findAll().size());

        // Check if a student with the new name was found, if yes we are done with this test
        for(Student s : studentService.findAll()) {
            if(s.getFirstname().equals("TestStudent1234")) {
                return;
            }
        }

        // If the test is not done at this point, the student wasn't found because something happened
        Assert.fail("Edited Student not found");
    }

}