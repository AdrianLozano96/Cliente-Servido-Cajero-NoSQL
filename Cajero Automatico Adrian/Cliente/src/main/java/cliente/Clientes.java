package cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Clientes {

    static double cantidad;
    static String correo;
    static String password;
    static boolean sesion = false; //Almacena si la sesion es true o false
    static DataOutputStream datoPeticion = null;
    static DataInputStream datoRespuesta = null;

    public static void main(String[] args) throws IOException {

        try{

            InetAddress ip = InetAddress.getLocalHost();
            int puerto = 7777;
            Socket servidor = new Socket(ip, puerto);
            System.out.println("Conectado al servidor con la ip "+ servidor.getInetAddress()+" y el puerto "+servidor.getPort());
            datoPeticion = new DataOutputStream(servidor.getOutputStream());
            datoRespuesta = new DataInputStream(servidor.getInputStream());
            Scanner sc = new Scanner(System.in);
            int opcion = 0;

            while(!sesion) {
                iniciarLaSesion(datoPeticion, datoRespuesta, sc);
            }

            do{
                System.out.println(datoRespuesta.readUTF());  //Lee el menu del servidor
                opcion = sc.nextInt();
                datoPeticion.writeInt(opcion);  //1º Envío la opcion que es un dato

                switch(opcion){
                    case 1:
                        ingresarDinero(datoPeticion, datoRespuesta, sc);
                        break;
                    case 2:
                        retirarDinero(datoPeticion, datoRespuesta, sc);
                        break;
                    case 3:
                        consultarSaldo(datoPeticion, datoRespuesta);
                        break;
                    case 4:
                        salir();
                        break;
                }
            }while(opcion!=4);
        }catch(IOException e){
            System.out.println("Error: "+e);
        }
    }

    private static void iniciarLaSesion(DataOutputStream datoPeticion, DataInputStream datoRespuesta, Scanner sc) throws IOException {
        //Envia el correo y la contraseña
        System.out.println("Introduzca usuario: ");
        correo = sc.nextLine();
        System.out.println("Introduzca contraseña: ");
        password = sc.nextLine();
        password = Cifrado.cifradoSHA512(password);
        datoPeticion.writeUTF(correo);
        datoPeticion.writeUTF(password);
        System.out.println(datoRespuesta.readUTF());
        //Recibe una respuesta si es correcto sigue sino sale un mensaje dando error y que lo vuelva a intentar
        sesion = datoRespuesta.readBoolean();
    }

    private static void ingresarDinero(DataOutputStream datoPeticion, DataInputStream datoRespuesta, Scanner sc) throws IOException {
        System.out.println("Has seleccionado la Opción 1 Ingresar dinero");
        System.out.println("Indica la cantidad a ingresar");
        cantidad = sc.nextDouble();
        datoPeticion.writeDouble(cantidad); //2º Envio la cantidad que es un dato
        System.out.println("Se ha ingresado dinero por una cantidad de "+cantidad);
        String ingre = datoRespuesta.readUTF(); //3º Recibibimos información del Servidor (poner el sout de arriba)
        System.out.println(ingre);
        datoPeticion.flush();
    }

    private static void retirarDinero(DataOutputStream datoPeticion, DataInputStream datoRespuesta, Scanner sc) throws IOException {
        System.out.println("Has seleccionado la Opción 2 Retirar dinero");
        System.out.println("Indica la cantidad a retirar");
        cantidad = sc.nextDouble();
        datoPeticion.writeDouble(cantidad); //2º Envio la cantidad que es un dato
        System.out.println("Se ha retirado dinero por una cantidad de "+cantidad);
        String reti = datoRespuesta.readUTF(); //3º Recibibimos información del Servidor (poner el sout de arriba)
        System.out.println(reti);
        datoPeticion.flush();
    }

    private static void consultarSaldo(DataOutputStream datoPeticion, DataInputStream datoRespuesta) throws IOException {
        System.out.println("Has seleccionado la Opción 3 Consultar saldo");
        System.out.println(datoRespuesta.readUTF());    //2º Recibo la respuesta del servidor que será el saldo total
        datoPeticion.flush();
    }

    private static void salir() throws IOException {
        System.out.println("Has seleccionado la Opción 4 Salir");
        datoPeticion.flush();
        datoPeticion.close();
        datoRespuesta.close();
        //servidor.close();
        System.exit(0);
    }

}
