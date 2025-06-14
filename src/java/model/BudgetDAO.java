package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {
    public boolean addBudget(Budget budget) throws SQLException {
        String sql = "INSERT INTO budgets (user_id, category_id, amount, month_year) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, budget.getUserId());
            stmt.setInt(2, budget.getCategoryId());
            stmt.setDouble(3, budget.getAmount());
            stmt.setString(4, budget.getMonthYear());
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Budget> getBudgetsByUser(int userId) throws SQLException {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT b.*, c.name as category_name FROM budgets b " +
                     "JOIN categories c ON b.category_id = c.id " +
                     "WHERE b.user_id = ? ORDER BY b.month_year DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Budget budget = new Budget();
                    budget.setId(rs.getInt("id"));
                    budget.setUserId(rs.getInt("user_id"));
                    budget.setCategoryId(rs.getInt("category_id"));
                    budget.setAmount(rs.getDouble("amount"));
                    budget.setMonthYear(rs.getString("month_year"));
                    budget.setCategoryName(rs.getString("category_name"));
                    budgets.add(budget);
                }
            }
        }
        return budgets;
    }

    public boolean deleteBudget(int id) throws SQLException {
        String sql = "DELETE FROM budgets WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}