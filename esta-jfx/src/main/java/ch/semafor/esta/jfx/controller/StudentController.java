package ch.semafor.esta.jfx.controller;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.service.StudentService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * Created by tar on 26.12.16.
 */
@Component
public class StudentController implements Initializable {
  @Autowired
  StudentService studentService;
  @FXML
  private TableView<Student> table;
  @FXML
  private TextField firstnameFld;
  @FXML
  private TextField lastnameFld;
  @FXML
  private DatePicker birthdateFld;

  /**
   * find all students and set the student table
   */
   public void loadAll() {
     ObservableList<Student> rows =
                FXCollections.observableArrayList();
     List<Student> students = studentService.findAll();
     rows.addAll(students);
     table.setItems(rows);
     // Clear student details.
     showStudentDetails(null);
   }
   /**
   * set the labels with info from the student object.
   * @param student
   */
   private void showStudentDetails(Student student) {
     if (student != null) {
       firstnameFld.setText(student.getFirstname());
       lastnameFld.setText(student.getLastname());
       Instant instant = Instant.ofEpochMilli(
                   student.getBirthdate().getTime());
       LocalDate birthdate = LocalDateTime.ofInstant(
         instant, ZoneId.systemDefault()).toLocalDate();
       birthdateFld.setValue(birthdate);
     } else {
       // student is null, remove all the text.
       firstnameFld.setText("");
       lastnameFld.setText("");
       birthdateFld.setValue(null);
     }
   }
   @Override
   public void initialize(URL location,
                          ResourceBundle resources) {
     loadAll();
     // Listen for selection changes
     table.getSelectionModel().selectedItemProperty()
        .addListener( (observable, oldValue, newValue) ->
                          showStudentDetails(newValue));
   }

   public void clearStudent(){
     showStudentDetails(null);
     table.getSelectionModel().clearSelection();
   }
   public void saveStudent(){
     Student student = table.getSelectionModel()
                               .getSelectedItem();
     if( student == null ){
       student=new Student();
     }
     student.setFirstname(firstnameFld.getText());
     student.setLastname(lastnameFld.getText());
     Instant instant = birthdateFld.getValue()
        .atStartOfDay().atZone(
             ZoneId.systemDefault()).toInstant();
     student.setBirthdate(Date.from(instant));
     studentService.save(student);
     loadAll();
   }

}
