package fr.eatroulette.cli.interpreter;

import fr.eatroulette.core.controllers.AllergenController;
import fr.eatroulette.core.controllers.CharacteristicController;
import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.controllers.TypeController;
import fr.eatroulette.core.models.AllergenModel;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;
import fr.eatroulette.core.plugin.PluginManager;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CliInterpreter {
    private ArrayList<String> args;
    private PluginManager pluginManager;

    public CliInterpreter(){
        this.pluginManager = new PluginManager();
    }

    public void setArgs(ArrayList<String> args) {
        this.args = args;
    }

    public void interpreter(){
        switch (args.get(0)){
            case "add":
                this.args.remove(0);
                this.addInterpreter();
                break;
            case "del":
                this.args.remove(0);
                this.delInterpreter();
                break;
            case "upd":
                this.args.remove(0);
                this.updInterpreter();
                break;
            case "show":
                this.args.remove(0);
                this.showInterpreter();
                break;
            case "help":
                this.args.remove(0);
                this.showHelp();
                break;
            case "run":
                this.args.remove(0);
                this.runInterpreter();
                break;
            default:
                this.invalidCommand();
        }
    }

    private void addInterpreter(){
        switch (args.get(0)){
            case "restaurant":
                this.args.remove(0);
                this.addRestaurantInterpreter();
                break;
            case "type":
                this.args.remove(0);
                TypeModel type = new TypeModel(this.args.remove(0));
                type = TypeController.addType(type);
                System.out.println(String.format("id: %s name: %s", type.getId(), type.getName()));
                break;
            case "allergen":
                this.args.remove(0);
                AllergenModel allergen = new AllergenModel(this.args.remove(0));
                allergen = AllergenController.addAllergen(allergen);
                System.out.println(String.format("id: %s name: %s", allergen.getId(), allergen.getName()));
                break;
            case "characteristic":
                this.args.remove(0);
                CharacteristicModel charac = new CharacteristicModel(this.args.remove(0));
                charac = CharacteristicController.addCharacteristic(charac);
                System.out.println(String.format("id: %s name: %s", charac.getId(), charac.getName()));
                break;
            default:
                this.invalidCommand();
        }
    }

    private void addRestaurantInterpreter(){
        if (this.args.size() == 6){
            RestaurantModel r = new RestaurantModel(this.args.remove(0), this.args.remove(0), this.args.remove(0),
                                                    this.args.remove(0), this.args.remove(0), this.args.remove(0));
            r = RestaurantController.addRestaurant(r);
            if (!r.getId().isEmpty()){
                System.out.println(String.format("id: %s name: %s site: %s address: %s town: %s postalCode: %s dep: %s ",
                                r.getId(), r.getName(), r.getSite(),
                                r.getAddress(), r.getCity(), r.getPostalCode(), r.getDep()));
            }
        }
    }

    private void delInterpreter(){
        switch (args.get(0)){
            case "restaurant":
                this.args.remove(0);
                RestaurantModel restaurant = new RestaurantModel(this.args.remove(0));
                if(RestaurantController.deleteRestaurant(restaurant)){
                    System.out.println("Restaurant deleted");
                }else {
                    System.out.println("Failed to deleted");
                }
                break;
            case "type":
                this.args.remove(0);
                TypeModel type = new TypeModel(this.args.remove(0), "");
                if(TypeController.deleteType(type)){
                    System.out.println("Type deleted");
                }else {
                    System.out.println("Failed to deleted");
                }
                break;
            case "allergen":
                this.args.remove(0);
                AllergenModel allergen = new AllergenModel(this.args.remove(0), "");
                if(AllergenController.deleteAllergen(allergen)){
                    System.out.println("Allergen deleted");
                }else {
                    System.out.println("Failed to deleted");
                }
                break;
            case "characteristic":
                this.args.remove(0);
                CharacteristicModel charac = new CharacteristicModel(this.args.remove(0), "");
                if(CharacteristicController.deleteCharacteristic(charac)){
                    System.out.println("Characteristic deleted");
                }else {
                    System.out.println("Failed to deleted");
                }
                break;
            default:
                this.invalidCommand();
        }
    }

    private void updInterpreter(){
        String id;
        String name;
        switch (args.get(0)){
            case "type":
                this.args.remove(0);
                if (!(this.args.size() == 2)) {
                    System.out.println("Not enought arguments");
                    return;
                }
                id = this.args.remove(0);
                name = this.args.remove(0);
                TypeModel type = new TypeModel(id, name);
                if(TypeController.updateType(type).getName().equals(name)){
                    System.out.println("Type updated");
                }else {
                    System.out.println("Failed to update");
                }
                break;
            case "allergen":
                this.args.remove(0);
                if (!(this.args.size() == 2)) {
                    System.out.println("Not enought arguments");
                    return;
                }
                id = this.args.remove(0);
                name = this.args.remove(0);
                AllergenModel allergen = new AllergenModel(id, name);
                if(AllergenController.updateAllergen(allergen).getName().equals(name)){
                    System.out.println("Allergen updated");
                }else {
                    System.out.println("Failed to update");
                }
                break;
            case "characteristic":
                this.args.remove(0);
                if (!(this.args.size() == 2)) {
                    System.out.println("Not enought arguments");
                    return;
                }
                id = this.args.remove(0);
                name = this.args.remove(0);
                CharacteristicModel charac = new CharacteristicModel(id, name);
                if(CharacteristicController.updateCharacteristic(charac).getName().equals(name)){
                    System.out.println("Characteristic updated");
                }else {
                    System.out.println("Failed to update");
                }
                break;
            default:
                this.invalidCommand();
        }
    }

    private void showInterpreter(){
        switch (args.get(0)){
            case "restaurants":
                this.args.remove(0);
                List<RestaurantModel> restaurants = RestaurantController.getAllRestaurants();
                for (RestaurantModel r : restaurants){
                    System.out.println(String.format("id: %s name: %s site: %s address: %s town: %s postalCode: %s dep: %s ", r.getId(), r.getName(), r.getSite(), r.getAddress(), r.getCity(), r.getPostalCode(), r.getDep()));
                }
                break;
            case "types":
                this.args.remove(0);
                List<TypeModel> types = TypeController.getAllTypes();
                for(TypeModel t : types) {
                    System.out.println(String.format("id: %s name: %s", t.getId(), t.getName()));
                }
                break;
            case "allergens":
                this.args.remove(0);
                List<AllergenModel> allergens = AllergenController.getAllAllergens();
                for (AllergenModel a : allergens){
                    System.out.println(String.format("id: %s name: %s", a.getId(), a.getName()));
                }
                break;
            case "characteristics":
                this.args.remove(0);
                List<CharacteristicModel> characteristics = CharacteristicController.getAllCharacteristics();
                for (CharacteristicModel c : characteristics){
                    System.out.println(String.format("id: %s name: %s", c.getId(), c.getName()));
                }
                break;
            case "plugins":
                for (String p : this.pluginManager.getPluginsName()){
                    System.out.println(String.format("plugin-name: %s", p));
                }
                break;
            default:
                this.invalidCommand();
        }
    }

    private void runInterpreter() {
        if (this.args.size() > 0){
            try {
                this.pluginManager.runPlugin(this.args.remove(0));
            } catch (IOException | InstantiationException |
                     InvocationTargetException | IllegalAccessException |
                     NoSuchMethodException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void showHelp() {
        System.out.println("EatRoulette-cli commands ...");
        System.out.println("*\tadd [restaurant | type | allergen | characteristic] [data]\n" +
                           "*\tupd [type | allergen | characteristic] id [data]\n" +
                           "*\tdell [restaurant | type | allergen | characteristic] id\n" +
                           "*\trun [plugin-name]\n" +
                           "*\tshow [restaurants | types | allergens | characteristics | plugins]\n" +
                           "*\thelp #To get this panel\n" +
                           "*\texit #To quit the application");
    }

    private void invalidCommand() {
        System.out.println("Invalid command line");
    }
}
