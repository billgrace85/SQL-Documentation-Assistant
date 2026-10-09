//Represents a database table.
// Contains table name, estimated row count,
//and a list of columns.

import java.util.ArrayList;
import java.util.List;

public class Table {

    private String tableName;
    private int rowCount;
    private List<Column> columns;

    public Table(String tableName, int rowCount) {
        this.tableName = tableName;
        this.rowCount = rowCount;
        this.columns = new ArrayList<>();
    }

    public void addColumn(Column col) {
        columns.add(col);
    }
    //Displays the structure of the table,
  //including columns and constraints.

    public void displayStructure() {
        System.out.println("\n═══════════════════════════════════");
        System.out.println("TABLE: " + tableName);
        System.out.println("Rows (estimated): " + rowCount);
        System.out.println("═══════════════════════════════════");
        System.out.printf("%-20s %-20s %s\n", "Column", "Type", "Details");
        System.out.println("────────────────────────────────────────");

        for (Column col : columns) {
            System.out.println(col.getColumnDetails());
            if (!col.getDescription().isEmpty()) {
                System.out.println("   -> " + col.getDescription());
            }
        }
    }

    public String getTableName() {
        return tableName;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public int getRowCount() {
        return rowCount;
    }
}
