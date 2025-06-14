package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {
    public boolean addTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transactions (user_id, title, amount, type, date, category_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getTitle());
            stmt.setDouble(3, transaction.getAmount());
            stmt.setString(4, transaction.getType());
            stmt.setDate(5, transaction.getDate());
            stmt.setInt(6, transaction.getCategoryId());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Transaction> getTransactionsByUser(int userId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT t.*, c.name as category_name FROM transactions t " +
                     "LEFT JOIN categories c ON t.category_id = c.id " +
                     "WHERE t.user_id = ? ORDER BY t.date DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Transaction transaction = new Transaction();
                    transaction.setId(rs.getInt("id"));
                    transaction.setUserId(rs.getInt("user_id"));
                    transaction.setTitle(rs.getString("title"));
                    transaction.setAmount(rs.getDouble("amount"));
                    transaction.setType(rs.getString("type"));
                    transaction.setDate(rs.getDate("date"));
                    transaction.setCategoryId(rs.getInt("category_id"));
                    transaction.setCategoryName(rs.getString("category_name"));
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public boolean deleteTransaction(int id) throws SQLException {
        String sql = "DELETE FROM transactions WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}