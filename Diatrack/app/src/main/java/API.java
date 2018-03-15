/**
 * Created by Marshall on 3/13/2018.
 */
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStream;
import java.net.*;
import java.io.*;
public class API {

    void GetData() throws IOException {
        URL url = new URL("https://trackapi.nutritionix.com/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("e7b6c459a87cc03a794eeac1237d8ce1", "90a86e7a");
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }

        in.close();
        con.disconnect();
    }
}

