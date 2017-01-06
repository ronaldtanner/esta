package ch.semafor.esta.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import ch.semafor.esta.R;
import ch.semafor.esta.android.adapter.StudentListAdapter;
import ch.semafor.esta.android.domain.Student;
import ch.semafor.esta.android.service.StudentService;

public class MainActivity extends AppCompatActivity {

    /**
     * The Id of the student you are currently editing
     * <p>
     * If it's set to -1 it means that a new Student is being added
     */
    private long idOfCurrentStudent = -1;

    /**
     * This OnItemClickListener gets called every time you click an item in the listView
     */
    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            // Get the listAdapter
            StudentListAdapter listAdapter = (StudentListAdapter) parent.getAdapter();

            Student student = listAdapter.getStudents().get(position);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());

            EditText firstnameText = (EditText) findViewById(R.id.firstnameText);
            EditText lastnameText = (EditText) findViewById(R.id.lastnameText);
            EditText birthdateText = (EditText) findViewById(R.id.birthdateText);

            // Set the contents of the textFields
            firstnameText.setText(student.getFirstname());
            lastnameText.setText(student.getLastname());
            birthdateText.setText(dateFormat.format(student.getBirthdate()));
            idOfCurrentStudent = student.getId();

        }
    };

    /**
     * This method gets called when the activity starts
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_main);


        ListView listView = (ListView) findViewById(R.id.list);

        // Tells the listView what object to call to when an item gets clicked
        listView.setOnItemClickListener(listItemClickListener);
        refreshList();

    }

    /**
     * Adds all the students to the ListView
     */
    private void refreshList() {
        // Creates the ListAdapter with all students in it
        StudentListAdapter students = new StudentListAdapter(getApplicationContext(),
                StudentService.getInstance().getAllStudents());

        // Gets the listView
        ListView listView = (ListView) findViewById(R.id.list);

        // Gives it the adapter
        listView.setAdapter(students);

    }

    /**
     * Clears all the input fields
     */
    private void clearFields() {
        // Sets the id to -1 so that it knows that it needs a new student
        idOfCurrentStudent = -1;

        EditText firstnameText = (EditText) findViewById(R.id.firstnameText);
        EditText lastnameText = (EditText) findViewById(R.id.lastnameText);
        EditText birthdateText = (EditText) findViewById(R.id.birthdateText);

        firstnameText.setText("");
        lastnameText.setText("");
        birthdateText.setText("");
    }
    /**
     * Gets called when the 'refresh' button is pressed
     */
    public void onRefresh(View view) {
        refreshList();
    }


    /**
     * Gets called when the 'new' button is pressed
     */
    public void onNew(View view) {
        clearFields();
    }

    /**
     * Saves the Current student.
     *
     * It gets saved as a new one if idOfCurrentStudent = -1
     */
    public void onSave(View view) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());

        EditText firstnameText = (EditText) findViewById(R.id.firstnameText);
        EditText lastnameText = (EditText) findViewById(R.id.lastnameText);
        EditText birthdateText = (EditText) findViewById(R.id.birthdateText);

        // Checks if the firstname or lastname Fields are empty
        if(firstnameText.getText().toString().length() < 1 ||
                lastnameText.getText().toString().length() < 1) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.notAllFieldsFilledString),
                    Toast.LENGTH_LONG).show();
            return;
        }

        // Creates the Student with an empty birthdate which gets added later
        Student s = new Student(
                firstnameText.getText().toString(),
                lastnameText.getText().toString(),
                null);

        try {
            s.setBirthdate(dateFormat.parse(birthdateText.getText().toString()));
        } catch (ParseException e) {
            // Shows an error Toast if any problems occur while parsing the birthdate
            // Any problem that might occur during parsing likely was due to invalid input by the user
            Toast.makeText(getApplicationContext(),
                    getString(R.string.invalidBirthdateString),
                    Toast.LENGTH_LONG).show();
            // Make the method return to prevent any faulty data from being saved
            return;
        }

        // If the id is < 0 then add a new student
        if(idOfCurrentStudent < 0) {
            StudentService.getInstance().addStudent(s);
        } else {
            s.setId(idOfCurrentStudent);
            StudentService.getInstance().updateStudent(s, idOfCurrentStudent);
        }
        refreshList();
        clearFields();
    }
}
