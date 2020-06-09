package fr.eatroulette.ui.main.plugin;


import com.google.common.io.Files;
import fr.eatroulette.core.plugin.PluginManager;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.restaurant.RestaurantControllerUi;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;


import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PluginController extends Application {
    public ScrollPane ScrollPanePlugin;
    PluginManager p1Manager = new PluginManager();
    private Stage stage;
    private Router router;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        this.router = new Router(stage);
        router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
        stage.setTitle("Plugin Manager");
        stage.show();
    }


    @FXML
    public void addFileJarToDirectory() throws IOException {
        FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(stage);
        Path fileJar = Paths.get(file.getPath());

        // get the pwd of the project
        Path pathProject = Paths.get("");
        String strPathProject = pathProject.toAbsolutePath().toString();

        // concatenate the pwd with the location of the target
        Path pluginPath = Paths.get(strPathProject+File.separator+"plugins"+ File.separator + file.getName());

        // check the extension of the file
        String fileExtension = PluginController.getExtensionByGuava(file.getName());

        if(fileExtension.equals("jar")) {
             java.nio.file.Files.copy(fileJar, pluginPath ,REPLACE_EXISTING);
        }
    }

    @FXML
    public void refreshAllPlugin() {
        this.p1Manager.loadAllJar();
        VBox root = new VBox();
        for (String pluginName: this.p1Manager.getPluginsName()){
            Button button = new Button(pluginName);
            button.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        p1Manager.runPlugin(pluginName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            });
            root.getChildren().add(button);
        }
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        this.ScrollPanePlugin.setContent(root);
    }

    public void setRouter(final Router router) {
        this.router = router;
    }

    public static String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }

    public void goToRestaurant(){
        this.router.<RestaurantControllerUi>goTo("Restaurant", controller -> controller.setRouter(router));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
