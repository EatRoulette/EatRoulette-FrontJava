package fr.eatroulette.core.models;

public class AllergenModel {
    private String id;
    private String name;

    public AllergenModel() {
        this.id = "";
        this.name = "";
    }

    public AllergenModel(String name){
        this.id = "";
        this.name = name;
    }

    public AllergenModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String toJSON(){
        return String.format("{\"name\": \"%s\"}", this.getName());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
