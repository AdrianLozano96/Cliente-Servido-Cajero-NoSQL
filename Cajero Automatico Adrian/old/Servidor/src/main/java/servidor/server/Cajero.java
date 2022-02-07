package servidor.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cajero extends Thread {

    public static boolean activo = true;   //Lo puede llamar el cliente al indicar salir poniendolo a false y cerrando el servidor
    public Cajero(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        ServerSocket server = null;
        Socket cliente = null;
        int puerto = 7777;
        //boolean activo = true;
        //movimiento.saldoEstado()
        System.out.println("Servidor en funcionamiento ...");

        try {
            server = new ServerSocket(puerto);   //Se declara el servidor.
            while(activo){
                System.out.println("Esperando conexión ...");
                cliente = server.accept();
                //System.out.println("Info: "+cliente.getInetAddress()+" "+cliente.getPort());
                //GestorClientes gc = new GestorClientes(cliente, movimiento);
                GestorClientes gc = new GestorClientes(cliente);
                gc.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            System.out.println("El servidor ha finalizado");
            try {
                server.close();
                cliente.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parar(){
        activo = false;
    }


    //public boolean isActivo() {return activo;}
    //public void setActivo(boolean activo) {this.activo = activo;}
}
