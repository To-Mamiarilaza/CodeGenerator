package codegenerator.controller;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import codegenerator.CodeGenerator;
import codegenerator.database.Column;
import codegenerator.dbservice.DBService;
import codegenerator.model.Model;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.JsonUtil;
import codegenerator.util.WordFormatter;

public class Controller {
    /// Field
    Model model;
    String language;
    String framework;
    String controllerType;
    String DAO;
    String packageName;
    String requestMapping;
    String outputPath;
    String dbServicePackage;
    String templateContent;
    DBService dbService;

    /// Getter and setter
    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getControllerType() {
        return controllerType;
    }

    public void setControllerType(String controllerType) {
        this.controllerType = controllerType;
    }

    public String getDAO() {
        return DAO;
    }

    public void setDAO(String dAO) {
        DAO = dAO;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getRequestMapping() {
        return requestMapping;
    }

    public void setRequestMapping(String requestMapping) {
        this.requestMapping = requestMapping;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getDbServicePackage() {
        return dbServicePackage;
    }

    public void setDbServicePackage(String dbServicePackage) {
        this.dbServicePackage = dbServicePackage;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public DBService getDbService() {
        return dbService;
    }

    public void setDbService(DBService dbService) {
        this.dbService = dbService;
    }

    /// Constructor
    public Controller(Model model, String language, String framework, String controllerType, String DAO,
            String packageName, String requestMapping, String outputPath, String dbServicePackage) {
        this.model = model;
        this.language = language;
        this.framework = framework;
        this.controllerType = controllerType;
        this.DAO = DAO;
        this.packageName = packageName;
        this.dbServicePackage = dbServicePackage;

        if (requestMapping.equals("DEFAULT")) {
            requestMapping = WordFormatter.toCamelCase(getModel().getTable().getName());
            if (!getModel().getTable().getName().endsWith("s")) {
                requestMapping = requestMapping + "s";
            }
        }
        this.requestMapping = "/" + requestMapping;

        this.outputPath = WordFormatter.preparePath(outputPath);
    }

    /// Methods
    public void setPackageDeclaration() throws Exception {
        String packageDeclaration = Model.getModelData().get("packaging").getAsJsonObject().get(this.getLanguage())
                .getAsString();
        packageDeclaration = packageDeclaration.replace("{packageName}", this.getPackageName());
        setTemplateContent(getTemplateContent().replace("#package#", packageDeclaration));
    }

    public void setImportsDeclaration() throws Exception {
        String imports = "";

        // imports controller requirements
        JsonElement controllerImportsElement = getControllerData().get("controllerImports").getAsJsonObject()
                .get(getFramework()).getAsJsonObject().get(getControllerType());
        String importDeclaration = Model.getModelData().get("imports").getAsJsonObject().get(getLanguage())
                .getAsString();

        if (!controllerImportsElement.isJsonNull()) {
            String customImportDeclaration = importDeclaration;
            for (JsonElement controllerImport : controllerImportsElement.getAsJsonArray()) {
                imports += customImportDeclaration.replace("{type}", controllerImport.getAsString()) + "\n";
            }
        }

        // Model import
        String importMethod = getModel().getModelData().get("importMethod").getAsJsonObject().get(getModel().getLanguage()).getAsString();
        
        String modelImport = importDeclaration;
        String type = getModel().getPackageName();
        if (importMethod.equals("WITH TYPE")) {
            type += "." + getModel().getClassName();
        }

        imports += modelImport.replace("{type}", type) + "\n";

        // DB Service Imports
        if (getDbService() != null) {
            String dbServiceImport = importDeclaration;

            String dbServiceImportValue = getDbService().getPackageName();
            if (importMethod.equals("WITH TYPE")) {
                dbServiceImportValue += "." + getDbService().getClassName();
            }

            imports += dbServiceImport.replace("{type}", dbServiceImportValue);
        }

        // DAO imports requirements
        // ...

        setTemplateContent(getTemplateContent().replace("#imports#", imports));
    }

    public void setClassAnnotation() throws Exception {
        JsonElement classAnnotationElement = getControllerData().get("classAnnotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject().get(getControllerType());
        if (classAnnotationElement.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#classAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#classAnnotation#",
                    classAnnotationElement.getAsString().replace("{requestMapping}", getRequestMapping())));
        }
    }

    public void setControllerName() {
        String controllerName = getControllerName();
        setTemplateContent(getTemplateContent().replace("#className#", controllerName));
    }

    public void setExtendsRequirement() throws Exception {
        String extendsDeclaration = Model.getModelData().get("extends").getAsJsonObject().get(getLanguage()).getAsString();
        JsonElement extendsRequirement = getControllerData().get("extendsRequirement").getAsJsonObject()
                .get(getFramework()).getAsJsonObject().get(getControllerType());
        if (extendsRequirement.isJsonNull()) {
            setTemplateContent(getTemplateContent().replace("#extends#", ""));
        } else {
            setTemplateContent(getTemplateContent().replace("#extends#", extendsDeclaration.replace("{type}", extendsRequirement.getAsString())));
        }
    }

    public void setDbServiceAnnotation() throws Exception {
        if (dbService == null) return;
            
        JsonElement dbServiceAnnotation = getControllerData().get("dbServiceAnnotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject().get(getControllerType());
        if (dbServiceAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#dbServiceAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(
                    getTemplateContent().replace("#dbServiceAnnotation#", dbServiceAnnotation.getAsString()));
        }
    }

    public void setDbServiceDeclaration() throws Exception {
        if (dbService == null) return;
            
        JsonElement dbServiceDeclaration = getControllerData().get("dbServiceDeclaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject().get(getControllerType());
        if (dbServiceDeclaration.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#dbServiceDeclaration#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#dbServiceDeclaration#", dbServiceDeclaration.getAsString()
                    .replace("{dbServiceType}", getDbService().getClassName())
                    .replace("{dbServiceFieldName}", getDbService().getFieldName())));
        }
    }

    public void setConstructor() throws Exception {
        if (getDbService() == null) return;
            
        JsonElement constructor = getControllerData().get("constructor").getAsJsonObject().get(getFramework())
                .getAsJsonObject().get(getControllerType());
        if (constructor.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#constructor#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#constructor#", constructor.getAsString()
                .replace("{controllerName}", getControllerName())
                .replace("{dbServiceType}", getDbService().getClassName())
                .replace("{dbServiceFieldName}", getDbService().getFieldName())));
        }
    }

    public void setGetAllAnnotation() throws Exception {
        JsonElement getAllAnnotation = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getAll").getAsJsonObject()
                .get("annotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        if (getAllAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#getAllAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#getAllAnnotation#", getAllAnnotation.getAsString()));
        }
    }

    public void setGetAllReturnType() throws Exception {
        JsonElement getAllReturnType = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getAll").getAsJsonObject()
                .get("returnType").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#getAllReturnType#", getAllReturnType.getAsString().replace("{type}", getModel().getClassName())));
    }

    public void setGetAllDeclaration() throws Exception {
        JsonElement getAllDeclaration = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getAll").getAsJsonObject()
                .get("declaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#getAllDeclaration#", getAllDeclaration.getAsString().replace("{type}", getModel().getClassName())));
    }

    public void setGetAllContent() throws Exception {
        JsonElement getAllContent = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getAll").getAsJsonObject()
                .get("content").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#getAllContent#", getAllContent.getAsString()
                .replace("{dbServiceFieldName}", getDbService().getFieldName())
                .replace("{type}", getModel().getClassName())));
    }

    public void setGetByIdAnnotation() throws Exception {
        JsonElement getByIdAnnotation = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getById").getAsJsonObject()
                .get("annotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        if (getByIdAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#getByIdAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#getByIdAnnotation#", getByIdAnnotation.getAsString())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName())));
        }
    }

    public void setGetByIdReturnType() throws Exception {
        JsonElement getByIdReturnType = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getById").getAsJsonObject()
                .get("returnType").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#getByIdReturnType#",
                getByIdReturnType.getAsString().replace("{type}", getModel().getClassName())));
    }

    public void setGetByIdDeclaration() throws Exception {
        JsonElement getByIdDeclaration = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getById").getAsJsonObject()
                .get("declaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#getByIdDeclaration#", getByIdDeclaration.getAsString()
                .replace("{type}", getModel().getClassName())
                .replace("{pkFieldType}", getModel().getPrimaryKeyFieldType())
                .replace("{pkFieldName}", getModel().getPrimaryKeyFieldName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))));
    }

    public void setGetByIdContent() throws Exception {
        JsonElement getByIdContent = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("getById").getAsJsonObject()
                .get("content").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent()
                .replace("#getByIdContent#", getByIdContent.getAsString())
                .replace("{typeFieldName}", getModel().getFieldName())
                .replace("{dbServiceFieldName}", getDbService().getFieldName())
                .replace("{type}", getModel().getClassName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
        );

    }

    public void setCreateAnnotation() throws Exception {
        JsonElement createAnnotation = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("create").getAsJsonObject()
                .get("annotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        if (createAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#createAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#createAnnotation#", createAnnotation.getAsString()));
        }
    }

    public void setCreateReturnType() throws Exception {
        JsonElement createReturnType = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("create").getAsJsonObject()
                .get("returnType").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#createReturnType#", createReturnType.getAsString()
                .replace("{type}", getModel().getClassName())));
    }

    public void setCreateDeclaration() throws Exception {
        JsonElement createDeclaration = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("create").getAsJsonObject()
                .get("declaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#createDeclaration#", createDeclaration.getAsString()
                .replace("{type}", getModel().getClassName())
                .replace("{parameterName}", WordFormatter.toCamelCase(getModel().getTable().getName()))
                .replace("{typeFieldName}", getModel().getFieldName())));
    }

    public void setCreateContent() throws Exception {
        JsonElement createContent = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("create").getAsJsonObject()
                .get("content").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#createContent#", createContent.getAsString()
                .replace("{dbServiceFieldName}", getDbService().getFieldName())
                .replace("{type}", getModel().getClassName())
                .replace("{typeFieldName}", getModel().getFieldName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
                .replace("{pkFieldName}", getModel().getPrimaryKeyFieldName())
        ));
    }

    public void setUpdateAnnotation() throws Exception {
        JsonElement updateAnnotation = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("update").getAsJsonObject()
                .get("annotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        if (updateAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#updateAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#updateAnnotation#", updateAnnotation.getAsString())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName())));
        }
    }

    public void setUpdateReturnType() throws Exception {
        JsonElement updateReturnType = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("update").getAsJsonObject()
                .get("returnType").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#updateReturnType#", updateReturnType.getAsString()
                .replace("{type}", getModel().getClassName())));
    }

    public void setUpdateDeclaration() throws Exception {
        JsonElement updateDeclaration = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("update").getAsJsonObject()
                .get("declaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#updateDeclaration#", updateDeclaration.getAsString()
                .replace("{pkFieldType}", getModel().getPrimaryKeyFieldType())
                .replace("{pkFieldName}", getModel().getPrimaryKeyFieldName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
                .replace("{type}", getModel().getClassName())
                .replace("{parameterName}", WordFormatter.toCamelCase(getModel().getTable().getName()))));
    }

    public void setUpdateContent() throws Exception {
        JsonElement updateContent = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("update").getAsJsonObject()
                .get("content").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#updateContent#", updateContent.getAsString())
                .replace("{typeFieldName}", getModel().getFieldName())
                .replace("{pkFieldName}", getModel().getPrimaryKeyFieldName())
                .replace("{dbServiceFieldName}", getDbService().getFieldName())
                .replace("{type}", getModel().getClassName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
                .replace("{upperPkFieldName}", WordFormatter.capitalizeFirstLetter(getModel().getPrimaryKeyFieldName())));
    }

    public void setDeleteAnnotation() throws Exception {
        JsonElement deleteAnnotation = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("delete").getAsJsonObject()
                .get("annotation").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        if (deleteAnnotation.isJsonNull()) {
            setTemplateContent(CodeFormatter.removeContainingLine("#deleteAnnotation#", getTemplateContent()));
        } else {
            setTemplateContent(getTemplateContent().replace("#deleteAnnotation#", deleteAnnotation.getAsString())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName())));
        }
    }

    public void setDeleteReturnType() throws Exception {
        JsonElement deleteReturnType = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("delete").getAsJsonObject()
                .get("returnType").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#deleteReturnType#", deleteReturnType.getAsString()
                .replace("{type}", getModel().getClassName())));
    }

    public void setDeleteDeclaration() throws Exception {
        JsonElement deleteDeclaration = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("delete").getAsJsonObject()
                .get("declaration").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#deleteDeclaration#", deleteDeclaration.getAsString()
                .replace("{pkFieldType}", getModel().getPrimaryKeyFieldType())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
                .replace("{type}", getModel().getClassName())));
    }

    public void setDeleteContent() throws Exception {
        JsonElement deleteContent = getControllerData()
                .get("CRUDMethods").getAsJsonObject()
                .get("delete").getAsJsonObject()
                .get("content").getAsJsonObject()
                .get(getFramework()).getAsJsonObject()
                .get(getControllerType());

        setTemplateContent(getTemplateContent().replace("#deleteContent#", deleteContent.getAsString())
                .replace("{typeFieldName}", getModel().getFieldName())
                .replace("{dbServiceFieldName}", getDbService().getFieldName())
                .replace("{type}", getModel().getClassName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName())));
    }

    public void executeDBServiceSetting() throws Exception {
        JsonElement dbServiceElement = DBService.getDBServiceData().get("DAOServiceRequirements").getAsJsonObject().get(getDAO());
        if (!dbServiceElement.isJsonNull()) {
            DBService dbService = new DBService(getModel(), getDAO(), dbServiceElement.getAsString(), getDbServicePackage(), getOutputPath());
            dbService.loadTemplate();
            dbService.generate();
            setDbService(dbService);
        } else {
            CodeFormatter.removeContainingLine("#dbServiceAnnotation#", getTemplateContent());
            CodeFormatter.removeContainingLine("#dbServiceDeclaration#", getTemplateContent());
        }
    }

    public void loadTemplate() throws Exception {
        // get the template content
        String templateContent = FileUtil.toString("./template/controller.template");
        setTemplateContent(templateContent);

        // Execute DB service setting
        executeDBServiceSetting();

        // set all file content by part
        setPackageDeclaration();
        setImportsDeclaration();

        setClassAnnotation();
        setControllerName();

        setExtendsRequirement();

        setDbServiceAnnotation();
        setDbServiceDeclaration();

        setConstructor();

        setGetAllAnnotation();
        setGetAllReturnType();
        setGetAllDeclaration();
        setGetAllContent();

        setGetByIdAnnotation();
        setGetByIdReturnType();
        setGetByIdDeclaration();
        setGetByIdContent();

        setCreateAnnotation();
        setCreateReturnType();
        setCreateDeclaration();
        setCreateContent();

        setUpdateAnnotation();
        setUpdateReturnType();
        setUpdateDeclaration();
        setUpdateContent();

        setDeleteAnnotation();
        setDeleteReturnType();
        setDeleteDeclaration();
        setDeleteContent();

        setTemplateContent(CodeFormatter.formatCode(getTemplateContent()));
    }

    public String getControllerName() {
        return getModel().getClassName() + "Controller";
    }

    public String getFileName() throws Exception {
        String fileExtension = Model.getModelData().get("fileExtension").getAsJsonObject().get(getLanguage())
                .getAsString();
        return getControllerName() + fileExtension;
    }

    public void generate() throws Exception {
        String packagePath = getPackageName().replace(".", "/");
        FileUtil.createFileWithContent(getTemplateContent(), getOutputPath() + packagePath, getFileName());
    }

    public static JsonObject getControllerData() throws Exception {
        return JsonUtil.toJsonObject("/data/controller.json", "IN");
    }
}
