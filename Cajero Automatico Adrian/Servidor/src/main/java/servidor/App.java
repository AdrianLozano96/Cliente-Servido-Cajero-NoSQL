package servidor;

import servidor.database.MiMongo;
import servidor.model.Usuarios;

import java.util.List;

public class App {

    public static void main(String[] args) {

        MiMongo miMongo = MiMongo.getInstance();

        miMongo.conectar();
        miMongo.eliminarColeccion();
        List<Usuarios> misUsuarios = miMongo.listaUsuarios();

        miMongo.insertarVariosUsuarios(misUsuarios);

    }

}
