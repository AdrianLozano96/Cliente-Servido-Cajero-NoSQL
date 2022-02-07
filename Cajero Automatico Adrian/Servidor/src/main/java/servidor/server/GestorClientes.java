package servidor.server;

import servidor.database.MiMongo;
import servidor.model.Movimientos;
import servidor.model.Usuarios;
import servidor.util.Sesion;
import servidor.util.Utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class GestorClientes extends Thread{

    Socket socket;
    boolean sesion = false;
    static boolean activo = false;

    double cantidad;
    String correo;
    String password;
    int opcion;
    DataOutputStream datoRespuestaDelServidor = null;
    DataInputStream datoPeticionDelCliente = null;
    MiMongo miMongo = MiMongo.getInstance();

    public GestorClientes(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {


        try {


            datoRespuestaDelServidor = new DataOutputStream(socket.getOutputStream());
            datoPeticionDelCliente = new DataInputStream(socket.getInputStream());

            while(!sesion) {
                iniciar(datoRespuestaDelServidor, datoPeticionDelCliente);
            }

            Usuarios usuario = Sesion.usuarioSesion(correo, password);
            Movimientos movimiento = new Movimientos(LocalDateTime.now(), usuario);


            while(!activo){  //probar do while

                datoRespuestaDelServidor.writeUTF(menuOpciones());  //enviamos el menu al cliente
                opcion = datoPeticionDelCliente.readInt();  //1º Se obtiene la opción deseada por el cliente

                switch(opcion){
                    case 1:
                        ingresar(usuario, movimiento, datoRespuestaDelServidor, datoPeticionDelCliente);
                        break;
                    case 2:
                        retirar(usuario, movimiento, datoRespuestaDelServidor, datoPeticionDelCliente);
                        break;
                    case 3:
                        consultar(movimiento, datoRespuestaDelServidor);
                        break;
                    case 4:
                        salir();
                        break;
                }
            }

        } catch (IOException | SQLException e) {
            //e.printStackTrace();
        }
    }

    private void iniciar(DataOutputStream datoRespuestaDelServidor, DataInputStream datoPeticionDelCliente) throws IOException {
        //Obtener el correo y la contraseña del usuario y lo almaceno en variables
        correo = datoPeticionDelCliente.readUTF();
        password = datoPeticionDelCliente.readUTF();
        //se verifican con un método y si son correctos se sigue con el programa sino se sale
        sesion = Sesion.inicioSesion(correo, password);
        //Se envia un mensaje indicando si los datos son o no correctos
        datoRespuestaDelServidor.writeUTF("Resultado de inicio de sesión: " + sesion);
        //Enviar sesion
        datoRespuestaDelServidor.writeBoolean(sesion);
        //si la sesion esta bien se sige sino se sale?
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
    //Opción de ingresar dinero
    private void ingresar(Usuarios usuario, Movimientos movimiento, DataOutputStream datoRespuestaDelServidor, DataInputStream datoPeticionDelCliente) throws IOException, SQLException {
        System.out.println("Has seleccionado la Opción 1 Ingresar dinero");
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 1 Ingresar dinero");
        cantidad = datoPeticionDelCliente.readDouble();  //2º Se obtiene la cantidad deseada del cliente
        movimiento.ingreso(cantidad);   //3º Se modifican los datos necesarios del objeto
        miMongo.update(usuario);
        datoRespuestaDelServidor.writeUTF(movimiento.getMovimientoRealizado());   //4º Se envia un mensaje al cliente
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, movimiento.getMovimientoRealizado());
        datoRespuestaDelServidor.flush();
    }
    //Opción de retirar dinero
    private void retirar(Usuarios usuario, Movimientos movimiento, DataOutputStream datoRespuestaDelServidor, DataInputStream datoPeticionDelCliente) throws IOException, SQLException {
        System.out.println("Has seleccionado la Opción 2 Retirar dinero");
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 2 Retirar dinero");
        cantidad = datoPeticionDelCliente.readDouble();  //2º Se obtiene la cantidad deseada del cliente

        movimiento.retiro(cantidad);   //3º Se modifican los datos necesarios del objeto
        miMongo.update(usuario);
        datoRespuestaDelServidor.writeUTF(movimiento.getMovimientoRealizado());   //4º Se envia un mensaje al cliente
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, movimiento.getMovimientoRealizado());
        datoRespuestaDelServidor.flush();
    }
    //Opción de consultar dinero
    private void consultar(Movimientos movimiento, DataOutputStream datoRespuestaDelServidor) throws IOException {
        System.out.println("Has seleccionado la Opción 3 Consultar saldo");
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 3 Consultar saldo");
        datoRespuestaDelServidor.writeUTF("Saldo actual de la cuenta: "+movimiento.hucha());
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Saldo actual de la cuenta: "+movimiento.hucha());
        datoRespuestaDelServidor.flush();
    }
    //Opción salir el cliente
    private void salir() throws IOException {
        System.out.println("Has seleccionado la Opción 4 Salir");
        Utils.registrarInfo(GestorClientes.class, Utils.TipoLog.INFO, "Has seleccionado la Opción 4 Salir");
        //System.exit(0);
        //this.activo = true;
        datoRespuestaDelServidor.flush();
        datoRespuestaDelServidor.close();
        datoPeticionDelCliente.close();
        //socket.close();
        //Cajero.setActivo(true);
        //parar();
        //this.interrupt();
    }

    //Para parar el while VER
    public static void parar(){
        activo = true;
    }

}