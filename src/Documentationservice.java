import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

//Manages generation, storage, annotation,
 //and export of table documentation.
public class Documentationservice {

    private Map<String, String> docsByTable = new HashMap<>();
    private Map<String, List<String>> annotationsByTable = new HashMap<>();
    private String version = "1.0.0";

    // Generate docs for all tables
    public void generateDocumentation(List<Table> tables) {
        for (Table t : tables) {
            String doc = buildDocForTable(t);
            docsByTable.put(t.getTableName(), doc);
            annotationsByTable.putIfAbsent(t.getTableName(), new ArrayList<>());
        }
    }
     //the Documentation format
    private String buildDocForTable(Table t) {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        sb.append("=== Documentation: ").append(t.getTableName()).append(" ===\n");
        sb.append("Version: ").append(version).append("\n");
        sb.append("Generated: ").append(LocalDateTime.now().format(df)).append("\n");
        sb.append("Row estimate: ").append(t.getRowCount()).append("\n");
        sb.append("Columns:\n");
        for (Column c : t.getColumns()) {
            sb.append(" - ").append(c.getName()).append(" (").append(c.getColumnDetails()).append(")\n");
            if (!c.getDescription().isEmpty()) sb.append("   Description: ").append(c.getDescription()).append("\n");
        }
        List<String> notes = annotationsByTable.getOrDefault(t.getTableName(), Collections.emptyList());
        if (!notes.isEmpty()) {
            sb.append("Annotations:\n");
            for (String n : notes) sb.append(" * ").append(n).append("\n");
        }
        sb.append("====================================\n");
        return sb.toString();
    }
     //Adding comment from the user
    public void addAnnotation(String tableName, String note) {
        annotationsByTable.putIfAbsent(tableName, new ArrayList<>());
        String entry = "[" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + "] " + note;
        annotationsByTable.get(tableName).add(entry);
        // regenerate doc if present
        if (docsByTable.containsKey(tableName)) {
            // For simplicity, do not rebuild full docs here; instead inform user to regenerate if needed
            System.out.println("Commenting saved for " + tableName + ". You may regenerate docs to include annotations in the text output.");
        } else {
            System.out.println("Commenting saved for " + tableName + ".");
        }
    }
    /**
     * Export documentation for a single table into a .txt file.
     */
    public void exportSingle(String tableName) {
        if (!docsByTable.containsKey(tableName)) {
            System.out.println("No documentation found for " + tableName + ".");
            return;
        }

        String filename = tableName + "_documentation.txt";
        String content = docsByTable.get(tableName);

        try {
            java.nio.file.Files.write(
                    java.nio.file.Paths.get(filename),
                    content.getBytes()
            );
            System.out.println("Exported: " + filename);

        } catch (Exception e) {
            System.out.println("Error exporting file: " + e.getMessage());
        }
    }

    /**
     * Export all generated documentation into individual .txt files.
     */
    public void exportAll() {
        if (docsByTable.isEmpty()) {
            System.out.println("No documentation to export.");
            return;
        }

        for (String tableName : docsByTable.keySet()) {
            exportSingle(tableName);
        }
    }


    public void listDocumentedTables() {
        if (docsByTable.isEmpty()) {
            System.out.println("No documentation generated yet.");
            return;
        }
        System.out.println("Documented tables:");
        for (String t : docsByTable.keySet()) System.out.println("- " + t);
    }

    public String getDocumentationForTable(String tableName) {
        return docsByTable.get(tableName);
    }

    public boolean hasDocumentation(String tableName) {
        return docsByTable.containsKey(tableName);
    }

    public boolean isEmpty() {
        return docsByTable.isEmpty();
    }

    public void exportToFormat(String tableName, String format) {
        if (!docsByTable.containsKey(tableName)) {
            System.out.println("No documentation found for " + tableName + ". Generate docs first.");
            return;
        }
        String filename = tableName + "_doc_v" + version + "." + format.toLowerCase();
        System.out.println("Exporting " + tableName + " to file: " + filename + " (simulated)");
    }

    public void exportAll(String format) {
        if (docsByTable.isEmpty()) {
            System.out.println("No documentation to export.");
            return;
        }
        for (String tableName : docsByTable.keySet()) exportToFormat(tableName, format);
    }
}
