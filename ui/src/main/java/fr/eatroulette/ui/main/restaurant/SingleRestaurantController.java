package fr.eatroulette.ui.main.restaurant;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.layout.VBox;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;

public class SingleRestaurantController implements Initializable {

    private StringProperty name = new SimpleStringProperty();
    private StringProperty address = new SimpleStringProperty();
    private StringProperty types = new SimpleStringProperty();
    private StringProperty allergens = new SimpleStringProperty();
    private StringProperty characteristics = new SimpleStringProperty();
    private StringProperty website = new SimpleStringProperty();
    private StringProperty city = new SimpleStringProperty();
    private StringProperty postalCode = new SimpleStringProperty();

    /**
     *
     * @param jsonObject
     */
    public SingleRestaurantController(JSONObject jsonObject) {
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if(jsonObject.get(key) == "" || jsonObject.get(key) instanceof JSONArray ){
                jsonObject.put(key,"No information");
            }
        }
        name.set(jsonObject.getString("name"));
        address.set(jsonObject.getString("address"));
        types.set((String) jsonObject.get("types"));
        //allergens.set(jsonObject.getString("allergens"));
        //characteristics.set(jsonObject.getString("characteristics"));
        //website.set(jsonObject.getString("website"));
        city.set(jsonObject.getString("city"));
        postalCode.set(jsonObject.getString("postalCode"));
    }

    @FXML
    Text nameRestaurantField;

    @FXML
    Text addressRestaurantField;
    @FXML
    Text typesRestaurantField;
    @FXML
    Text allergensRestaurantField;
    @FXML
    Text characteristicsRestaurantField;
    @FXML
    Text websiteRestaurantField;
    @FXML
    Text cityRestaurantField;
    @FXML
    Text postalCodeRestaurantField;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nameRestaurantField.setText(name.get());
        addressRestaurantField.setText(address.get());
        typesRestaurantField.setText(types.get());
        allergensRestaurantField.setText(allergens.get());
        characteristicsRestaurantField.setText(characteristics.get());
        websiteRestaurantField.setText(website.get());
        cityRestaurantField.setText(city.get());
        postalCodeRestaurantField.setText(postalCode.get());
    }
}