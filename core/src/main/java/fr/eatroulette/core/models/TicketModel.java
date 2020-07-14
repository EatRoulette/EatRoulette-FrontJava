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
    }
}
