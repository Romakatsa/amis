package fpm.dao.oracle;

import fpm.dao.interfaces.PhoneDAO;
import fpm.entities.Phone;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Roma on 29.12.2016.
 */
public class OraclePhoneDAO implements
        PhoneDAO {

    public OraclePhoneDAO() {
        // initialization
    }

    private Connection con;

    @Override
    public boolean insertPhone(Phone phone, String login) {
        this.con = OracleDAOFactory.open();
        PreparedStatement ins = null;
        int id = -1;
        try {
            ins = con.prepareStatement("insert into Users_Phones (login,phone_number,phone_name) values (?,?,?)");
            ins.setString(1,login);

            ins.setString(2,phone.getPhoneNumber());
            ins.setString(3,phone.getPhoneName());
            id = ins.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        if (id == -1) {
            return false;
        }
        else {
            return true;
        }
    }

    @Override
    public boolean deleteCard(Phone phone) {
        return false;
    }

    @Override
    public Phone selectBy() {
        return null;
    }

    @Override
    public Phone[] selectPhonesByLogin(String login) {

        this.con = OracleDAOFactory.open();
        ArrayList<Phone> phonesList = new ArrayList<Phone>();

        try {

            PreparedStatement select = con.prepareStatement("Select phone_number,phone_name from Users_Phones where login = ?");
            select.setString(1, login);
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            while (rs.next()) {
                phonesList.add(new Phone(rs.getString(1), rs.getString(2)));
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return phonesList.toArray(new Phone[phonesList.size()]);


    }

    public boolean isExistPhone(Phone phone, String login) {

        this.con = OracleDAOFactory.open();
        PreparedStatement sel = null;
        boolean result = false;
        try {
            sel = con.prepareStatement("select login from users_phones where login = ? and phone_number = ?");

            sel.setString(1,login);
            sel.setString(2,phone.getPhoneNumber());

            ResultSet rs = sel.executeQuery();
            if (!rs.isBeforeFirst()) {
            }
            else {
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return  result;

    }

}