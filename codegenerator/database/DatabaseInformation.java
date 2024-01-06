/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.database;

import codegenerator.util.JsonUtil;
import com.google.gson.JsonObject;
import java.sql.Statement;
import java.sql.ResultSet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author to
 */
public class DatabaseInformation {

    /// Field
    String name;
    String host;
    String user;
    String password;
    String type;
    List<Table> tables;

    /// Getter and Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    /// Constructor
    public DatabaseInformation(String name, String host, String user, String password, String type) {
        this.name = name;
        this.host = host;
        this.user = user;
        this.password = password;
        this.type = type;
    }

    /// methods
    
    // load all tables from database
    public void fetchInformations() throws Exception {
        if (getTables() == null) {
            loadTables();
        }
    }
    
    public void loadTables() throws Exception {
        List<Table> tableList = new ArrayList<>();
        String query = getDatabaseData().get("information").getAsJsonObject().get(this.type).getAsJsonObject().get("tables").getAsString();
        
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            connection = getConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {   
                Table table = new Table(resultSet.getString("table_name"), this);
                table.loadColumns(connection);
                tableList.add(table);
            }
            
            setTables(tableList);
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }
    
    public JsonObject getDatabaseData() throws Exception {
        return JsonUtil.toJsonObject("./data/database.json");
    }
    
    public Connection getConnection() throws Exception {
        String urlPrefix = getDatabaseData().get("information").getAsJsonObject().get(this.type).getAsJsonObject().get("urlPrefix").getAsString();
        Connection connection = DriverManager.getConnection("jdbc:" + urlPrefix + "://localhost:5432/" + this.getName(), this.getUser(), this.getPassword());
        return connection;
    }

}
