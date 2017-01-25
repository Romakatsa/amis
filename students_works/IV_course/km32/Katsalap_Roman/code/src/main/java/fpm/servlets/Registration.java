package fpm.servlets;

import com.google.gson.Gson;
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
import java.io.PrintWriter;


class ResponseData {
   public boolean success;
   public String message;
}


@WebServlet(urlPatterns = {"/reg", "/confirm"})
public class Registration extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("text/plain");
        String check = req.getParameter("check");
        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        UserDAO userDao = oracleDaoFactory.getUserDAO();
        PrintWriter out = resp.getWriter();

        if (check != null) {
            String field = req.getParameter(check);
            if (userDao.isAlreadyExists(field, "login".equals(check))) {
                out.write("exists");
            } else {
                out.write("ok");

            }
            out.close();
        }
        else {

            String login = req.getParameter("u");
            String link = req.getParameter("c");

            if ((login != null) && (link != null)) {

                boolean exist = userDao.isAlreadyExists(login, true);
                if (exist) {
                    User user = userDao.getUserByLogin(login);
                    String hashlink = user.getHashlink();
                    String crypted = Crypt.crypt(link, user.getSalt());
                    if (hashlink.equals(crypted)) {
                        switch (user.getStatus()) {
                            case RESET:
                            case ACTIVE:
                            case CONFIRMED:
                                req.setAttribute("status", "success");
                                req.setAttribute("message", "Your account had been already activated.");
                                break;
                            case UNCONFIRMED:
                                user.setStatus(Status.CONFIRMED);
                                userDao.updateUser(user);
                                req.setAttribute("status", "success");
                                req.setAttribute("message", "You confirmed your registration. Now you can log in your account");
                                break;

                            case BANNED:
                                req.setAttribute("status", "fail");
                                req.setAttribute("message", "We restricted access to your account. Reason: "+ user.getStatus_msg() +". Check email" + Crypt.hideCharsInEmail(user.getEmail()) + "for additional information");
                                break;

                        }
                    } else {
                        req.setAttribute("status", "fail");
                        req.setAttribute("message", "Cunfirmation link is invalid.");
                    }
                } else {
                    req.setAttribute("status", "fail");
                    req.setAttribute("message", "Confirmation link is invalid");
                }
                req.getRequestDispatcher("restore.jsp").forward(req,resp);
            }

        }
    return;


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {




        if (req.getParameter("action") != null && req.getParameter("action").equals("resend")) {

            String login = req.getParameter("login");
            OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
            UserDAO userDao = oracleDaoFactory.getUserDAO();
            if (!userDao.isAlreadyExists(login,true)) {
                return;
            }
            User user = userDao.getUserByLogin(login);
            String uri = req.getScheme() + "://" +req.getServerName() + ":" + req.getServerPort() + "/confirm";
            String link = Crypt.getRandomString(20);
            String confirmLink = uri + "?u=" + login +"&c=" + link;
            String hashLink = Crypt.crypt(link,user.getSalt());
            user.setHashlink(hashLink);
            userDao.updateUser(user);
            PrintWriter out = resp.getWriter();
            out.write(confirmLink);
            return;


        }

        Gson gson = new Gson();
        ResponseData responseData = new ResponseData();
        responseData.message = "";
        responseData.success = true;


        String email = req.getParameter("email");
        String pass = req.getParameter("password");
        String login = req.getParameter("login");

        /*if (!Validation.isValidLogin(login)) {

            valid = false;
        }

        if (!Validation.isValidEmail(email)) {
            req.setAttribute("isValidEmail","invalid");
            valid = false;
        }

        if (!Validation.isValidPass(pass)) {
            req.setAttribute("isValidPass","invalid");
            valid = false;
        }

        if (!valid) {

        }
        */
        PrintWriter out = resp.getWriter();
        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        UserDAO userDao = oracleDaoFactory.getUserDAO();

        if (userDao.isAlreadyExists(login,true)) {
            responseData.message += "<span class='reg_err_msg'>This login is registered by another user.</span>";
            responseData.success = false;
        }
        if (userDao.isAlreadyExists(email,false)) {
            responseData.message += "<span class='reg_err_msg'>This email is registered by another user.</span>";
            responseData.success = false;
        }

        if (!responseData.success) {
            String json_answer = gson.toJson(responseData);
            out.write(json_answer);
            return;
        }

        String[] hashSalt = Crypt.crypt(pass);
        String uri = req.getScheme() + "://" +req.getServerName() + ":" + req.getServerPort() + "/confirm";
        String link = Crypt.getRandomString(20);
        String confirmLink = uri + "?u=" + login +"&c=" + link;
        String hashLink = Crypt.crypt(link,hashSalt[1]);
        User newUser = new User(login,email,hashSalt[0],hashSalt[1],hashLink,Status.UNCONFIRMED);
        int newUserNo = userDao.insertUserWithPassword(newUser,hashSalt[0],hashSalt[1],hashLink);

        if (newUserNo>0) {
            responseData.message += "Success! Confirmation link: <a href='"+confirmLink+"'>"+confirmLink+"</a>";
            responseData.success = true;
            String json_answer = gson.toJson(responseData);
            out.write(json_answer);
        }
        else {
            responseData.message += "Unknown error!";
            responseData.success = false;
            String json_answer = gson.toJson(responseData);
            out.write(json_answer);
        }
    }
}
