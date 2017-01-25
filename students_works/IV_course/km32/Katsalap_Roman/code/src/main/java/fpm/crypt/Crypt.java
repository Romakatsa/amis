package fpm.crypt;

import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Guest on 25.12.2016.
 */
public class Crypt {

    private static String localParam = "HappyNewYear";

    public static String[] crypt(String password) throws UnsupportedEncodingException {


        String salt = BCrypt.gensalt(12);
        String fullsalt = salt.concat(localParam);
        String hash = BCrypt.hashpw(password,fullsalt);
        String arr[] = new String[2];
        arr[0] = hash;
        arr[1] = salt;
        return arr;

    }

    public static String getHash(String password,String salt) {
        String fullsalt = salt.concat(localParam);
        String hash = BCrypt.hashpw(password,fullsalt);
        return hash;
    }

    public static String crypt(String link, String salt) {

        String hash = BCrypt.hashpw(link,salt);
        return hash;

    }

    public static String getRandomString(int length) {
        Random rng = new Random();
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789_-";
        char[] randomChars = new char[length];
        for (int i = 0; i < length; i++) {
            randomChars[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(randomChars);
    }

    public static String hideCharsInEmail(String email) {
        int atPosition = email.indexOf('@');
        if (atPosition > 2) {
            String unmask1 = email.substring(0,2);
            char[] maskchars = new char[atPosition - 2];
            Arrays.fill(maskchars,'*');
            String mask = new String(maskchars);
            String unmask2 = email.substring(atPosition);
            return unmask1 + mask + unmask2;
        }
        return "Error";
    }

}
