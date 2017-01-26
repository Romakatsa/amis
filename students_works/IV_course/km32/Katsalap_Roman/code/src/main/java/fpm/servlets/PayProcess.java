package fpm.servlets;

import com.google.gson.Gson;
import fpm.crypt.Crypt;
import fpm.dao.interfaces.CardDAO;
import fpm.dao.interfaces.PaymentDAO;
import fpm.dao.interfaces.PhoneDAO;
import fpm.dao.interfaces.UserDAO;
import fpm.dao.oracle.OracleDAOFactory;
import fpm.entities.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;

import java.time.OffsetDateTime;
import java.sql.Timestamp;

/**
 * Created by Guest on 25.12.2016.
 */

@WebServlet("/pay")
public class PayProcess extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        String login = session.getAttribute("loggedInUser").toString();

        String phone = req.getParameter("phone_input");
        String amountString = req.getParameter("amount_input");
        String cardNo = req.getParameter("card_select");
        String token;
        Card payCard;

        OracleDAOFactory oracleDaoFactory = new OracleDAOFactory();
        CardDAO cardDao = oracleDaoFactory.getCardDAO();

        Gson gson = new Gson();
        ResponseData responseData = new ResponseData();
        responseData.message = "";
        responseData.success = true;
        PrintWriter out = resp.getWriter();


            String month = req.getParameter("month_select");
            String year = req.getParameter("year_select");
            String cvv = req.getParameter("cvv_input");





            if (!month.equals("") && !year.equals("") && !cvv.equals("")) {
                //check card in bank.....
                //if bank responses status ok, then insert cart

                token = "tokenString";
                payCard = new Card(cardNo.substring(12),"",token);
                String cardOwner = cardDao.isExistCard(payCard);
                if (cardOwner == null) {
                    if (!cardDao.insertCard(payCard,login)) {
                        responseData.message = "<span class='pay_err_msg'>Card can't be processed</span>";
                        responseData.success = false;
                    }
                }
                else {
                    if (!cardOwner.equals(login)) {
                        responseData.message = "<span class='pay_err_msg'>This card is already in use.</span>";
                        responseData.success = false;
                    }
                }
            }
            else {
            Card[] cards = cardDao.selectCardsByLogin(req.getSession(false).getAttribute("loggedInUser").toString());

            payCard = null;
            for (Card card : cards ) {
                if (card.getCardNo().equals(cardNo)) {
                    payCard = card;
                    break;
                }
            }
            if (payCard == null) {
                responseData.message = "<span class='pay_err_msg'>Card can't be processed</span>";
                responseData.success = false;
            }

            token = payCard.getToken();  // token from bank response

        }



        // connecting to bank;
        // retrieving paydate.

        //if OK
        boolean success = true;

        if (responseData.success) {

            PaymentDAO paymentDao = oracleDaoFactory.getPaymentDAO();
            PhoneDAO phoneDao = oracleDaoFactory.getPhoneDAO();
            Phone userPhone = new Phone(phone,"");
            if (!phoneDao.isExistPhone(userPhone,login)) {
                if(!phoneDao.insertPhone(userPhone, login)) {
                    responseData.message = "<span class='pay_err_msg'>Unknown error!</span>";
                    responseData.success = false;
                }
            }
            Payment payment = new Payment(Float.parseFloat(amountString),phone,payCard);
            int id = paymentDao.insertPayment(payment, login);


            if (id>0) {
                responseData.message += "<span class='pay_err_msg'>Payment processed!</span>";
                responseData.success = true;
                String json_answer = gson.toJson(responseData);
                out.write(json_answer);
            }
            else {
                responseData.message += "Payment cannot be processed!";
                responseData.success = false;
                String json_answer = gson.toJson(responseData);
                out.write(json_answer);
            }

        }
        else {
            String json_answer = gson.toJson(responseData);
            out.write(json_answer);
        }
    return;
    }

}

