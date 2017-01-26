package fpm.dao.interfaces;

import fpm.entities.Status;
import fpm.entities.User;

import java.sql.Timestamp;

/**
 * Created by Guest on 24.12.2016.
 */
public interface UserDAO {

    public int insertUserWithPassword(User user,String pass,String salt,String confirmLink);
    public boolean deleteUser(User user);
    public boolean updateUser(User user);
    public User getUserByLogin(String login);
    public boolean isAlreadyExists(String login, boolean bylogin);
    public int[] getUsersCount();
    public User[] getExtendUsers(String login, String email, String role, String status, Timestamp reg_from, Timestamp reg_till);
    public User[] getUserStatuses(String login);
}
