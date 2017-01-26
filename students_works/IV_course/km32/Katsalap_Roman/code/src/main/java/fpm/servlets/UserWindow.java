package fpm.servlets;

import fpm.dao.interfaces.UserDAO;
import fpm.dao.oracle.OracleDAOFactory;
import fpm.entities.Status;
import fpm.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

@WebServlet("/userWindow")
public class UserWindow extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        String action = req.getParameter("action");
        OracleDAOFactory oracleDAOFactory = new OracleDAOFactory();
        UserDAO userDAO = oracleDAOFactory.getUserDAO();

        if (action.equals("fetch")) {
            String login = req.getParameter("login");
            String status = req.getParameter("status");
            User[] users = userDAO.getUserStatuses(login);
            req.setAttribute("statuses",users);
            req.setAttribute("login",login);
            req.setAttribute("status",status);
            req.getRequestDispatcher("user_window.jsp").forward(req,resp);
            return;

        }
        else if (action.equals("ban")) {
            String login = req.getParameter("login");
            User user = userDAO.getUserByLogin(login);
            user.setStatus(Status.BANNED);
            userDAO.updateUser(user);
            PrintWriter out = resp.getWriter();
            out.write("success");
        }
        else if (action.equals("unban")) {
            String login = req.getParameter("login");
            User user = userDAO.getUserByLogin(login);
            user.setStatus(Status.ACTIVE);
            userDAO.updateUser(user);
            PrintWriter out = resp.getWriter();
            out.write("success");
        }

        return;
    }
}
