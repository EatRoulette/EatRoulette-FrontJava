package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthController {

    /**
     * Login and get the user token
     * @param email
     * @param password
     * @return
     */
    public static String login(String email, String password){
        String token = "";
        try {
            URL url = new URL(ControllerConstant.API_URL+"/auth/login");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.POST);
            conn.setRequestProperty("Content-Type", "application/json");

            String input = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);

            OutputStream os = conn.getOutputStream();
            os.write(input.getBytes());
            os.flush();

            if (conn.getResponseCode() == HttpURLConnection.HTTP_UNAUTHORIZED){
                throw new Exception(conn.getResponseMessage());
            }

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);
            token = (String) jsonObject.get("token");
            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    /**
     * Get the user by token
     * @param token
     * @return
     */
    public static UserModel getUserByToken(String token){
        UserModel user = new UserModel();
        try {
            URL url = new URL(ControllerConstant.API_URL+"/user/"+token);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(ControllerConstant.GET);
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            String output = br.readLine();

            JSONObject jsonObject = new JSONObject(output);

            String type = (String) jsonObject.get("type");

            if(!type.equals("admin")){
                AuthController.logout(token);
                throw new Exception("You are not admin");
            }

            user = new UserModel(token,
                        (String) jsonObject.get("firstName"),
                        (String) jsonObject.get("lastName"));

            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Disconnect the user
     * @param token
     * @return
     */
    public static boolean logout(String token){
        boolean ret = false;

        try {
            URL url = new URL(ControllerConstant.API_URL+"/auth/logout/"+ token );
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(ControllerConstant.DELETE);
            conn.setRequestProperty("Content-Type", "application/json");

            if(conn.getResponseCode() == HttpURLConnection.HTTP_OK){
                ret = true;
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }



}
