package com.swaglabs.BrowserUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static Properties props = new Properties();
        public static void loadProperties(String filePath) {
            try (FileInputStream ip = new FileInputStream(filePath)) {
                props.load(ip);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load properties file: " + filePath);
            }
        }
    
        // Get property by key
        public static String get(String key) {
            if (props == null || props.isEmpty()) {
                throw new IllegalStateException("Properties not loaded. Call loadProperties() first.");
            }
            return props.getProperty(key);
        }
    
    
    
}
