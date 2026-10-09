/**
 * ------------------------------------------------------------
 * Module: MOD003484
 *
 * Application Name: SQL Documentation Assistant (SQL-DA)
 *
 * Student ID: 2415808
 *
 */



import java.util.*;

 // Entry point of the application.
 //Displays the main dashboard and routes user input
 // to the appropriate functionality.


public class Main {


    private static final Scanner scanner = new Scanner(System.in);
    private static DbCon connection = null;
    private static List<Table> schemaTables = new ArrayList<>();
    private static Documentationservice docsService = new Documentationservice();

    public static void main(String[] args) {
        showHeader();

        boolean running = true;
        while (running) {
            showDashboard();
            String choice = scanner.nextLine().trim();  //Used Switch cases for the user to choose the menu
            switch (choice) {
                case "1": handleConnect(); break;
                case "2": handleSchemaExplorer(); break;
                case "3": handleGenerateDocumentation(); break;
                case "4": handleDocsEditor(); break;
                case "5": handleExport(); break;
                case "6": handleAIAssistant(); break;
                case "0":
                    System.out.println("Exiting application. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option — enter a number from the menu.");
            }
        }

        scanner.close();
    }

    private static void showHeader() {
        System.out.println("     SQL DOCUMENTATION ASSISTANT (SQL-DA) - Console App  ");
        System.out.println("                   Student ID: 2415808                    ");

    }

    private static void showDashboard() {               //The Main Menu
        System.out.println("\n--- DASHBOARD ---");
        System.out.println("1) Connect to Database ");
        System.out.println("2) Schema Explorer ");
        System.out.println("3) Generate Documentation ");
        System.out.println("4) Documentation Editor / Viewer ");
        System.out.println("5) Export Documentation ");
        System.out.println("6) AI Assistant ");
        System.out.println("0) Exit");
        System.out.print(">> ");
    }

    // ---------- Menu handlers ----------

    private static void handleConnect() {          //Checking connection
        System.out.println("\n1. Database Connection Setup");

        String dbType = getNonEmptyString("Database Type (e.g., PostgreSQL)");
        String hostname = getNonEmptyString("Hostname (e.g., localhost)");

        int port = getValidInt("Port (e.g., 5432)");

        String username = getNonEmptyString("Username");

        String password = getNonEmptyString("Password");

        String dbName = getValidDatabaseName();

        connection = new DbCon(hostname, port, dbType, username, password, dbName);
        boolean ok = connection.testConnection();
        if (ok) {
            System.out.println("Simulated connection established.");
            loadSampleSchema(); // load predefined tables into schemaTables
            System.out.println("Sample schema loaded into Schema Explorer.");
        } else {
            System.out.println("Connection failed (invalid parameters).");
            connection = null;
        }
    }

              //Allows the user to explore the database schema.
               //Users can list tables and view detailed table structures.
    private static void handleSchemaExplorer() {
        System.out.println("\n2. Schema Explorer");
        if (!ensureConnected()) return;

        if (schemaTables.isEmpty()) {
            System.out.println("No tables available in the schema.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\nSchema Explorer Menu:");
            System.out.println("1) List tables");
            System.out.println("2) View table details");
            System.out.println("0) Back to Dashboard");
            System.out.print("> ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1":
                    listTables();
                    break;
                case "2":
                    try {
                        System.out.print("Enter table name to view: ");
                        String name = scanner.nextLine().trim();

                        Table t = findTableByName(name);

                        if (t == null) {
                            System.out.println("Table not found: " + name);
                        } else {
                            t.displayStructure();
                        }

                    } catch (Exception ex) {
                        System.out.println("An unexpected error occurred while viewing table details.");
                        System.out.println("Error message: " + ex.getMessage());
                    }
                    break;

                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    //Generates documentation for all tables in the schema
        //using the Documentationservice class
    private static void handleGenerateDocumentation() {
        System.out.println("\n3. Generate Documentation");
        if (!ensureConnected()) return;

        if (schemaTables.isEmpty()) {
            System.out.println("No tables loaded — connect to database to load the sample schema.");
            return;
        }

        System.out.println("Generating documentation for all tables in the schema..."); //Here the Documnetation has been created
        docsService.generateDocumentation(schemaTables);
        System.out.println("Documentation generated for " + schemaTables.size() + " table(s).");
    }
        //The user is allowed to see their documnetation
    // Also let them to comment
    private static void handleDocsEditor() {
        System.out.println("\n4. Documentation Editor / Viewer");
        if (docsService.isEmpty()) {
            System.out.println("No generated documentation available. Run 'Generate Documentation' first.");
            return;
        }

        boolean back = false;
        while (!back) {
            System.out.println("\nDocs Menu:");
            System.out.println("1) List documented tables");
            System.out.println("2) View documentation for table");
            System.out.println("3) Add Comment to the Documentation");
            System.out.println("0) Back");
            System.out.print("> ");
            String c = scanner.nextLine().trim();
            switch (c) {
                case "1":
                    docsService.listDocumentedTables();
                    break;
                case "2":
                    System.out.print("Enter table name: ");
                    String tn = scanner.nextLine().trim();
                    String doc = docsService.getDocumentationForTable(tn);
                    if (doc == null) System.out.println("No documentation found for table: " + tn);
                    else System.out.println("\n" + doc);
                    break;
                case "3":
                    System.out.print("Enter table name to Commenting: ");
                    String tname = scanner.nextLine().trim();
                    if (!docsService.hasDocumentation(tname)) {
                        System.out.println("No documentation for " + tname + ". Generate docs first.");
                    } else {
                        System.out.print("Enter Commenting/note: ");
                        String note = scanner.nextLine().trim();
                        docsService.addAnnotation(tname, note);
                    }
                    break;
                case "0":
                    back = true;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }


 //AI Assistant (Simulated)
 // This method represents a conceptual AI assistant as proposed
 //in component 010/1 of the project. The assistant does not use
 //real artificial intelligence or machine learning.


 //The assistant recommends the most informative table to the user,
 //demonstrating decision modelling and algorithmic thinking.

    private static void handleAIAssistant() {
        System.out.println("\n6. AI Assistant (Simulated)");
        System.out.println("NOTE: This is a Demo AI Assistant for SDL Documentation Assistant");

        if (docsService.isEmpty()) {
            System.out.println("No documentation available. Please generate documentation first.");
            return;
        }

        // AI logic: pick documentation of the table with the most columns (simple simulation)
        Table bestTable = null;
        int mostColumns = -1;

        for (Table t : schemaTables) {
            if (docsService.hasDocumentation(t.getTableName())) {
                int colCount = t.getColumns().size();
                if (colCount > mostColumns) {
                    mostColumns = colCount;
                    bestTable = t;
                }
            }
        }

        if (bestTable == null) {
            System.out.println("AI Assistant could not find any valid documentation.");
            return;
        }

        System.out.println("\nAI Assistant Recommendation:");
        System.out.println("Based on connected documentation sources, the most informative table is:");
        System.out.println(" → " + bestTable.getTableName());
        System.out.println("\nShowing recommended documentation:\n");

        String doc = docsService.getDocumentationForTable(bestTable.getTableName());
        System.out.println(doc);

    }
    //Handles exporting documentation to text files.
    // Supports exporting a single table or all tables.
    private static void handleExport() {
        System.out.println("\n5. Export Documentation");

        if (docsService.isEmpty()) {
            System.out.println("No documentation to export. Run 'Generate Documentation' first.");
            return;
        }

        System.out.print("Enter table name to export (or type 'all' to export everything): ");
        String choice = scanner.nextLine().trim();

        if (choice.equalsIgnoreCase("all")) {
            docsService.exportAll();  // No format needed
        } else {
            docsService.exportSingle(choice);  // Export only this one
        }
    }




    private static boolean ensureConnected() {  //to make sure the connection is successfull or not
        if (connection == null || !connection.isConnected()) {
            System.out.println("You are not connected. Please use option 1 (Connect to Database first.)");
            return false;
        }
        return true;
    }

    private static void listTables() {  //list down the table by their table name
        System.out.println("\nTables in schema:");
        for (Table t : schemaTables) {
            System.out.printf("- %s (columns: %d)\n", t.getTableName(), t.getColumns().size());
        }
    }

    private static Table findTableByName(String name) { //finding the table
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Table name cannot be null or empty.");
        }

        for (Table t : schemaTables) {
            if (t.getTableName().equalsIgnoreCase(name.trim())) {
                return t;
            }
        }

        return null; // not found (safe return)
    }


    // Load predefined sample schema (Customers, Orders, Products)
    private static void loadSampleSchema() {
        schemaTables.clear();
       //i have added 3 database table
        Table customers = new Table("customers", 1250);
        customers.addColumn(new Column("customer_id", "INT", true, false, false));
        customers.addColumn(new Column("first_name", "VARCHAR(50)", false, false, true));
        customers.addColumn(new Column("last_name", "VARCHAR(50)", false, false, true));
        customers.addColumn(new Column("email", "VARCHAR(150)", false, false, true));
        customers.getColumns().get(0).setDescription("Primary key for customers.");

        Table orders = new Table("orders", 3420);
        orders.addColumn(new Column("order_id", "INT", true, false, false));
        orders.addColumn(new Column("customer_id", "INT", false, true, false));
        orders.addColumn(new Column("order_date", "TIMESTAMP", false, false, false));
        orders.addColumn(new Column("total_amount", "DECIMAL(10,2)", false, false, false));
        orders.getColumns().get(1).setDescription("");

        Table products = new Table("products", 200);
        products.addColumn(new Column("product_id", "INT", true, false, false));
        products.addColumn(new Column("name", "VARCHAR(200)", false, false, false));
        products.addColumn(new Column("price", "DECIMAL(10,2)", false, false, false));

        schemaTables.add(customers);
        schemaTables.add(orders);
        schemaTables.add(products);
    }


    private static String getNonEmptyString(String prompt) {
        String val;
        while (true) {
            System.out.print(prompt + ": ");
            val = scanner.nextLine().trim();
            if (!val.isEmpty()) return val;
            System.out.println(" Error: Input cannot be empty.");
        }
    }
    //Below Syntaxes are for validation
    private static int getValidInt(String prompt) {
        while (true) {
            System.out.print(prompt + ": ");
            String raw = scanner.nextLine().trim();
            try {
                return Integer.parseInt(raw);
            } catch (NumberFormatException e) {
                System.out.println(" Error: Please enter a valid integer.");
            }
        }
    }

    // Database name validator: not empty, not numbers-only, not starting with digit, letters/digits/underscore only
    private static String getValidDatabaseName() {
        while (true) {
            System.out.print("Database Name: ");
            String val = scanner.nextLine().trim();
            if (val.isEmpty()) {
                System.out.println(" Error: Database name cannot be empty.");
                continue;
            }
            if (val.matches("\\d+")) {
                System.out.println(" Error: Database name cannot be only numbers.");
                continue;
            }
            if (Character.isDigit(val.charAt(0))) {
                System.out.println(" Error: Database name cannot start with a number.");
                continue;
            }
            if (!val.matches("[A-Za-z_][A-Za-z0-9_]*")) {
                System.out.println(" Error: Only letters, digits and underscores allowed; must start with letter or underscore.");
                continue;
            }
            return val;
        }
    }
}
