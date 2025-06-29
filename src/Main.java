public class Main {

    private final String[] allowedParameters = {"-n", "-t"};
    private final String type = "-t";
    private final String usernameInput = "-n";
    private final String[] allowedTypes = {"prevusernames"};
    private String username;
    private String urlToApi;

    public static void main(String[] args) {
       

        // if (args[i - 1].equals("-n")) {
                     //   String[] argumentInput = {args[i - 1], args[i]}; //argument + the parameter
                       // executeArgument(argumentInput);
                   // }

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
            case "-t": //type
                switch(argument[1]) {
                    case "prevusernames":
                        getAllUsernames();
                }
        }
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void getAllUsernames() {

    }

    public void printHelp() {

    }
}