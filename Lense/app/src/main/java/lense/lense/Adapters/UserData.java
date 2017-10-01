package lense.lense.Adapters;

/**
 * Created by franciscoyimesinostroza on 04-06-17.
 */

public class UserData {



    private String name;

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String lastName;
    private String mail;
    private String password;

    public UserData(String name, String lastName, String mail, String password)
    {
        this.name = name;
        this.lastName = lastName;
        this.mail = mail;
        this.password = password;
    }



}