package fr.eatroulette.core.models;

import java.time.LocalDate;
import java.util.List;

public class TicketModel {
    private String id;
    private UserModel author;
    private String title;
    private String message;
    private String Status;
    private String type;
    private Integer emergency;
    private List<CommentModel> comments;
    private LocalDate createdAt;
    private boolean sortedComment;


    public TicketModel(String id, UserModel author,
                       String title, String message,
                       String status, String type, Integer emergency,
                       List<CommentModel> comments, LocalDate createdAt) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.message = message;
        Status = status;
        this.type = type;
        this.emergency = emergency;
        this.comments = comments;
        this.createdAt = createdAt;
        this.sortedComment = false;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        String name = "";
        if(this.author != null){
            name = this.author.getFirstname() + " " + this.author.getLastname();
        }
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEmergency() {
        return emergency;
    }

    public void setEmergency(Integer emergency) {
        this.emergency = emergency;
    }

    public void addComment(CommentModel c){
        this.comments.add(c);
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public boolean isSortedComment() {
        return sortedComment;
    }

    public void setSortedComment(boolean sortedComment) {
        this.sortedComment = sortedComment;
    }
}
