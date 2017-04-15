package id.semmi.pickme.model;

/**
 * Created by Semmiverian on 4/15/17.
 */

public class User {

    private String name;
    private String uuid;
    private String email;

    public User() {
    }

    public User (String name, String uuid, String email) {

        this.name = name;
        this.uuid = uuid;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
