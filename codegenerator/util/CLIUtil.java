package codegenerator.util;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import codegenerator.database.DatabaseInformation;

public class CLIUtil {
    // get all database type
    public static List<String> getAllDatabaseType() throws Exception {
        List<String> databaseTypes = new ArrayList<>();

        JsonArray typesArray = DatabaseInformation.getDatabaseData().get("type").getAsJsonArray();
        for (JsonElement jsonElement : typesArray) {
            databaseTypes.add(jsonElement.getAsString());
        }

        return databaseTypes;
    }
}

