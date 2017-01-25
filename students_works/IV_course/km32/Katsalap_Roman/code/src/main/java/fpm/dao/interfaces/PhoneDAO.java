package fpm.dao.interfaces;

import fpm.entities.Phone;

/**
 * Created by Roma on 29.12.2016.
 */
public interface PhoneDAO {

    public boolean insertPhone(Phone phone, String login);
    public boolean isExistPhone(Phone phone, String login);
    public boolean deleteCard(Phone phone);
    public Phone selectBy();
    public Phone[] selectPhonesByLogin(String login);
}

