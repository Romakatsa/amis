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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/admin")
public class Admin extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession(false);
        if (session == null) {
            return;
        }
        resp.setContentType("text/html");
        String login = session.getAttribute("loggedInUser").toString();
        String role = session.getAttribute("role").toString();
        String action = req.getParameter("action");

        if (action == null || action.equals("")) {
            return;
        }

        if (action.equals("users")) {

            String reg_date_from = req.getParameter("reg_date_from");
            String reg_date_till = req.getParameter("reg_date_till");
            String account_status = req.getParameter("account_status");
            String user_login = req.getParameter("login");
            String user_email = req.getParameter("email");
            String user_role = req.getParameter("role");
            OracleDAOFactory oracleDAOFactory = new OracleDAOFactory();
            UserDAO userDAO = oracleDAOFactory.getUserDAO();
            Timestamp reg_timestamp_from = null;
            Timestamp reg_timestamp_till = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            if (!reg_date_from.equals("")) {
                try {
                    Date parsedDate = dateFormat.parse(reg_date_from);
                    reg_timestamp_from = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (!reg_date_till.equals("")) {
                try {
                    Date parsedDate = dateFormat.parse(reg_date_till);
                    reg_timestamp_till = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            User[] users = userDAO.getExtendUsers(user_login, user_email, user_role, account_status, reg_timestamp_from, reg_timestamp_till);
            req.setAttribute("users",users);
            req.getRequestDispatcher("admin_users_result.jsp").forward(req,resp);
            return;

        }
        else if (action.equals("payments")) {

            String pay_date_from = req.getParameter("pay_date_from");
            String pay_date_till = req.getParameter("pay_date_till");
            String amount_min = req.getParameter("amount_min");
            String amount_max = req.getParameter("amount_max");
            String phone = req.getParameter("phone");
            String payment_id = req.getParameter("payment_id");
        }
        else if (action.equals("delete")) {

        }
        else if (action.equals("user")) {
            String user_login = req.getParameter("login");
        }
        else {
            return;
        }


    }

    @Override
    protected void
    doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession(false);
        resp.setContentType("text/html");
        String login = session.getAttribute("loggedInUser").toString();
        String role = session.getAttribute("role").toString();
        String action = req.getParameter("action");

        if (action == null || action.equals("")) {
            return;
        }

        if (action.equals("users")) {

            String reg_date_from = req.getParameter("reg_date_from");
            String reg_date_till = req.getParameter("reg_date_till");
            String account_status = req.getParameter("status_radio_input");
            String user_login = req.getParameter("filter_login");
            String user_email = req.getParameter("filter_email");
            String user_role = req.getParameter("is_admin");
            OracleDAOFactory oracleDAOFactory = new OracleDAOFactory();
            UserDAO userDAO = oracleDAOFactory.getUserDAO();
            Timestamp reg_timestamp_from = null;
            Timestamp reg_timestamp_till = null;
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

            if (account_status == null) {
                account_status = "";
            }
            if (user_role == null) {
                user_role = "";
            }

            if (!reg_date_from.equals("")) {
                try {
                    Date parsedDate = dateFormat.parse(reg_date_from);
                    reg_timestamp_from = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (!reg_date_till.equals("")) {
                try {
                    Date parsedDate = dateFormat.parse(reg_date_till);
                    reg_timestamp_till = new Timestamp(parsedDate.getTime());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }


            User[] users = userDAO.getExtendUsers(user_login, user_email, user_role, account_status, reg_timestamp_from, reg_timestamp_till);
            req.setAttribute("users",users);

            req.setAttribute("role",role);
            req.setAttribute("login",login);
            req.getRequestDispatcher("admin_users_result.jsp").forward(req,resp);
            return;

        }
        else if (action.equals("payments")) {

            String pay_date_from = req.getParameter("pay_date_from");
            String pay_date_till = req.getParameter("pay_date_till");
            String amount_min = req.getParameter("amount_min");
            String amount_max = req.getParameter("amount_max");
            String phone = req.getParameter("phone");
            String payment_id = req.getParameter("payment_id");
        }
        else if (action.equals("delete")) {

        }
        else if (action.equals("user")) {
            String user_login = req.getParameter("login");
        }
        else {
            return;
        }



    }
}
