package fpm.dao.oracle;


import fpm.dao.interfaces.CardDAO;
import fpm.entities.Card;
import fpm.entities.Payment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OracleCardDAO implements
        CardDAO {

    public OracleCardDAO() {
        // initialization
    }

    private Connection con;

    @Override
    public Card[] selectCardsByLogin(String login) {

        this.con = OracleDAOFactory.open();
        ArrayList<Card> cardsList = new ArrayList<Card>();

        try {

            PreparedStatement select = con.prepareStatement("Select Card_No,Card_Name from Cards where login = ?");
            select.setString(1,login);
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            while(rs.next()) {
                cardsList.add(new Card(rs.getString(1),rs.getString(2)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return cardsList.toArray(new Card[cardsList.size()]);

    }

    @Override
    public String isExistCard(Card card) {

        this.con = OracleDAOFactory.open();
        PreparedStatement sel = null;
        String result = null;
        try {
            sel = con.prepareStatement("select login from cards where card_no = ?");
            sel.setString(1,card.getCardNo());

            ResultSet rs = sel.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            rs.next();
            result = rs.getString(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return result;
    }


    @Override
    public boolean insertCard(Card card, String login) {

        this.con = OracleDAOFactory.open();
        PreparedStatement ins = null;
        int id = -1;
        try {
            ins = con.prepareStatement("insert into cards (card_no, login, token, card_name) values (?,?,?,?)");
            ins.setString(1,card.getCardNo());
            ins.setString(2,login);
            ins.setString(3,card.getToken());
            ins.setString(4,card.getName());
            id = ins.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        if (id != -1) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCard(Card card) {
        return false;
    }

    @Override
    public boolean deleteCard(Card card) {
        return false;
    }

    @Override
    public Card selectBy() {
        return null;
    }
}