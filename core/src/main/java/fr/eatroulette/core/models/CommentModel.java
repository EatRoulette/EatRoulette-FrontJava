package fr.eatroulette.core.models;

import java.time.LocalDate;

public class CommentModel {
    private String id;
    private String message;
    private UserModel author;
    private LocalDate created_at;

    public CommentModel() {
        this.id = "";
        this.message = "";
    }

    public CommentModel(String message){
        this.id = "";
        this.message = message;
    }

    public CommentModel(String id, String message, UserModel author, LocalDate created_at){
        this.id = id;
        this.message = message;
        this.author = author;
        this.created_at = created_at;
    }

    public String toJSON(){
        return String.format("{\"message\": \"%s\"}", this.getMessage());
    }

    public String getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public UserModel getAuthor() {
        return author;
    }

    public LocalDate getCreated_at() {
        return created_at;
    }
}
