package servidor.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Cajero extends Thread {

    private boolean activo = false;   //Lo puede llamar el cliente al indicar salir poniendolo a false y cerrando el servidor
    public Cajero(String nombre) {
        super(nombre);
    }

    @Override
    public void run() {
        ServerSocket server = null;
        Socket cliente = null;
        int puerto = 7777;
        System.out.println("Servidor en funcionamiento ...");

        try {
            server = new ServerSocket(puerto);   //Se declara el servidor.
            System.out.println("Esperando conexión ...");
            while(!activo){
                cliente = server.accept();
                System.out.println("Conexión establecida");
                System.out.println("Información de la conexión: Ip:"+cliente.getInetAddress()+" Port: "+cliente.getPort());
                GestorClientes gc = new GestorClientes(cliente);
                gc.start();
                System.out.println("Bienvenido al Cajero "+this.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            System.out.println("El servidor ha finalizado");
            try {
                server.close();
                cliente.close();
                System.out.println("El servidor se ha cerrado");
                //System.exit(0);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isActivo() {return activo;}
    public void setActivo(boolean activo) {this.activo = activo;}
}
