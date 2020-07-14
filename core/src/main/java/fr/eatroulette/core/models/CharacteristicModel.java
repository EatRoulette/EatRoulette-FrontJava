package fr.eatroulette.core.models;

public class CharacteristicModel {
    private String id;
    private String name;

    public CharacteristicModel() {
        this.id = "";
        this.name = "";
    }

    public CharacteristicModel(String name){
        this.id = "";
        this.name = name;
    }

    public CharacteristicModel(String id, String name){
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
