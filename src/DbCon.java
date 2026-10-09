//DbCon represents a database connection configuration.
//It stores connection details and simulates a connection test.
public class DbCon {

    private String hostname;
    private int port;
    private String dbType;
    private String username;
    private String password;
    private String databaseName;
    private boolean connected;

    public DbCon(String hostname, int port, String dbType, String username, String password, String databaseName) {
        this.hostname = hostname;
        this.port = port;
        this.dbType = dbType;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
        this.connected = false;
    }

    public boolean validateConnection() {
        return hostname != null && !hostname.isEmpty()
                && port > 0
                && username != null && !username.isEmpty()
                && databaseName != null && !databaseName.isEmpty();
    }

    public boolean testConnection() {
        System.out.println("\nTesting Database Connection...");
        System.out.println("Type: " + dbType);
        System.out.println("Host: " + hostname + ":" + port);
        System.out.println("Database: " + databaseName);

        if (validateConnection()) {
            System.out.println("Status: CONNECTED ");
            this.connected = true;
            return true;
        }

        System.out.println("Status: FAILED ");
        return false;
    }

    public boolean isConnected() {
        return connected;
    }

    public String getConnectionString() {
        return dbType + "://" + hostname + ":" + port + "/" + databaseName;
    }
}
