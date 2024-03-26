package codegenerator.view;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import codegenerator.CodeGenerator;
import codegenerator.database.Column;
import codegenerator.database.Table;
import codegenerator.model.Model;
import codegenerator.util.CodeFormatter;
import codegenerator.util.FileUtil;
import codegenerator.util.WordFormatter;

public class View {
    private Model model;
    private Table table;
    private String choice;
    private String fileName;
    private String viewPackage;
    private String htmlRequirement;
    private String pageTitle;
    private String formAction;
    private String formInput;
    private String errorDiv;
    private String tableHead;
    private String tableBody;
    private String linkList;
    private JsonObject data;
    private String outputPath;
    private String pageImport;
    private String templateContent;
    private String listPageTemplateContent;
    private String createPageTemplateContent;
    private String updatePageTemplateContent;
    private String apiUrl;
    private CodeGenerator codeGenerator; // For getting the FK model

    public View(Model model, String choice, String viewPackage, String outputPath, JsonObject data,
            CodeGenerator codeGenerator, String apiUrl) throws Exception {
        setCodeGenerator(codeGenerator);
        setModel(model);
        setTable(model.getTable());
        setChoice(choice);
        setViewPackage(viewPackage);
        setData(data);
        setOutputPath(outputPath);
        setFileName();
        setHtmlRequirement();
        setPageTitle();
        setFormAction();
        setTableHead();
        setTableBody();
        setFormInput();
        setPageImport();
        setErrorDiv();
        setLinkList();
        setApiUrl(apiUrl);
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }

    public void setCodeGenerator(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public String getViewPackage() {
        return viewPackage;
    }

    public void setViewPackage(String viewPackage) {
        this.viewPackage = viewPackage;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getListPageTemplateContent() {
        return listPageTemplateContent;
    }

    public void setListPageTemplateContent(String listPageTemplateContent) {
        this.listPageTemplateContent = listPageTemplateContent;
    }

    public String getCreatePageTemplateContent() {
        return createPageTemplateContent;
    }

    public void setCreatePageTemplateContent(String createPageTemplateContent) {
        this.createPageTemplateContent = createPageTemplateContent;
    }

    public String getUpdatePageTemplateContent() {
        return updatePageTemplateContent;
    }

    public void setUpdatePageTemplateContent(String updatePageTemplateContent) {
        this.updatePageTemplateContent = updatePageTemplateContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName() {
        String fileExtension = getData().get("page").getAsJsonObject().get("fileExtension").getAsJsonObject()
                .get(getChoice()).getAsString();
        String fileName = WordFormatter.toCamelCase(getModel().getClassName()) + fileExtension;
        this.fileName = fileName;
    }

    public String getPageFileName(String targetPage) {
        String fileExtension = getData().get("page").getAsJsonObject().get("fileExtension").getAsJsonObject()
                .get(getChoice()).getAsString();
        String fileName = getData().get("page").getAsJsonObject().get("fileName")
                .getAsJsonObject().get(targetPage).getAsJsonObject().get(getChoice()).getAsString()
                .replace("{typeFieldName}", WordFormatter.firstLetterToLower(getModel().getClassName()))
                .replace("{className}", getModel().getClassName());
        return fileName + fileExtension;
    }

    public String getSelfPackaging() {
        String packagingCase = getData().get("page").getAsJsonObject().get("packagingCase").getAsJsonObject()
                .get(getChoice()).getAsString();
        String selfPackaging = getModel().getClassName();
        if (packagingCase.equals("LOWER")) {
            selfPackaging = WordFormatter.firstLetterToLower(selfPackaging);
        }
        return selfPackaging;
    }

    public String getPageRequirement(String targetPage) {
        String pageRequirement = getData().get("page").getAsJsonObject().get("pageRequirement").getAsJsonObject()
                .get(targetPage).getAsJsonObject()
                .get(getChoice()).getAsString();
        String className = WordFormatter.capitalizeFirstLetter(getModel().getClassName());
        pageRequirement = pageRequirement.replace("{className}", className);

        return pageRequirement;
    }

    public String getHtmlRequirement() {
        return htmlRequirement;
    }

    public void setHtmlRequirement() {
        String htmlRequirement = getData().get("page").getAsJsonObject().get("htmlRequirement").getAsJsonObject()
                .get(getChoice()).getAsString();
        ;
        this.htmlRequirement = htmlRequirement;
    }

    public void setErrorDiv() {
        String errorDiv = getData().get("page").getAsJsonObject().get("errorDiv").getAsJsonObject()
        .get(getChoice()).getAsString();
        this.errorDiv = errorDiv;
    }

    public String getErrorDiv() {
        return errorDiv;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle() {
        String pageTitle = WordFormatter.toSpacedUpperCase(getModel().getClassName());
        this.pageTitle = pageTitle;
    }

    public void setLinkList() {
        String linkList = "";

        String linkListDeclaration = getData().get("page").getAsJsonObject().get("linkList").getAsJsonObject()
        .get(getChoice()).getAsString();

        for (Model model : getCodeGenerator().getModels()) {
            linkList += linkListDeclaration.replace("{type}", model.getClassName())
            .replace("{typeFieldName}", WordFormatter.firstLetterToLower(model.getClassName()))
            .replace(("{entityName}"), WordFormatter.toSpacedUpperCase(model.getTable().getName()));
        }


        this.linkList = linkList;
    }

    public String getLinkList() {
        return linkList;
    }

    public String getDescription(String targetPage) {
        String listDescription = getData().get("page").getAsJsonObject().get("description").getAsJsonObject()
                .get(targetPage).getAsString()
                .replace("{typeFieldName}", WordFormatter.firstLetterToLower(getModel().getClassName()));
        return listDescription;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction() {
        this.formAction = getData().get("page").getAsJsonObject().get("updateLink")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{typeFieldName}", WordFormatter.firstLetterToLower(getModel().getClassName()))
                .replace("{className}", getModel().getClassName());
    }

    public String getFormInput() {
        return formInput;
    }

    public void setFormInput() throws Exception {
        String formInput = "";
        String fieldCase = Model.getModelData().get("fieldCase").getAsJsonObject().get(getModel().getLanguage())
                .getAsString();

        for (Column c : getTable().getColumns()) {
            if (c.getForeignKey() != null) {
                String formSelectDeclaration = getData().get("page").getAsJsonObject().get("formSelect")
                        .getAsJsonObject().get(getChoice()).getAsString();

                String fieldName = WordFormatter.toCamelCase(c.getForeignKey().getTableName());
                String upperFieldName = WordFormatter.capitalizeFirstLetter(fieldName);
                String fieldPk = getCodeGenerator().getModelWithName(c.getForeignKey().getTableName())
                        .getPrimaryKeyFieldName();
                String upperFieldPk = WordFormatter.capitalizeFirstLetter(fieldPk);
                String fieldFkDisplay = getCodeGenerator().getModelWithName(c.getForeignKey().getTableName())
                        .getDisplayField();

                formInput += formSelectDeclaration
                        .replace("{fieldName}", fieldName)
                        .replace("{lowerFieldName}", WordFormatter.firstLetterToLower(fieldName))
                        .replace("{upperFieldName}", upperFieldName)
                        .replace("{upperFieldPk}", upperFieldPk)
                        .replace("{fieldFkDisplay}", fieldFkDisplay)
                        .replace("{className}", getModel().getClassName())
                        .replace("{fieldPk}", fieldPk);
            } else if (!c.getIsPrimaryKey()) {
                String type = getData().get("inputMapping").getAsJsonObject().get(c.getType()).getAsString();

                String inputType = "other";
                if (type.equals("checkbox")) {
                    inputType = "checkbox";
                }

                String formGroupDeclaration = getData().get("page").getAsJsonObject().get("formGroup").getAsJsonObject()
                        .get(inputType).getAsJsonObject().get(getChoice()).getAsString();

                // Case checking
                String fieldName = WordFormatter.toCamelCase(c.getName());
                if (fieldCase.equals("UPPER")) {
                    fieldName = WordFormatter.capitalizeFirstLetter(fieldName);
                }

                formInput += formGroupDeclaration
                        .replace("{fieldName}", fieldName)
                        .replace("{lowerFieldName}", WordFormatter.firstLetterToLower(fieldName))
                        .replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(fieldName))
                        .replace("{fieldInputType}", type);
            }
        }
        this.formInput = formInput;
    }

    public String getTableHead() {
        return tableHead;
    }

    public void setTableHead() {
        String tableHead = "";
        for (Column c : getTable().getColumns()) {
            String th = WordFormatter.toSpacedUpperCase(c.getName());
            if (c.getForeignKey() != null) {
                th = WordFormatter.toSpacedUpperCase(c.getForeignKey().getTableName());
            }
            tableHead += "\n\t\t\t\t\t\t<th>" + th + "</th>";
        }
        tableHead += "\n\t\t\t\t\t\t<th></th>";
        this.tableHead = tableHead;
    }

    public String getTableBody() {
        return tableBody;
    }

    public void setTableBody() throws Exception {
        String variableName = getModel().getFieldName();

        String fieldCase = Model.getModelData().get("fieldCase").getAsJsonObject().get(getModel().getLanguage()).getAsString();

        if (fieldCase.equals("UPPER")) {
            variableName = WordFormatter.capitalizeFirstLetter(variableName);
        }

        String dataName = variableName + "s";
        String tr = getData().get("page").getAsJsonObject().get("tableLooping").getAsJsonObject()
                .get("tr").getAsJsonObject().get(getChoice()).getAsString();
        tr = tr.replace("{variableName}", variableName);
        tr = tr.replace("dataName", dataName);
        tr = tr.replace("{typeFieldName}", getModel().getFieldName());
        tr = tr.replace("{pkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()));
        String td = getData().get("page").getAsJsonObject().get("tableLooping").getAsJsonObject()
                .get("td").getAsJsonObject().get(getChoice()).getAsString();
        String tds = "";
        for (Column c : getTable().getColumns()) {
            String fieldName = WordFormatter.toCamelCase(c.getName());

            if (c.getForeignKey() != null) {
                String fkTypeField = WordFormatter.toCamelCase(c.getForeignKey().getTableName());
                String fkDisplayField = getCodeGenerator().getModelWithName(c.getForeignKey().getTableName())
                        .getDisplayField();

                fieldName = WordFormatter.firstLetterToLower(fkTypeField) + "." + WordFormatter.firstLetterToLower(fkDisplayField);
            }

            String tdCopy = td;
            tdCopy = tdCopy.replace("{fieldName}", fieldName);
            tdCopy = tdCopy.replace("{typeFieldName}", getModel().getFieldName());
            tdCopy = tdCopy.replace("{variableName}", variableName);
            tds += tdCopy;
        }

        tds += getData().get("page").getAsJsonObject().get("listAction")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{variableName}", variableName)
                .replace("{pkFieldName}", getModel().getPrimaryKeyFieldName())
                .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()))
                .replace("{typeFieldName}", getModel().getFieldName())
                .replace("{className}", getModel().getClassName());

        tr = tr.replace("{td}", tds);
        this.tableBody = tr;
    }

    public void setData(JsonObject data) {
        this.data = data;
    }

    public JsonObject getData() {
        return data;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    public void setPageImport() {
        String pageImport = getData().get("page").getAsJsonObject()
                .get("pageImport").getAsJsonObject().get(getChoice()).getAsString()
                .replace("{modelPackage}", getModel().getPackageName());
        this.pageImport = pageImport;
    }

    public String getPageImport() {
        return this.pageImport;
    }

    public String getCreateNewLink() {
        return getData().get("page").getAsJsonObject().get("createNewLink")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{typeFieldName}", WordFormatter.firstLetterToLower(getModel().getClassName()))
                .replace("{className}", getModel().getClassName());
    }

    public String getCreateNewDescription() {
        return "Nouvelle " + WordFormatter.firstLetterToLower(getModel().getClassName());
    }

    public String getBackLink() {
        return getData().get("page").getAsJsonObject().get("backLink")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{typeFieldName}", WordFormatter.firstLetterToLower(getModel().getClassName()))
                .replace("{className}", getModel().getClassName());
    }

    public String getIdHiddenInput() throws Exception {
        return getData().get("page").getAsJsonObject().get("idHiddenInput")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{fieldName}", getModel().getPrimaryKeyFieldName());
    }

    public String getFormRequirements() throws Exception {
        return getData().get("page").getAsJsonObject().get("formRequirements")
                .getAsJsonObject().get(getChoice()).getAsString()
                .replace("{variableName}", WordFormatter.firstLetterToLower(getModel().getClassName()));
    }

    public String getFkSelectOptionsState() {
        String selectOptions = "";

        String declaration = getData().get("page").getAsJsonObject().get("fkSelectOptionsState").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }
        List<Column> columns = getModel().getTable().getForeignKeyColumns();
        for (Column column : columns) {
            String fieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
            selectOptions += declaration.replace("{fieldName}", fieldName).replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(fieldName));
        }

        return selectOptions;
    }

    public String getFieldValueState() {
        String fieldValueStates = "";

        String declaration = getData().get("page").getAsJsonObject().get("fieldValueState").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }
        List<Column> columns = getModel().getTable().getColumns();
        for (Column column : columns) {
            String fieldName = WordFormatter.toCamelCase(column.getName());
            fieldValueStates += declaration.replace("{fieldName}", fieldName).replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(fieldName));
        }

        return fieldValueStates;
    }

    public String getFkElementsFetching() throws Exception {
        String fkElementsFetching = "";

        String declaration = getData().get("page").getAsJsonObject().get("fkElementsFetching").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }

        List<Column> columns = getModel().getTable().getForeignKeyColumns();
        for (Column column : columns) {
            String fkFieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
            // the pk of the foreign key
            String pkFieldName = getCodeGenerator().getModelWithName(column.getForeignKey().getTableName()).getPrimaryKeyFieldName();

            fkElementsFetching += declaration.replace("{fieldName}", fkFieldName)
            .replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(fkFieldName))
            .replace("{pkFieldName}", WordFormatter.firstLetterToLower(pkFieldName))
            .replace("{upperPkFieldName}", WordFormatter.capitalizeFirstLetter(pkFieldName));
        }

        return fkElementsFetching;
    }

    public String getFieldValueSetting() throws Exception {
        String fieldValueSetting = "";

        String declaration = getData().get("page").getAsJsonObject().get("fieldValueSetting").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }

        List<Column> columns = getModel().getTable().getColumns();
        for (Column column : columns) {
            String upperFieldName = WordFormatter.toCamelCase(column.getName());
            String fieldName = WordFormatter.toCamelCase(column.getName());

            if (column.getForeignKey() != null) {
                fieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName()) + "." + fieldName;
            }

            fieldValueSetting += declaration.replace("{fieldName}", fieldName)
            .replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(upperFieldName));
        }

        return fieldValueSetting;
    }

    public String getHandleFkSelectOptionsChange() throws Exception {
        String handleFkSelectOptionsChange = "";

        String declaration = getData().get("page").getAsJsonObject().get("handleFkSelectOptionsChange").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }

        List<Column> columns = getModel().getTable().getForeignKeyColumns();
        for (Column column : columns) {
            String fkFieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
            // the pk of the foreign key
            String pkFieldName = getCodeGenerator().getModelWithName(column.getForeignKey().getTableName()).getPrimaryKeyFieldName();

            handleFkSelectOptionsChange += declaration.replace("{fieldName}", fkFieldName)
            .replace("{upperFieldName}", WordFormatter.capitalizeFirstLetter(fkFieldName))
            .replace("{upperPkFieldName}", WordFormatter.capitalizeFirstLetter(pkFieldName));
        }

        return handleFkSelectOptionsChange;
    }

    public String getObjectJsonTemplate() throws Exception {
        String objectJsonTemplate = "";

        String declaration = getData().get("page").getAsJsonObject().get("objectJsonTemplate").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }

        List<Column> columns = getModel().getTable().getColumns();
        for (Column column : columns) {
            String fieldNameKey = WordFormatter.toCamelCase(column.getName());
            String fieldNameValue = WordFormatter.toCamelCase(column.getName());

            if (column.getForeignKey() != null) {
                fieldNameKey = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
                String foreignPk = getCodeGenerator().getModelWithName(column.getForeignKey().getTableName()).getPrimaryKeyFieldName();
                foreignPk = WordFormatter.firstLetterToLower(foreignPk);
                fieldNameValue = "{ \n        \"" + foreignPk + "\": " + foreignPk + "\n      }";
            }

            objectJsonTemplate += declaration.replace("{fieldNameKey}", fieldNameKey)
            .replace("{fieldNameValue}", fieldNameValue);
        }

        objectJsonTemplate = objectJsonTemplate.substring(0, objectJsonTemplate.length() - 1);
        return objectJsonTemplate;
    }

    public String getFkOptionsRowDisplay() throws Exception {
        String fkOptionsRowDisplay = "";

        String declaration = getData().get("page").getAsJsonObject().get("fkOptionsRowDisplay").getAsJsonObject().get(getChoice()).getAsString();
        if (declaration.equals("")) {
            return "";
        }
        
        List<Column> columns = getModel().getTable().getForeignKeyColumns();
        for (Column column : columns) {
            String fkFieldName = WordFormatter.toCamelCase(column.getForeignKey().getTableName());
            // the pk of the foreign key
            String pkFieldName = getCodeGenerator().getModelWithName(column.getForeignKey().getTableName()).getPrimaryKeyFieldName();
            String fkDisplayField = getCodeGenerator().getModelWithName(column.getForeignKey().getTableName()).getDisplayField();

            fkOptionsRowDisplay += declaration.replace("{fieldName}", fkFieldName)
            .replace("{fkDisplayField}", WordFormatter.firstLetterToLower(fkDisplayField))
            .replace("{pkFieldName}", WordFormatter.firstLetterToLower(pkFieldName))
            .replace("{lowerPkFieldName}", WordFormatter.firstLetterToLower(pkFieldName));
        }

        return fkOptionsRowDisplay;
    }

    public void loadListPageTemplate() throws Exception {
        String templateName = getData().get("page").getAsJsonObject().get("listTemplate").getAsJsonObject().get(getChoice()).getAsString();
        String listContent = FileUtil.toStringInnerFile("/template/view/" + templateName);
        listContent = listContent.replace("#pageImport#", getPageImport());
        listContent = listContent.replace("#pageRequirement#", getPageRequirement("list"));
        listContent = listContent.replace("#htmlRequirement#", getHtmlRequirement());
        listContent = listContent.replace("#createNewLink#", getCreateNewLink());
        listContent = listContent.replace("#createNewDescription#", getCreateNewDescription());
        listContent = listContent.replace("#description#", getDescription("list"));
        listContent = listContent.replace("#pageTitle#", getPageTitle());
        listContent = listContent.replace("#tableHead#", getTableHead());
        listContent = listContent.replace("#tableBody#", getTableBody());
        listContent = listContent.replace("#linkList#", getLinkList());
        listContent = listContent.replace("#className#", getModel().getClassName());
        listContent = listContent.replace("#typeFieldName#", getModel().getFieldName());
        listContent = listContent.replace("#typeFieldNameUrl#", getModel().getFieldNameUrl());
        listContent = listContent.replace("#pkFieldName#", getModel().getPrimaryKeyFieldName());
        listContent = listContent.replace("#lowerPkFieldName#", WordFormatter.firstLetterToLower(getModel().getPrimaryKeyFieldName()));
        listContent = listContent.replace("#apiUrl#", getApiUrl());

        setListPageTemplateContent(listContent);
    }

    public void loadCreatePageTemplate() throws Exception {
        String templateName = getData().get("page").getAsJsonObject().get("createTemplate").getAsJsonObject().get(getChoice()).getAsString();
        String createContent = FileUtil.toStringInnerFile("/template/view/" + templateName);
        createContent = createContent.replace("#pageImport#", getPageImport());
        createContent = createContent.replace("#pageRequirement#", getPageRequirement("edit"));
        createContent = createContent.replace("#htmlRequirement#", getHtmlRequirement());
        createContent = createContent.replace("#description#", getDescription("create"));
        createContent = createContent.replace("#pageTitle#", getPageTitle());
        createContent = createContent.replace("#formAction#", getFormAction());
        createContent = createContent.replace("#formInput#", getFormInput());
        createContent = createContent.replace("#backLink#", getBackLink());
        createContent = createContent.replace("#idHiddenInput#", getIdHiddenInput());
        createContent = createContent.replace("#formRequirements#", getFormRequirements());
        createContent = createContent.replace("#errorDiv#", getErrorDiv());
        createContent = createContent.replace("#className#", getModel().getClassName());
        createContent = createContent.replace("#typeFieldName#", getModel().getFieldName());
        createContent = createContent.replace("#typeFieldNameUrl#", getModel().getFieldNameUrl());

        // For front end separated technology like react
        createContent = createContent.replace("#fkSelectOptionsState#", getFkSelectOptionsState());
        createContent = createContent.replace("#fieldValueState#", getFieldValueState());
        createContent = createContent.replace("#fkElementsFetching#", getFkElementsFetching());
        createContent = createContent.replace("#fieldValueSetting#", getFieldValueSetting());
        createContent = createContent.replace("#handleFkSelectOptionsChange#", getHandleFkSelectOptionsChange());
        createContent = createContent.replace("#objectJsonTemplate#", getObjectJsonTemplate());
        createContent = createContent.replace("#apiUrl#", getApiUrl());
        createContent = createContent.replace("#fkOptionsRowDisplay#", getFkOptionsRowDisplay());

        setCreatePageTemplateContent(createContent);
    }

    public void loadTemplate() throws Exception {
        loadListPageTemplate();
        loadCreatePageTemplate();
    }

    public void generate() {
        // generating list page
        FileUtil.createFileWithContent(getListPageTemplateContent(),
                getOutputPath() + "/" + getViewPackage() + "/" + getSelfPackaging(), getPageFileName("list"));
        FileUtil.createFileWithContent(getCreatePageTemplateContent(),
                getOutputPath() + "/" + getViewPackage() + "/" + getSelfPackaging(), getPageFileName("create"));
    }
}
