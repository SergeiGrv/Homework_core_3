import com.opencsv.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringJoiner;


public class ClientLog {

    HashMap<Integer, Integer> operations = new HashMap<>();

    protected void log(int productNum, int amount){
        operations.put(productNum, amount);
    }

    protected void exportAsCSV(File txtFile) {
        String[] txtArray = txtFile.list();
        try(CSVWriter writer = new CSVWriter(new FileWriter("log.csv"))){
            writer.writeNext(txtArray);
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
