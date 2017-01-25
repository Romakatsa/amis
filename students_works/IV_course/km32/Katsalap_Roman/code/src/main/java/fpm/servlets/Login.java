package fpm.servlets;

import fpm.crypt.Crypt;
import fpm.dao.interfaces.UserDAO;
import fpm.dao.oracle.OracleDAOFactory;
import fpm.entities.Status;
import fpm.entities.User;
import fpm.util.Validation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        if(req.getParameter("action") != null && req.getParameter("action").equals("logout")) {
            req.getSession().setAttribute("loggedInUser", null);
        }
        return;

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        resp.setContentType("text/html");
        String login = req.getParameter("login_login");
        String pass = req.getParameter("login_password");

        if (!Validation.isValidLogin(login)) {
            req.setAttribute("showErrorMsg",true);
        }
        else {
            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            UserDAO userDao = oracleDaoFactory.getUserDAO();
            User user = userDao.getUserByLogin(login);
            if (user!=null && user.getHash().equals(Crypt.getHash(pass,user.getSalt()))) {
                switch (user.getStatus()) {
                    case UNCONFIRMED:
                        req.setAttribute("message","You haven't completed registration. Check your email " + Crypt.hideCharsInEmail(user.getEmail()) +
                                "and confirm registration or click <a id='resend_confirmation'>here</a> to resend confirmation link");
                        break;
                    case CONFIRMED:
                        req.getSession().setAttribute("loggedInUser", user.getLogin());
                        req.getSession().setAttribute("role",user.getAdmin() ? "admin": "user");
                        user.setStatus(Status.ACTIVE);
                        userDao.updateUser(user);
                        resp.sendRedirect("/services?action=payments");

                        return;
                    case RESET:
                        req.setAttribute("message","You had requested account restoring. Check your email" + Crypt.hideCharsInEmail(user.getEmail()) +
                                "and finish operation or click <a id='resend_restore'>here</a> to resend restore link.");
                        break;
                    case BANNED:
                        req.setAttribute("message","We restricted access to your account. Reason: "+ user.getStatus_msg() +". Check email" + Crypt.hideCharsInEmail(user.getEmail()) +
                                "for additional information.");
                        break;
                    case ACTIVE:

                        req.getSession().setAttribute("loggedInUser", user.getLogin());
                        req.getSession().setAttribute("loggedInUser", user.getLogin());
                        req.getSession().setAttribute("role",user.getAdmin() ? "admin": "user");
                        resp.sendRedirect("/services?action=payments");
                        return;
                }
            }else {
                req.setAttribute("message","Invalid login/password");
                req.setAttribute("logged",false);
                req.setAttribute("showErrorMsg",true);
            }
        }

        req.getRequestDispatcher("start.jsp").forward(req, resp);

    }
}
