/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package codegenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import codegenerator.controller.Controller;
import codegenerator.database.*;
import codegenerator.model.*;
import codegenerator.util.CLIUtil;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.JsonUtil;;

/**
 *
 * @author to
 */
public class CodeGenerator {

    /**
     * @param args the command line arguments
     * @throws java.lang.Exception
     */

    DatabaseInformation databaseInformation;
    List<Model> models = new ArrayList<>();

    public DatabaseInformation getDatabaseInformation() {
        return databaseInformation;
    }

    public void setDatabaseInformation(DatabaseInformation databaseInformation) {
        this.databaseInformation = databaseInformation;
    }

    public List<Model> getModels() {
        return models;
    }

    public void setModels(List<Model> models) {
        this.models = models;
    }

    // find model with given name
    public Model getModelWithName(String name) {
        for (Model model : models) {
            if (model.getTable().getName().equals(name)) {
                return model;
            }
        }

        return null;
    }

    public void setDatabaseInformation(JsonObject config) throws Exception {
        String type = config.get("type").getAsString();
        checkTypeExistence(type);

        String database = config.get("database").getAsString();
        String host = config.get("host").getAsString();
        String username = config.get("username").getAsString();
        String password = config.get("password").getAsString();

        DatabaseInformation databaseInformation = new DatabaseInformation(database, host, username, password, type);
        try {
            databaseInformation.fetchInformations();
            setDatabaseInformation(databaseInformation);
            System.out.println("CONNECTION EFFECTUE !");

        } catch (Exception e) {
            System.out.println("CONNECTION ECHOUE !");
            System.out.println("ERREUR : " + e.getMessage());
        }
    }

    public void checkTypeExistence(String type) throws Exception {
        String typeList = DatabaseInformation.getDatabaseData().get("type").getAsJsonArray().toString();
        if (!typeList.contains(type)) {
            throw new Exception("Le type de base de donnees que vous avez entrer n'est pas encore supporte !");
        }
    }

    public void checkLanguageExistence(String language) throws Exception {
        String typeList = Model.getModelData().get("language").getAsJsonArray().toString();
        if (!typeList.contains(language)) {
            throw new Exception("Le language que vous entrer n'est pas encore supporte !");
        }
    }

    public void checkDAOCompatibility(String language, String DAO) throws Exception {
        if (DAO.equals("")) {
            return;
        }

        String supportedDAO = Model.getModelData().get("DAOCompatibility").getAsJsonObject().get(language)
                .getAsJsonArray().toString();
        if (!supportedDAO.contains(DAO)) {
            throw new Exception("Le DAO que vous avez choisi n'est pas supporte par le language !");
        }
    }

    public void resolveDependencyImport(Model model) throws Exception {
        List<Column> fKColumns = model.getTable().getForeignKeyColumns();
        for (Column column : fKColumns) {
            Model referencedModel = getModelWithName(column.getForeignKey().getTableName());
            if (referencedModel != null) {
                String importDeclaration = Model.getModelData().get("imports").getAsJsonObject()
                        .get(model.getLanguage()).getAsString();
                String typeImport = referencedModel.getPackageName() + "." + referencedModel.getClassName();

                importDeclaration = importDeclaration.replace("{type}", typeImport);

                model.setTemplateContent(
                        model.getTemplateContent().replace("#import-" + referencedModel.getClassName() + "#", importDeclaration));
            } else {
                model.setTemplateContent(CodeFormatter.removeContainingLine("#import-" + model.getClassName() + "#",
                        model.getTemplateContent()));
            }
        }
    }

    public void resolveAllDependencyImport() throws Exception {
        for (Model model : models) {
            resolveDependencyImport(model);
        }
    }

    public void generateAllModel() throws Exception {
        for (Model model : models) {
            model.generate();
        }
    }

    public void generateModel(String language, String outputPath, JsonObject modelConfig) throws Exception {
        // check if the given language exist
        checkLanguageExistence(language);

        JsonElement DAOElement = modelConfig.get("DAO");
        String DAO = "";
        if (!DAOElement.isJsonNull()) {
            DAO = DAOElement.getAsString();
        }

        JsonArray tableArray = modelConfig.get("tables").getAsJsonArray();

        // check if language support DAO
        checkDAOCompatibility(language, DAO);

        for (JsonElement jsonElement : tableArray) {
            JsonObject tableParameter = jsonElement.getAsJsonObject();

            // if generating all tables
            if (tableParameter.get("name").getAsString().equals("*")) {
                String packageName = tableParameter.get("package").getAsString();
                List<Table> targetTables = getDatabaseInformation().getTables();
                for (Table table : targetTables) {
                    System.out.print("- " + table.getName() + " : ");

                    Model model = new Model(table, language, DAO, packageName, outputPath);
                    model.loadTemplate();

                    getModels().add(model);

                    System.out.println("OK");
                }
            } else {
                String tableName = tableParameter.get("name").getAsString();
                String packageName = tableParameter.get("package").getAsString();
                Table targetTable = getDatabaseInformation().getTableWithName(tableName);
                System.out.print("- " + tableName + " : ");

                if (targetTable != null) {
                    Model model = new Model(targetTable, language, DAO, packageName, outputPath);
                    model.loadTemplate();

                    getModels().add(model);

                    System.out.println("OK");
                } else {
                    System.out.println("ECHOUE");
                }
            }

            // resolving interdependance imports
            resolveAllDependencyImport();

            // generating models
            generateAllModel();

        }

    }

    public void init(String configFilePath, String action) throws Exception {
        // Clear console first
        System.out.print("\033[H\033[2J");
        System.out.flush();

        JsonObject config = JsonUtil.toJsonObject(configFilePath);
        setDatabaseInformation(config.get("database").getAsJsonObject());

        // remove outputPath if exist
        String outputPath = config.get("outputPath").getAsString();
        FileUtil.removeDirectory(new File(outputPath));

        if (action.equals("model")) {
            System.out.println("GENERATION CODE MODEL");
            generateModel(config.get("language").getAsString(), outputPath, config.get("model").getAsJsonObject());

            // Test the controller
            Model targetModel = getModelWithName("fiche");
            Controller controller = new Controller(targetModel, "C#", ".net", "REST", "Entity", "controllers", "fiches", outputPath);
            controller.loadTemplate();
            controller.generate();

        } else if (action.equals("controller")) {
            System.out.println("GENERATION CODE CONTROLLER");

        } else if (action.equals("view")) {
            System.out.println("GENERATION CODE VIEW");

        } else {
            throw new Exception("L' action que vous mentionner n' est pas correct !");
        }
    }

    public static void main(String[] args) throws Exception {
        // CLI for interacting with code generator
        CodeGenerator codeGenerator = new CodeGenerator();

        try {
            String configPathChecking = args[0];
            String actionChecking = args[1];
        } catch (Exception e) {
            throw new Exception(
                    "Vous devez mentionner les arguments suivant : \n1. Path du fichier de configuration\n2. Action a effectuer");
        }

        codeGenerator.init(args[0], args[1]);
    }

}