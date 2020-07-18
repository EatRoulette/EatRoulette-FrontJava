package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.AllergenModel;
import fr.eatroulette.core.models.CharacteristicModel;
import fr.eatroulette.core.models.RestaurantModel;
import fr.eatroulette.core.models.TypeModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to communicate with our API
 */
public class RestaurantController {

    /**
     * Add a restaurant
     * @param restaurantModel
     * @return
     */
    public static RestaurantModel addRestaurant(RestaurantModel restaurantModel) {
        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = restaurantModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("website"),
                    (String) jsonObject.get("address"),
                    (String) jsonObject.get("city"),
                    (String) jsonObject.get("postalCode"),
                    (String) jsonObject.get("dep"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Update the restaurant
     * @param restaurantModel
     * @return
     */
    public static RestaurantModel updateRestaurant(RestaurantModel restaurantModel) {
        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.PUT);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = restaurantModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                                            (String) jsonObject.get("name"),
                                            (String) jsonObject.get("website"),
                                            (String) jsonObject.get("address"),
                                            (String) jsonObject.get("city"),
                                            (String) jsonObject.get("postalCode"),
                                            (String) jsonObject.get("dep"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Delete a restaurant
     * @param restaurantModel
     * @return
     */
    public static boolean deleteRestaurant(RestaurantModel restaurantModel){
        boolean deleted = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = restaurantModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                deleted = true;
            }

            conn.disconnect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    /**
     * Get all restaurants
     * @return
     */
    public static List<RestaurantModel> getAllRestaurants(){
        List<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(ControllerConstant.GET);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            if (conn.getResponseCode() != 200) {
                throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONArray jsonArray = new JSONArray(output);
            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray types = (JSONArray) jsonObject.get("types");
                List<TypeModel> typesList = new ArrayList<TypeModel>();
                for(int y = 0; y < types.length(); y++){
                    JSONObject jsonType = types.getJSONObject(y);
                    TypeModel t = new TypeModel((String) jsonType.get("name"));
                    typesList.add(t);
                }

                JSONArray allergens = (JSONArray) jsonObject.get("allergens");
                List<AllergenModel> allergensList = new ArrayList<AllergenModel>();
                for(int y = 0; y < allergens.length(); y++){
                    JSONObject jsonType = allergens.getJSONObject(y);
                    AllergenModel a = new AllergenModel((String) jsonType.get("name"));
                    allergensList.add(a);
                }

                JSONArray characteristics = (JSONArray) jsonObject.get("characteristics");
                List<CharacteristicModel> characteristicsList = new ArrayList<CharacteristicModel>();
                for(int y = 0; y < characteristics.length(); y++){
                    JSONObject jsonType = characteristics.getJSONObject(y);
                    CharacteristicModel c = new CharacteristicModel((String) jsonType.get("name"));
                    characteristicsList.add(c);
                }


                RestaurantModel restaurant = new RestaurantModel(checkAndReturnValue(jsonObject,"_id"),
                                                                checkAndReturnValue(jsonObject,"name"),
                                                                checkAndReturnValue(jsonObject,"site"),
                                                                checkAndReturnValue(jsonObject,"address"),
                                                                checkAndReturnValue(jsonObject,"city"),
                                                                checkAndReturnValue(jsonObject,"postalCode"),
                                                                checkAndReturnValue(jsonObject,"dep"),
                                                                typesList, allergensList, characteristicsList);
                restaurants.add(restaurant);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurants;
    }

    public static String checkAndReturnValue(JSONObject obj, String key){
        return obj.has(key)? obj.getString(key) : null;
    }

    /**
     * Add a type in a restaurant
     * @param restaurantModel
     * @param type
     * @return
     */
    public static RestaurantModel addTypeToRestaurant(RestaurantModel restaurantModel, TypeModel type){
        if (RestaurantController.isRestaurantContainType(restaurantModel, type)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idType\": \"%s\"}", type.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            restaurantModel.addType(type);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Delete a type of a restaurant
     * @param restaurantModel
     * @param typeModel
     * @return
     */
    public static RestaurantModel deleteTypeToRestaurant(RestaurantModel restaurantModel, TypeModel typeModel){
        if (!RestaurantController.isRestaurantContainType(restaurantModel, typeModel)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant/del/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idType\": \"%s\"}", typeModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
            }

            restaurantModel.delType(typeModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Check if the restaurant contain the type
     * @param restaurantModel
     * @param typeModel
     * @return
     */
    public static boolean isRestaurantContainType(RestaurantModel restaurantModel,TypeModel typeModel){
        for (TypeModel t: restaurantModel.getTypes()){
            if(t.getName().equals(typeModel.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the restaurant contain the characteristic
     * @param restaurantModel
     * @param characteristicModel
     * @return
     */
    public static boolean isRestaurantContainCharac(RestaurantModel restaurantModel,CharacteristicModel characteristicModel){
        for (CharacteristicModel t: restaurantModel.getCharacteristics()){
            if(t.getName().equals(characteristicModel.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the restaurant contain the Allergen
     * @param restaurantModel
     * @param allergenModel
     * @return
     */
    public static boolean isRestaurantContainAllergen(RestaurantModel restaurantModel, AllergenModel allergenModel){
        for (AllergenModel a: restaurantModel.getAllergens()){
            if(a.getName().equals(allergenModel.getName())){
                return true;
            }
        }
        return false;
    }

    /**
     * Add allergen to restaurant
     * @param restaurantModel
     * @param allergenModel
     * @return
     */
    public static RestaurantModel addAllergenToRestaurant(RestaurantModel restaurantModel, AllergenModel allergenModel){
        if (RestaurantController.isRestaurantContainAllergen(restaurantModel, allergenModel)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergen/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idAllergen\": \"%s\"}", allergenModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            restaurantModel.addAllergen(allergenModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Delete allergen of a restaurant
     * @param restaurantModel
     * @param allergenModel
     * @return
     */
    public static RestaurantModel deleteAllergenToRestaurant(RestaurantModel restaurantModel, AllergenModel allergenModel){
        if (!RestaurantController.isRestaurantContainAllergen(restaurantModel, allergenModel)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergen/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idAllergen\": \"%s\"}", allergenModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
            }

            restaurantModel.delAllergen(allergenModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Add characteristic to a restaurant
     * @param restaurantModel
     * @param characteristicModel
     * @return
     */
    public static RestaurantModel addCharacteristicToRestaurant(RestaurantModel restaurantModel, CharacteristicModel characteristicModel){
        if (RestaurantController.isRestaurantContainCharac(restaurantModel, characteristicModel)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristic/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idCharac\": \"%s\"}", characteristicModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            restaurantModel.addCharacteristic(characteristicModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    /**
     * Delete characteristic to of a restaurant
     * @param restaurantModel
     * @param characteristicModel
     * @return
     */
    public static RestaurantModel deleteCharacteristicToRestaurant(RestaurantModel restaurantModel, CharacteristicModel characteristicModel){
        if (!RestaurantController.isRestaurantContainCharac(restaurantModel, characteristicModel)){
            return restaurantModel;
        }

        RestaurantModel restaurant = new RestaurantModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristic/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = String.format("{\"idCharac\": \"%s\"}", characteristicModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : " + conn.getResponseCode());
            }

            restaurantModel.delCharacteristic(characteristicModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return restaurant;
    }
}
