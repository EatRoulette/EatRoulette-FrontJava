package fr.eatroulette.core.controllers;

import fr.eatroulette.core.models.CommentModel;
import fr.eatroulette.core.models.TicketModel;
import fr.eatroulette.core.models.TypeModel;
import fr.eatroulette.core.models.UserModel;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.stream.events.Comment;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller to communicate with our API
 */
public class TicketController {

    /**
     * Get all tickets
     * @return
     */
    public static List<TicketModel> getAllTickets(){
        List<TicketModel> tickets = new ArrayList<>();

        try {
            URL url = new URL(ControllerConstant.API_URL+"/tickets");
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
                UserModel author = manageUser(jsonObject.getJSONObject("author"));
                JSONArray jsonComments = jsonObject.getJSONArray("comments");
                List<CommentModel> comments = new ArrayList<>();
                for (int j = 0; j <jsonComments.length(); j++){
                    JSONObject jsonComment = jsonComments.getJSONObject(j);
                    UserModel commentAuthor = manageUser(jsonComment.getJSONObject("author"));
                    CommentModel comment = new CommentModel(
                            //String id, String message, UserModel author, LocalDate created_at
                            jsonObject.get("_id").toString(),
                            jsonObject.get("message").toString(),
                            commentAuthor,
                            manageDate(jsonObject.get("created_at").toString())
                    );
                    comments.add(comment);
                }

                TicketModel ticket = new TicketModel(
                        jsonObject.get("_id").toString(),
                        author,
                        jsonObject.get("title").toString(),
                        jsonObject.get("message").toString(),
                        jsonObject.get("status").toString(),
                        jsonObject.get("type").toString(),
                        Integer.parseInt(jsonObject.get("emergency").toString()),
                        comments,
                        manageDate(jsonObject.get("createdAt").toString())
                 );
                tickets.add(ticket);
            }
            conn.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    private static UserModel manageUser(JSONObject jsonAuthor){
        return new UserModel(
                "",
                jsonAuthor.get("_id").toString(),
                jsonAuthor.get("firstname").toString(),
                jsonAuthor.get("lastname").toString()
        );
    }
    private static LocalDate manageDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return LocalDate.parse(date, formatter);
    }

}
