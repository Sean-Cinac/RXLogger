import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.*;

public class UserIDbyUsername {
    public static Integer getUserId(String username, String url) {
        try {
            HttpClient client = HttpClient.newHttpClient();

            // Prepare JSON body
            JsonObject requestBody = new JsonObject();
            JsonArray usernames = new JsonArray();
            usernames.add(username);
            requestBody.add("usernames", usernames);
            requestBody.addProperty("excludeBannedUsers", true);

            // Build POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Parse JSON
            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            JsonArray data = json.getAsJsonArray("data");

            if (data.size() > 0) {
                JsonObject user = data.get(0).getAsJsonObject();
                return user.get("id").getAsInt();
            } else {
                System.out.println("User not found.");
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
