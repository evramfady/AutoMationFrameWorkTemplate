package Utilities;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.Properties;

public class DataUtils {

    public static final String TESTDATAPATH = "src/test/resources/TestData/";

    private DataUtils() {
    }

    public static String getJsonData(String fileName, String key) {
        String path = TESTDATAPATH + fileName + ".json";
        JsonObject json;
        try (Reader reader = new FileReader(path)) {
            json = JsonParser.parseReader(reader).getAsJsonObject();
        } catch (IOException | RuntimeException e) {
            LogsUtils.error("Failed to read JSON file " + path + ": " + e.getMessage());
            throw new RuntimeException("Failed to read JSON file " + path, e);
        }
        JsonElement value = json.get(key);
        if (value == null || value.isJsonNull()) {
            String msg = "Key '" + key + "' not found in " + path;
            LogsUtils.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return value.getAsString();
    }

    public static String getPropertyData(String fileName, String key) {
        String path = TESTDATAPATH + fileName + ".properties";
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(path)) {
            properties.load(input);
        } catch (IOException e) {
            LogsUtils.error("Failed to read properties file " + path + ": " + e.getMessage());
            throw new RuntimeException("Failed to read properties file " + path, e);
        }
        String value = properties.getProperty(key);
        if (value == null) {
            String msg = "Key '" + key + "' not found in " + path;
            LogsUtils.error(msg);
            throw new IllegalArgumentException(msg);
        }
        return value;
    }
}
