package fr.eatroulette.core.models;

import java.util.ArrayList;
import java.util.List;

public class RestaurantModel {
    private String id;
    private String name;
    private String site;
    private String address;
    private String city;
    private String postalCode;
    private String dep;
    private List<TypeModel> types;

    public RestaurantModel(){
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
    }

    public RestaurantModel(String id, String name, String site, String address, String city, String postalCode, String dep, List<TypeModel> types) {
        this.id = id;
        this.name = name;
        this.site = site;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.dep = dep;
        this.types = types;
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

}
