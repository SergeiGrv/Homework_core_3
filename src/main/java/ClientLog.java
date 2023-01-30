import com.opencsv.CSVWriter;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class ClientLog {

    ArrayList<String> operations = new ArrayList<>();

    protected void log(int productNum, int amount) {
       String productNumStr = String.valueOf(productNum);
       String amountStr = String.valueOf(amount);
        operations.add(productNumStr);
        operations.add(amountStr);
    }

    protected void exportAsCSV(File txtFile)  {
        String[] operationsArray = operations.toArray(new String[0]);
        try (CSVWriter writer = new CSVWriter(new FileWriter(txtFile, true))) {
            writer.writeNext(operationsArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
