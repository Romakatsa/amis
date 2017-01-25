package fpm.dao.oracle;


import fpm.crypt.Crypt;
import fpm.dao.interfaces.UserDAO;
import fpm.entities.Status;
import fpm.entities.User;

import javax.naming.NamingException;
import javax.swing.text.html.HTMLDocument;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;

public class OracleUserDAO implements UserDAO {


    private final static String SQL_INSERT = "insert into USERS (login,email,hash_pass,salt,hash_link) values (?,?,?,?,?)";
    private final static String INSERT_STATUS = "insert into USERS_ACCOUNT_STATUSES (login, status_name, status_message) values (?,?,?)";
    private final static String SQL_UPDATE = "update USERS set email=?, password=?,user_role=? where ROMA.USERS.login = ?";
    private Connection con;

    public OracleUserDAO() {

    }




    @Override
    public int insertUserWithPassword(User user,String pass,String salt,String confirmLink) {

        this.con = OracleDAOFactory.open();
        PreparedStatement ins = null;
        int ins_user_num = 0;
        int ins_status_num = 0;
        try {
            con.setAutoCommit(false);
            ins = con.prepareStatement(SQL_INSERT);
            ins.setString(1, user.getLogin());
            ins.setString(2, user.getEmail());
            ins.setString(3, user.getHash());
            ins.setString(4, user.getSalt());
            ins.setString(5, confirmLink);
            ins_user_num = ins.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();

        }
        //OracleDAOFactory.close(con);
        //this.con = OracleDAOFactory.open();
        PreparedStatement ins2 = null;
        try {
            con.setAutoCommit(false);
            ins2 = con.prepareStatement(INSERT_STATUS);
            //Calendar cal = Calendar.getInstance();
            //java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            ins2.setString(1,user.getLogin());
            String str = user.getStatus().toString();
            ins2.setString(2, user.getStatus().toString());
            //ins2.setTimestamp(3, timestamp);
            ins2.setString(3,user.getStatus_msg());
            ins_status_num = ins2.executeUpdate();
            con.commit();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        OracleDAOFactory.close(con);
        return ins_status_num;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        this.con = OracleDAOFactory.open();
        try {
            PreparedStatement update = con.prepareStatement("update USERS set hash_link=?,hash_pass=?,salt=?,email=? WHERE login = ?");
            update.setString(1,user.getHashlink());
            update.setString(2,user.getHash());
            update.setString(3,user.getSalt());
            update.setString(4,user.getEmail());
            update.setString(5,user.getLogin());
            update.executeUpdate();

            PreparedStatement insert = con.prepareStatement("insert into users_account_statuses (login,status_name) values (?,?)");
            insert.setString(1,user.getLogin());
            insert.setString(2,user.getStatus().toString());
            //Calendar cal = Calendar.getInstance();
            //java.sql.Timestamp timestamp = new java.sql.Timestamp(cal.getTimeInMillis());
            //insert.setTimestamp(3,timestamp);
            insert.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override  public User getUserByLogin(String login) {
        User user;
        this.con = OracleDAOFactory.open();
        try {
            PreparedStatement select = con.prepareStatement("select login,email,hash_pass,salt,hash_link,is_admin from USERS where login = ?");
            select.setString(1,login);
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            rs.next();

            // REMAKE AS PROCEDURE
            PreparedStatement selectStatus = con.prepareStatement("select status_name from (select status_name from USERS_ACCOUNT_STATUSES where login = ? ORDER BY date_of_status DESC) WHERE ROWNUM = 1");
            selectStatus.setString(1,login);
            ResultSet result_status = selectStatus.executeQuery();
            if(!result_status.isBeforeFirst()) {
                return null;
            }
            result_status.next();
            // END PROCEDURE

            Status status = Status.valueOf(result_status.getString(1));
            user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getString(5),status);
            user.setAdmin(rs.getBoolean(6));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        OracleDAOFactory.close(con);
        return user;
    }


    @Override
    public boolean isAlreadyExists(String field, boolean bylogin) {
        boolean exist = false;
        this.con = OracleDAOFactory.open();
        PreparedStatement select = null;
        try {
            if (bylogin) {
                select = con.prepareStatement("select login from USERS where login = ?");

            }
            else {
                select = con.prepareStatement("select login from USERS where email = ?");

            }
            select.setString(1,field);
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                exist = false;
            }
            else {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return exist;
    }

}
