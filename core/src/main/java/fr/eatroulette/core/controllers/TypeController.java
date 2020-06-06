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
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to communicate with our API
 */
public class TypeController {

    /**
     * Add a type
     * @param typeModel
     * @return
     */
    public static TypeModel addType(TypeModel typeModel) {
        TypeModel type = new TypeModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = typeModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            type = new TypeModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * Update the type
     * @param typeModel
     * @return
     */
    public static TypeModel updateType(TypeModel typeModel) {
        TypeModel type = new TypeModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant/"+typeModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.PUT);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = typeModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            type = new TypeModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return type;
    }

    /**
     * Delete a type
     * @param typeModel
     * @return
     */
    public static boolean deleteType(TypeModel typeModel){
        boolean deleted = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant/"+typeModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = typeModel.toJSON();

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
     * Get all types
     * @return
     */
    public static List<TypeModel> getAllTypes(){
        List<TypeModel> types = new ArrayList<TypeModel>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/type/restaurant");
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

                TypeModel type = new TypeModel((String) jsonObject.get("_id"),
                        (String) jsonObject.get("name"));
                types.add(type);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return types;
    }

    public static void main(String[] args) {
//        TypeModel typeModel1 = new TypeModel("Chinois");
//        System.out.println(TypeController.addType(typeModel1));
        List<TypeModel> types =TypeController.getAllTypes();
        for(TypeModel typeModel: types){
            System.out.println(typeModel.getName()+" "+typeModel.getId());
        }
    }


}
