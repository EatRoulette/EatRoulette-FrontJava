package fr.eatroulette.cli.interpreter;

import fr.eatroulette.core.controllers.AllergenController;
import fr.eatroulette.core.controllers.CharacteristicController;
import fr.eatroulette.core.controllers.RestaurantController;
import fr.eatroulette.core.controllers.TypeController;
import fr.eatroulette.core.models.AllergenModel;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;

import java.util.ArrayList;
import java.util.List;

public class CliInterpreter {
    private ArrayList<String> args;

    public CliInterpreter(){}

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

    private void addRestaurantInterpreter(){}

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
                }break;
            default:
                this.invalidCommand();
        }
    }

    private void updInterpreter(){}

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
            default:
                this.invalidCommand();
        }
    }

    private void runInterpreter() {}

    private void showHelp() {
        System.out.println("Commands ...");
        System.out.println("\tadd [restaurant | type | allergen | characteristic] \n" +
                           "\tdell [restaurant | type | allergen | characteristic] id");
    }

    private void invalidCommand() {
        System.out.println("Invalid command line");
    }
}
