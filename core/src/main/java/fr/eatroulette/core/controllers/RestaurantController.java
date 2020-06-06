package fr.eatroulette.core.controllers;

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
import java.security.cert.CertSelector;
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

            String input = restaurantModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"),
                    (String) jsonObject.get("site"),
                    (String) jsonObject.get("address"),
                    (String) jsonObject.get("city"),
                    (String) jsonObject.get("postalCode"),
                    (String) jsonObject.get("dep"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            String input = restaurantModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                                            (String) jsonObject.get("name"),
                                            (String) jsonObject.get("site"),
                                            (String) jsonObject.get("address"),
                                            (String) jsonObject.get("city"),
                                            (String) jsonObject.get("postalCode"),
                                            (String) jsonObject.get("dep"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
//        RestaurantModel restaurant = new RestaurantModel();
        boolean deleted = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant/"+restaurantModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");

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

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONArray jsonArray = new JSONArray(output);
            System.out.println(jsonArray);
            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                JSONArray types = (JSONArray) jsonObject.get("types");
                List<TypeModel> typesList = new ArrayList<TypeModel>();
                for(int y = 0; y < types.length(); y++){
                    JSONObject jsonType = types.getJSONObject(y);
                    TypeModel t = new TypeModel((String) jsonType.get("name"));
                    typesList.add(t);
                }

                RestaurantModel restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                                                                 (String) jsonObject.get("name"),
                                                                 (String) jsonObject.get("site"),
                                                                 (String) jsonObject.get("address"),
                                                                 (String) jsonObject.get("city"),
                                                                 (String) jsonObject.get("postalCode"),
                                                                 (String) jsonObject.get("dep"),
                                                                  typesList);
                restaurants.add(restaurant);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurants;
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

            String input = String.format("{\"idType\": \"%s\"}", type.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            restaurantModel.addType(type);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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

            String input = String.format("{\"idType\": \"%s\"}", typeModel.getId());

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            restaurantModel.delType(typeModel);
            restaurant = restaurantModel;

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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


    public static void main(String[] args) {
//        RestaurantModel restaurantModel = new RestaurantModel("5ed535234d114b002471b49b", "TESTFORDELETE2", "TESTS", "----", "PARIS", "75012", "75");
//        boolean result = RestaurantController.deleteRestaurant(restaurantModel);
//        System.out.println(result);
        TypeModel typeModel = new TypeModel( "5edb978cd1959e0024b7df7a", "Chinois");
        List<RestaurantModel> restaurants = getAllRestaurants();
        for (RestaurantModel restaurant: restaurants){
            System.out.println(restaurant.getId()+" "+restaurant.getName());
            for (TypeModel t: restaurant.getTypes()){
                System.out.println(t.getName());
            }
        }
        RestaurantModel restaurantModel = restaurants.get(0);
        System.out.println(restaurantModel.getName());
        RestaurantModel result = RestaurantController.deleteTypeToRestaurant(restaurantModel, typeModel);
        System.out.println(result.getName());

    }

}
