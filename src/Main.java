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
    program.validateSyntax(args);
    program.setUsernameFirst(args);
    program.setUserIDFirst(args);
    program.setType(args);

    }

    public boolean validateSyntax(String[] args) { //checking if the user is starting the program with the correct options
        boolean parameterMode = false;
        for (int i = 0; i < args.length; i++) {
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
                            return false;
                        }
                   }
                }
            }
            else if (checkIfSyntaxExists(args[i])) {
                parameterMode = true; //next input should be a parameter for the argument
            }

        }
        return true;
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
                    case "prevusernames":
                        getAllUsernames();
                        break;
                    case "getUserID":
                        getUserIDByUsername();
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

    public void getUserIDByUsername() {
        urlToApi = "https://users.roblox.com/v1/usernames/users";
        UserIDbyUsername apiFetch = new UserIDbyUsername();
        String foundUserID = apiFetch.getUserId(username, urlToApi);

        System.out.printf("UserID for %s: %s%n", this.username, foundUserID);

    }

    public void getAllUsernames() {

    }

    public void setUsernameFirst(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(usernameInput)) {
                String[] argumentInput = {args[i], args[i + 1]};
                executeArgument(argumentInput);
            }
        }
    }
    public void setUserIDFirst(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(userIDInput)) {
                String[] argumentInput = {args[i], args[i + 1]};
                executeArgument(argumentInput);
            }
        }
    }
    public void setType(String[] args) {
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals(type)) {
                String[] argumentInput = {args[i], args[i + 1]};
                executeArgument(argumentInput);
            }
        }
    }

    public void printHelp() {

    }
}