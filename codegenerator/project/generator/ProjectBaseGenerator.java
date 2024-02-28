package codegenerator.project.generator;

import com.google.gson.JsonObject;

public interface ProjectBaseGenerator {

    // Every project generator class has their own generator method
    public void generate(JsonObject config) throws Exception;

}
