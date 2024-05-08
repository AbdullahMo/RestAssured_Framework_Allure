package utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyUtils {
    public static Properties propertyLoader(String filePath){
        Properties properties = new Properties();
        BufferedReader bufferedReader;

        try {
            bufferedReader = new BufferedReader(new FileReader(filePath));
            try {
                properties.load(bufferedReader);
                bufferedReader.close();
            }catch (IOException e){
                LoggerUtil.getLogger().error("Failed to load properties file.."+ "\n" + e.fillInStackTrace());
            }
        } catch (FileNotFoundException e) {
            LoggerUtil.getLogger().error("File is not found.." + "\n" + e.fillInStackTrace());
        }
        return properties;
    }
}
