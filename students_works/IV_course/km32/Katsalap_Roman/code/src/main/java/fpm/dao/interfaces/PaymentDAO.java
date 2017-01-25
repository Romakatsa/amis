package fpm.dao.interfaces;

import fpm.entities.Payment;

/**
 * Created by Guest on 24.12.2016.
 */
public interface PaymentDAO {

    public Payment[] selectPaymentsByLogin(String login);
    public int insertPayment(Payment payment, String login);

}
