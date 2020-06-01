package fr.eatroulette.ui.main.restaurant;

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
        JSONObject JsonformRestaurant = new JSONObject();
        JsonformRestaurant = this.verifInput(JsonformRestaurant);
        System.out.println(JsonformRestaurant);
        //TODO: request vers l'api CreateRestaurant
    }

    public JSONObject verifInput(JSONObject jsonObject){
        //TODO: L'api fait deja les test
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
        //TODO: request vers l'api getAllRestaurant
        String jsonString = "{ \"allRestaurant\":[\n" +
                "    {\n" +
                "    \"_id\" : \"\",\n" +
                "    \"types\" : [],\n" +
                "    \"allergens\" : [],\n" +
                "    \"characteristics\" : [],\n" +
                "    \"name\" : \"rere al taglio\",\n" +
                "    \"site\" : \"----\",\n" +
                "    \"address\" : \"27 Rue Erard\",\n" +
                "    \"city\" : \"PARIS\",\n" +
                "    \"postalCode\" : \"75012\",\n" +
                "    \"dep\" : \"75\",\n" +
                "    \"__v\" : 0\n" +
                "    },\n" +
                "    {\n" +
                "    \"_id\" : \"\",\n" +
                "    \"types\" : [],\n" +
                "    \"name\" : \"zerzer al taglio\",\n" +
                "    \"site\" : \"----\",\n" +
                "    \"address\" : \"27 Rue Erard\",\n" +
                "    \"city\" : \"PARIS\",\n" +
                "    \"postalCode\" : \"75012\",\n" +
                "    \"dep\" : \"75\",\n" +
                "    \"_idSituation\" : \"---\",\n" +
                "    \"__v\" : 0\n" +
                "    }]\n" +
                "}";
        JSONObject jsonAllRestaurants = new JSONObject(jsonString);
        VBox restaurantsRoot = new VBox();
        // ClearView
        RestaurantBox.getChildren().clear();
        JSONArray restaurants = jsonAllRestaurants.getJSONArray("allRestaurant");

        for (int i = 0; i < restaurants.length(); i++){
            JSONObject restaurant = restaurants.getJSONObject(i);
            Button renderButton = new Button(restaurant.getString("name"));
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
