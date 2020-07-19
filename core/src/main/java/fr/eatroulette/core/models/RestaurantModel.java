package fr.eatroulette.core.models;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {
    private String status;
    private String id;
    private String name;
    private String site;
    private String address;
    private String city;
    private String postalCode;
    private String dep;
    private List<TypeModel> types;
    private List<AllergenModel> allergens;
    private List<CharacteristicModel> characteristics;

    public RestaurantModel(){
        this.name = "";
        this.site = "";
        this.address = "";
        this.city = "";
        this.postalCode = "";
        this.dep = "";
    }

    public RestaurantModel(String id){
        this.id = id;
        this.name = "";
        this.site = "";
        this.address = "";
        this.city = "";
        this.postalCode = "";
        this.dep = "";
    }

    public RestaurantModel(String name, String site, String address, String city, String postalCode, String dep) {
        this.name = name;
        this.site = site;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.dep = dep;
    }

    public RestaurantModel(String id, String name, String site, String address, String city, String postalCode, String dep) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.dep = dep;
        this.types = new ArrayList<TypeModel>();
        this.allergens = new ArrayList<AllergenModel>();
        this.characteristics = new ArrayList<CharacteristicModel>();
    }

    public RestaurantModel(String id, String name, String site, String address,
                           String city, String postalCode, String dep, String status,
                           List<TypeModel> types, List<AllergenModel> allergens, List<CharacteristicModel> characteristics) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.dep = dep;
        this.status = status;
        this.types = types;
        this.allergens = allergens;
        this.characteristics = characteristics;
    }

    public String toJSON(){
        return String.format("{\"name\": \"%s\"," +
                                 "\"site\": \"%s\"," +
                                 "\"address\": \"%s\"," +
                                 "\"city\": \"%s\"," +
                                 "\"postalCode\": \"%s\"," +
                                 "\"dep\": \"%s\"," +
                                 "\"types\": [], \"_idSituation\": \"---\"" +
                            "}", name, site,address, city, postalCode, dep);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSite() {
        return site;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getDep() {
        return dep;
    }

    public void addType(TypeModel type){
        this.types.add(type);
    }

    public void delType(TypeModel type){
        this.types.remove(type);
    }

    public List<TypeModel> getTypes() {
        return types;
    }

    public void addAllergen(AllergenModel allergen){
        this.allergens.add(allergen);
    }

    public void delAllergen(AllergenModel allergen){
        this.allergens.remove(allergen);
    }

    public List<AllergenModel> getAllergens() {
        return allergens;
    }

    public void addCharacteristic(CharacteristicModel characteristic){
        this.characteristics.add(characteristic);
    }

    public void delCharacteristic(CharacteristicModel characteristic){
        this.characteristics.remove(characteristic);
    }

    public List<CharacteristicModel> getCharacteristics() {
        return characteristics;
    }

}
