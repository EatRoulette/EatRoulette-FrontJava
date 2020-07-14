package fr.eatroulette.ui.main.restaurant;

import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.models.RestaurantModel;
import javafx.scene.control.TextField;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import netscape.javascript.JSObject;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class RestaurantControllerUi extends Application {

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
    @FXML
    public TextField nameFieldRestaurant;
    public TextField typesFieldRestaurant;
    public TextField cityFieldRestaurant;
    public TextField addressFieldRestaurant;
    public TextField postalCodeFieldRestaurant;

    public Text siteFieldRestaurant;


    @Override
    public void start(Stage stage) throws Exception {

    }

    public void RenderFormCreateRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/RestaurantCreateForm.fxml")));
    }

    public void SendFormCreateRestaurant(){
        RestaurantModel restaurant = this.collectFormRestaurantCreator();
        RestaurantController.addRestaurant(restaurant);
    }

    public RestaurantModel collectFormRestaurantCreator(){
        //TODO: L'api fait deja les test
        String name = "";
        String site = "";
        String city = "";
        String address = "";
        String postalCode = "";
        String dep = "";


        if(!nameFormField.getText().isEmpty()){
             name = nameFormField.getText();
        }

        if(!siteFormField.getText().isEmpty()){
            site = siteFormField.getText();
        }

        if(!cityFormField.getText().isEmpty()){
            city = cityFormField.getText();
        }

        if(!addressFormField.getText().isEmpty()){
            address = addressFormField.getText();
        }

        if(!postalCodeFormField.getText().isEmpty()){
            postalCode = postalCodeFormField.getText();
        }

        if(!depFormField.getText().isEmpty()){
            dep = depFormField.getText();
        }

        return new RestaurantModel(name, site, city, address, postalCode, dep);
    }

    public void ClearView(){
        FormViewCreateRestaurant.getChildren().clear();
    }

    public void RenderAllRestaurant(){
        List <RestaurantModel> restaurants = RestaurantController.getAllRestaurants();
        VBox restaurantsRoot = new VBox();
        // ClearView
        RestaurantBox.getChildren().clear();

        for (int i = 0; i < restaurants.size(); i++){
            RestaurantModel restaurant = restaurants.get(i);
            Button renderButton = new Button(restaurant.getName());
            renderButton.setOnAction(click -> {
                    try {
                        RestaurantBox.getChildren().clear();
                        FXMLLoader loaderSingleRestaurant = new FXMLLoader(getClass().getResource("/RestaurantOneJsonView.fxml"));
                        SingleRestaurantController singleRestaurantController = new SingleRestaurantController(restaurant);
                        loaderSingleRestaurant.setController(singleRestaurantController);
                        VBox vBoxSingleRestaurant = loaderSingleRestaurant.load();
                        RestaurantBox.getChildren().setAll(vBoxSingleRestaurant);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            });
            restaurantsRoot.getChildren().add(renderButton);
        }
        restaurantsRoot.setSpacing(10);
        restaurantsRoot.setAlignment(Pos.CENTER);
        this.setDataPane(restaurantsRoot);
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
