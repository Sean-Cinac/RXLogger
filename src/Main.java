import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Main {

    private final String[] allowedParameters = {"-n", "-i", "-t"};
    private final String type = "-t";
    private final String usernameInput = "-n";
    private final String userIDInput = "-i";
    private final String[] allowedTypes = {"prevUsernames", "getUserID", "userInfo"};
    private String username;
    private String userID;
    private String urlToApi;

    public static void main(String[] args) {
        Main program = new Main();

        if (args[0].equals("--help") || args == null) {
            program.printHelp();    
        } else {
            // && program.validateSyntaxCombination(args)
            if (program.validateSyntax(args)) { //if syntax is correct
                if (program.checkForElement(args, program.getUseridinput())) {
                    program.setElement(args, program.getUseridinput()); //set the userID insead of the username if given
                } else {
                    program.setElement(args, program.getUsernameInput());
                }
                program.setElement(args, program.getType()); //set and execute the type delivered

            }
        }
    }

    public String getUseridinput() {
        return userIDInput;
    }
    public String getUsernameInput() {
        return usernameInput;
    }
    public String[] getAllowedTypes() {
        return allowedTypes;
    }
    public String getType() {
        return type;
    }

    public boolean validateSyntax(String[] args) { //checking if the user is starting the program with the correct options
        boolean parameterMode = false;
        int argumentCount = 0;
        for (int i = 0; i < args.length; i++) {
            if (argumentCount > 2) {
                System.out.println("Used too many arguments, only two allowed (Usernamer/ID + type)");
                printHelp();
                return false;
            }
            if (!checkIfSyntaxExists(args[0])) { //first input has to be valid argument
                printHelp();
                return false;
            }
            if (parameterMode) {
                if (checkIfSyntaxExists(args[i])) { //Cannot use another argument in the space of a parameter to previous argument
                    System.out.println("No Parameter for option " + args[i - 1] + " entered");
                    printHelp();
                    return false;
                } else {
                    parameterMode = false;
                   if (args[i - 1].equals(type)) {
                        if (!validateType(args[i])) {
                            System.out.println("Wrong Type entered");
                            printHelp();
                            return false;
                        }
                   }
                }
            }
            else if (checkIfSyntaxExists(args[i])) {
                argumentCount++; //only two should be allowed
                parameterMode = true; //next input should be a parameter for the argument
            }

        }
        return true;
    }

    public boolean validateSyntaxCombination(String[] arg) {

        return false;
    }

    public boolean checkIfSyntaxExists(String syntaxStatement) {
        for (int i = 0; i < allowedParameters.length; i++) {
            if (allowedParameters[i].equals(syntaxStatement)) {
                return true;
            }
        }
        return false;
    }

    public boolean validateType(String typeInput) {
        for (int i = 0; i < allowedTypes.length; i++) {
            if (allowedTypes[i].equals(typeInput)) {
                return true;
            }
        }
        return false; 
    }

    public void executeArgument(String[] argument) {
        switch(argument[0]) {
            case "-n": //username
                setUsername(argument[1]);
                break;
            case "-i":
                setUserID(argument[1]);
                break;
            case "-t": //type
                switch(argument[1]) {
                    case "prevUsernames":
                        getAllUsernames();
                        break;
                    case "getUserID":
                        String foundUserID = getUserIDByUsername();
                        System.out.printf("UserID for %s: %s%n", this.username, foundUserID);
                        break;
                }
            
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserIDByUsername() {
        urlToApi = "https://users.roblox.com/v1/usernames/users";
        UserIDbyUsername apiFetch = new UserIDbyUsername();
        return apiFetch.getUserId(this.username, urlToApi);


    }

    public void getAllUsernames() {
        if (this.userID == null) {
            if (this.username != null) {
                this.userID = getUserIDByUsername();
            }
        } //UserID required

        urlToApi = String.format("https://users.roblox.com/v1/users/%s/username-history", this.userID);
        PrevUsernames apiConnection = new PrevUsernames();
        JsonObject response = apiConnection.getData(userID, urlToApi);
        

        if (response == null || !response.has("data")) {
        System.out.println("No username history found.");
        return;
        }

        JsonArray history = response.getAsJsonArray("data");
        if (history.size() == 0) {
            System.out.println("No previous usernames found.");
            return;
        }

        System.out.println("Previous usernames for userID " + this.userID + ":");
        for (JsonElement element : history) {
            JsonObject entry = element.getAsJsonObject();
            String previousName = entry.get("name").getAsString();
            System.out.println("- " + previousName);
        }
    }

    public boolean checkForElement(String[] args, String element) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(element)) {
                return true;
            }
        }
        return false;
    }

    public void setElement(String[] args, String element) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(element)) {
                String[] argumentInput = {args[i], args[i + 1]};
                executeArgument(argumentInput);
            }
        }
    }

    public void printHelp() {
        System.out.println("\n=== RXLogger Help ===");
        System.out.println("Usage: java Main [option] [value] [option] [value]");
        System.out.println("Options:");
        System.out.println("  -n <username>       Specify the username to use.");
        System.out.println("  -i <userID>         Specify the user ID to use (overrides username).");
        System.out.println("  -t <type>           Specify the action to perform. Allowed types:");
        System.out.println("                        prevUsernames - Retrieve previous usernames");
        System.out.println("                        getUserID     - Get user ID from username");
        System.out.println("                        userInfo      - Fetch user information");
        System.out.println();
        System.out.println("Examples:");
        System.out.println("-n coolUser123 -t getUserID");
        System.out.println("-i 123456 -t prevUsernames");
        System.out.println();
        System.out.println("Note:");
        System.out.println("  - Either -n (username) or -i (user ID) must be specified.");
        System.out.println("  - Only two arguments (option + value) allowed.");
        System.out.println("=======================\n");
    }
}