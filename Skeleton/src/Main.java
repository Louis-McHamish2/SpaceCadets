import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    // create list which will hold variable objects
    public static List<Object> varList = new ArrayList<>();

    // loopStack holds the line number of while loops we need to return to
    public static Stack<Integer> loopStack = new Stack<Integer>();

    // whileIndent holds many while loops we are currently in (same for ifIndent)
    public static int whileIndent = 0;
    public static int ifIndent = 0;

    // ignoreLines is if we should be ignoring (not executing) lines
    static boolean ignoreLines = false;

    // lists used for if, else, endif loops
    static List<Integer> elseLevels = new ArrayList<Integer>();
    static List<Integer> ifLevels = new ArrayList<Integer>();


    public static void main(String[] args) throws Exception {

        // get the name of the file from the user
        System.out.println("Enter File Name (e.g. demo0.txt): ");
        Scanner inputScanner = new Scanner(System.in);
        String fileName = "src/" + inputScanner.nextLine();

        // get list of lines in the file
        ReadFile File = new ReadFile();
        System.out.println(fileName);
        List<String> lineList = File.getData(fileName);

        for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

            // get the line for the current line number
            String line = lineList.get(lineNumber);

            // split the line into the first word and the rest
            String[] words = line.split(" ", 2);

            // checks what instruction is being used
            if ((words[0].equals("clear")
                    || words[0].equals("incr")
                    || words[0].equals("decr")
                    || words[0].equals("mul")
                    || words[0].equals("div"))
                    && !ignoreLines) {

                String[] parameters = words[1].split(",");

                // search for the variable we are trying to change
                VarObject var = (VarObject) findVariable(parameters[0]);
                if (var == null) {
                    // create a new variable
                    createNewVariable(words, parameters);
                } else {
                    // update a variable
                    updateVariable(var, words, parameters);
                }

            } else if (words[0].equals("while")) {
                whileKeyword(words, lineNumber);

            } else if (words[0].equals("end")) {
                lineNumber = endKeyword(words, lineNumber);

            } else if (words[0].equals("if")) {
                ifKeyword(words, lineNumber);

            } else if (words[0].equals("else")) {
                elseKeyword(words, lineNumber);
            }


            if (!(ignoreLines || words[0].equals("end"))){
                // print state of vars
                printVariableState(line, varList);
            }
        }
    }

    private static void elseKeyword(String[] words, int lineNumber) {

        if (elseLevels.contains(ifIndent - 1)){
            // if this else is valid
            ignoreLines = false;
            elseLevels.remove(ifIndent - 1);
        } else {
            ignoreLines = true;
        }
    }

    private static void ifKeyword(String[] words, int lineNumber) throws Exception {

        ifIndent++;

        if (!ignoreLines){

            String[] condition = words[1].replace("do", " ").split(" ");
            boolean conditionIsTrue = conditions.testCondition(condition);

            if (!conditionIsTrue) {
                ignoreLines = true;
                elseLevels.add(ifIndent - 1);
            }

            ifLevels.add(ifIndent - 1);

        }
    }


    private static int endKeyword(String[] words, int lineNumber) {

        if (words[1].equals("while")) {

            // decrease indent level
            whileIndent--;

            // test if the indent level is less than the number of line numbers on the stack
            if (whileIndent < loopStack.size()) {
                // we need to jump back as the while loop is still valid
                ignoreLines = false;
                //System.out.println("jumping");
                return loopStack.pop() - 1;

            } else if (whileIndent == loopStack.size()) {
                // we are moving out of old while loop, we should not ignore the subsequent lines
                //System.out.println("not jumping");
                ignoreLines = false;
            }
        } else if (words[1].equals("if")) {

            // decrease indent level
            ifIndent--;


            if (ifLevels.contains(ifIndent)){
                // if this else is valid
                ignoreLines = false;
                ifLevels.remove(ifIndent);
            } else {
                ignoreLines = true;
            }
        }
        return lineNumber;
    }

    private static void whileKeyword(String[] words, int lineNumber) throws Exception {

        // increase indent level
        whileIndent++;

        if (!ignoreLines) {

            // test the condition
            String[] condition = words[1].replace("do", " ").split(" ");
            boolean conditionIsTrue = conditions.testCondition(condition);

            if (conditionIsTrue) {
                // add the line number to the stack, so we can jump back to it
                loopStack.push(lineNumber);
            } else {
                // ignore subsequent lines as the condition is false
                ignoreLines = true;
            }
        }
    }

    public static void createNewVariable(String[] words, String[] parameters) throws Exception {
        // create new variable
        if (words[0].equals("clear")) {
            varList.add(new VarObject(words[1], 0));
        } else{
            throw new Exception("variable " + words[1] + " has not yet been defined");
        }
    }

    public static void updateVariable(VarObject var, String[] words, String[] parameters) throws Exception {
        // change variable
        if (words[0].equals("clear")) {
            var.setValue(0);
        } else {

            int value = value(parameters[1]);

            if (words[0].equals("incr")) {
                var.incrValue(value);
            } else if (words[0].equals("decr")) {
                var.incrValue(-value);
            } else if (words[0].equals("mul")) {
                var.mulValue(value);
            } else if (words[0].equals("div")) {
                var.mulValue(1f/value);
            }
        }
    }

    private static int value(String var){

        var = var.trim();

        try {
            // is value
            return Integer.parseInt(var);
        } catch (NumberFormatException e) {
            // is variable
            return ((VarObject) findVariable(var)).getValue();
        }
    }

    private static void printVariableState(String line, List<Object> varList){
        // print state of vars
        System.out.println("=====================");
        System.out.println(line);

        for (Object var : varList) {
            System.out.print(((VarObject)var).getName() + " = ");
            System.out.println(((VarObject)var).getValue());

        }
    }

    //find a variable in a list
    public static Object findVariable(String varName) {

        // iterate through the list
        for (Object var : varList) {
            // if we find the object return it
            if (varName.equals(((VarObject)var).getName())) {
                return var;
            }
        }
        return null;
    }
}
