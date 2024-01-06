/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.model;

import codegenerator.database.Column;
import codegenerator.database.Table;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.JsonUtil;
import codegenerator.util.WordFormatter;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 *
 * @author to
 */
public class Model {
    /// Field
    Table table;
    String language;
    String DAO;
    String packageName;
    String outputPath;
    
    /// Getter and setter

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getDAO() {
        return DAO;
    }

    public void setDAO(String DAO) {
        this.DAO = DAO;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }
    
    /// Constructor

    public Model(Table table, String language, String DAO, String packageName, String outputPath) {
        this.table = table;
        this.language = language;
        this.DAO = DAO;
        this.packageName = packageName;
        this.outputPath = WordFormatter.preparePath(outputPath);
    }
    
    /// Method
    
    public String getGetterAndSetter() throws Exception {
        String gettersAndSetters = "";
        
        // check if the language has getter and setter declaration
        String declaration = null;
        try {
            declaration = getModelData().get("getterAndSetter").getAsJsonObject().get(getLanguage()).getAsString();
        } catch (Exception e) {
            return "";
        }
        
        for (Column column : getTable().getColumns()) {
            String type = getModelData().get("typeMapping").getAsJsonObject().get(getTable().getDatabaseInformation().getType()).getAsJsonObject().get(column.getType()).getAsJsonObject().get(getLanguage()).getAsString();
            gettersAndSetters += declaration.replace("{type}", type)
                    .replace("{fieldName}", WordFormatter.toCamelCase(column.getName()))
                    .replace("{capitalFieldName}", WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(column.getName())));
        }
        
        return gettersAndSetters;
    }
    
    public String getFieldsDeclaration() throws Exception {
        String fields = "";
        String fieldsDeclaration = getModelData().get("fields").getAsJsonObject().get(getLanguage()).getAsString();
        
        for (Column column : getTable().getColumns()) {
            String type = getModelData().get("typeMapping").getAsJsonObject().get(getTable().getDatabaseInformation().getType()).getAsJsonObject().get(column.getType()).getAsJsonObject().get(getLanguage()).getAsString();
            fields += fieldsDeclaration.replace("{type}", type).replace("{fieldName}", WordFormatter.toCamelCase(column.getName())) + "\n\t";
        }
        
        return fields;
    }
    
    public String getFileName() throws Exception {
        String fileExtension = getModelData().get("fileExtension").getAsJsonObject().get(getLanguage()).getAsString();
        return getClassName() + fileExtension;
    }
    
    public String getClassName() {
        return WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(getTable().getName()));
    }
    
    public String getImportsDeclaration() throws Exception {
        String imports = "";
        for (Column column : getTable().getColumns()) {
            String importDeclaration = getModelData().get("imports").getAsJsonObject().get(getLanguage()).getAsString();
            String type = getModelData().get("typeMapping").getAsJsonObject().get(getTable().getDatabaseInformation().getType()).getAsJsonObject().get(column.getType()).getAsJsonObject().get(getLanguage()).getAsString();
            JsonElement typeImport = getModelData().get("typeImport").getAsJsonObject().get(getLanguage()).getAsJsonObject().get(type);
            
            if (!typeImport.isJsonNull()) {
                imports += importDeclaration.replace("{type}", typeImport.getAsString()) + "\n";
            }
        }
        return imports;
    }
    
    public String getPackageDeclaration() throws Exception {
        String packageDeclaration = getModelData().get("packaging").getAsJsonObject().get(this.getLanguage()).getAsString();
        return packageDeclaration.replace("{packageName}", this.getPackageName());
    }
    
    public void generate() throws Exception {
        // get the template content
        String templateContent = FileUtil.toString("./template/model.template");
        
        // get all file content
        String packageDeclaration = getPackageDeclaration();
        String importsDeclaration = getImportsDeclaration();
        String className = getClassName();
        String fileName = getFileName();
        String fieldsDeclaration = getFieldsDeclaration();
        String gettersAndSetters = getGetterAndSetter();
        
        // replace template content
        templateContent = templateContent.replace("#package#", packageDeclaration);
        templateContent = templateContent.replace("#imports#", importsDeclaration);
        templateContent = templateContent.replace("#className#", className);        
        templateContent = templateContent.replace("#fields#", fieldsDeclaration);        
        templateContent = templateContent.replace("#gettersAndSetters#", gettersAndSetters);

        // Formatter le code
        templateContent = CodeFormatter.formatCode(templateContent);

        // generate file to the outputPath
        System.out.println(templateContent);
        String packagePath = getPackageName().replace(".", "/");
        FileUtil.createFileWithContent(templateContent, getOutputPath() + packagePath, fileName);
    }
    
    public JsonObject getModelData() throws Exception {
        return JsonUtil.toJsonObject("./data/model.json");
    }
}
