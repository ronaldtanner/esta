package ch.semafor.esta.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

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
        addStudentsToList();

    }

    /**
     * Adds all the students to the ListView
     */
    private void addStudentsToList() {
        // Creates the ListAdapter with all students in it
        StudentListAdapter students = new StudentListAdapter(getApplicationContext(),
                StudentService.getInstance().getAllStudents());

        // Gets the listView
        ListView listView = (ListView) findViewById(R.id.list);

        // Gives it the adapter
        listView.setAdapter(students);

    }

    /**
     * Gets called when the 'refresh' button is pressed
     */
    public void onRefresh(View view) {
        addStudentsToList();
    }
}
