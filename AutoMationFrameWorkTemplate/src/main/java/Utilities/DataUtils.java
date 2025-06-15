package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DataUtils {

    public final static String TESTDATAPATH = "src/test/resources/TestData/";

    //TODO: Read Data From Json File
    public static String getJsonData(String fileName, String key) throws FileNotFoundException {
        try {
            FileReader reader = new FileReader(TESTDATAPATH + fileName + ".json");
            JsonElement jsonElement = JsonParser.parseReader(reader);
            return jsonElement.getAsJsonObject().get(key).getAsString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    //TODO: Read Data From Properties File
    public static String getPropertyData(String fileName, String key) throws IOException {
    try {
        Properties properties = new Properties();
        properties.load(new FileInputStream(TESTDATAPATH + fileName + ".properties"));
        return properties.getProperty(key);
    }
    catch (FileNotFoundException e) {
        e.printStackTrace();
    }
        return "";
    }
}
