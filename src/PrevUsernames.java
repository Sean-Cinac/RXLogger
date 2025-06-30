import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PrevUsernames {
    public static JsonObject getData(String userID, String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Build GET request (for username history)
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON response
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            return json;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
