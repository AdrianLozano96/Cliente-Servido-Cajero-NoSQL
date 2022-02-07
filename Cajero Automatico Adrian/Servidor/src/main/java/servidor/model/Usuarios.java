package servidor.model;


import org.bson.types.ObjectId;
import servidor.util.Utils;

import java.io.Serializable;


public class Usuarios implements Serializable {

    private ObjectId id;
    private String correo;
    private String password;    //contraseña original
    private double saldo;
    //private String passwordCifrado; //con SHA-512

    public Usuarios() {}

    //Constructor para hacer insercciones
    public Usuarios(String correo, String password, double saldo) {
        this.correo = correo;
        this.password = passwordCodificado(password);
        //this.passwordCifrado = passwordCodificado(password);
        this.saldo = saldo;
    }

    //Constructor para consutar y el que se utilizará
    public Usuarios(ObjectId id, String correo, String password, double saldo) {
        this.id = id;
        this.correo = correo;
        this.password = passwordCodificado(password);
        //this.passwordCifrado = passwordCodificado(password);
        this.saldo = saldo;
    }

    public ObjectId getId() {return id;}
    public void setId(ObjectId id) {this.id = id;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public double getSaldo() {return saldo;}

    public void setSaldo(double saldo) {this.saldo = saldo;}

    //public String getPasswordCifrado() {this.passwordCifrado = passwordCodificado(password);return passwordCifrado;}
    //public void setPasswordCifrado(String passwordCifrado) {this.passwordCifrado = passwordCodificado(password);}

    public synchronized String passwordCodificado(String password){
        String passwordSHA = Utils.cifradoSHA512(password);
        return passwordSHA;
    }


    @Override
    public String toString() {
        return "Usuario con el " +
                "id: " + id +
                ", correo: '" + correo + '\'' +
                ", password: '" + password + '\'' +
                ", saldo: " + saldo;
    }
}
