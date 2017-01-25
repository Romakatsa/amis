package fpm.servlets;

import fpm.crypt.Crypt;
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

/**
 * Created by Guest on 25.12.2016.
 */

@WebServlet("/restore")
public class Restore extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("u");
        String link = req.getParameter("r");

        if ((login != null) && (link != null)) {

            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            UserDAO userDao = oracleDaoFactory.getUserDAO();
            boolean exist = userDao.isAlreadyExists(login, true);
            if (exist) {
                User user = userDao.getUserByLogin(login);
                if (user.getHashlink().equals(Crypt.crypt(link, user.getSalt()))) {
                switch (user.getStatus()) {
                    case UNCONFIRMED:
                        req.setAttribute("status", "fail");
                        req.setAttribute("message", "You haven't completed registration. Check your email " + Crypt.hideCharsInEmail(user.getEmail()) +
                                "and confirm registration or click <a id='resend_confirmation'>here</a> to resend confirmation link");
                        break;
                    case RESET:
                            String new_pass = Crypt.getRandomString(9);
                            String[] hashSalt = Crypt.crypt(new_pass);
                            user.setPassword(hashSalt[0]);
                            user.setSalt(hashSalt[1]);
                            user.setHashlink("");
                            user.setStatus(Status.CONFIRMED);
                            userDao.updateUser(user);
                            req.setAttribute("status", "success");
                            req.setAttribute("new_pass", new_pass);
                            req.setAttribute("message", "We've sent you a letter with new password. Change it immediately after signed in.");
                        break;

                    case BANNED:
                        req.setAttribute("status", "fail");
                        req.setAttribute("message", "We restricted access to your account. Reason: "+ user.getStatus_msg() +". Check email" + Crypt.hideCharsInEmail(user.getEmail()) + "for additional information");
                        break;

                    case CONFIRMED:
                    case ACTIVE:
                        req.setAttribute("status", "fail");
                        req.setAttribute("message", "Your password have been already changed. Check email" + Crypt.hideCharsInEmail(user.getEmail()) + "for additional information");
                        break;
                }
                } else {
                    req.setAttribute("status", "fail");
                    req.setAttribute("message", "Reset link is invalid.");
                }
            } else {
                req.setAttribute("status", "fail");
                req.setAttribute("message", "Reset link is invalid");
            }
            req.getRequestDispatcher("restore.jsp").forward(req,resp);
        } else {
            boolean logged = true;
            HttpSession session = req.getSession(false);

            if (session == null || session.getAttribute("loggedInUser") == null) {
                logged = false;
            }

            req.setAttribute("logged", logged);
            resp.setHeader("Cache-Control", "no-cache");
            resp.setHeader("Pragma", "no-cache");
            req.getRequestDispatcher("restore-form.jsp").forward(req, resp);
        }
    }


    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        String login = req.getParameter("login");

        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        UserDAO userDao = oracleDaoFactory.getUserDAO();
        User user = userDao.getUserByLogin(login);
        String answer = "Error";
        if  (user != null) {

            switch (user.getStatus()) {
                case RESET:
                    answer = "You have already requested account restoring. Check your email" + Crypt.hideCharsInEmail(user.getEmail()) +
                                " and finish operation or click <a id='resend_restore' login='"+login+"'>here</a> to resend restore link.";
                    break;
                case BANNED:
                    answer = "We restricted access to your account. Check email" + Crypt.hideCharsInEmail(user.getEmail()) +
                            " for additional information.";
                    break;
                case UNCONFIRMED:
                    answer = "You haven't confirm registration yet. Check email" + Crypt.hideCharsInEmail(user.getEmail()) +
                            " and confirm registration or click <a id='resend_confirmation' login='"+login+"'>here</a> to resend confirmation link.";
                    break;
                case CONFIRMED:
                case ACTIVE:
                    String uri = req.getScheme() + "://" +req.getServerName() + ":" + req.getServerPort() + "/restore";
                    String link = Crypt.getRandomString(20);
                    String restoreLink = uri + "?u=" + login +"&r=" + link;
                    String hashLink = Crypt.crypt(link,user.getSalt());
                    user.setStatus(Status.RESET);
                    user.setHashlink(hashLink);
                    userDao.updateUser(user);

                    answer = "We've sent letter on your email" + Crypt.hideCharsInEmail(user.getEmail()) +
                            ". Check it and follow written instructions. <span id='restore_link_span'>Restore link = <a href="+restoreLink+">"+restoreLink+"</a></span>";
                    break;
            }

            PrintWriter out = resp.getWriter();
            out.write(answer);

            // MAIL CONFIRMATION LINK
        }
        else {
            PrintWriter out = resp.getWriter();
            out.write(answer);
        }

    }

}
