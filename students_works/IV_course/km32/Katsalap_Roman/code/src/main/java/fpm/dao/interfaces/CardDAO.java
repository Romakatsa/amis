package fpm.dao.interfaces;

import fpm.entities.Card;

/**
 * Created by Guest on 24.12.2016.
 */
public interface CardDAO {

    public boolean insertCard(Card card, String login);
    public boolean updateCard(Card card);
    public boolean deleteCard(Card card);
    public Card selectBy();
    public Card[] selectCardsByLogin(String login);
    public String isExistCard(Card card);

}
