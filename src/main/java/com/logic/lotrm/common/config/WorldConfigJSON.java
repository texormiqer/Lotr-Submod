package com.logic.lotrm.common.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class WorldConfigJSON {
    // Configuration properties
    public static String mapPath;
    public static int scale;

    /**
     * Loads configuration from the specified file path.
     *
     * @param configFilePath The path to the configuration file.
     */
    public static void loadConfig(String configFilePath) {
        FileReader reader = null;
        try {
            File configFile = new File(configFilePath);

            if (!configFile.exists()) {
                createDefaultConfig(configFile);
            }

            // Read configuration from file
            reader = new FileReader(configFile);
            JsonObject jsonObject = new JsonParser().parse(reader).getAsJsonObject();
            mapPath = jsonObject.get("mapPath").getAsString();
            scale = jsonObject.get("scale").getAsInt();
        } catch (IOException e) {
            // Error handling if configuration file cannot be read
            System.err.println("Error: Could not read config file. Using default values.");
            setDefaultValues();
        } finally {
            // Close the reader
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Creates a new config file with default values if it doesn't exist.
     *
     * @param configFile The File object representing the config file.
     * @throws IOException If an I/O error occurs while creating the file.
     */
    private static void createDefaultConfig(File configFile) throws IOException {
        JsonObject defaultConfig = new JsonObject();
        defaultConfig.addProperty("mapPath", "assets/lotrm/textures/map/map.png");
        defaultConfig.addProperty("scale", 8);

        // Write default configuration to file
        FileWriter writer = null;
        try {
            writer = new FileWriter(configFile);
            writer.write(defaultConfig.toString());
        } finally {
            // Close the writer
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Sets default values for mapPath and scale.
     */
    private static void setDefaultValues() {
        mapPath = "assets/lotrm/textures/map/map.png";
        scale = 8;
    }

    /**
     * Retrieves the configured map path.
     *
     * @return The map path.
     */
    public static String getMapPath() {
        return mapPath;
    }

    /**
     * Retrieves the configured scale.
     *
     * @return The scale.
     */
    public static int getScale() {
        return scale;
    }

    /**
     * Initializes the configuration by loading values from the JSON file.
     * Prints the loaded map path and scale.
     */
    public static void init() {
        // Load configuration from the JSON file
        loadConfig("config.json");

        // Access configuration values
        System.out.println("Map Path: " + getMapPath());
        System.out.println("Scale: " + getScale());
    }
}
