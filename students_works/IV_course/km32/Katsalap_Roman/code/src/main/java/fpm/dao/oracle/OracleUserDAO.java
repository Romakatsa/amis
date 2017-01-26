package fpm.dao.oracle;


import fpm.crypt.Crypt;
import fpm.dao.interfaces.UserDAO;
import fpm.entities.Card;
import fpm.entities.Payment;
import fpm.entities.Status;
import fpm.entities.User;

import javax.naming.NamingException;
import javax.swing.text.html.HTMLDocument;
import java.sql.*;
import java.util.ArrayList;
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

    public int[] getUsersCount() {

        this.con = OracleDAOFactory.open();
        PreparedStatement select = null;
        int unconfirmed = 0;
        int confirmed = 0;
        int banned = 0;
        int reset = 0;
        int active = 0;

        try {

            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            select = con.prepareStatement("with groups as (select login, max(date_of_status) maxdate FROM USERS_ACCOUNT_STATUSES GROUP BY login)" +
                    "select count(*) from USERS_ACCOUNT_STATUSES uas join groups ON groups.maxdate = uas.date_of_status AND groups.login = uas.login WHERE uas.status_name = ?");

            select.setString(1,Status.UNCONFIRMED.toString());
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                unconfirmed = 0;
            }
            else {
                rs.next();
                unconfirmed = rs.getInt(1);
            }


            select.setString(1,Status.CONFIRMED.toString());
            rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                confirmed = 0;
            }
            else {
                rs.next();
                confirmed = rs.getInt(1);
            }


            select.setString(1,Status.RESET.toString());
            rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                reset = 0;
            }
            else {
                rs.next();
                reset = rs.getInt(1);
            }


            select.setString(1,Status.BANNED.toString());
            rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                banned = 0;
            }
            else {
                rs.next();
                banned = rs.getInt(1);
            }

            select.setString(1,Status.ACTIVE.toString());
            rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                active = 0;
            }
            else {
                rs.next();
                active = rs.getInt(1);
            }

            con.commit();


        } catch (SQLException e) {
            e.printStackTrace();
        }

        OracleDAOFactory.close(con);
        return new int[] {unconfirmed,confirmed,reset,banned,active};




    }

    public User[] getExtendUsers(String login, String email, String role, String status, Timestamp reg_from, Timestamp reg_till) {

        this.con = OracleDAOFactory.open();
        PreparedStatement select = null;
        String where = " where ";
        boolean need_and = false;
        ArrayList<User> usersList = new ArrayList<User>();

        if (!status.equals("")) {
            where += " UPPER(ls.STATUS_NAME) = ?";
            need_and = true;
        }

        if (reg_from != null) {
            if (need_and) {
                where += " AND ";
            }
            where += " reg_list.date_of_status > ? ";
            need_and = true;
        }

        if (reg_till != null) {
            if (need_and) {
                where += " AND ";
            }
            where += " reg_list.date_of_status < ? ";
            need_and = true;
        }

        if (!login.equals("")) {
            if (need_and) {
                where += " AND ";
            }
            where += " UPPER(u.login) = ? ";
            need_and = true;
        }


        if (!email.equals("")) {
            if (need_and) {
                where += " AND ";
            }
            where += " UPPER(u.email) like ? ";
            need_and = true;
        }


        if (!role.equals("")) {
            if (need_and) {
                where += " AND ";
            }
            where += " u.is_admin = ? ";
            need_and = true;
        }


        try {
            String sql = "select u.login, u.email, u.is_admin, reg_list.date_of_status, ls.MAXDATE, ls.STATUS_NAME FROM USERS u join last_statuses ls on u.login = ls.login join (select login, date_of_status from USERS_ACCOUNT_STATUSES where status_name = 'UNCONFIRMED') reg_list on u.login = reg_list.login";
            if (need_and) {
                sql += where;
            }
            select = con.prepareStatement(sql);
            int position = 1;
            if (!status.equals("")) {
                select.setString(position,status.toUpperCase());
                position++;
            }
            if (reg_from != null) {
                select.setTimestamp(position,reg_from);
                position++;
            }
            if (reg_till != null) {
                select.setTimestamp(position,reg_till);
                position++;
            }
            if (!login.equals("")) {
                select.setString(position,login.toUpperCase());
                position++;
            }
            if (!email.equals("")) {
                select.setString(position, "%" + email.toUpperCase() + "%");
                position++;
            }
            if (!role.equals("")) {
                if (role.equals("admin")) {
                    select.setInt(position,1);
                }
                else {
                    select.setInt(position,0);
                    //select.setNull(position,Types.INTEGER);
                }
                position++;
            }

            ResultSet rs = select.executeQuery();
            if (rs.isBeforeFirst()) {
                while(rs.next()) {
                    usersList.add(new User(rs.getString(1),rs.getString(2),rs.getInt(3), rs.getTimestamp(4), rs.getTimestamp(5), Status.valueOf(rs.getString(6))));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return usersList.toArray(new User[usersList.size()]);

    }

    public User[] getUserStatuses(String login) {
        this.con = OracleDAOFactory.open();
        PreparedStatement select = null;
        ArrayList<User> usersList = new ArrayList<User>();

        try {
            select = con.prepareStatement("Select status_name, date_of_status,status_message from users_account_statuses where login = ?");
            select.setString(1,login);
            ResultSet rs = select.executeQuery();
            if (rs.isBeforeFirst()) {
                while(rs.next()) {
                    usersList.add(new User(Status.valueOf(rs.getString(1)),rs.getTimestamp(2),rs.getString(3)));
                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }

        OracleDAOFactory.close(con);
        return usersList.toArray(new User[usersList.size()]);
    }




}
