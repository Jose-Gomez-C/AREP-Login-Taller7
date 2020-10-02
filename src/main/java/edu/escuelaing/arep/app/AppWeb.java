package edu.escuelaing.arep.app;

import static spark.Spark.*;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;

import com.google.gson.Gson;

import edu.escuelaing.arep.app.model.Usuario;
import spark.staticfiles.StaticFilesConfiguration;

/**
 * Calse encargada de crear la pagina web.
 * 
 * @author Jose Luis Gomez Camacho
 *
 */
public class AppWeb {

	public static void main(String[] args) {
		port(getPort());
		Gson gson = new Gson();
		Map<String, String> usuarios = new HashMap<String, String>();
		usuarios.put("prueba@gmail.com", encriptar("1234"));
		
		secure("keystores/ecikeystore.p12", "Hola123", null, null);
		Httpclient.conexion();
		

		before("protected/*", (req, response) -> {
			req.session(true);
			if (req.session().isNew()) {
				req.session().attribute("Loged", false);
			}
			boolean auth = req.session().attribute("Loged");
			if (!auth) {
				halt(401, "<h1>Usted no esta autorizado</h1>");
			}
		});

		before("/login.html", ((req, response) -> {
			req.session(true);
			if (req.session().isNew()) {
				req.session().attribute("Loged", false);
			}
			boolean auth = req.session().attribute("Loged");
			if (auth) {
				response.redirect("protected/index.html");
			}
		}));

		StaticFilesConfiguration staticHandler = new StaticFilesConfiguration();
		staticHandler.configure("/public");
		before((request, response) -> staticHandler.consume(request.raw(), response.raw()));
		
		get("/conexion", ((request, response) -> {
			return Httpclient.getInformacion();
		}));
		get("/", ((request, response) -> {
			response.redirect("/login.html");
			return "Ok";
		}));

		get("protected/user", ((request, response) -> {
			return request.session().attribute("User");
		}));

		post("/login", (request, response) -> {

			System.out.println(request.body());
			request.session(true);
			Usuario posibleUsurario = gson.fromJson(request.body(), Usuario.class);
			if (encriptar(posibleUsurario.getPassword()).equals(usuarios.get(posibleUsurario.getName()))) {
				request.session().attribute("User", posibleUsurario.getName());
				request.session().attribute("Loged", true);
			}
			return "ok";
		});

	}

	public static String encriptar(String clave) {

		return DigestUtils.md5Hex(clave);
	}

	static int getPort() {

		if (System.getenv("PORT") != null) {
			return Integer.parseInt(System.getenv("PORT"));
		}
		return 5000; // returns default port if heroku-port isn't set (i.e. on localhost) }
	}
}
