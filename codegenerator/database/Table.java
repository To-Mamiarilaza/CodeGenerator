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

    // find column with the given name
    public Column getColumnWithName(String name) {
        for (Column column : columns) {
            if (column.getName().equals(name)) {
                return column;
            }
        }

        return null;
    }

    // find and set all foreign keys
    public void setAllForeignKeys(Connection connection) throws Exception {
        ResultSet resultSet = null;
        
        try {
            String catalog = null;
            String schemaPattern = "public"; // Nom du schéma
            String tableNamePattern = getName(); // Nom de la table

            resultSet = connection.getMetaData().getImportedKeys(catalog, schemaPattern, tableNamePattern);

            while (resultSet.next()) {    
                String referenceTableName = resultSet.getString("PKTABLE_NAME");
                String referenceColumnName = resultSet.getString("PKCOLUMN_NAME");

                Column targetColumn = getColumnWithName(resultSet.getString("FKCOLUMN_NAME"));
                targetColumn.setForeignKey(new ForeignKey(referenceTableName, referenceColumnName));
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }

    // find all foreign key column
    public List<Column> getForeignKeyColumns() {
        List<Column> fkColumns = new ArrayList<>();
        for (Column column : getColumns()) {
            if (column.getForeignKey() != null) {
                fkColumns.add(column);
            }
        }

        return fkColumns;
    }

    // get the primary key column
    public Column getPrimaryKeyColumn() {
        for (Column column : columns) {
            if (column.isPrimaryKey) {
                return column;
            }
        }
        return null;    
    }

    // find and set the primary key
    public void setPrimaryKey(Connection connection) throws Exception {
        ResultSet resultSet = null;
        
        try {
            String catalog = null;
            String schemaPattern = "public"; // Nom du schéma
            String tableNamePattern = getName(); // Nom de la table

            resultSet = connection.getMetaData().getPrimaryKeys(catalog, schemaPattern, tableNamePattern);

            while (resultSet.next()) {    
                String columnName = resultSet.getString("COLUMN_NAME");

                Column targetColumn = getColumnWithName(columnName);
                if (targetColumn != null) {
                    targetColumn.setIsPrimaryKey(true);
                }
            }
            
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
    
    public void loadColumns(Connection connection) throws Exception {
        List<Column> columnList = new ArrayList<>();
       

        ResultSet resultSet = null;
        
        try {
            String catalog = null;
            String schemaPattern = "public"; // Nom du schéma
            String tableNamePattern = getName(); // Nom de la table
            String columnNamePattern = null;

            resultSet = connection.getMetaData().getColumns(catalog, schemaPattern, tableNamePattern, columnNamePattern);

            while (resultSet.next()) {    
                String columnName = resultSet.getString("COLUMN_NAME");
                String type = resultSet.getString("TYPE_NAME");

                Column newColumn = new Column(columnName, type, false, null, this);
                columnList.add(newColumn);
            }
            setColumns(columnList);

            // find the primary key
            setPrimaryKey(connection);
            
            // set all foreign keys
            setAllForeignKeys(connection);
            
        } catch (Exception e) {
            throw e;
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
        }
    }
    
}
