package servidor.model;

import servidor.util.Utils;

import java.io.Serializable;

public class Usuario implements Serializable {

    private long id;
    private String correo;
    private String password;
    private String passwordSinCifrado;
    private String passwordCifrado;
    private double saldo;

    public Usuario() {
        id = 1;
        correo = "hjbj";
        password = passwordCodificado("supercanica89");
        saldo = 1000;
    }

    public Usuario(long id, String correo, String password, double saldo) {
        this.id = id;
        this.correo = correo;
        this.passwordSinCifrado = password;
        this.password = passwordCodificado(password);
        this.saldo = saldo;
    }

    public synchronized String passwordCodificado(String password){
        passwordCifrado = Utils.cifradoSHA512(password);
        return passwordCifrado;
    }


    public long getId() {return id;}
    public void setId(long id) {this.id = id;}

    public String getCorreo() {return correo;}
    public void setCorreo(String correo) {this.correo = correo;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public double getSaldo() {return saldo;}
    public void setSaldo(double saldo) {this.saldo = saldo;}

    public String getPasswordSinCifrado() {return passwordSinCifrado;}
    public void setPasswordSinCifrado(String passwordSinCifrado) {this.passwordSinCifrado = passwordSinCifrado;}

    public String getPasswordCifrado() {return passwordCifrado;}

    public void setPasswordCifrado(String passwordCifrado) {this.passwordCifrado = passwordCifrado;}

    @Override
    public String toString() {
        return "Usuario con los datos: " +
                "id=" + id +
                ", correo='" + correo + '\'' +
                ", password='" + password + '\'' +
                ", saldo=" + saldo;
    }

}
