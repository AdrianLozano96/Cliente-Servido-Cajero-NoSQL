package servidor.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Movimientos implements Serializable {

    private LocalDateTime momento; //fecha y hora en las que se realizo la transacción
    //private double valor; //valor de la transacción dinero a sacar o ingresar
    private double dinero;  //almacena el sueldo actual del usuario para poderlo actualizar según el movimiento realizado
    private Usuario usuario;    //Usuario que realiza la transacción
    private String movimientoRealizado ="No se ha realizado ningun movimiento"; //Para saber que ha pasado ( no es necesari pero esta bien)

    public Movimientos() {
        momento = LocalDateTime.now();
        //valor = 100;
        usuario = new Usuario();
    }

    public Movimientos(LocalDateTime momento, Usuario usuario) {
        this.momento = momento;
        this.usuario = usuario;
    }

    public synchronized void ingreso(double cantidad){  //Para meter dinero
        dinero = usuario.getSaldo();
        dinero+=cantidad;
        usuario.setSaldo(dinero);
        movimientoRealizado = "Se ha realizado un ingreso de "+cantidad+" quedando la cuenta del usuario "+
                usuario.getCorreo()+" con un importe de "+dinero;
    }

    public synchronized void retiro(double cantidad){   //Para sacar dinero
        dinero = usuario.getSaldo();
        dinero-=cantidad;
        usuario.setSaldo(dinero);
        movimientoRealizado = "Se ha realizado un retiro de "+cantidad+" quedando la cuenta del usuario "+
                usuario.getCorreo()+" con un saldo de "+dinero;
    }

    public synchronized String hucha(){   //Para ver el dinero que le queda al usuario (poner un if por si se queda sin dinero u algo)
        dinero = usuario.getSaldo();
        String dineroHucha ="";
        if(saldoEstado())
            dineroHucha = "Enorabuena aun tienes saldo suficiente en tu cuenta, tu saldo actual es de "+dinero;
        if(!saldoEstado())
            dineroHucha = "No tienes saldo suficien en tu cuenta, debes dinero ya que tienes un saldo de "+dinero;
        return dineroHucha;
    }

    public synchronized boolean saldoEstado(){    //Para comprobar el estado del saldo
        dinero = usuario.getSaldo();
        if (dinero>=0)
            return true;
        return false;
    }

    public LocalDateTime getMomento() {return momento;}
    public void setMomento(LocalDateTime momento) {this.momento = momento;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public double getDinero() {return dinero;}
    public String getMovimientoRealizado() {return movimientoRealizado;}

    @Override
    public String toString() {
        return "Movimientos{" +
                "momento=" + momento +
                ", usuario=" + usuario +
                ", movimientoRealizado='" + movimientoRealizado + '\'' +
                '}';
    }
}