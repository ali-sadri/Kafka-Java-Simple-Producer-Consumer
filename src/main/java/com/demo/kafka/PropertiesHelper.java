package com.demo.kafka;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesHelper {
    public static Properties getProperties() throws Exception {
        Properties props = null;
        try (InputStream input = SimpleProducer.class.getClassLoader().getResourceAsStream("config.properties")) {
            props = new Properties();
            if (input == null) {
                throw new Exception("Sorry, unable to find config.properties");
            }
            props.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return props;
    }
}
