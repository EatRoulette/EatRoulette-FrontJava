package fr.eatroulette.core.models;

public class TypeModel {
    private String id;
    private String name;

    public TypeModel() {
        this.id = "";
        this.name = "";
    }

    public TypeModel(String name){
        this.id = "";
        this.name = name;
    }

    public TypeModel(String id, String name){
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
