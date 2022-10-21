import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Main {

    // create list which will hold variable objects
    public static List<Object> varList = new ArrayList<>();

    // loopStack holds the line number of while loops we need to return to
    public static Stack<Integer> loopStack = new Stack<Integer>();

    // indentLevel holds many loops we are currently in
    public static int indentLevel = 0;

    // ignoreLines is if we should be ignoring (not executing) lines
    static boolean ignoreLines = false;


    public static void main(String[] args) throws Exception {

        // get the name of the file from the user
        System.out.println("Enter File Name (e.g. demo1.txt): ");
        Scanner inputScanner = new Scanner(System.in);
        String fileName = "src/" + inputScanner.nextLine();

        // get list of lines in the file
        ReadFile File = new ReadFile();
        System.out.println(fileName);
        List<String> lineList = File.getData(fileName);

        for (int lineNumber = 0; lineNumber < lineList.size(); lineNumber++) {

            // get the line for the current line number
            String line = lineList.get(lineNumber);

            // remove new lines + tabs from Line
            line = line.replaceAll("\\r|\\n|\t", "");
            // remove whitespace from start and end of Line
            line = line.trim();

            // split the line into the first word and the rest
            String[] words = line.split(" ", 2);

            // checks what instruction is being used
            if ((words[0].equals("clear")
                    || words[0].equals("incr")
                    || words[0].equals("decr"))
                    && !ignoreLines) {

                // search for the variable we are trying to change
                VarObject var = (VarObject) findVariable(words[1]);
                if (var == null) {
                    // create a new variable
                    createNewVariable(words);
                } else {
                    // update a variable
                    updateVariable(var, words);
                }

            } else if (words[0].equals("while")) {

                // increase indent level
                indentLevel++;

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

            } else if (words[0].equals("end")) {

                // decrease indent level
                indentLevel--;

                // test if the indent level is less than the number of line numbers on the stack
                if (indentLevel < loopStack.size()) {
                    // we need to jump back as the while loop is still valid
                    ignoreLines = false;
                    lineNumber = loopStack.pop() - 1;
                    continue;
                } else if (indentLevel == loopStack.size()) {
                    // we are moving out of old while loop, we should not ignore the subsequent lines
                    ignoreLines = false;
                }
            }

            if (!ignoreLines){
                // print state of vars
                printVariableState(line, varList);
            }
        }
    }

    public static void createNewVariable(String[] words){
        // create new variable
        if (words[0].equals("clear")) {
            varList.add(new VarObject(words[1], 0));
        } else if (words[0].equals("incr")) {
            varList.add(new VarObject(words[1], 1));
        } else if (words[0].equals("decr")) {
            varList.add(new VarObject(words[1], -1));
        }
    }

    public static void updateVariable(VarObject var, String[] words) throws Exception {
        // change variable
        if (words[0].equals("clear")) {
            var.setValue(0);
        } else if (words[0].equals("incr")) {
            var.incrValue(1);
        } else if (words[0].equals("decr")) {
            var.incrValue(-1);
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
