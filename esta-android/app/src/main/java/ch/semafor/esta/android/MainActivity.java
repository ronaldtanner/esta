package ch.semafor.esta.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import ch.semafor.esta.R;
import ch.semafor.esta.android.adapter.StudentListAdapter;
import ch.semafor.esta.android.service.StudentService;

public class MainActivity extends AppCompatActivity {

    /**
     * This OnItemClickListener gets called every time you click an item in the listView
     */
    private AdapterView.OnItemClickListener listItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // TODO: Implement this method
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
