package server;
import model.Purchase;
import java.sql.*;

public class DatabaseHelper {
    private final String url, user, pass;
    public DatabaseHelper(String url, String user, String pass){
        this.url=url; this.user=user; this.pass=pass;
    }

    public void savePurchase(Purchase p) {
        String sql = "INSERT INTO purchases(username, ticket_type, price, time) VALUES(?, ?, ?, ?)";
        try (Connection c = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, p.getUsername());
            ps.setString(2, p.getTicket().getType());
            ps.setDouble(3, p.getTicket().getPrice());
            ps.setTimestamp(4, Timestamp.valueOf(p.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("DB save error: " + e.getMessage());
        }
    }
}