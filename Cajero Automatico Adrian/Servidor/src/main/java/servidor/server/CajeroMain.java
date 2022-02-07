package servidor.server;

import servidor.database.MiMongo;
import servidor.database.MongoController;

public class CajeroMain {

    public static void main(String[] args) {
        MiMongo mongo = MiMongo.getInstance();
        mongo.conectar();
        //MongoController m = MongoController.getInstance();
        //m.open();
        Cajero c = new Cajero("Cajero 1");
        c.start();
    }

}
