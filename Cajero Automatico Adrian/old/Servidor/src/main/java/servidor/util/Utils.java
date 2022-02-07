package servidor.util;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {
    /**
     *  Método para pasar un cadena a SHA-512
     * @param password String
     * @return String contraseña en SHA-515
     */
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
        StringBuffer sb = new StringBuffer();
        for(byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Para la realización del Log con Maven
     */
    public enum TipoLog {
        DEBUG, ERROR, FATAL, INFO, WARNING
    }

    private static Logger log = Logger.getLogger(Utils.class);

    @SuppressWarnings("rawtypes")
    public static void registrarInfo(Class clase, Utils.TipoLog tipo, String mensaje)
    {
        log = LogManager.getLogger(clase);
        switch (tipo)
        {
            case DEBUG:
                log.debug(mensaje);
                break;
            case ERROR:
                log.error(mensaje);
                break;
            case FATAL:
                log.fatal(mensaje);
                break;
            case INFO:
                log.info(mensaje);
                break;
            case WARNING:
                log.warn(mensaje);
        }
    }

}
