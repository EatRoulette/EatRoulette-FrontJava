package fr.eatroulette.ui.main.restaurant;

import fr.eatroulette.core.controllers.AllergenController;
import fr.eatroulette.core.controllers.CharacteristicController;
import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.controllers.TypeController;

import fr.eatroulette.core.models.AllergenModel;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;
import javafx.collections.FXCollections;
import javafx.scene.control.*;

import fr.eatroulette.ui.main.tickets.TicketsControllerUI;
import javafx.scene.control.TextField;

import fr.eatroulette.ui.main.Router;
import fr.eatroulette.ui.main.plugin.PluginController;
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

public class RestaurantControllerUi {

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
    @FXML
    public ComboBox comboAllergen;

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
    @FXML
    public TextField allergenNameField;

    private HashMap<String, RestaurantModel> hashMapRestaurant = new HashMap<String, RestaurantModel>();
    private HashMap<String, TypeModel> hashMapType = new HashMap<String, TypeModel>();
    private HashMap<String, CharacteristicModel> hashMapCharac = new HashMap<String, CharacteristicModel>();
    private HashMap<String, AllergenModel> hashMapAllergen = new HashMap<String, AllergenModel>();
    private List<String> listRestaurantName = new ArrayList<String>();
    private List<String> listTypeName = new ArrayList<String>();
    private List<String> listCharacName = new ArrayList<String>();
    private List<String> listAllergenName = new ArrayList<String>();

    /**
     * Restaurant view create form
     */
    public void RenderFormCreateRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/RestaurantCreateForm.fxml")));
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
                        FXMLLoader loaderSingleRestaurant = new FXMLLoader(getClass().getResource("/restaurants_views/RestaurantOneJsonView.fxml"));
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
     * Add Allergen to restaurant view
     */
    public void renderFormAddAllergenToRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/AddAllergenToRestaurantView.fxml")));
    }

    public void loadRestaurantAllergenBoxes(){
        this.loadRestaurants();
        this.loadAllergens();
        comboRestaurant.setItems(FXCollections.observableArrayList(this.listRestaurantName));
        comboAllergen.setItems(FXCollections.observableArrayList(this.listAllergenName));
    }

    public void sendAllergenToRestaurant(){
        RestaurantModel r = this.hashMapRestaurant.get(this.comboRestaurant.getValue());
        AllergenModel a = this.hashMapAllergen.get(this.comboAllergen.getValue());
        r = RestaurantController.addAllergenToRestaurant(r, a);
        if (!r.getName().isEmpty()){
            this.ClearView();
        }
    }

    /**
     * Load del allergen of restaurant
     */
    public void renderFormDelAllergenOfRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/DelAllergenOfRestaurantView.fxml")));
    }

    public void loadAllergensOfRestaurants(){
        this.loadAllergensOfRestaurant();
        comboAllergen.setItems(FXCollections.observableArrayList(this.listAllergenName));
    }

    public void delAllergenOfRestaurant(){
        RestaurantModel r = this.hashMapRestaurant.get(this.comboRestaurant.getValue());
        AllergenModel a = this.hashMapAllergen.get(this.comboAllergen.getValue());
        AllergenModel afull = AllergenController.getAllAllergens().stream()
                .filter(allergenModel -> allergenModel.getName().equals(a.getName()))
                .collect(Collectors.toList()).get(0);

        r = RestaurantController.deleteAllergenToRestaurant(r, afull);
        if (!r.getId().isEmpty()){
            this.ClearView();
        }
    }

    /**
     * Add type to restaurant view
     */
    public void renderFormAddTypeRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/RestaurantAddTypeView.fxml")));
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
     * Load add allergen view
     */
    public void loadAllergenForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/AddAllergenView.fxml")));
    }

    public void saveAllergen(){
        String allergenName = this.allergenNameField.getText();
        if(!allergenName.isEmpty() || !allergenName.isBlank()) {
            AllergenModel allergen = new AllergenModel(allergenName);
            if (!AllergenController.addAllergen(allergen).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     * Load update Allergen
     */
    public void loadUpdateAllergenForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/UpdateAllergenView.fxml")));
    }

    public void loadAllergenBoxes(){
        this.loadAllergens();
        comboAllergen.setItems(FXCollections.observableArrayList(this.listAllergenName));
    }

    public void updateAllergen(){
        AllergenModel old = this.hashMapAllergen.get(this.comboAllergen.getValue());
        String newName = this.allergenNameField.getText();
        if (!newName.isEmpty() || !newName.isBlank()){
            AllergenModel a = new AllergenModel(old.getId(), this.allergenNameField.getText());
            if (!AllergenController.updateAllergen(a).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     *  Add type view
     */
    public void loadTypeForm()throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/AddTypeView.fxml")));
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
     * Load update type view
     */
    public void loadUpdateTypeForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/UpdateTypeView.fxml")));
    }

    public void loadTypeCombo(){
        this.loadTypes();
        comboType.setItems(FXCollections.observableArrayList(this.listTypeName));
    }

    public void updateType() {
        TypeModel old = this.hashMapType.get(this.comboType.getValue());
        String newName = this.typeNameField.getText();
        if (!newName.isEmpty() || !newName.isBlank()){
            TypeModel a = new TypeModel(old.getId(), newName);
            if (!TypeController.updateType(a).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     * Add characteristic view
     */
    public void loadCharacForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/AddCharacView.fxml")));
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
     * load Update Characteristic view
     */
    public void loadUpdateCharacForm() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/UpdateCharacteristicView.fxml")));
    }

    public void loadCharacCombobox(){
        this.loadCharcteristics();
        comboCharac.setItems(FXCollections.observableArrayList(this.listCharacName));
    }

    public void updateCharac(){
        CharacteristicModel old = this.hashMapCharac.get(this.comboCharac.getValue());
        String newName = this.characNameField.getText();
        if (!newName.isEmpty() || !newName.isBlank()){
            CharacteristicModel c = new CharacteristicModel(old.getId(), newName);
            if (!CharacteristicController.updateCharacteristic(c).getId().isEmpty()){
                this.ClearView();
            }
        }
    }

    /**
     * Form Add characteristic to restaurant
     */
    public void renderFormAddCharacRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/RestaurantAddCharacView.fxml")));
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
    public void renderFormDelTypeRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/DelTypeOfRestaurantView.fxml")));
    }

    public void loadTypeCombobox(){
        this.loadTypesOfRestaurant();
        comboType.setItems(FXCollections.observableArrayList(this.listTypeName));
    }

    public void delTypeOfRestaurant(){
        RestaurantModel r = this.hashMapRestaurant.get(this.comboRestaurant.getValue());
        TypeModel t = this.hashMapType.get(this.comboType.getValue());
        TypeModel tfull = TypeController.getAllTypes().stream()
                .filter(type -> type.getName().equals(t.getName()))
                .collect(Collectors.toList()).get(0);

        r = RestaurantController.deleteTypeToRestaurant(r, tfull);
        if (!r.getId().isEmpty()){
            this.ClearView();
        }
    }

    /**
     * Load form to delete a restaurant characteristic
     */
    public void renderFormDelCharacRestaurant() throws IOException {
        setDataPane(FXMLLoader.load(getClass().getResource("/restaurants_views/DelCharacOfRestaurantView.fxml")));
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

    private void loadTypesOfRestaurant(){
        this.listTypeName.clear();
        this.hashMapType.clear();
        List<TypeModel> types = this.hashMapRestaurant.get(this.comboRestaurant.getValue()).getTypes();
        for(TypeModel t : types){
            this.listTypeName.add(t.getName());
            this.hashMapType.put(t.getName(), t);
        }
    }

    private void loadAllergensOfRestaurant(){
        this.listAllergenName.clear();
        this.hashMapAllergen.clear();
        List<AllergenModel> allergens = this.hashMapRestaurant.get(this.comboRestaurant.getValue()).getAllergens();
        for (AllergenModel a : allergens){
            this.listAllergenName.add(a.getName());
            this.hashMapAllergen.put(a.getName(), a);
        }
    }

    private void loadAllergens(){
        this.listAllergenName.clear();
        this.hashMapAllergen.clear();

        List<AllergenModel> allergenModelList = AllergenController.getAllAllergens();
        for (AllergenModel a : allergenModelList){
            this.listAllergenName.add(a.getName());
            this.hashMapAllergen.put(a.getName(), a);
        }
    }

    /**
     * Navigation and utils
     */
    public void goToPlugin(){
        this.router.<PluginController>goTo("Plugin", controller -> controller.setRouter(router));
    }
    public void goToTickets(){
        this.router.<TicketsControllerUI>goTo("Tickets", controller -> controller.setRouter(router));
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
