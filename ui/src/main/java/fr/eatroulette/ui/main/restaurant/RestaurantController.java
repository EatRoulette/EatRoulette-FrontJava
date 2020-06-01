package fr.eatroulette.ui.main.restaurant;

import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.json.JSONObject;

import java.io.IOException;

public class RestaurantController extends Application {

    public TextField nameFormField;
    public TextField siteFormField;
    public TextField cityFormField;
    public TextField addressFormField;
    public TextField postalCodeFormField;
    public TextField depFormField;
    public AnchorPane FormViewCreateRestaurant;
    public AnchorPane RestaurantBox;
    private Stage stage;
    private Router router;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        //this.router = new Router(stage);
        //router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
        stage.setTitle("Plugin Manager");
        stage.show();
    }

    public void RenderFormCreateRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/RestaurantCreateForm.fxml")));
    }

    public void SendFormCreateRestaurant(){
        JSONObject JsonformRestaurant = new JSONObject();
        JsonformRestaurant = this.verifInput(JsonformRestaurant);
        System.out.println(JsonformRestaurant);
    }

    public JSONObject verifInput(JSONObject jsonObject){
        if(!nameFormField.getText().isEmpty()){
            jsonObject.put("name", nameFormField.getText());
        }

        if(!siteFormField.getText().isEmpty()){
            jsonObject.put("site", siteFormField.getText());
        }

        if(!cityFormField.getText().isEmpty()){
            jsonObject.put("city", cityFormField.getText());
        }

        if(!addressFormField.getText().isEmpty()){
            jsonObject.put("address", addressFormField.getText());
        }

        if(!postalCodeFormField.getText().isEmpty()){
            jsonObject.put("postalCode", postalCodeFormField.getText());
        }

        if(!depFormField.getText().isEmpty()){
            jsonObject.put("dep", depFormField.getText());
        }

        return jsonObject;
    }

    public void ClearView(){
        FormViewCreateRestaurant.getChildren().clear();
    }

    public void RenderAllRestaurant(){

    }

    public void goToPlugin(){
        this.router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
    }

    public void setDataPane(Node node){
        RestaurantBox.getChildren().setAll(node);
    }

    public void setRouter(final Router router) {
        this.router = router;
    }
}
