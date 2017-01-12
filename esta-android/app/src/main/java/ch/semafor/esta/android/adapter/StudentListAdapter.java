package ch.semafor.esta.android.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import ch.semafor.esta.R;
import ch.semafor.esta.android.domain.Student;

/**
 * The adapter inflates the layout for each row in the listView.
 */
public class StudentListAdapter extends ArrayAdapter<Student> {

    private final Context context;
    private final List<Student> students;

    public StudentListAdapter(Context context, List<Student> students) {
        super(context, -1, students);
        this.context = context;
        this.students = students;
    }

    /**
     * Inflates the layout and returns the View
     *
     * @return The view to insert into the list
     */
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View linearView;

        if (convertView == null) {
            linearView = inflater.inflate(R.layout.list_view, parent, false);
        } else {
            linearView = convertView;
        }

        // Get all the textViews
        TextView firstnameText = (TextView) linearView.findViewById(R.id.firstnameTextInList);
        TextView lastnameText = (TextView) linearView.findViewById(R.id.lastnameTextInList);
        TextView birthdateText = (TextView) linearView.findViewById(R.id.birthdateTextInList);

        Student student = students.get(position);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());

        // Set the text in all textViews
        firstnameText.setText(student.getFirstname());
        lastnameText.setText(student.getLastname());
        birthdateText.setText(dateFormat.format(student.getBirthdate()));

        return linearView;
    }

    /**
     * Get the list of students currently saved
     *
     * @return The list of students
     */
    public List<Student> getStudents() {
        return students;
    }
}
