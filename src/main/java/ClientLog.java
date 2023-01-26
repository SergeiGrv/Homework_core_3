import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.HashMap;


public class ClientLog {
    HashMap<Integer, Integer> operations = new HashMap<>();

    protected void log(int productNum, int amount) {
        operations.put(productNum, amount);
    }

    protected void exportAsCSV(File file)  {
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(file));
            JSONObject jsonObject = (JSONObject) obj;
            Gson gson = new Gson();
            System.out.println(jsonObject);
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}
