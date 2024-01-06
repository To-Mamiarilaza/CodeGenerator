/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.database;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author to
 */
public class Table {
    /// Field
    String name;
    List<Column> columns;
    DatabaseInformation databaseInformation;
    
    /// Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public DatabaseInformation getDatabaseInformation() {
        return databaseInformation;
    }

    public void setDatabaseInformation(DatabaseInformation databaseInformation) {
        this.databaseInformation = databaseInformation;
    }
    
    /// Constructor

    public Table(String name, DatabaseInformation databaseInformation) {
        this.name = name;
        this.databaseInformation = databaseInformation;
    }
    
    /// Methods
    
    public void loadColumns(Connection connection) throws Exception {
        List<Column> columnList = new ArrayList<>();
       
        String query = getDatabaseInformation().getDatabaseData().get("information").getAsJsonObject().get(getDatabaseInformation().getType()).getAsJsonObject().get("columns").getAsString();
        query = query.replace("{tableName}", this.getName());
        
        Statement statement = null;
        ResultSet resultSet = null;
        
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
            
            while (resultSet.next()) {    
                String columnName = resultSet.getString(1);
                String type = resultSet.getString(2);
                Boolean isPrimaryKey = resultSet.getString(3) != null;
                
                columnList.add(new Column(columnName, type, isPrimaryKey, null, this));
            }
            setColumns(columnList);
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
        }
    }
    
}
