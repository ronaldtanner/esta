package ch.semafor.esta.android.domain;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Student implements Comparable<Student> {

    private String href;
    private String firstname;
    private String lastname;
    private Date birthdate;

    public Student(String first, String last, Date birthdate) {
        this.firstname = first;
        this.lastname = last;
        this.birthdate = birthdate;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public JSONObject toJSONObject() throws JSONException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd", Locale.getDefault());
        JSONObject obj = new JSONObject();
        obj.put("firstname", this.firstname);
        obj.put("lastname", this.lastname);
        obj.put("birthdate", dateFormat.format(this.birthdate));
        return obj;
    }

    @Override
    public int compareTo(@NonNull Student o) {
        return this.href.compareTo(o.getHref());
    }
}