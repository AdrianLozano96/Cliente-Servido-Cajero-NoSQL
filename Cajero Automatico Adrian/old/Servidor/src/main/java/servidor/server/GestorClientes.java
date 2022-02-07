package servidor.server;

import servidor.model.Movimientos;
import servidor.model.Usuario;
import servidor.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class GestorClientes extends Thread{

    Socket socket;
    Movimientos movimiento = eligeCliente(1);
    static boolean activo = true;

    public GestorClientes(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int opcion;
        double cantidad;
        DataOutputStream datoRespuestaDelServidor = null;
        DataInputStream datoPeticionDelCliente = null;

        try {
            //Puedo poner aquí el while para salir
            datoRespuestaDelServidor = new DataOutputStream(socket.getOutputStream());
            datoPeticionDelCliente = new DataInputStream(socket.getInputStream());
            //Obtener el correo y la contraseña del usuario
            //se verifican con un método y si son correctos se sigue con el programa sino se sale
            //Se envia un mensaje indicando si los datos son o no correctos

            while(activo){

                //enviamos el mensaje al cliente
                datoRespuestaDelServidor.writeUTF(menuOpciones());
                opcion = datoPeticionDelCliente.readInt();  //1º Se obtiene la opción deseada por el cliente
                //Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "");
                switch(opcion){
                    case 1:
                        System.out.println("Has seleccionado la Opción 1 Ingresar dinero");
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 1 Ingresar dinero");
                        cantidad = datoPeticionDelCliente.readDouble();  //2º Se obtiene la cantidad deseada del cliente
                        movimiento.ingreso(cantidad);   //3º Se modifican los datos necesarios del objeto
                        datoRespuestaDelServidor.writeUTF(movimiento.getMovimientoRealizado());   //4º Se envia un mensaje al cliente
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, movimiento.getMovimientoRealizado());
                        datoRespuestaDelServidor.flush();
                        break;
                    case 2:
                        System.out.println("Has seleccionado la Opción 2 Retirar dinero");
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 2 Retirar dinero");
                        cantidad = datoPeticionDelCliente.readDouble();  //2º Se obtiene la cantidad deseada del cliente
                        movimiento.retiro(cantidad);   //3º Se modifican los datos necesarios del objeto
                        datoRespuestaDelServidor.writeUTF(movimiento.getMovimientoRealizado());   //4º Se envia un mensaje al cliente
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, movimiento.getMovimientoRealizado());
                        datoRespuestaDelServidor.flush();
                        break;
                    case 3:
                        System.out.println("Has seleccionado la Opción 3 Consultar saldo");
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 3 Consultar saldo");
                        datoRespuestaDelServidor.writeUTF("Saldo actual de la cuenta: "+movimiento.hucha());
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Saldo actual de la cuenta: "+movimiento.hucha());
                        datoRespuestaDelServidor.flush();
                        break;
                    case 4:
                        System.out.println("Has seleccionado la Opción 4 Salir");
                        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 4 Salir");
                        Cajero.parar();
                        parar();
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*
        finally{
            try {
                datoRespuestaDelServidor.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
         */
    }

    //Opciones del Menú
    private static String menuOpciones() {
        String miMenu = "Menú"+"\n"+
                "1.- Ingresar saldo"+"\n"+
                "2.- Retirar saldo"+"\n"+
                "3.- Comprobar saldo"+"\n"+
                "4.- Salir"+"\n"+
                "Introduzca la opción deseada"+"\n";
        return miMenu;
    }

    //Para parar el while VER
    public static void parar(){
        activo = false;
    }

    //Para seleccionar el usuario que realize la transacción
    private static Movimientos eligeCliente(int nCliente) {
        Movimientos m = new Movimientos();
        switch(nCliente){
            case 1:
                Usuario user1 = new Usuario(1, "user@1.com","Usuario1",1000);
                Movimientos m1 = new Movimientos(LocalDateTime.now(), user1);
                m = m1;
                break;
            case 2:
                Usuario user2 = new Usuario(2, "user@2.com","Usuario2",2000);
                Movimientos m2 = new Movimientos(LocalDateTime.now(), user2);
                m = m2;
                break;
        }
        return m;
    }


    

}