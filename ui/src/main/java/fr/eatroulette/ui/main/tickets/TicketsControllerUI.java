package fr.eatroulette.ui.main.tickets;


import fr.eatroulette.core.controllers.TicketController;
import fr.eatroulette.core.models.CommentModel;
import fr.eatroulette.core.models.TicketModel;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import fr.eatroulette.ui.main.restaurant.RestaurantControllerUi;
import javafx.collections.FXCollections;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TicketsControllerUI implements Initializable {
    public ScrollPane ScrollPanePlugin;
    private Stage stage;
    private Router router;

    public AnchorPane TicketsBox;
    private static List <TicketModel> tickets;
    private List<String> status = new ArrayList<String>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tickets = TicketController.getAllTickets();
        this.status.add("En cours de traitement");
        this.status.add("En attente");
        this.status.add("Traité");
        this.displayTickets();
    }

    public void displayOnlyBugTickets(){
        VBox ticketsRoot = new VBox();
        this.ClearView();

        List<TicketModel> filteredList = tickets.stream()
                .filter(t -> t.getType().equals("Bogue") )
                .collect(Collectors.toList());
        display(ticketsRoot, filteredList);

    }

    public void displayOnlyDemandsTickets(){
        VBox ticketsRoot = new VBox();
        this.ClearView();

        List<TicketModel> filteredList = tickets.stream()
                .filter(t -> t.getType().equals("Demande") )
                .collect(Collectors.toList());
        display(ticketsRoot, filteredList);
    }

    public void displayOnlyNewRestaurantRequest(){
        VBox ticketsRoot = new VBox();
        this.ClearView();

        List<TicketModel> filteredList = tickets.stream()
                .filter(t -> t.getType().equals("Nouveau restaurant") )
                .collect(Collectors.toList());
        display(ticketsRoot, filteredList);
    }

    public void displayTickets(){
        VBox ticketsRoot = new VBox();
        this.ClearView();

        display(ticketsRoot, tickets);
    }

    private void display (VBox box, List<TicketModel> ticketsToDisplay){

        for (int i = 0; i < ticketsToDisplay.size(); i++){
            TicketModel ticket = ticketsToDisplay.get(i);
            Button renderButton = new Button(ticket.getTitle());
            renderButton.setOnAction(click -> {
                try {
                    box.getChildren().clear();
                    // display details
                    displayDetails(box, ticket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            box.getChildren().add(renderButton);
        }
        box.setSpacing(10);
        box.setAlignment(Pos.CENTER);
        this.setDataPane(box);
    }

    private void displayDetails (VBox box, TicketModel ticketToDisplay){
        Label title = new Label("Titre : " + ticketToDisplay.getTitle());
        Label author = new Label("Auteur : " + ticketToDisplay.getAuthorName());
        Label message = new Label("Message : " + ticketToDisplay.getMessage());
        HBox hBoxStatus = new HBox(10);
        Label status = new Label("Status : " + ticketToDisplay.getStatus());
        ComboBox<String> comboBoxStatus = new ComboBox<>(FXCollections.observableArrayList(this.status));
        Label type = new Label("Type : " + ticketToDisplay.getType());
        Label createdAt = new Label("Créé le : " + ticketToDisplay.getCreatedAt().toString());
        Button updateStatusBtn = new Button("Update");
        updateStatusBtn.setOnAction(click -> {
            ticketToDisplay.setStatus(comboBoxStatus.getValue());
            if (TicketController.updateTicketStatus(ticketToDisplay)){
                box.getChildren().clear();
                displayDetails(box, ticketToDisplay);
            }
        });
        HBox addCommHbox = new HBox(10);
        TextField commentField = new TextField();
        Button addCommBtn = new Button("Commenter");
        addCommBtn.setOnAction(click -> {
            CommentModel c = new CommentModel(commentField.getText());
            if(TicketController.addCommentToTicket(ticketToDisplay, c)){
                Collections.reverse(ticketToDisplay.getComments());
                ticketToDisplay.addComment(c);
                box.getChildren().clear();
                Collections.reverse(ticketToDisplay.getComments());
                displayDetails(box, ticketToDisplay);
            }
        });

        box.getChildren().add(title);
        box.getChildren().add(author);
        box.getChildren().add(message);
        hBoxStatus.getChildren().add(status);
        hBoxStatus.getChildren().add(comboBoxStatus);
        hBoxStatus.getChildren().add(updateStatusBtn);
        box.getChildren().add(hBoxStatus);
        box.getChildren().add(type);
        box.getChildren().add(createdAt);
        box.getChildren().add( new Label("Section commentaires : "));
        box.getChildren().add( new Separator());
        addCommHbox.getChildren().add( new Label("Nouveau commentaire :"));
        addCommHbox.getChildren().add(commentField);
        addCommHbox.getChildren().add(addCommBtn);
        box.getChildren().add(addCommHbox);
        box.getChildren().add( new Label("Commentaires :"));

        List<CommentModel> comments = ticketToDisplay.getComments();
        //To get the latest first
        if (!ticketToDisplay.isSortedComment()){
            Collections.reverse(comments);
            ticketToDisplay.setSortedComment(true);
        }
        if(comments != null){
            for(int i = 0; i < comments.size(); i++){
                CommentModel comment = comments.get(i);
                Label commentLabel = new Label(comment.getMessage());
                box.getChildren().add(commentLabel);
            }
        }

        box.setSpacing(10);
        box.setAlignment(Pos.BASELINE_LEFT);
        this.setDataPane(box);
    }

    public void setRouter(final Router router) {
        this.router = router;
    }

    public void goToPlugin(){
        this.router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
    }

    public void goToRestaurant(){
        this.router.<RestaurantControllerUi>goTo("Restaurant", controller -> controller.setRouter(router));
    }

    private void setDataPane(Node node){
        TicketsBox.getChildren().setAll(node);
    }

    public void ClearView(){
        TicketsBox.getChildren().clear();
    }
}
