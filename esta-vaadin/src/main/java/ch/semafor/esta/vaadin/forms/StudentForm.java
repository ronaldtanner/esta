package ch.semafor.esta.vaadin.forms;

import ch.semafor.esta.core.domain.Student;
import ch.semafor.esta.core.service.StudentService;
import com.vaadin.ui.*;

import java.util.Date;

/**
 */
public class StudentForm extends VerticalLayout {
  private final Button saveBtn = new Button("Save");
  private final Button cancelBtn = new Button("Cancel");
  private final TextField firstName = new TextField("First Name");
  private final TextField lastName = new TextField("Last Name");
  private final DateField birthdate = new DateField("Birthdate");


  public StudentForm(StudentService studentService) {
    birthdate.setDateFormat("yyyy-MM-dd");

    HorizontalLayout footer = new HorizontalLayout();
    footer.setSpacing(true);
    footer.addComponent(saveBtn);
    footer.addComponent(cancelBtn);
    addComponents(firstName, lastName, birthdate, footer);
    setMargin(true);
    setSpacing(true);

      saveBtn.addClickListener(event ->
              studentService.save(new Student(
              (String) firstName.getValue(),
              (String) lastName.getValue(),
              (Date) birthdate.getValue())));
  }
}
