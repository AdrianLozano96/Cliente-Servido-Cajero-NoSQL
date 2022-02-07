package servidor.database;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndReplaceOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;
import org.bson.types.ObjectId;
import servidor.model.Usuarios;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mongodb.client.model.Filters.eq;

public class MiMongo {

    private MongoController controller = MongoController.getInstance();
    MongoCollection<Usuarios> usuariosCollection = collectionAccess();
    //MongoCollection<Usuarios> usuariosCollection = collectionAccessGenerico("cajero", "usuarios", Usuarios.class);
            //controller.getCollection("cajero", "usuarios", Usuarios.class);

    private static MiMongo mongo;
    private MiMongo(){}
    public static MiMongo getInstance(){
        if(mongo == null){mongo = new MiMongo();}
        return mongo;
    }

    //Me conecto a Mongo
    public void conectar(){
        controller.open();
    }

    //Me desconecto de Mongo
    public void desconectar(){
        controller.close();
    }

    //Se muestran todas las bases de datos
    public void listaDB(){
        // Obtenemos las bases de datos
        Optional<List<Document>> databases = controller.getDataBases();
        System.out.println("Todos las bases de datos existentes");
        databases.ifPresent(documents -> documents.forEach(db -> System.out.println(db.toJson())));
    }


    //Me conecto a una GENERAL
    public <TCollection> MongoCollection<TCollection> collectionAccessGenerico(String dataBaseName,
                                                                       String collectionName,
                                                                       java.lang.Class<TCollection> collectionClass){
        conectar();
        // Me conecto a la colección de departamentos
        MongoCollection<TCollection> miCollection = controller
                .getCollection(dataBaseName, collectionName, collectionClass);
        return miCollection;
    }

    //Me conecto a una colección
    public MongoCollection<Usuarios> collectionAccess(){
        conectar(); //IMPORTANTE
        // Me conecto a la colección de departamentos
        MongoCollection<Usuarios> miCollection = controller
                .getCollection("cajero", "usuarios", Usuarios.class);
        return miCollection;
    }

    //Borra la coleccion
    public void eliminarColeccion(){
        // La borro para tenerla limpia cada colección
        controller.removeCollection("cajero", "usuarios");
    }

    public void eliminarDB(){
        // incluso la BD completa
        controller.removeDataBase("test");
    }


    //CRUD

    public List<Usuarios> listaUsuarios() {
        // Vamos con los usuarios
        System.out.println("Añadiendo usuarios...");
        // Usuarios
        Usuarios user1 = new Usuarios(new ObjectId(), "user@1.com", "Usuario1", 1000.0);
        Usuarios user2 = new Usuarios(new ObjectId(), "user@2.com", "Usuario2", 2000.0);
        Usuarios user3 = new Usuarios(new ObjectId(), "user@3.com", "Usuario3", 3000.0);
        Usuarios user4 = new Usuarios(new ObjectId(), "user@4.com", "Usuario4", 4000.0);
        Usuarios user5 = new Usuarios(new ObjectId(), "user@5.com", "Usuario5", 5000.0);
        Usuarios user6 = new Usuarios(new ObjectId(), "user@6.com", "Usuario6", 6000.0);
        List<Usuarios> nuevosUsuarios = new ArrayList<>(Arrays.asList(user1, user2, user3, user4, user5, user6));
        return nuevosUsuarios;
    }

    public void insertarVariosUsuarios(List<Usuarios>nuevosUsuarios) {
        System.out.println("Insertando Usuarios");
        usuariosCollection.insertMany(nuevosUsuarios);
    }

    public void insertarUnUsuario(Usuarios usuario){
        System.out.println("Insertar Usuario");
        usuariosCollection.insertOne(usuario);
    }

    public void mostrarDocs(){
        // Recorremos la colección
        System.out.println("Mostrando todos los Usuarios");
        usuariosCollection.find().into(new ArrayList<>()).forEach(System.out::println);
    }

    public Usuarios buscarUsuario(String correo){
        System.out.println("Usuario con el correo "+correo);
        System.out.println("Buscando...");
        Usuarios usuarioBuscado = usuariosCollection.find(eq("correo", correo)).first();
        System.out.println("Encontrado:\t" + usuarioBuscado);
        return usuarioBuscado;
    }


    public Optional<Usuarios> update(Usuarios usuario) throws SQLException {
        try {
            Document filtered = new Document("_id", usuario.getId());
            FindOneAndReplaceOptions returnDoc = new FindOneAndReplaceOptions().returnDocument(ReturnDocument.AFTER);

            return Optional.ofNullable(usuariosCollection.findOneAndReplace(filtered, usuario, returnDoc));
        } catch (Exception e) {
            throw new SQLException("Error Usuario al actualizar con id: " +
                    usuario.getId() + ": " + e.getMessage());
        } finally {
            //controller.close();
        }
    }



}
