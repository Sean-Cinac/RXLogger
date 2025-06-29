public class Main {

    final static String[] allowedParameters = {"-n", "-t"};

    public static void main(String[] args) {
        String urlToApi;
        String username; 


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
                    printHelp();
                    return false;
                } else {
                    parameterMode = false;
                    String[] argumentInput = {args[i - 1], args[i]}; //argument + the parameter
                    executeArgument(argumentInput);
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
    public void executeArgument(String[] argument) {

    }
    public void printHelp() {

    }
}