package jaego.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import jaego.utils.DialogUtils;

import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:inventory.db";
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    public static void initDB() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS items (
                item_num INTEGER PRIMARY KEY AUTOINCREMENT,
                item_id TEXT UNIQUE,
                name TEXT NOT NULL,
                price REAL NOT NULL CHECK (price >= 0),
                quantity INTEGER NOT NULL CHECK (quantity >= 0),
                category TEXT NOT NULL CHECK (category IN (
                    'Stationery',
                    'Electronics',
                    'Office Supplies',
                    'Furniture',
                    'Cleaning Supplies',
                    'IT Equipment',
                    'Other'
                ))
            );
        """;
        
        try (Connection conn = getConnection();
        Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
        } catch (SQLException e) {
            DialogUtils.showError(
                "Failed to initialize database: " + e.getMessage(),
                "Database error");
        }
    }
}
