package utils;

import java.util.Properties;

// Singelton pattern class
public class ConfigLoader {
    private final Properties properties;
    private static ConfigLoader configLoader;

    private ConfigLoader(){
        properties = PropertyUtils.propertyLoader(System.getProperty("user.dir") + "\\src\\main\\resources\\config.properties");
    }

    public static ConfigLoader getInstance(){
        if(configLoader == null){
            configLoader = new ConfigLoader();
        }
        return configLoader;
    }

    public String getPropertyValue(String propertyKey){
        String prop = null;
        try{
            prop = properties.getProperty(propertyKey);
        }catch (Exception e) {
            LoggerUtil.getLogger().error("Couldn't find property key specified: " + propertyKey + "\n" + e.getMessage());
        }
        return prop;
    }
}
