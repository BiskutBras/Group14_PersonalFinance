<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User, model.Transaction, model.TransactionDAO, java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    TransactionDAO transactionDAO = new TransactionDAO();
    List<Transaction> transactions = transactionDAO.getTransactionsByUser(user.getId());
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Transactions - Personal Finance Tracker</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.0/font/bootstrap-icons.css" rel="stylesheet">
    <style>
        .sidebar {
            height: 100vh;
            background-color: #212529;
            color: white;
        }
        .sidebar a {
            color: white;
            text-decoration: none;
        }
        .sidebar a:hover {
            color: #adb5bd;
        }
        .main-content {
            padding: 20px;
        }
        .income {
            color: green;
        }
        .expense {
            color: red;
        }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="row">
            <!-- Sidebar -->
            <div class="col-md-3 col-lg-2 d-md-block sidebar collapse">
                <div class="position-sticky pt-3">
                    <h4 class="text-center mb-4">Personal Finance</h4>
                    <ul class="nav flex-column">
                        <li class="nav-item mb-2">
                            <a class="nav-link" href="dashboard.jsp">
                                <i class="bi bi-house-door"></i> Dashboard
                            </a>
                        </li>
                        <li class="nav-item mb-2">
                            <a class="nav-link active" href="transactions.jsp">
                                <i class="bi bi-cash-stack"></i> Transactions
                            </a>
                        </li>
                        <li class="nav-item mb-2">
                            <a class="nav-link" href="budgets.jsp">
                                <i class="bi bi-piggy-bank"></i> Budgets
                            </a>
                        </li>
                        <li class="nav-item mb-2">
                            <a class="nav-link" href="auth?action=logout">
                                <i class="bi bi-box-arrow-right"></i> Logout
                            </a>
                        </li>
                    </ul>
                </div>
            </div>

            <!-- Main content -->
            <div class="col-md-9 col-lg-10 ms-sm-auto main-content">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">Transactions</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <a href="add-transaction.jsp" class="btn btn-sm btn-outline-primary">
                            <i class="bi bi-plus"></i> Add Transaction
                        </a>
                    </div>
                </div>

                <% if (session.getAttribute("success") != null) { %>
                    <div class="alert alert-success">${success}</div>
                    <% session.removeAttribute("success"); %>
                <% } %>
                
                <% if (session.getAttribute("error") != null) { %>
                    <div class="alert alert-danger">${error}</div>
                    <% session.removeAttribute("error"); %>
                <% } %>

                <div class="table-responsive">
                    <table class="table table-striped table-hover">
                        <thead>
                            <tr>
                                <th>Date</th>
                                <th>Title</th>
                                <th>Category</th>
                                <th>Amount</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for (Transaction transaction : transactions) { %>
                            <tr>
                                <td><%= transaction.getDate() %></td>
                                <td><%= transaction.getTitle() %></td>
                                <td><%= transaction.getCategoryName() != null ? transaction.getCategoryName() : "N/A" %></td>
                                <td class="<%= "Income".equals(transaction.getType()) ? "income" : "expense" %>">
                                    <%= "Income".equals(transaction.getType()) ? "+" : "-" %> <%= transaction.getAmount() %>
                                </td>
                                <td>
                                    <a href="transaction?action=delete&id=<%= transaction.getId() %>" 
                                       class="btn btn-sm btn-danger" 
                                       onclick="return confirm('Are you sure you want to delete this transaction?')">
                                        <i class="bi bi-trash"></i>
                                    </a>
                                </td>
                            </tr>
                            <% } %>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>