package ch.semafor.esta.vaadin.forms;

import ch.semafor.esta.core.service.StudentService;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import org.springframework.beans.factory.annotation.Autowired;

/**
 */
@SpringUI
public class MainDialog extends UI {
    @Autowired
    StudentService studentService;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
      StudentForm studentForm = new StudentForm(studentService);
      setContent(studentForm);
    }

}
