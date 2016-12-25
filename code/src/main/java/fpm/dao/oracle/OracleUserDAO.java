package fpm.dao.oracle;


import fpm.crypt.Crypt;
import fpm.dao.interfaces.UserDAO;
import fpm.entities.Status;
import fpm.entities.User;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleUserDAO implements UserDAO {


    private final static String SQL_INSERT = "insert into users (login,email,hash,salt,status) values (?,?,?,?,?)";
    private final static String SQL_UPDATE = "update users set email=?, password=?,status=? where login = ?";
    private Connection con;

    public OracleUserDAO() {

    }




    @Override
    public int insertUserWithPassword(User user,String pass,String salt) {

        this.con = OracleDAOFactory.open();
        PreparedStatement ins = null;
        try {
            ins = con.prepareStatement(SQL_INSERT);
            ins.setString(1,user.getLogin());
            ins.setString(2,user.getEmail());
            ins.setString(3,user.getHash());
            ins.setString(4,user.getSalt());
            ins.setObject(5,user.getStatus().toString());       //  SQL TARGET TYPE ?
            int num = ins.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        OracleDAOFactory.close(con);
        return 0;
    }

    @Override
    public boolean deleteUser(User user) {
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override  public User getUserByLogin(String login) {
        User user;
        this.con = OracleDAOFactory.open();
        try {
            PreparedStatement select = con.prepareStatement("select * from users where login = ?");
            select.setString(1,login);
            ResultSet rs = select.executeQuery();
            if (!rs.isBeforeFirst()) {
                return null;
            }
            rs.next();
            Status status = Status.valueOf(rs.getString(5));
            user = new User(rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4),status);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        OracleDAOFactory.close(con);
        return user;
    }


    @Override
    public boolean isAlreadyExists(String login) {
        boolean exist = false;
        this.con = OracleDAOFactory.open();
        try {
            PreparedStatement select = con.prepareStatement("select login from users where login = ?");
            select.setString(1,login);
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
