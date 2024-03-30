package codegenerator.util;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

import com.google.gson.JsonObject;

import codegenerator.controller.Controller;
import codegenerator.database.DatabaseInformation;

public class AuthentificationUtil {

    // Crée les views pour le login et le signup
    public static void generateAuthAdditionalView(String outputPath, String viewPackage, String viewChoice, String apiUrl, JsonObject viewData) throws IOException {
        JsonObject usedData = viewData.get("authentification").getAsJsonObject().get(viewChoice).getAsJsonObject();

        // Generate the login view
        String packagePath = viewPackage.replace(".", "/");

        System.out.println("\nGeneration du page pour le Login ...");
        String loginViewContent = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("loginPageTemplate").getAsString());
        loginViewContent = loginViewContent.replace("#API_BASE_URL#", apiUrl);
        FileUtil.createFileWithContent(loginViewContent, WordFormatter.preparePath(outputPath) + packagePath + "/Authentification",
        usedData.get("loginPageName").getAsString());

        // Generate the signup view
        System.out.println("Generation du page pour le Signup ...");
        String signupViewContent = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("signupPageTemplate").getAsString());
        signupViewContent = signupViewContent.replace("#API_BASE_URL#", apiUrl);
        FileUtil.createFileWithContent(signupViewContent, WordFormatter.preparePath(outputPath) + packagePath + "/Authentification",
        usedData.get("signupPageName").getAsString());
    }

    // Crée le AuthController et la classe util
    public static void generateAuthRequiredFile(String outputPath, String controllerPackage, String userModelPackage, String repositoryPackage, String framework)
            throws Exception {
        JsonObject usedData = Controller.getControllerData().get("authentification").getAsJsonObject().get(framework).getAsJsonObject();

        // Generate authentification controller file
        System.out.println("\nGeneration du controller AuthController.java ...");
        String authControllerContent = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("authControllerTemplate").getAsString());
        authControllerContent = authControllerContent.replace("#ControllerPackage#", controllerPackage);
        authControllerContent = authControllerContent.replace("#UtilisateurModelPackage#", userModelPackage + ".Utilisateur");
        authControllerContent = authControllerContent.replace("#UtilisateurRepositoryPackage#", repositoryPackage + ".UtilisateurRepository");

        String packagePath = controllerPackage.replace(".", "/");
        FileUtil.createFileWithContent(authControllerContent, WordFormatter.preparePath(outputPath) + packagePath,
        usedData.get("authControllerFileName").getAsString());

        // Authentification util file
        System.out.println("Generation de l'util JWTToken.java ...");
        String authUtilContent = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("authUtilTemplate").getAsString());
        authUtilContent = authUtilContent.replace("#ControllerPackage#", controllerPackage);
        authUtilContent = authUtilContent.replace("#ExceptionPackage#", controllerPackage.replace("controller", "exception") + ".NotAuthorizedException");

        FileUtil.createFileWithContent(authUtilContent, WordFormatter.preparePath(outputPath) + packagePath,
        usedData.get("authUtilFileName").getAsString());

        // Generate utilisateur repository
        System.out.println("Generation du repository de l'utilisateur ...");
        String userRepContent = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("userRepositoryTemplate").getAsString());
        userRepContent = userRepContent.replace("#UtilisateurModelPackage#", userModelPackage + ".Utilisateur");
        userRepContent = userRepContent.replace("#RepositoryPackage#", repositoryPackage);

        FileUtil.createFileWithContent(userRepContent, WordFormatter.preparePath(outputPath) + repositoryPackage.replace(".", "/"), usedData.get("userRepositoryName").getAsString());

        // Generate NotAthorizedException
        System.out.println("Generation du repository de l'exception NotAuthorizedException ...");
        String exceptionPackage = controllerPackage.replace("controller", "exception");     // Ceci peut causer une erreur grave mais laissons le pour le moment
        String exceptionTemplate = FileUtil.toStringInnerFile("/template/auth/" + usedData.get("customExceptionTemplate").getAsString());
        exceptionTemplate = exceptionTemplate.replace("#ExceptionPackage#", exceptionPackage);

        FileUtil.createFileWithContent(exceptionTemplate, WordFormatter.preparePath(outputPath) + exceptionPackage.replace(".", "/"), usedData.get("customExceptionName").getAsString());

    }

    // Crée la table utilisateur pour contenir les informations de connection
    public static void initAuthentificationTable(DatabaseInformation databaseInformation) throws Exception {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = databaseInformation.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate("DROP TABLE IF EXISTS utilisateur");
            statement.executeUpdate(
                    "CREATE TABLE utilisateur ( id_utilisateur SERIAL PRIMARY KEY, username VARCHAR(50), password VARCHAR(50))");

            System.out.println("\nAUTHENTIFICATION :");
            System.out.println("Création de la table d'authentification : utilisateur\n");

        } catch (Exception e) {
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
}
