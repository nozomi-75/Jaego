package jaego.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import jaego.utils.SampleItem;

public class ItemDAO {
    public void insertProduct (SampleItem item) throws SQLException {
        String sql = "INSERT INTO items (item_id, name, price, quantity, category) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getID());
            stmt.setString(2, item.getName());
            stmt.setDouble(3, item.getPrice());
            stmt.setInt(4, item.getQty());
            stmt.setString(5, item.getCategory());

            stmt.executeUpdate();
        }
    }

    public List<SampleItem> getAllProducts() throws SQLException {
        List<SampleItem> list = new ArrayList<>();
        String sql = "SELECT * FROM items";
        try (Connection conn = DatabaseManager.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                SampleItem item = new SampleItem(
                    rs.getString("item_id"),
                    rs.getString("name"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getString("category")
                );
                list.add(item);
            }
        }
        return list;
    }

    public void updateProduct(SampleItem item) throws SQLException {
        String sql = "UPDATE items SET name = ?, price = ?, quantity = ?, category = ? WHERE item_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, item.getName());
            stmt.setDouble(2, item.getPrice());
            stmt.setInt(3, item.getQty());
            stmt.setString(4, item.getCategory());
            stmt.setString(5, item.getID());

            stmt.executeUpdate();
        }
    }

    public void deleteProduct(String itemId) throws SQLException {
        String sql = "DELETE FROM items WHERE item_id = ?";

        try (Connection conn = DatabaseManager.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, itemId);
            stmt.executeUpdate();
        }
    }

    public List<SampleItem> searchProducts(String query) throws SQLException {
        List<SampleItem> results = new ArrayList<>();
        String sql = "SELECT * FROM items WHERE LOWER(name) LIKE ? OR LOWER(category) LIKE ?";
    
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            String pattern = "%" + query.toLowerCase() + "%";
            stmt.setString(1, pattern);
            stmt.setString(2, pattern);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    SampleItem item = new SampleItem(
                        rs.getString("item_id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                    );
                    results.add(item);
                }
            }
        }
        return results;
    }
}
