/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author to
 */
public class Column {
    /// Field
    String name;
    String type;
    Boolean isPrimaryKey;
    ForeignKey foreignKey;
    Table table;
    
    /// Getter and Setter

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    public ForeignKey getForeignKey() {
        return foreignKey;
    }

    public void setForeignKey(ForeignKey foreignKey) {
        this.foreignKey = foreignKey;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
    
    /// Constructor

    public Column(String name, String type, Boolean isPrimaryKey, ForeignKey foreignKey, Table table) {
        this.name = name;
        this.type = type;
        this.isPrimaryKey = isPrimaryKey;
        this.foreignKey = foreignKey;
        this.table = table;
    }

    /// methods

    
    
}
