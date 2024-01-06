/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package codegenerator.database;

/**
 *
 * @author to
 */
public class ForeignKey {
    /// Field
    String tableName;
    String columnName;
    
    /// Getter and Setter

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }
    
    /// Constructor

    public ForeignKey(String tableName, String columnName) {
        this.tableName = tableName;
        this.columnName = columnName;
    }
    
}
