import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserInfo {
    public static JsonObject getUserDetails(String userId) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            String url = String.format("https://users.roblox.com/v1/users/%s", userId);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JsonObject userData = JsonParser.parseString(response.body()).getAsJsonObject();
                return userData;
            } else {
                System.out.println("User not found or invalid ID. Status code: " + response.statusCode());
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
