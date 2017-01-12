package ch.semafor.esta.android.service;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

import ch.semafor.esta.R;
import ch.semafor.esta.android.domain.NetworkOperationParameter;
import ch.semafor.esta.android.domain.Student;

/**
 * Helper Class to perform a network Operation on Esta
 */
public class NetworkOperation extends AsyncTask<NetworkOperationParameter, Integer, List<Student>> {

    private final Context context;

    /**
     * Constructs a network Operation
     *
     * @param context Current application Context
     */
    private NetworkOperation(Context context) {
        this.context = context;
    }

    public static NetworkOperation create(Context context) {
        return new NetworkOperation(context);
    }

    /**
     * Executes a network operation
     *
     * @param params The parameters. Only one should be given. If more are given
     *               all except the first one are ignored
     * @return A list of students or null Depending on the parameters
     */
    @Override
    protected List<Student> doInBackground(NetworkOperationParameter... params) {
        try {

            NetworkOperationParameter param = params[0];

            switch (param.getRequestType()) {
                case "GET":
                    return getStudents();
                case "POST":
                    addStudent(param);
                    break;
                case "PUT":
                    updateStudent(param);
                    break;
                default:
                    return null;
            }


        } catch (IOException | JSONException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Gets student(s) based on the param
     *
     * @return The List of students
     */
    private List<Student> getStudents() throws IOException, JSONException, ParseException {

        String urlString = context.getString(R.string.webURL);
        URL requestURL = new URL(urlString);

        // Opens the connection
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();


        // Converts the InputStream of the connection to a String
        Scanner s = new Scanner(connection.getInputStream()).useDelimiter("\\A");
        String jsonString = s.hasNext() ? s.next() : "";

        JSONObject json = new JSONObject(jsonString);
        List<Student> students = new ArrayList<>();

        // The JSON you get is different when you get all than when you only get one
        // Gets the array of student objects
        JSONArray studentsArray = json.getJSONObject("_embedded").getJSONArray("students");

        // Iterates trough the array and extract the students
        for (int i = 0; i < studentsArray.length(); i++) {
            JSONObject objStud = studentsArray.getJSONObject(i);
            students.add(getStudentFromJSONObject(objStud));
        }
        return students;
    }


    /**
     * Adds a new Student
     *
     * @param param The parameter with the Student field filled out
     * @throws IOException If there was trouble with the url
     */
    private void addStudent(NetworkOperationParameter param) throws IOException, JSONException {
        URL requestURL = new URL(context.getString(R.string.webURL));
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        // Set the header
        connection.setRequestProperty("Content-Type", "application/json");

        // Set the parameters in the connection
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod(NetworkOperationParameter.POST);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        writer.write(param.getStudent().toJSONObject().toString());
        writer.flush();
        writer.close();
        os.close();

        connection.getResponseCode();
    }

    /**
     * Edits a Student
     *
     * @param param The parameter with the Student field filled out
     * @throws IOException If there was trouble with the url
     */
    private void updateStudent(NetworkOperationParameter param) throws IOException, JSONException {
        URL requestURL = new URL(param.getStudent().getHref());
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection();

        // Set the header
        connection.setRequestProperty("Content-Type", "application/json");

        // Set the parameters in the connection
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(15000);
        connection.setRequestMethod(NetworkOperationParameter.PUT);
        connection.setDoInput(true);
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

        writer.write(param.getStudent().toJSONObject().toString());
        writer.flush();
        writer.close();
        os.close();

        connection.getResponseCode();
    }

    /**
     * Returns a Student from the given JSONObject
     *
     * @param json The JSONObject of this structure;
     *             <code>  {
     *             "firstname" : "Jakob",
     *             "lastname" : "Lanz",
     *             "birthdate" : "1993-04-09",
     *             "_links" : {
     *             "self" : {
     *             "href" : "http://localhost:8080/api/students/1"
     *             },
     *             "student" : {
     *             "href" : "http://localhost:8080/api/students/1"
     *             }
     *             }
     *             }</code>
     * @return The student contained in the JSONObject
     * @throws JSONException  If invalid JSON is passed
     * @throws ParseException If the birthdate is invalid
     */
    private Student getStudentFromJSONObject(JSONObject json) throws JSONException, ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());

        Student stud = new Student(
                json.getString("firstname"),
                json.getString("lastname"),
                dateFormat.parse(json.getString("birthdate"))
        );

        // The Id isn't stored directly, so it needs to get it from that link
        // The last character of that link is the id we need
        String urlWithId = json.getJSONObject("_links").getJSONObject("student").getString("href");

        stud.setHref(urlWithId);

        return stud;
    }
}
