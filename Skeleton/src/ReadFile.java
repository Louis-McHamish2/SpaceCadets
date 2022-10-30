import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadFile {
    public List<String> getData(String fileName) throws Exception {
        try {

            // create a new scanner and List
            Scanner myScanner = new Scanner(new File(fileName));
            List<String> fileList = new ArrayList<String>();

            // set ; to be the delimiter
            myScanner.useDelimiter(";|\\n");

            // while there are lines in the scanner
            while (myScanner.hasNext()) {

                String nextLine = myScanner.next();

                // remove new lines + tabs from Line
                nextLine = nextLine.replaceAll("\\r|\\n|\t", "");
                // remove whitespace from start and end of Line
                nextLine = nextLine.trim();


                if (!nextLine.equals("") && !nextLine.startsWith("//")) {
                    // add them to the list
                    fileList.add(nextLine);
                }
            }
            // return the list of lines from the file
            return fileList;

        } catch (FileNotFoundException e) {
            throw new Exception("File not found");
        }
    }
}