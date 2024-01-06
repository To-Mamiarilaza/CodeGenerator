/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author to
 */
public class JsonUtil {
    
    // formating a json file to a json object
    public static JsonObject toJsonObject(String filePath) throws Exception {
        String content = FileUtil.toString(filePath);
        JsonParser parser = new JsonParser();
        return parser.parse(content).getAsJsonObject();
    }
}
