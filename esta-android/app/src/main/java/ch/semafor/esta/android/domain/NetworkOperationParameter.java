package ch.semafor.esta.android.domain;

public class NetworkOperationParameter {

    public static final String PUT = "PUT";
    public static final String GET = "GET";
    public static final String POST = "POST";


    /**
     * What type of request it is.
     * <p>
     * The static fields of this Class represent the types
     */
    private String requestMethod;
    private Student student;

    public NetworkOperationParameter(String requestMethod, Student student) {
        this.requestMethod = requestMethod;
        this.student = student;
    }

    public String getRequestType() {
        return requestMethod;
    }

    public Student getStudent() {
        return student;
    }
}
