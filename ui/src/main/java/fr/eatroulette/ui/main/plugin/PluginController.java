package fr.eatroulette.ui.main.plugin;


import com.google.common.io.Files;
import fr.eatroulette.core.plugins.PluginManager;
import fr.eatroulette.ui.main.Router;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.IOException;



import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class PluginController extends Application {
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
        Path pluginPath = Paths.get(strPathProject+File.separator+"plugin"+ File.separator + file.getName());

        // check the extension of the file
        String fileExtension = PluginController.getExtensionByGuava(file.getName());

        if(fileExtension.equals("jar")) {
             java.nio.file.Files.copy(fileJar, pluginPath ,REPLACE_EXISTING);
        }
    }

    @FXML
    public void refreshAllPlugin() {
        //plManage.loadAllJar();
    }

    void setRouter(final Router router) {
        this.router = router;
    }

    public static String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
