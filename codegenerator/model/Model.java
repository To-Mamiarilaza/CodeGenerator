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

import java.util.List;

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

    String templateContent;

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

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
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

    public String getPrimaryKeyFieldType() throws Exception {
        Column column = getTable().getPrimaryKeyColumn();
        if (column == null)
            return null;

        String type = getModelData().get("typeMapping").getAsJsonObject().get(column.getType()).getAsJsonObject()
                .get(getLanguage()).getAsString();
        return type;
    }

    public String getPrimaryKeyFieldName() throws Exception {
        Column column = getTable().getPrimaryKeyColumn();
        if (column == null)
            return null;

        String fieldName = WordFormatter.toCamelCase(column.getName());
        String fieldCase = getModelData().get("fieldCase").getAsJsonObject().get(getLanguage()).getAsString();
        if (fieldCase.equals("UPPER")) {
            fieldName = WordFormatter.capitalizeFirstLetter(fieldName);
        }
        return fieldName;
    }

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
            String type = getModelData().get("typeMapping").getAsJsonObject().get(column.getType()).getAsJsonObject()
                    .get(getLanguage()).getAsString();
            String fieldName = WordFormatter.toCamelCase(column.getName());

            // Foreign key managing
            if (column.getForeignKey() != null) {
                type = WordFormatter
                        .capitalizeFirstLetter(WordFormatter.toCamelCase(column.getForeignKey().getTableName()));
                fieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
            }

            gettersAndSetters += declaration.replace("{type}", type)
                    .replace("{fieldName}", fieldName)
                    .replace("{capitalFieldName}", WordFormatter.capitalizeFirstLetter(fieldName));
        }

        return gettersAndSetters;
    }

    public String getFieldsDeclaration() throws Exception {
        String fields = "";
        String fieldsDeclaration = getModelData().get("fields").getAsJsonObject().get(getLanguage()).getAsString();

        for (Column column : getTable().getColumns()) {
            String customFieldsDeclaration = fieldsDeclaration;

            String type = getModelData().get("typeMapping").getAsJsonObject().get(column.getType()).getAsJsonObject()
                    .get(getLanguage()).getAsString();
            String fieldName = WordFormatter.toCamelCase(column.getName());

            // set the field case
            String fieldCase = getModelData().get("fieldCase").getAsJsonObject().get(getLanguage()).getAsString();
            if (fieldCase.equals("UPPER")) {
                fieldName = WordFormatter.capitalizeFirstLetter(fieldName);
            }

            if (column.getForeignKey() != null) {
                type = WordFormatter
                        .capitalizeFirstLetter(WordFormatter.toCamelCase(column.getForeignKey().getTableName()));

                fieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
                if (fieldCase.equals("UPPER")) {
                    fieldName = WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(column.getForeignKey().getTableName()));
                }
            }

            // managing field annotation
            JsonElement fieldAnnotationElement = getModelData().get("DAOAnnotations").getAsJsonObject().get("field")
                    .getAsJsonObject().get(getDAO());
            if (fieldAnnotationElement == null) {
                customFieldsDeclaration = customFieldsDeclaration.replace("{fieldAnnotation}\n ", "");
            } else {
                customFieldsDeclaration = customFieldsDeclaration.replace("{fieldAnnotation}",
                        fieldAnnotationElement.getAsString());
                customFieldsDeclaration = customFieldsDeclaration.replace("{columnName}", column.getName());

                // Managing primary key
                if (column.getIsPrimaryKey()) {
                    String primaryKeyAnnotation = getModelData().get("PKAnnotations").getAsJsonObject().get(getDAO())
                            .getAsString();
                    customFieldsDeclaration = customFieldsDeclaration.replace("{PKAnnotation}", primaryKeyAnnotation);

                } else {
                    // for single annotation
                    customFieldsDeclaration = customFieldsDeclaration.replace("{PKAnnotation}\n", "");
                    // for integrated annotation
                    customFieldsDeclaration = customFieldsDeclaration.replace("{PKAnnotation}", "");
                }

                // Managing foreign key
                if (column.getForeignKey() != null) {
                    String foreignKeyAnnotation = getModelData().get("FKAnnotations").getAsJsonObject().get(getDAO())
                            .getAsString();
                    foreignKeyAnnotation = foreignKeyAnnotation.replace("{fkColumnName}",
                            column.getForeignKey().getColumnName());

                    customFieldsDeclaration = customFieldsDeclaration.replace("{FKAnnotation}", foreignKeyAnnotation);

                    // removing column name delcaration
                    customFieldsDeclaration = customFieldsDeclaration.replace("{ColumnMappingAnnotation}", "");
                } else {
                    // for single annotation or integrated annotation
                    customFieldsDeclaration = customFieldsDeclaration.replace("{FKAnnotation}\n", "");
                    customFieldsDeclaration = customFieldsDeclaration.replace("{FKAnnotation}", "");

                    // add column mapping annotation
                    String columnMapping = getModelData().get("ColumnMappingAnnotations").getAsJsonObject()
                            .get(getDAO()).getAsString();
                    columnMapping = columnMapping.replace("{columnName}", column.getName());
                    customFieldsDeclaration = customFieldsDeclaration.replace("{ColumnMappingAnnotation}",
                            columnMapping);
                }

                // add an espacement for structuring
                customFieldsDeclaration = customFieldsDeclaration + "\n";
            }

            fields += customFieldsDeclaration.replace("{type}", type).replace("{fieldName}", fieldName) + "\n\t";
        }

        return fields;
    }

    public String getFileName() throws Exception {
        String fileExtension = getModelData().get("fileExtension").getAsJsonObject().get(getLanguage()).getAsString();
        return getClassName() + fileExtension;
    }

    public String getFieldName() {
        return WordFormatter.firstLetterToLower(getClassName());
    }

    public String getClassName() {
        String className = WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(getTable().getName()));
        // if (className.endsWith("s")) {
        //     className = className.substring(0, className.length() - 1);
        // }
        return className;
    }

    public String getImportsDeclaration() throws Exception {
        String imports = "";
        for (Column column : getTable().getColumns()) {
            String importDeclaration = getModelData().get("imports").getAsJsonObject().get(getLanguage()).getAsString();
            String type = getModelData().get("typeMapping").getAsJsonObject().get(column.getType()).getAsJsonObject()
                    .get(getLanguage()).getAsString();
            JsonElement typeImport = getModelData().get("typeImport").getAsJsonObject().get(getLanguage())
                    .getAsJsonObject().get(type);

            if (column.getForeignKey() != null) {
                String classType = WordFormatter
                        .capitalizeFirstLetter(WordFormatter.toCamelCase(column.getForeignKey().getTableName()));
                imports += "#import-" + classType + "#\n";
            } else if (!typeImport.isJsonNull() && !imports.contains(typeImport.getAsString())) {
                imports += importDeclaration.replace("{type}", typeImport.getAsString()) + "\n";
            }
        }

        // managing dao imports
        JsonElement DAOImportsElement = getModelData().get("DAOImports").getAsJsonObject().get(getDAO());
        if (DAOImportsElement != null) {
            String importDeclaration = getModelData().get("imports").getAsJsonObject().get(getLanguage()).getAsString();
            for (JsonElement DAOImport : DAOImportsElement.getAsJsonArray()) {
                imports += importDeclaration.replace("{type}", DAOImport.getAsString()) + "\n";
            }
        }

        return imports;
    }

    public String getPackageDeclaration() throws Exception {
        String packageDeclaration = getModelData().get("packaging").getAsJsonObject().get(this.getLanguage())
                .getAsString();
        return packageDeclaration.replace("{packageName}", this.getPackageName());
    }

    public String getClassAnnotation() throws Exception {
        JsonElement classAnnotationElement = getModelData().get("DAOAnnotations").getAsJsonObject().get("class")
                .getAsJsonObject().get(getDAO());
        if (classAnnotationElement == null) {
            return null;
        } else {
            return classAnnotationElement.getAsString().replace("{tableName}", getTable().getName());
        }
    }

    public void loadTemplate() throws Exception {
        // get the template content
        String templateContent = FileUtil.toStringInnerFile("/template/model.template");

        // get all file content
        String packageDeclaration = getPackageDeclaration();
        String importsDeclaration = getImportsDeclaration();
        String className = getClassName();
        String fieldsDeclaration = getFieldsDeclaration();
        String gettersAndSetters = getGetterAndSetter();
        String classAnnotation = getClassAnnotation();

        // replace template content
        templateContent = templateContent.replace("#package#", packageDeclaration);
        templateContent = templateContent.replace("#imports#", importsDeclaration);
        templateContent = templateContent.replace("#className#", className);
        templateContent = templateContent.replace("#fields#", fieldsDeclaration);
        templateContent = templateContent.replace("#gettersAndSetters#", gettersAndSetters);

        // if section may be empty
        templateContent = classAnnotation != null ? templateContent.replace("#classAnnotation#", classAnnotation)
                : CodeFormatter.removeContainingLine("#classAnnotation#", templateContent);

        // Formatter le code
        templateContent = CodeFormatter.formatCode(templateContent);

        setTemplateContent(templateContent);
    }

    // generate file to the outputPath
    public void generate() throws Exception {
        String packagePath = getPackageName().replace(".", "/");
        FileUtil.createFileWithContent(getTemplateContent(), getOutputPath() + packagePath, getFileName());
    }

    public static JsonObject getModelData() throws Exception {
        return JsonUtil.toJsonObject("/data/model.json", "IN");
    }
}
