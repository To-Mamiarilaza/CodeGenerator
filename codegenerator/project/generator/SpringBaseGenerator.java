package codegenerator.project.generator;

import java.io.File;

import com.google.gson.JsonObject;

import codegenerator.util.Cmd;
import codegenerator.util.FileUtil;

public class SpringBaseGenerator implements ProjectBaseGenerator {

    @Override
    public void generate(JsonObject config) throws Exception {
        System.out.println("GENERATION DU PROJET SPRING");

        String projectName = config.get("projectName").getAsString();
        String outputPath = config.get("outputPath").getAsString();

        // Create empty base project
        String command = "mvn archetype:generate -DgroupId=" + projectName.toLowerCase() + " -DartifactId="
                + projectName + " -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false";
        Cmd.execute(command, new File(outputPath));

        // Add all dependencies
        addAllDependencies(projectName, outputPath);

        // Add application properties
        addApplicationProperties(projectName, outputPath, config.get("database").getAsJsonObject());

        // Build the project
        System.out.println("BUILD DU PROJET");
        Cmd.execute("mvn clean install", new File(outputPath + "/" + projectName));
    }

    public void addAllDependencies(String projectName, String outputPath) throws Exception {
        String pomXmlTemplate = FileUtil.toStringInnerFile("/template/other/pom.template");

        pomXmlTemplate = pomXmlTemplate.replace("#projectName#", projectName).replace("#lowerProjectName#",
                projectName.toLowerCase());

        FileUtil.createFileWithContent(pomXmlTemplate, outputPath + "/" + projectName + "/", "pom.xml");
    }

    public void addApplicationProperties(String projectName, String outputPath, JsonObject databaseConfing)
            throws Exception {
        String appPpties = FileUtil.toStringInnerFile("/template/other/applicationProperties.template");

        appPpties = appPpties.replace("#databaseType#", databaseConfing.get("type").getAsString())
                .replace("#databaseName#", databaseConfing.get("database").getAsString())
                .replace("#databaseUser#", databaseConfing.get("username").getAsString())
                .replace("#databasePassword#", databaseConfing.get("password").getAsString());

        FileUtil.createFileWithContent(appPpties, outputPath + "/" + projectName + "/src/main/resources/", "application.properties");
    }

}
