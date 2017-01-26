package fpm.entities;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class User {

    private String login;
    private String email;
    private String hash;
    private String salt;
    private String hashlink;
    private Status status;
    private String status_msg;
    private boolean isAdmin;

    private Timestamp reg_date;
    private Timestamp status_date;

    public User() {

    }

    public User(Status status, Timestamp status_date, String status_msg) {
        this.status = status;
        this.status_date = status_date;
        this.status_msg = status_msg;
    }

    public User(String login,String email,String hash, String salt, String hashlink, Status status) {
        this.email=email;
        this.login=login;
        this.status = status;
        this.salt = salt;
        this.hash = hash;
        this.hashlink = hashlink;
    }

    public User(String login, String email, int isAdmin,  Timestamp reg_date, Timestamp status_date, Status status) {
        this.login = login;
        this.email = email;
        this.status = status;
        this.isAdmin = isAdmin > 0 ? true : false;
        this.reg_date = reg_date;
        this.status_date = status_date;
    }

    public String getStatus_msg() {return status_msg;}
    public void setStatus_msg(String status_msg) {this.status_msg = status_msg;}
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getHash() {
        return hash;
    }
    public String getLogin() {
        return login;
    }
    public Status getStatus() {
        return status;
    }
    public void setPassword(String password) {
        this.hash = password;
    }
    public String getSalt() {return salt;}
    public void setStatus(Status status) {this.status = status;}
    public void setHashlink(String hashlink) {this.hashlink = hashlink;}
    public String getHashlink() { return hashlink;}
    public void setSalt(String salt) {this.salt = salt;}
    public boolean getAdmin() {return isAdmin;}
    public void setAdmin(boolean isAdmin) {this.isAdmin = isAdmin;}

    public void setReg_date(Timestamp reg_date) {this.reg_date = reg_date;}
    public String getRegDateTimeFormatted() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        return formatter.format(reg_date);
    }
    public void setStatus_date(Timestamp status_date) {this.status_date = status_date;}
    public String getStatusDateTimeFormatted() {
        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        return formatter.format(status_date);
    }
    public String getRole() {
        return isAdmin ? "admin" : "user";
    }
}
