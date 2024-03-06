package codegenerator.project.generator;

import java.io.File;

import com.google.gson.JsonObject;

import codegenerator.util.Cmd;
import codegenerator.util.FileUtil;

public class DotnetBaseGenerator implements ProjectBaseGenerator {

    @Override
    public void generate(JsonObject config) throws Exception {
        System.out.println("GENERATION DU PROJET .NET\n");

        String projectName = config.get("projectName").getAsString();
        String outputPath = config.get("outputPath").getAsString();

        // Creating the project
        Cmd.execute("dotnet new mvc -n " + projectName, new File(outputPath));

        // Adding all dependencies
        Cmd.execute("dotnet add package Microsoft.EntityFrameworkCore.Design --version 6.0.0", new File(outputPath + "/" + projectName));
        Cmd.execute("dotnet add package Npgsql.EntityFrameworkCore.PostgreSQL --version 6.0.0", new File(outputPath + "/" + projectName));

        // Add needed appsettings file
        String appSettings = FileUtil.toStringInnerFile("/template/other/appsettings.template");
        appSettings = appSettings.replace("{databaseName}", config.get("database").getAsJsonObject().get("database").getAsString())
        .replace("{username}", config.get("database").getAsJsonObject().get("username").getAsString())
        .replace("{password}", config.get("database").getAsJsonObject().get("password").getAsString());
        FileUtil.createFileWithContent(appSettings, outputPath + "/" + projectName, "appsettings.json");

        // Add updated probramCs File
        String programCs = FileUtil.toStringInnerFile("/template/other/programCs.template");
        programCs = programCs.replace("{databaseContextNamespace}", config.get("controller").getAsJsonObject().get("dbServicePackage").getAsString());
        FileUtil.createFileWithContent(programCs, outputPath + "/" + projectName, "Program.cs");

        // Layout base
        String layout = FileUtil.toStringInnerFile("/template/other/_layout.cshtml.template");
        FileUtil.createFileWithContent(layout, outputPath + "/" + projectName + "/Views/Shared", "_Layout.cshtml");
    }

}
