package edu.escuelaing.arep.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

public class Httpclient {
	   
	    public static void conexion() {
	        // Create a file and a password representation
	        File trustStoreFile = new File("keystores/myTrustStore");
	        char[] trustStorePassword = "Hola123".toCharArray();
	        // Load the trust store, the default type is "pkcs12", the alternative is "jks"
	        KeyStore trustStore = null;
	        TrustManagerFactory tmf = null;
	        SSLContext sslContext = null;
	        try {
	            trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
	            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);
	            // Get the singleton instance of the TrustManagerFactory
	            tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
	            // Itit the TrustManagerFactory using the truststore object
	            tmf.init(trustStore);
	            //Set the default global SSLContext so all the connections will use it
	            sslContext = SSLContext.getInstance("TLS");
	            sslContext.init(null, tmf.getTrustManagers(), null);
	            SSLContext.setDefault(sslContext);
	            javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier((hostname, sslSession) -> true);
	        } catch (IOException | NoSuchAlgorithmException | CertificateException | KeyStoreException | KeyManagementException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * Realizar la peticion get al servidor de logica en aws
	     * @return la respuesta del servicio
	     * @throws IOException si no se ecuentra las respuesta
	     */
	    public static String getInformacion() throws IOException {
	        URL url = new URL("https:http://ec2-3-83-244-7.compute-1.amazonaws.com:8080/respuesta");
	        HttpURLConnection con = (HttpURLConnection) url.openConnection();
	        con.setRequestMethod("GET");
	        con.setDoOutput(true);

	        try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
	            StringBuilder response = new StringBuilder();
	            String responseLine = null;
	            while ((responseLine = br.readLine()) != null) {
	                response.append(responseLine.trim());
	            }
	            return response.toString();

	        }
	    }

}
