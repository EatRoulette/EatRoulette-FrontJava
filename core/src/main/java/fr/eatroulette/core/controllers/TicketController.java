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
import java.util.Date;
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
            URL url = new URL("http://localhost:3000/tickets");
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
                boolean hasAuthor = jsonObject.has("author");
                UserModel author = hasAuthor ? manageUser(jsonObject.getJSONObject("author")) : null;
                JSONArray jsonComments = jsonObject.getJSONArray("comments");
                List<CommentModel> comments = new ArrayList<>();
                for (int j = 0; j <jsonComments.length(); j++){
                    JSONObject jsonComment = jsonComments.getJSONObject(j);
                    CommentModel comment = new CommentModel(
                            jsonComment.get("message").toString()
                    );
                    comments.add(comment);
                }

                TicketModel ticket = new TicketModel(
                        jsonObject.get("id").toString(),
                        author,
                        jsonObject.get("title").toString(),
                        jsonObject.get("message").toString(),
                        jsonObject.get("status").toString(),
                        jsonObject.get("type").toString(),
                        0,
                        comments,
                        manageDate(jsonObject.get("created_at").toString())
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        return LocalDate.parse(date, formatter);
    }

}
