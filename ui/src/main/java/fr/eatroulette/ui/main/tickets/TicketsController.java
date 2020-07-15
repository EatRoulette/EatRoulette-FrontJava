package fr.eatroulette.ui.main.tickets;


import com.google.common.io.Files;
import fr.eatroulette.core.plugin.PluginManager;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import fr.eatroulette.ui.main.restaurant.RestaurantControllerUi;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class TicketsController extends Application {
    public ScrollPane ScrollPanePlugin;
    PluginManager p1Manager = new PluginManager();
    private Stage stage;
    private Router router;
    private String LOGO_URL_64 = "https://i.ibb.co/s6tQ5bB/Eat-Roulette-logo-64.png";
    private String LOGO_URL_32 = "https://i.ibb.co/0jsyNT9/Eat-Roulette-logo-32.png";
    private String LOGO_URL_16 = "https://i.ibb.co/yng1L8K/Eat-Roulette-logo-16.png";

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

    public void setRouter(final Router router) {
        this.router = router;
    }

    public void goToPlugin(){
        this.router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
    }
    public void goToRestaurant(){
        this.router.<RestaurantControllerUi>goTo("Restaurant", controller -> controller.setRouter(router));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
