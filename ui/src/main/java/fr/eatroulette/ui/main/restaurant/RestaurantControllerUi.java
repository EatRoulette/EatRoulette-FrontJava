package fr.eatroulette.ui.main.restaurant;

import fr.eatroulette.core.controllers.CharacteristicController;
import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.controllers.TypeController;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantControllerUi extends Application {

    @FXML
    public TextField nameFormField;
    @FXML
    public TextField siteFormField;
    @FXML
    public TextField cityFormField;
    @FXML
    public TextField addressFormField;
    @FXML
    public TextField postalCodeFormField;
    @FXML
    public TextField depFormField;

    @FXML
    public AnchorPane FormViewCreateRestaurant;
    @FXML
    public AnchorPane RestaurantBox;
    @FXML
    public ComboBox<String> comboRestaurant;
    @FXML
    public ComboBox comboType;
    @FXML
    public ComboBox comboCharac;
    private Stage stage;
    private Router router;
    @FXML
    public TextField nameFieldRestaurant;
    public TextField typesFieldRestaurant;
    public TextField cityFieldRestaurant;
    public TextField addressFieldRestaurant;
    public TextField postalCodeFieldRestaurant;
    public Text siteFieldRestaurant;

    @FXML
    public TextField typeNameField;
    @FXML
    public TextField characNameField;

    private HashMap<String, RestaurantModel> hashMapRestaurant = new HashMap<String, RestaurantModel>();
    private HashMap<String, TypeModel> hashMapType = new HashMap<String, TypeModel>();
    private HashMap<String, CharacteristicModel> hashMapCharac = new HashMap<String, CharacteristicModel>();
    private List<String> listRestaurantName = new ArrayList<String>();
    private List<String> listTypeName = new ArrayList<String>();
    private List<String> listCharacName = new ArrayList<String>();

    @Override
    public void start(Stage stage) throws Exception {

    }

    /**
     * Restaurant view create form
     */
    public void RenderFormCreateRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/RestaurantCreateForm.fxml")));
    }

    public void SendFormCreateRestaurant(){
        RestaurantModel restaurant = this.collectFormRestaurantCreator();
        RestaurantController.addRestaurant(restaurant);
    }

    public RestaurantModel collectFormRestaurantCreator(){
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

    /**
     * Render a button for each restaurant
     */
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

    /**
     * Add type to restaurant view
     */
    public void RenderFromAddTypeRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/RestaurantAddTypeView.fxml")));
    }

    private void loadRestaurantTypeInformation(){
        this.loadRestaurants();
        this.loadTypes();
    }

    public void loadBoxes(){
        this.loadRestaurantTypeInformation();
        comboRestaurant.setItems(FXCollections.observableArrayList(this.listRestaurantName));
        comboType.setItems(FXCollections.observableArrayList(this.listTypeName));
    }

    public void sendFormAddType(){
        RestaurantModel r = this.hashMapRestaurant.get(comboRestaurant.getValue());
        TypeModel t = this.hashMapType.get(comboType.getValue());
        r = RestaurantController.addTypeToRestaurant(r, t);
        if (!r.getName().isEmpty()){
            this.ClearView();
        }
    }

    /**
     *  Add type view
     */
    public void loadTypeForm()throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/AddTypeView.fxml")));
    }

    public void saveType(){
        String typeName = this.typeNameField.getText();
        if(!typeName.isEmpty() || !typeName.isBlank()) {
            TypeModel type = new TypeModel(typeName);
            if (!TypeController.addType(type).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     * Add characteristic view
     */
    public void loadCharacForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/AddCharacView.fxml")));
    }

    public void saveCharac(){
        String characName = this.characNameField.getText();
        if(!characName.isEmpty() && !characName.isBlank()){
            CharacteristicModel c = new CharacteristicModel(characName);
            if(!CharacteristicController.addCharacteristic(c).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     * Form Add characteristic to restaurant
     */
    public void renderFormAddCharacRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/RestaurantAddCharacView.fxml")));
    }

    public void loadRestaurantCharacBoxes(){
        this.loadRestaurants();
        this.loadCharcteristics();
        comboRestaurant.setItems(FXCollections.observableArrayList(this.listRestaurantName));
        comboCharac.setItems(FXCollections.observableArrayList(this.listCharacName));
    }

    public void sendFormAddCharac(){
        RestaurantModel r = this.hashMapRestaurant.get(comboRestaurant.getValue());
        CharacteristicModel c = this.hashMapCharac.get(comboCharac.getValue());
        r = RestaurantController.addCharacteristicToRestaurant(r, c);
        if (!r.getName().isEmpty()){
            this.ClearView();
        }
    }

    /**
     * Load form to delete a restaurant type
     */
    public void renderFormDelTypeRestaurant(){

    }

    /**
     * Load form to delete a restaurant characteristic
     */
    public void renderFormDelCharacRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/DelCharacOfRestaurantView.fxml")));
    }

    public void loadRestaurantComboBox(){
        this.loadRestaurants();
        comboRestaurant.setItems(FXCollections.observableArrayList(this.listRestaurantName));
    }

    public void loadCharacteristicCombobox(){
        this.loadCharacOfRestaurant();
        comboCharac.setItems(FXCollections.observableArrayList(this.listCharacName));
    }

    public void delCharacteristicOfRestaurant(){
        RestaurantModel r = this.hashMapRestaurant.get(this.comboRestaurant.getValue());
        CharacteristicModel c = this.hashMapCharac.get(this.comboCharac.getValue());
        CharacteristicModel cfull = CharacteristicController.getAllCharacteristics().stream()
                .filter(characteristicModel -> characteristicModel.getName().equals(c.getName()))
                .collect(Collectors.toList()).get(0);

        r = RestaurantController.deleteCharacteristicToRestaurant(r, cfull);
        if (!r.getId().isEmpty()){
            this.ClearView();
        }
    }

    /**
     * Data loaders
     */
    private void loadRestaurants(){
        this.hashMapRestaurant.clear();
        this.listRestaurantName.clear();

        List<RestaurantModel> restaurants = RestaurantController.getAllRestaurants();
        for(RestaurantModel r : restaurants){
            this.listRestaurantName.add(r.getName());
            this.hashMapRestaurant.put(r.getName(), r);
        }
    }

    private void loadTypes(){
        this.listTypeName.clear();
        this.hashMapType.clear();
        List<TypeModel> types = TypeController.getAllTypes();
        for(TypeModel t : types){
            this.listTypeName.add(t.getName());
            this.hashMapType.put(t.getName(), t);
        }
    }

    private void loadCharcteristics(){
        this.listCharacName.clear();
        this.hashMapCharac.clear();
        List<CharacteristicModel> characs = CharacteristicController.getAllCharacteristics();
        for(CharacteristicModel c : characs){
            this.listCharacName.add(c.getName());
            this.hashMapCharac.put(c.getName(), c);
        }
    }

    private void loadCharacOfRestaurant(){
        this.listCharacName.clear();
        this.hashMapCharac.clear();
        List<CharacteristicModel> characs = this.hashMapRestaurant.get(this.comboRestaurant.getValue()).getCharacteristics();
        for(CharacteristicModel c : characs){
            this.listCharacName.add(c.getName());
            this.hashMapCharac.put(c.getName(), c);
        }
    }

    /**
     * Navigation and utils
     */
    public void goToPlugin(){
        this.router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
    }

    private void setDataPane(Node node){
        RestaurantBox.getChildren().setAll(node);
    }

    public void setRouter(final Router router) {
        this.router = router;
    }

    public void ClearView(){
        FormViewCreateRestaurant.getChildren().clear();
    }
}
