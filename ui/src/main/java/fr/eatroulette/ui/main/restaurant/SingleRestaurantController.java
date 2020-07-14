package fr.eatroulette.ui.main.restaurant;

import fr.eatroulette.core.models.AllergenModel;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;
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
import java.lang.reflect.Type;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
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

    /***
     *
     * @param restaurant
     */
    public SingleRestaurantController(RestaurantModel restaurant) {
        name.set(restaurant.getName());
        address.set(restaurant.getAddress());
        types.set(this.listToStringTypeModel(restaurant.getTypes()));
        allergens.set(this.listToStringAllergenModel(restaurant.getAllergens()));
        characteristics.set(this.listToStringCharacteristicModel(restaurant.getCharacteristics()));
        website.set(restaurant.getSite());
        city.set(restaurant.getCity());
        postalCode.set(restaurant.getPostalCode());
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

    public String listToStringAllergenModel(List<AllergenModel> list){
        if(list.isEmpty()) return "No Information";
        String text = "";

        for (AllergenModel o: list) {
            text += o.getName()+" ,";
        }

        return text;
    }

    public String listToStringCharacteristicModel(List<CharacteristicModel> list){
        if(list.isEmpty()) return "No Information";
        String text = "";

        for (CharacteristicModel o: list) {
            text += o.getName()+" ,";
        }

        return text;
    }

    public String listToStringTypeModel(List<TypeModel> list){
        if(list.isEmpty()) return "No Information";
        String text = "";

        for (TypeModel o: list) {
            text += o.getName()+" ,";
        }

        return text;
    }
}