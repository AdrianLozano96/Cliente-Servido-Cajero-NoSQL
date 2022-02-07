package cliente;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Cifrado {

    public static String cifradoSHA512(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
        byte[] hash = md.digest(password.getBytes());
        //byte[] hash = md.digest(password.getBytes("UTF-8"));
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

}
