package edu.escuelaing.arep.app;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.secure;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.StandardCharsets;
/**
 * Calse encargada de crear la pagina web.
 * @author Jose Luis Gomez Camacho
 *
 */
public class AppWeb {
	
    public static void main(String[] args) {
        port(getPort());
        staticFiles.location("/public");
        secure("keystores/ecikeystore.p12", "Hola123", null, null);
        get("/hello", (req, res) -> "Hello World");
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000; //returns default port if heroku-port isn't set (i.e. on localhost) }
    }
}
	
