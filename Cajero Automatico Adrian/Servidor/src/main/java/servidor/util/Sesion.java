package servidor.util;

import com.mongodb.client.MongoCollection;
import servidor.database.MiMongo;
import servidor.database.MongoController;
import servidor.model.Movimientos;
import servidor.model.Usuarios;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class Sesion {

    static MiMongo miMongo = MiMongo.getInstance();
    static MongoController controller = MongoController.getInstance();


    //Me devuleve los usuarios de la base de datos
    public static List<Usuarios> listaUsuariosDB(){
        // Me conecto a la colección
        MongoCollection<Usuarios> misUsuarios = //miMongo.collectionAccess();
        controller.getCollection("cajero", "usuarios", Usuarios.class);
        List<Usuarios>losUsuarios = misUsuarios.find().into(new ArrayList<>());
        return losUsuarios;
    }

    //Se comprueba que se ha iniciado sesión correctaente
    public static boolean inicioSesion(String mail, String passw){
        boolean verificado = false;
        List<Usuarios> usuarios = listaUsuariosDB();
        for(Usuarios user : usuarios){
            if(user.getCorreo().equalsIgnoreCase(mail) && user.getPassword().equals(passw))
                verificado = true;
        }
        return verificado;
    }

    //Devuelve el usuario que ha iniciado sesión
    public static Usuarios usuarioSesion(String mail, String passw){
        Usuarios usu = null;
        List<Usuarios> usuarios = listaUsuariosDB();
        for(Usuarios user : usuarios){
            if(user.getCorreo().equalsIgnoreCase(mail) && user.getPassword().equals(passw))
                usu = user;
        }
        return usu;
    }

/*
    public static void main(String[] args) throws SQLException {

        conexion();
        mongoController.listaDB();
        mongoController.mostrarDocs();

        String correo;
        String password;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduzca usuario: ");
        correo = sc.nextLine();
        System.out.println("Introduzca contraseña: ");
        password = sc.nextLine();

        boolean inicio = inicioSesion(correo, password);
        System.out.println(inicio);

        if(inicio){
            //Usuarios usuario = usuarioSesion(correo, password);
            Usuarios usuario = mongoController.suUsuario(correo);
            //usuario.setPasswordCifrado(password);
            Movimientos movimiento = new Movimientos(LocalDateTime.now(), usuario);
            //usuario.setSaldo(6046.0);
            movimiento.ingreso(64.0);
            //usuario.setPasswordCifrado(password);
            mongoController.update(usuario);
            //System.out.println(movimiento.toString());
            System.out.println(usuario.toString());
        }
        System.out.println("Buscar");
        mongoController.buscarUsuario(correo);


    }

 */

}

