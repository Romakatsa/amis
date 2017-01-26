package fpm.dao.oracle;


import fpm.dao.interfaces.PaymentDAO;
import fpm.entities.Card;
import fpm.entities.Payment;
import fpm.entities.User;

import java.sql.*;
import java.time.ZoneId;
import java.util.ArrayList;
import java.time.OffsetDateTime;
import java.util.List;

public class OraclePaymentDAO implements
        PaymentDAO {

    public OraclePaymentDAO() {
        // initialization
    }

    private Connection con;

    @Override
    public Payment[] selectPaymentsByLogin(String login) {

        ArrayList<Payment> paymentsList = new ArrayList<Payment>();
        this.con = OracleDAOFactory.open();

        try {



            PreparedStatement select = con.prepareStatement("Select * from cardspayments where login = ?");
                select.setString(1,login);
                ResultSet rs = select.executeQuery();
                if (!rs.isBeforeFirst()) {
                return null;
                }
                while(rs.next()) {
                    paymentsList.add(new Payment(rs.getTimestamp(2),rs.getFloat(3),rs.getString(4),new Card(rs.getString(5),rs.getString(7)), rs.getInt(1)));
                }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return paymentsList.toArray(new Payment[paymentsList.size()]);

    }

    @Override
    public int insertPayment(Payment payment,String login) {

        this.con = OracleDAOFactory.open();
        PreparedStatement ins = null;
        int id = -1;
        try {
            ins = con.prepareStatement("insert into payments (amount,phone_number,Card_No,login) values (?,?,?,?)",new String[]{"PAYMENT_ID"});

            ins.setFloat(1,payment.getAmount());
            ins.setString(2,payment.getPhone());
            ins.setString(3,payment.getCard().getCardNo());
            ins.setString(4,login);
            id = ins.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return id;

    }

}