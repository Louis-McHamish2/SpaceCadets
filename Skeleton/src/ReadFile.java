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
            myScanner.useDelimiter(";");

            // while there are lines in the scanner
            while (myScanner.hasNext()) {
                // add them to the list
                fileList.add(myScanner.next());
            }
            // return the list of lines from the file
            return fileList;

        } catch (FileNotFoundException e) {
            throw new Exception("File not found");
        }
    }
}