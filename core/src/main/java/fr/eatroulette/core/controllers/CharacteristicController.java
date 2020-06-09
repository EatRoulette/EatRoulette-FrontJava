package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.CharacteristicModel;
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

public class CharacteristicController {

    /**
     * Add an characteristic
     * @param characteristicModel
     * @return
     */
    public static CharacteristicModel addCharacteristic(CharacteristicModel characteristicModel) {
        CharacteristicModel characteristic = new CharacteristicModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristic");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = characteristicModel.toJSON();

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
            characteristic = new CharacteristicModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return characteristic;
    }

    /**
     * Update the characteristic
     * @param characteristicModel
     * @return
     */
    public static CharacteristicModel updateCharacteristic(CharacteristicModel characteristicModel) {
        CharacteristicModel characteristic = new CharacteristicModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristic/"+characteristicModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.PUT);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = characteristicModel.toJSON();

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
            characteristic = new CharacteristicModel((String) jsonObject.get("_id"),
                    (String) jsonObject.get("name"));

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return characteristic;
    }

    /**
     * Delete an characteristic
     * @param characteristicModel
     * @return
     */
    public static boolean deleteCharacteristic(CharacteristicModel characteristicModel){
        boolean deleted = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristic/"+characteristicModel.getId());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = characteristicModel.toJSON();

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
     * Get all characteristics
     * @return
     */
    public static List<CharacteristicModel> getAllCharacteristics(){
        List<CharacteristicModel> allergens = new ArrayList<CharacteristicModel>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/characteristics");
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
            System.out.println(jsonArray);
            for (int i = 0; i <jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                CharacteristicModel allergen = new CharacteristicModel((String) jsonObject.get("id"),
                        (String) jsonObject.get("name"));
                allergens.add(allergen);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return allergens;
    }

}