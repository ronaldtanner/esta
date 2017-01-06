package ch.semafor.esta.android.domain;

import java.util.Date;

public class Student {

    private Long id;
    private String firstname;
    private String lastname;
    private Date birthdate;

    public Student() {
    }

    public Student(String first, String last, Date birthdate) {
        this.firstname = first;
        this.lastname = last;
        this.birthdate = birthdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

}