<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="model.User, model.Category, model.CategoryDAO, java.util.List" %>
<%
    User user = (User) session.getAttribute("user");
    if (user == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    
    CategoryDAO categoryDAO = new CategoryDAO();
    List<Category> expenseCategories = categoryDAO.getCategoriesByType("Expense");
%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Add Budget - Personal Finance Tracker</title>
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
                            <a class="nav-link" href="transactions.jsp">
                                <i class="bi bi-cash-stack"></i> Transactions
                            </a>
                        </li>
                        <li class="nav-item mb-2">
                            <a class="nav-link active" href="budgets.jsp">
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
                    <h1 class="h2">Add Budget</h1>
                    <div class="btn-toolbar mb-2 mb-md-0">
                        <a href="budgets.jsp" class="btn btn-sm btn-outline-secondary">
                            <i class="bi bi-arrow-left"></i> Back
                        </a>
                    </div>
                </div>

                <div class="card">
                    <div class="card-body">
                        <form action="budget" method="post">
                            <input type="hidden" name="action" value="add">
                            
                            <div class="mb-3">
                                <label for="category_id" class="form-label">Category</label>
                                <select class="form-select" id="category_id" name="category_id" required>
                                    <option value="">Select Category</option>
                                    <% for (Category category : expenseCategories) { %>
                                    <option value="<%= category.getId() %>"><%= category.getName() %></option>
                                    <% } %>
                                </select>
                            </div>
                            
                            <div class="mb-3">
                                <label for="amount" class="form-label">Amount</label>
                                <input type="number" step="0.01" class="form-control" id="amount" name="amount" required>
                            </div>
                            
                            <div class="mb-3">
                                <label for="month_year" class="form-label">Month</label>
                                <input type="month" class="form-control" id="month_year" name="month_year" required>
                            </div>
                            
                            <button type="submit" class="btn btn-primary">Add Budget</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>