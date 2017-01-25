package fpm.entities;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;

/**
 * Created by Guest on 24.12.2016.
 */
public class Payment {

    private Timestamp paydate;
    private float amount;
    private String phone;
    private Card card;
    private int id;

    public Payment(float amount,String phone,Card card) {
        this.amount=amount;
        this.phone=phone;
        this.card=card;
    }

    public Payment(Timestamp paydate,float amount,String phone,Card card, int id) {
        this.paydate=paydate;
        this.amount=amount;
        this.phone=phone;
        this.card=card;
        this.id = id;
    }


    public int getID() {
        return this.id;
    }


    public String getPhone() {
        return phone;
    }

    public float getAmount() {
        return amount;
    }

    public Timestamp getDateTime() {
        return paydate;
    }

    public String getDateTimeFormatted() {

        DateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        return formatter.format(paydate);
    }

    public Timestamp getSqlDate() {

        return paydate;
    }

    public Card getCard() {
        return card;
    }




}
