package cliente;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;

public class Clientes {

    private static int opcion = 0;
    private static double cantidad;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        InetAddress ip;
        Socket servidor = null;
        int puerto = 7777;
        DataOutputStream datoPeticion = null;
        DataInputStream datoRespuesta = null;

        try{
            ip = InetAddress.getLocalHost();
            servidor = new Socket(ip, puerto);
            System.out.println("Conectado al servidor con la ip "+ servidor.getInetAddress()+" y el puerto "+servidor.getPort());
            datoPeticion = new DataOutputStream(servidor.getOutputStream());
            datoRespuesta = new DataInputStream(servidor.getInputStream());

            //Envia el correo y la contraseña
            //Recibe una respuesta si es correcto sigue sino sale un mensaje dando error y que lo vuelva a intentar
            //poner un while(dataosVerificados)

            do{


                System.out.println(datoRespuesta.readUTF());  //Lee el menu del servidor
                opcion = sc.nextInt();
                datoPeticion.writeInt(opcion);  //1º Envío la opcion que es un dato

                switch(opcion){
                    case 1:
                        System.out.println("Has seleccionado la Opción 1 Ingresar dinero");
                        System.out.println("Indica la cantidad a ingresar");
                        cantidad = sc.nextDouble();
                        datoPeticion.writeDouble(cantidad); //2º Envio la cantidad que es un dato
                        System.out.println("Se ha ingresado dinero por una cantidad de "+cantidad);
                        String ingre = datoRespuesta.readUTF(); //3º Recibibimos información del Servidor (poner el sout de arriba)
                        System.out.println(ingre);
                        datoPeticion.flush();
                        break;
                    case 2:
                        System.out.println("Has seleccionado la Opción 2 Retirar dinero");
                        System.out.println("Indica la cantidad a retirar");
                        cantidad = sc.nextDouble();
                        datoPeticion.writeDouble(cantidad); //2º Envio la cantidad que es un dato
                        System.out.println("Se ha retirado dinero por una cantidad de "+cantidad);
                        String reti = datoRespuesta.readUTF(); //3º Recibibimos información del Servidor (poner el sout de arriba)
                        System.out.println(reti);
                        datoPeticion.flush();
                        break;
                    case 3:
                        System.out.println("Has seleccionado la Opción 3 Consultar saldo");
                        System.out.println(datoRespuesta.readUTF());    //2º Recibo la respuesta del servidor que será el saldo total
                        datoPeticion.flush();
                        break;
                    case 4:
                        System.out.println("Has seleccionado la Opción 4 Salir");
                        break;
                }
            }while(opcion!=4);

        }catch(IOException e){
            System.out.println("Error: "+e);
        }

        /*
        finally{
            try {
                peticion.close();
                respuesta.close();
                servidor.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

         */

    }

}
