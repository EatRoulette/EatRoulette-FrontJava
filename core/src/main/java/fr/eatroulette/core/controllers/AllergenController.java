package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.AllergenModel;
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

public class AllergenController {

    /**
     * Add an allergen
     * @param allergenModel
     * @return
     */
    public static AllergenModel addAllergen(AllergenModel allergenModel) {
        AllergenModel allergen = new AllergenModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergen");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = allergenModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            allergen = new AllergenModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allergen;
    }

    /**
     * Update the allergen
     * @param allergenModel
     * @return
     */
    public static AllergenModel updateAllergen(AllergenModel allergenModel) {
        AllergenModel allergen = new AllergenModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergen/"+allergenModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.PUT);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = allergenModel.toJSON();

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            allergen = new AllergenModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allergen;
    }

    /**
     * Delete an allergen
     * @param allergenModel
     * @return
     */
    public static boolean deleteAllergen(AllergenModel allergenModel){
        boolean deleted = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergen/"+allergenModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            String input = allergenModel.toJSON();

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
     * Get all allergens
     * @return
     */
    public static List<AllergenModel> getAllAllergens(){
        List<AllergenModel> allergens = new ArrayList<AllergenModel>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/allergens");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(ControllerConstant.GET);
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("x-access-token", ControllerConstant.ADM_TOKEN);

            if (conn.getResponseCode() != 200) {
                throw new Exception("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();

            JSONArray jsonArray = new JSONArray(output);
            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                AllergenModel allergen = new AllergenModel((String) jsonObject.get("id"),
                        (String) jsonObject.get("name"));
                allergens.add(allergen);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allergens;
    }

}
