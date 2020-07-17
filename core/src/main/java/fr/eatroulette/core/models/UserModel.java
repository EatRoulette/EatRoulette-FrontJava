package fr.eatroulette.core.models;

public class UserModel {
    private String token;
    private String id;
    private String firstname;
    private String lastname;

    public UserModel(){
        this.token = "";
        this.id = "";
        this.firstname = "";
        this.lastname = "";
    }

    public UserModel(String token, String firstname, String lastname){
        this.token = token;
        this.id = "";
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserModel(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public UserModel(String token, String id, String firstname, String lastname) {
        this.token = token;
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public String getToken() {
        return token;
    }

    public String getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

}
