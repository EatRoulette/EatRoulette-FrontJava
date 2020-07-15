package fr.eatroulette.ui.main.tickets;


import com.google.common.io.Files;
import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.controllers.TicketController;
import fr.eatroulette.core.models.CommentModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TicketModel;
import fr.eatroulette.core.plugin.PluginManager;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import fr.eatroulette.ui.main.restaurant.RestaurantControllerUi;
import fr.eatroulette.ui.main.restaurant.SingleRestaurantController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TicketsController extends Application {
    public ScrollPane ScrollPanePlugin;
    PluginManager p1Manager = new PluginManager();
    private Stage stage;
    private Router router;
    private String LOGO_URL_64 = "https://i.ibb.co/s6tQ5bB/Eat-Roulette-logo-64.png";
    private String LOGO_URL_32 = "https://i.ibb.co/0jsyNT9/Eat-Roulette-logo-32.png";
    private String LOGO_URL_16 = "https://i.ibb.co/yng1L8K/Eat-Roulette-logo-16.png";

    public AnchorPane TicketsBox;
    private static List <TicketModel> tickets;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.router = new Router(stage);
        router.<TicketsController>goTo("Tickets", controller -> controller.setRouter(router));
        stage.setTitle("EatRoulette Admin Application");
        stage.getIcons().addAll(
                new Image(LOGO_URL_64),
                new Image(LOGO_URL_32),
                new Image(LOGO_URL_16)
        );
        stage.show();
    }

    @FXML
    public void initialize() {
        tickets = TicketController.getAllTickets();
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
        Label status = new Label("Status : " + ticketToDisplay.getStatus());
        Label type = new Label("Type : " + ticketToDisplay.getType());
        Label createdAt = new Label("Créé le : " + ticketToDisplay.getCreatedAt().toString());
        // TODO Label author = new Label(ticketToDisplay.getAuthor());

        box.getChildren().add(title);
        box.getChildren().add(message);
        box.getChildren().add(status);
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
    public void setDataPane(Node node){
        TicketsBox.getChildren().setAll(node);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
