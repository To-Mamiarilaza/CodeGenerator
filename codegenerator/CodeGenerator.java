/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package codegenerator;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

import codegenerator.controller.Controller;
import codegenerator.database.*;
import codegenerator.dbservice.DBService;
import codegenerator.model.*;
import codegenerator.util.CLIUtil;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.JsonUtil;
import codegenerator.view.View;;

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
    DBService globalDbService;

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

    public DBService getGlobalDbService() {
        return globalDbService;
    }

    public void setGlobalDbService(DBService globalDbService) {
        this.globalDbService = globalDbService;
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
        String oldImport = ""; // for managing import redondence

        for (Column column : fKColumns) {
            Model referencedModel = getModelWithName(column.getForeignKey().getTableName());
            if (referencedModel != null) {
                String importDeclaration = Model.getModelData().get("imports").getAsJsonObject()
                        .get(model.getLanguage()).getAsString();

                String importMethod = Model.getModelData().get("importMethod").getAsJsonObject()
                        .get(model.getLanguage()).getAsString();
                String typeImport = referencedModel.getPackageName();
                if (importMethod.equals("WITH TYPE")) {
                    typeImport += "." + referencedModel.getClassName();
                }

                // avoid redondancies
                if (oldImport.contains(typeImport)) {
                    model.setTemplateContent(CodeFormatter.removeContainingLine("#import-" + referencedModel.getClassName() + "#",
                    model.getTemplateContent()));
                } else {
                    importDeclaration = importDeclaration.replace("{type}", typeImport);
                    model.setTemplateContent(
                            model.getTemplateContent().replace("#import-" + referencedModel.getClassName() + "#",
                                    importDeclaration));
                    oldImport += "-" + typeImport;
                }

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

    public void generateModel(String language, String DAO, String outputPath, JsonObject modelConfig) throws Exception {
        // check if the given language exist
        checkLanguageExistence(language);

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

    public void checkFrameworkExistence(String framework) throws Exception {
        if (framework == null || framework.trim().equals("")) {
            throw new Exception("Le framework ne doit pas etre vide !");
        }

        String frameworkList = Controller.getControllerData().get("framework").getAsJsonArray().toString();
        if (!frameworkList.contains(framework)) {
            throw new Exception("Le framework que vous entrer n'est pas encore supporte !");
        }
    }

    public void checkFrameworkCompatibility(String language, String framework) throws Exception {
        String frameworkList = Controller.getControllerData()
                .get("frameworkCompatibility")
                .getAsJsonObject().get(language).getAsJsonArray().toString();

        if (!frameworkList.contains(framework)) {
            throw new Exception("Le framework et le language que vous entrer n'est pas compatible !");
        }
    }

    public void generateGlobalDatabaseService(JsonElement dbServiceElement, String dbServicePackage, String outputPath, String language) throws Exception {
        if (!dbServiceElement.isJsonNull()) {
            String dbServiceType = DBService.getDBServiceData().get("DBServiceType").getAsJsonObject().get(dbServiceElement.getAsString()).getAsString();
            if (dbServiceType.equals("GLOBAL")) {
                DBService dbService = new DBService(getModels(), dbServiceElement.getAsString(), dbServicePackage, outputPath, language);
                dbService.loadTemplate();
                dbService.generate();
        
                setGlobalDbService(dbService);
            }
        }

    }

    public void generateController(String language, String framework, String DAO, String outputPath,
            JsonObject controllerConfig) throws Exception {
        // check if the given framework exist and supported
        checkFrameworkExistence(framework);
        checkFrameworkCompatibility(language, framework);

        String type = controllerConfig.get("type").getAsString();
        String dbServicePackage = controllerConfig.get("dbServicePackage").getAsString();

        JsonArray tableArray = controllerConfig.get("tables").getAsJsonArray();

        // generate global db service if type is global
        JsonElement dbServiceElement = DBService.getDBServiceData().get("DAOServiceRequirements").getAsJsonObject().get(DAO);
        generateGlobalDatabaseService(dbServiceElement, dbServicePackage, outputPath, language);

        for (JsonElement jsonElement : tableArray) {
            JsonObject controllerParameter = jsonElement.getAsJsonObject();

            // if generating all tables
            if (controllerParameter.get("name").getAsString().equals("*")) {
                String packageName = controllerParameter.get("package").getAsString();
                String requestMapping = controllerParameter.get("requestMapping").getAsString();

                List<Table> targetTables = getDatabaseInformation().getTables();
                for (Table table : targetTables) {
                    System.out.print("- " + table.getName() + " : ");
                    Model model = getModelWithName(table.getName());

                    Controller controller = new Controller(model, language, framework, type, DAO, packageName,
                            requestMapping, outputPath, dbServicePackage);

                    // if we need global db service
                    controller.setDbService(globalDbService);

                    controller.loadTemplate();
                    controller.generate();

                    System.out.println("OK");
                }
            } else {
                String tableName = controllerParameter.get("name").getAsString();
                String packageName = controllerParameter.get("package").getAsString();
                String requestMapping = controllerParameter.get("requestMapping").getAsString();
                Table targetTable = getDatabaseInformation().getTableWithName(tableName);
                System.out.print("- " + tableName + " : ");

                if (targetTable != null) {
                    Model model = getModelWithName(targetTable.getName());

                    Controller controller = new Controller(model, language, framework, type, DAO, packageName,
                            requestMapping, outputPath, dbServicePackage);
                    
                    // if we need global db service
                    controller.setDbService(globalDbService);

                    controller.loadTemplate();
                    controller.generate();

                    System.out.println("OK");
                } else {
                    System.out.println("ECHOUE");
                }
            }
        }
    }

    public void generateView(JsonObject viewConfig, String outputPath, JsonObject data) throws Exception {
        String viewChoice = viewConfig.get("choice").getAsString();
        String viewPackage = viewConfig.get("package").getAsString();

        JsonArray tableArray = viewConfig.get("tables").getAsJsonArray();

        for (JsonElement jsonElement : tableArray) {
            JsonObject viewParameter = jsonElement.getAsJsonObject();

            // if generating all tables
            if (viewParameter.get("name").getAsString().equals("*")) {
                List<Table> targetTables = getDatabaseInformation().getTables();
                for (Table table : targetTables) {
                    System.out.print("- " + table.getName() + " : ");
                    Model model = getModelWithName(table.getName());

                    View view = new View(model, viewChoice, viewPackage, outputPath, data);
                    view.loadTemplate();
                    view.generate();

                    System.out.println("OK");
                }
            } else {
                String tableName = viewParameter.get("name").getAsString();
                Table targetTable = getDatabaseInformation().getTableWithName(tableName);
                System.out.print("- " + tableName + " : ");

                if (targetTable != null) {
                    Model model = getModelWithName(targetTable.getName());

                    View view = new View(model, viewChoice, viewPackage, outputPath, data);
                    view.loadTemplate();
                    view.generate();

                    System.out.println("OK");
                } else {
                    System.out.println("ECHOUE");
                }
            }
        }
    }

    public void init(String configFilePath) throws Exception {
        // Clear console first
        System.out.print("\033[H\033[2J");
        System.out.flush();

        JsonObject config = JsonUtil.toJsonObject(configFilePath, "OUT");
        setDatabaseInformation(config.get("database").getAsJsonObject());

        // remove outputPath if exist , not required
        String outputPath = config.get("outputPath").getAsString();
        // FileUtil.removeDirectory(new File(outputPath));

        String language = config.get("language").getAsString();
        String framework = config.get("framework").getAsString();

        JsonElement DAOElement = config.get("DAO");
        String DAO = "";
        if (!DAOElement.isJsonNull()) {
            DAO = DAOElement.getAsString();
        }

        // Generation des code
        System.out.println("GENERATION CODE MODEL");
        generateModel(language, DAO, outputPath, config.get("model").getAsJsonObject());

        System.out.println("\nGENERATION CODE CONTROLLER");
        generateController(language, framework, DAO, outputPath, config.get("controller").getAsJsonObject());

        System.out.println("\nGENERATION CODE VIEW");
        JsonObject data = JsonUtil.toJsonObject("/data/view.json", "IN");
        generateView(config.get("view").getAsJsonObject(), outputPath, data);

    }

    public static void main(String[] args) throws Exception {
        // CLI for interacting with code generator
        CodeGenerator codeGenerator = new CodeGenerator();

        try {
            String configPathChecking = args[0];
        } catch (Exception e) {
            throw new Exception(
                    "Vous devez mentionner le path du fichier de configuration !");
        }
        codeGenerator.init(args[0]);
    }

}