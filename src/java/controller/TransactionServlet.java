package controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Transaction;
import model.TransactionDAO;
import model.User;

@WebServlet("/transaction")
public class TransactionServlet extends HttpServlet {
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
            TransactionDAO transactionDAO = new TransactionDAO();
            
            if ("add".equals(action)) {
                Transaction transaction = new Transaction();
                transaction.setUserId(user.getId());
                transaction.setTitle(request.getParameter("title"));
                transaction.setAmount(Double.parseDouble(request.getParameter("amount")));
                transaction.setType(request.getParameter("type"));
                transaction.setDate(Date.valueOf(request.getParameter("date")));
                transaction.setCategoryId(Integer.parseInt(request.getParameter("category_id")));
                
                if (transactionDAO.addTransaction(transaction)) {
                    session.setAttribute("success", "Transaction added successfully");
                } else {
                    session.setAttribute("error", "Failed to add transaction");
                }
                response.sendRedirect("transactions.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing transaction");
            response.sendRedirect("transactions.jsp");
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
            TransactionDAO transactionDAO = new TransactionDAO();
            
            if ("delete".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                if (transactionDAO.deleteTransaction(id)) {
                    session.setAttribute("success", "Transaction deleted successfully");
                } else {
                    session.setAttribute("error", "Failed to delete transaction");
                }
                response.sendRedirect("transactions.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("error", "Error processing transaction");
            response.sendRedirect("transactions.jsp");
        }
    }
}