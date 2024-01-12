package codegenerator.view;

import com.google.gson.JsonObject;

import codegenerator.database.Column;
import codegenerator.database.Table;
import codegenerator.util.FileUtil;
import codegenerator.util.WordFormatter;

public class View {
    private Table table;
    private String choice;
    private String fileName;
    private String pageRequirement;
    private String htmlRequirement;
    private String pageTitle;
    private String formAction;
    private String formInput;
    private String tableHead;
    private String tableBody;
    private JsonObject data;
    private String outputPath;

    public View(Table table, String choice, String outputPath, JsonObject data) {
        setTable(table);
        setChoice(choice);
        setData(data);
        setOutputPath(outputPath);
        setFileName();
        setPageRequirement();
        setHtmlRequirement();
        setPageTitle();
        setFormAction();
        setTableHead();
        setTableBody();
        setFormInput();
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName() {
        String fileExtension = getData().get("page").getAsJsonObject().get("fileExtension").getAsJsonObject()
                .get(getChoice()).getAsString();
        String fileName = WordFormatter.toCamelCase(getTable().getName()) + fileExtension;
        this.fileName = fileName;
    }

    public String getPageRequirement() {
        return pageRequirement;
    }

    public void setPageRequirement() {
        String pageRequirement = getData().get("page").getAsJsonObject().get("pageRequirement").getAsJsonObject()
                .get(getChoice()).getAsString();
        String className = WordFormatter.capitalizeFirstLetter(WordFormatter.toCamelCase(getTable().getName()));
        pageRequirement = pageRequirement.replace("{className}", className);
        this.pageRequirement = pageRequirement;
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

    public String getPageTitle() {
        return pageTitle;
    }

    public void setPageTitle() {
        String pageTitle = WordFormatter.toSpacedUpperCase(getTable().getName());
        this.pageTitle = pageTitle;
    }

    public String getFormAction() {
        return formAction;
    }

    public void setFormAction() {
        String formAction = "/" + WordFormatter.toCamelCase(getTable().getName());
        this.formAction = formAction;
    }

    public String getFormInput() {
        return formInput;
    }

    public void setFormInput() {
        String formInput = "";
        for (Column c : getTable().getColumns()) {
            if (!c.getIsPrimaryKey()) {
                String id = c.getName().replaceAll("_","-");
                String type = getData().get("inputMapping").getAsJsonObject().get(c.getType()).getAsString();
                String name = c.getName();
                String label = WordFormatter.toSpacedUpperCase(name);
                String input = "\n\t\t\t<label for=\""+id+"\">"+label+":</label>\n\t\t\t<input id=\""+id+"\" type=\"" + type + "\" name=\"" + name + "\">";
                formInput += input;
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
            tableHead += "\n\t\t\t\t\t<th>" + th + "</th>";
        }
        this.tableHead = tableHead;
    }

    public String getTableBody() {
        return tableBody;
    }

    public void setTableBody() {
        String variableName = WordFormatter.toCamelCase(getTable().getName());
        String dataName = variableName + "s";
        String tr = getData().get("page").getAsJsonObject().get("tableLooping").getAsJsonObject()
                .get("tr").getAsJsonObject().get(getChoice()).getAsString();
        tr = tr.replace("{variableName}", variableName);
        tr = tr.replace("dataName", dataName);
        String td = getData().get("page").getAsJsonObject().get("tableLooping").getAsJsonObject()
                .get("td").getAsJsonObject().get(getChoice()).getAsString();
        String tds = "";
        for (Column c : getTable().getColumns()) {
            String fieldNameNotCapitalize = WordFormatter.toCamelCase(c.getName());
            String fieldNameCapitalize = WordFormatter.capitalizeFirstLetter(fieldNameNotCapitalize);
            String tdCopy = td;
            tdCopy = tdCopy.replace("{fieldNameNotCapitalize}", fieldNameNotCapitalize);
            tdCopy = tdCopy.replace("{fieldNameCapitalize}", fieldNameCapitalize);
            tdCopy = tdCopy.replace("{variableName}", variableName);
            tds += tdCopy;
        }
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

    public void generate() throws Exception{
        String templateContent = FileUtil.toString("./template/view.template");
        templateContent = templateContent.replace("#pageRequirement#", getPageRequirement());
        templateContent = templateContent.replace("#htmlRequirement#", getHtmlRequirement());
        templateContent = templateContent.replace("#pageTitle#", getPageTitle());
        templateContent = templateContent.replace("#formAction#", getFormAction());
        templateContent = templateContent.replace("#formInput#", getFormInput());
        templateContent = templateContent.replace("#tableHead#", getTableHead());
        templateContent = templateContent.replace("#tableBody#", getTableBody());
        FileUtil.createFileWithContent(templateContent, getOutputPath() + "/views/", getFileName());
    }
}
