package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.RestaurantModel;
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

public class RestaurantController {

    public static void main(String[] args) {
        RestaurantModel restaurantModel = new RestaurantModel("5ed535234d114b002471b49b", "TESTFORDELETE2", "TESTS", "----", "PARIS", "75012", "75");
        boolean result = RestaurantController.deleteRestaurant(restaurantModel);
        System.out.println(result);
//        List<RestaurantModel> restaurants = getAllRestaurants();
//        for (RestaurantModel restaurant: restaurants){
//            System.out.println(restaurant.getId()+" "+restaurant.getName());
//        }
    }

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

    public static List<RestaurantModel> getAllRestaurants(){
        List<RestaurantModel> restaurants = new ArrayList<RestaurantModel>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/restaurant");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(ControllerConstant.GET);
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONArray jsonArray = new JSONArray(output);
            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RestaurantModel restaurant = new RestaurantModel((String) jsonObject.get("_id"),
                                                                 (String) jsonObject.get("name"),
                                                                 (String) jsonObject.get("site"),
                                                                 (String) jsonObject.get("address"),
                                                                 (String) jsonObject.get("city"),
                                                                 (String) jsonObject.get("postalCode"),
                                                                 (String) jsonObject.get("dep"));
                restaurants.add(restaurant);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return restaurants;
    }


}
