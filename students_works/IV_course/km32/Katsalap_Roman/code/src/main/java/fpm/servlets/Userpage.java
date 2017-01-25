package fpm.servlets;

import fpm.dao.interfaces.CardDAO;
import fpm.dao.interfaces.PaymentDAO;
import fpm.dao.interfaces.PhoneDAO;
import fpm.dao.interfaces.UserDAO;
import fpm.dao.oracle.OracleDAOFactory;
import fpm.entities.Card;
import fpm.entities.Payment;
import fpm.entities.Phone;
import fpm.entities.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/services")

public class Userpage extends HttpServlet{


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        resp.setContentType("text/html");
        String actionParam = req.getParameter("action");
        String login = session.getAttribute("loggedInUser").toString();
        String role = session.getAttribute("role").toString();
        req.setAttribute("login",login);

        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        CardDAO cardDao = oracleDaoFactory.getCardDAO();
        Card[] userCards = cardDao.selectCardsByLogin(login);

        if (actionParam == null) {
            actionParam = "payments";
        }

        if (actionParam.equals("admin")) {
            if(!role.equals("admin")) {

                resp.sendRedirect("/services?action=payments");
                return;
            }
        }

        else if (actionParam.equals("payments")) {

            PaymentDAO paymentDao = oracleDaoFactory.getPaymentDAO();
            PhoneDAO phoneDao = oracleDaoFactory.getPhoneDAO();
            Payment[] userPayments = paymentDao.selectPaymentsByLogin(login);
            Phone[] userPhones = phoneDao.selectPhonesByLogin(login);

            req.setAttribute("phones",userPhones);
            req.setAttribute("payments",userPayments);
            req.setAttribute("cards",userCards);
        }

        else if (actionParam.equals("cards")) {



        }


        else if (actionParam.equals("settings")) {


        }


        req.setAttribute("cards",userCards);
        req.setAttribute("menu_item",actionParam);
        req.setAttribute("role",role);
        req.getRequestDispatcher("services.jsp").forward(req,resp);







    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        resp.setContentType("text/html");
        String actionParam = req.getParameter("action");
        String login = session.getAttribute("loggedInUser").toString();
        String role = session.getAttribute("role").toString();

        if (actionParam.equals("admin")) {
            if (role.equals("admin")) {
                req.getRequestDispatcher("admin.jsp").forward(req,resp);
                return;
            }
            else {
                actionParam = "payments";
            }
        }

        if (actionParam.equals("payments")) {
            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            PaymentDAO paymentDao = oracleDaoFactory.getPaymentDAO();
            PhoneDAO phoneDao = oracleDaoFactory.getPhoneDAO();
            Payment[] userPayments = paymentDao.selectPaymentsByLogin(login);
            Phone[] userPhones = phoneDao.selectPhonesByLogin(login);
            req.setAttribute("phones",userPhones);
            req.setAttribute("payments",userPayments);
            req.getRequestDispatcher("payments.jsp").forward(req,resp);
        }

        else if (actionParam.equals("cards")) {
            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            CardDAO cardDao = oracleDaoFactory.getCardDAO();
            Card[] userCards = cardDao.selectCardsByLogin(login);
            req.setAttribute("cards",userCards);
            req.getRequestDispatcher("cards.jsp").forward(req,resp);
        }


        else if (actionParam.equals("settings")) {
            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            UserDAO userDao = oracleDaoFactory.getUserDAO();
            User user = userDao.getUserByLogin(login);
            req.setAttribute("email",user.getEmail());
            req.getRequestDispatcher("settings.jsp").forward(req,resp);

        }



       //req.getRequestDispatcher("services.jsp").forward(req,resp);
    }
}
