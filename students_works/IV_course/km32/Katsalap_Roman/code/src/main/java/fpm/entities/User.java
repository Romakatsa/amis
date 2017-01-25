package fpm.entities;

public class User {

    private String login;
    private String email;
    private String hash;
    private String salt;
    private String hashlink;
    private Status status;
    private String status_msg;
    private boolean isAdmin;

    public User() {

    }

    public User(String login,String email,String hash, String salt, String hashlink, Status status) {
        this.email=email;
        this.login=login;
        this.status = status;
        this.salt = salt;
        this.hash = hash;
        this.hashlink = hashlink;
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
}
