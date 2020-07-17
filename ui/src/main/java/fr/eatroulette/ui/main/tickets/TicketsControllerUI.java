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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
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
        System.out.println(tickets.size());
        this.displayTickets();
    }

    public void displayOnlyBugTickets(){
        VBox ticketsRoot = new VBox();
        // ClearView
        TicketsBox.getChildren().clear();

        List<TicketModel> filteredList = tickets.stream().filter(t -> t.getType().equals("Bogue") ).collect(Collectors.toList());
        display(ticketsRoot, filteredList);

    }

    public void displayOnlyDemandsTickets(){
        VBox ticketsRoot = new VBox();
        // ClearView
        TicketsBox.getChildren().clear();

        List<TicketModel> filteredList = tickets.stream().filter(t -> t.getType().equals("Demande") ).collect(Collectors.toList());
        display(ticketsRoot, filteredList);
    }

    public void displayOnlyNewRestaurantRequest(){
        VBox ticketsRoot = new VBox();
        // ClearView
        TicketsBox.getChildren().clear();

        List<TicketModel> filteredList = tickets.stream().filter(t -> t.getType().equals("Nouveau restaurant") ).collect(Collectors.toList());
        display(ticketsRoot, filteredList);
    }

    public void displayTickets(){
        VBox ticketsRoot = new VBox();
        // ClearView
        TicketsBox.getChildren().clear();

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
        Label message = new Label("Message : " + ticketToDisplay.getMessage());
        HBox hBoxStatus = new HBox(10);
        Label status = new Label("Status : " + ticketToDisplay.getStatus());
        ComboBox<String> comboBoxStatus = new ComboBox<>(FXCollections.observableArrayList(this.status));
        Label type = new Label("Type : " + ticketToDisplay.getType());
        Label createdAt = new Label("Créé le : " + ticketToDisplay.getCreatedAt().toString());
        // TODO Label author = new Label(ticketToDisplay.getAuthor());

        box.getChildren().add(title);
        box.getChildren().add(message);
        hBoxStatus.getChildren().add(status);
        hBoxStatus.getChildren().add(comboBoxStatus);
        box.getChildren().add(hBoxStatus);
        box.getChildren().add(type);
        box.getChildren().add(createdAt);
        box.getChildren().add( new Label("Commentaires : "));

        if(ticketToDisplay.getComments() != null){
            for(int i = 0; i < ticketToDisplay.getComments().size(); i++){
                CommentModel comment = ticketToDisplay.getComments().get(i);
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
