package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Budget;
import model.BudgetDAO;
import model.User;

@WebServlet("/budget")
public class BudgetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            BudgetDAO budgetDAO = new BudgetDAO();
            
            if ("add".equals(action)) {
                Budget budget = new Budget();
                budget.setUserId(user.getId());
                budget.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
                budget.setAmount(Double.parseDouble(request.getParameter("amount")));
                budget.setMonthYear(request.getParameter("month_year"));
                
                if (budgetDAO.addBudget(budget)) {
                    session.setAttribute("success", "Budget added successfully");
                } else {
                    session.setAttribute("error", "Failed to add budget");
                }
                response.sendRedirect("budgets.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing budget");
            response.sendRedirect("budgets.jsp");
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        
        if (user == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        try {
            BudgetDAO budgetDAO = new BudgetDAO();
            
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (budgetDAO.deleteBudget(id)) {
                    session.setAttribute("success", "Budget deleted successfully");
                } else {
                    session.setAttribute("error", "Failed to delete budget");
                }
                response.sendRedirect("budgets.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing budget");
            response.sendRedirect("budgets.jsp");
        }
    }
}