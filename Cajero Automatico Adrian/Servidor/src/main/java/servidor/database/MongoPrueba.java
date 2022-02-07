package servidor.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.NonNull;
import org.bson.Document;
import servidor.model.Usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MongoPrueba {
    MongoClient miMongo = crearConexion();

    public MongoClient crearConexion(){
        MongoClient mongo = null;
        String servidor = "localhost";
        Integer puerto = 27017;
        try {
            mongo = new MongoClient(servidor, puerto);
        }catch (MongoException e){
            System.out.println("Error: "+e);
        }
        return mongo;
    }
    /**
     * Cierra la conexión con la base e datos
     */
    public void close() {
        if (miMongo!=null) miMongo.close();
    }

    public Optional<List<Document>> getDataBases() {
        return Optional.of(miMongo.listDatabases().into(new ArrayList<>()));
    }

    public <TDocument> MongoCollection<TDocument> getCollection(@NonNull String dataBaseName,
                                                                @NonNull String collectionName,
                                                                @NonNull java.lang.Class<TDocument> aClass) {
        MongoDatabase dataBase = miMongo.getDatabase(dataBaseName);
        return dataBase.getCollection(collectionName, aClass);
    }

    /**
     * Elimina una base de datos
     * @param dataBaseName Nombre de la Base de Datos
     */
    public void removeDataBase(@NonNull String dataBaseName) {
        MongoDatabase dataBase = miMongo.getDatabase(dataBaseName);
        dataBase.drop();// Si queremos borrar toda la base de datos
    }

    /**
     * Elimina una colleción de una base de datos
     * @param dataBaseName Nombre de la Base de Datos
     * @param collectionName Nombre de la Colección
     */
    public void removeCollection(@NonNull String dataBaseName, @NonNull String collectionName) {
        MongoDatabase dataBase = miMongo.getDatabase(dataBaseName);
        dataBase.getCollection(collectionName).drop();
    }

    public List<Usuarios> findAll() {
        MongoCollection<Usuarios> userCollection = getCollection("cajero", "usuarios", Usuarios.class);
        List<Usuarios> list = userCollection.find().into(new ArrayList<>());
        return list;
    }






/*
public MongoClient crearConexion(){
        MongoClient mongo = null;
        String servidor = "localhost";
        Integer puerto = 27017;
        try {
            mongo = new MongoClient(servidor, puerto);
            List<String> misDbs = mongo.getDatabaseNames();
            System.out.println("Conexión correcta "+misDbs.toString());
        }catch (MongoException e){
            System.out.println("Error: "+e);
        }
        return mongo;
    }
 */
}
