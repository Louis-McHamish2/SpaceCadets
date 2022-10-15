import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {

        // pass System.in object to a new BufferedReader
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("Enter email ID: ");

        // get the email id
        String emailID = input.readLine();

        // construct URL address
        String urlAddress = "https://www.ecs.soton.ac.uk/people/" + emailID;

        // create URL object
        URL myURL = new URL(urlAddress);

        // create Buffered Read object from the URL
        BufferedReader reader = new BufferedReader(new InputStreamReader(myURL.openStream()));

        String targetString = "<meta property=\"og:title\"";
        String targetLine = "";

        String line;
        while ((line = reader.readLine()) != null)

            if (line.contains(targetString)) {
                targetLine = line;
                break;
            }
        reader.close();

        if (!targetLine.equals("")) {

            // find the name in the line
            targetLine = targetLine.substring(targetLine.indexOf("content=") + 9, targetLine.indexOf("/>") - 2);

            // print the name
            System.out.println(targetLine);

        }

    }
}