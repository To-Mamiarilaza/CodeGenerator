package codegenerator.project.generator;

import java.io.File;

import com.google.gson.JsonObject;

import codegenerator.util.Cmd;

public class SpringBaseGenerator implements ProjectBaseGenerator {
    
    @Override
    public void generate(JsonObject config) throws Exception {
        System.out.println("GENERATION DU PROJET SPRING");

        String projectName = config.get("projectName").getAsString();
        String outputPath = config.get("outputPath").getAsString();

        createEmptyProject(projectName, outputPath);
    }

    public void createEmptyProject(String projectName, String outputPath) throws Exception {
        String command = "mvn archetype:generate -DgroupId=" + projectName.toLowerCase() + " -DartifactId=" + projectName + " -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false";
        
        Cmd.execute(command, new File("."));
    }

}
