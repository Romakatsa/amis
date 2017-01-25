package fpm.servlets;

import fpm.crypt.Crypt;
import fpm.dao.interfaces.UserDAO;
import fpm.dao.oracle.OracleDAOFactory;
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
 * Created by Roma on 24.01.2017.
 */

@WebServlet("/change")
public class Change extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        resp.setContentType("text/html");
        String login = session.getAttribute("loggedInUser").toString();
        String result = "fail";
        String action = req.getParameter("action");
        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        UserDAO userDao = oracleDaoFactory.getUserDAO();
        User user = userDao.getUserByLogin(login);

        if (action == null) {
            result = "fail";
            PrintWriter out = resp.getWriter();
            out.write(result);
            return;
        }

        if (action.equals("password")) {
            String old_pass = req.getParameter("old_pass");
            String new_pass = req.getParameter("new_pass");

            if (user.getHash().equals(Crypt.getHash(old_pass,user.getSalt()))) {
                String[] hashSalt = Crypt.crypt(new_pass);
                user.setPassword(hashSalt[0]);
                user.setSalt(hashSalt[1]);
                if (userDao.updateUser(user)) {
                    result = "success";
                }
                else {
                    result = "fail";
                }
            }
            else {
                result = "fail";
            }
        }

        if (action.equals("email")) {

            String new_email = req.getParameter("new_email");
            if (userDao.isAlreadyExists(new_email,false)) {
                result = "fail";
            }
            else {
                user.setEmail(new_email);
                if (userDao.updateUser(user)) {
                    result = "success";
                }
                else {
                    result = "fail";
                }
            }

        }

        PrintWriter out = resp.getWriter();
        out.write(result);
    }
}
