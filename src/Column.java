//Represents a database column.
// Stores metadata such as data type, constraints,
//and descriptive information.
public class Column {

    private String name;
    private String dataType;
    private boolean primaryKey;
    private boolean foreignKey;
    private boolean nullable;
    private String description;

    public Column(String name, String dataType, boolean pk, boolean fk, boolean nullable) {
        this.name = name;
        this.dataType = dataType;
        this.primaryKey = pk;
        this.foreignKey = fk;
        this.nullable = nullable;
        this.description = "";
    }

    public void setDescription(String desc) {
        this.description = desc == null ? "" : desc;
    }
    //Returns a formatted string describing the column
    // and its constraints.
    public String getColumnDetails() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-20s %-20s", name, dataType));
        if (primaryKey) sb.append(" [PK]");
        if (foreignKey) sb.append(" [FK]");
        if (!nullable) sb.append(" NOT NULL");
        return sb.toString();
    }

    public String getName() { return name; }

    public String getDescription() { return description; }
}
