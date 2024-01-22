package codegenerator.dbservice;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import codegenerator.model.Model;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.JsonUtil;
import codegenerator.util.WordFormatter;

public class DBService {
    /// Field
    Model model;
    String className;
    String type;
    String packageName;
    String outputPath;
    String templateContent;

    /// Getter and setter

    public Model getModel() {
        return model;
    }
    public void setModel(Model model) {
        this.model = model;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
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

    public DBService(Model model, String className, String type, String packageName, String outputPath) {
        this.model = model;
        this.className = model.getClassName() + WordFormatter.capitalizeFirstLetter(type);
        this.type = type;
        this.packageName = packageName;
        this.outputPath = WordFormatter.preparePath(outputPath);
    }

    /// Methods

    public void setPackageDeclaration() throws Exception {
        String packageDeclaration = Model.getModelData().get("packaging").getAsJsonObject().get(getModel().getLanguage())
                .getAsString();
        packageDeclaration = packageDeclaration.replace("{packageName}", this.getPackageName());
        setTemplateContent(getTemplateContent().replace("#package#", packageDeclaration));
    }

    public void setImportsDeclaration() throws Exception {
        String imports = "";

        // imports controller requirements
        JsonElement dbServiceElement = getDBServiceData().get("DBServiceImports").getAsJsonObject()
                .get(getType());
        String importDeclaration = Model.getModelData().get("imports").getAsJsonObject().get(getModel().getLanguage())
                .getAsString();

        if (!dbServiceElement.isJsonNull()) {
            String customImportDeclaration = importDeclaration;
            for (JsonElement dbServiceImport : dbServiceElement.getAsJsonArray()) {
                imports += customImportDeclaration.replace("{type}", dbServiceImport.getAsString()) + "\n";
            }
        }

        String modelImport = importDeclaration;
        String modelImportValue = getModel().getPackageName();

        String importMethod = Model.getModelData().get("importMethod").getAsJsonObject().get(getModel().getLanguage()).getAsString();
        if (importMethod.equals("WITH TYPE")) {
            modelImportValue += "." + getModel().getClassName();
        }

        imports += modelImport.replace("{type}", modelImportValue) + "\n";

        // DAO imports requirements
        // ...

        setTemplateContent(getTemplateContent().replace("#imports#", imports));
    }

    public void loadTemplate() throws Exception {
        // get the template content
        String templateContent = FileUtil.toStringInnerFile("/template/dbservice/" + getType() + ".template");
        setTemplateContent(templateContent);

        setPackageDeclaration();
        setImportsDeclaration();

        // change db service template content
        setTemplateContent(getTemplateContent().replace("{type}", getModel().getClassName()));
        setTemplateContent(getTemplateContent().replace("{pkFieldType}", getModel().getPrimaryKeyFieldType()));

        setTemplateContent(CodeFormatter.formatCode(getTemplateContent()));
    }

    public String getFieldName() throws Exception {
        String fieldName = DBService.getDBServiceData().get("fieldName")
                .getAsJsonObject().get(getType()).getAsString();

        return fieldName.replace("{fieldName}", WordFormatter.firstLetterToLower(getClassName()));
    }

    public String getFileName() throws Exception {
        String fileExtension = Model.getModelData().get("fileExtension").getAsJsonObject().get(getModel().getLanguage())
                .getAsString();
        return getClassName() + fileExtension;
    }

    public void generate() throws Exception {
        String packagePath = getPackageName().replace(".", "/");
        FileUtil.createFileWithContent(getTemplateContent(), getOutputPath() + packagePath, getFileName());
    }

    public static JsonObject getDBServiceData() throws Exception {
        return JsonUtil.toJsonObject("/data/DBService.json", "IN");
    }
    

}
