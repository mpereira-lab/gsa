package ar.com.mp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpsConfigurator;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpsParameters;
import com.sun.net.httpserver.HttpsServer;
//https://stackoverflow.com/questions/10079707/https-connection-using-curl-from-command-line

public class SimpleHTTPSServer2 {

//	private static List<String> servers;
	
//	public static void loadServers() {
//		servers = new ArrayList<>();
//		servers.add("127.0.0.1:27000");
//		servers.add("127.0.0.1:27001");
//		servers.add("172.20.226.47:27000");
//		servers.add("172.20.226.47:27001");
//		servers.add("172.20.40.220:27000");
//		servers.add("172.20.40.220:27001");
//	}
	
//    public static class MyHandler implements HttpHandler {
//        @Override
//        public void handle(HttpExchange t) throws IOException {
//        	String response = servers.stream().map(Object::toString)
//            	    .reduce((x, u) -> x + ";" + u)
//            	    .orElse("");
//            HttpsExchange httpsExchange = (HttpsExchange) t;
//            t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
//            t.sendResponseHeaders(200, response.getBytes().length);
//            OutputStream os = t.getResponseBody();
//            os.write(response.getBytes());
//            os.close();
//        }
//    }

    public static void main(String[] args) throws Exception {

//    	loadServers();
    	
        try {
            // setup the socket address
            InetSocketAddress address = new InetSocketAddress(8000);

            // initialise the HTTPS server
            HttpsServer httpsServer = HttpsServer.create(address, 0);
            SSLContext sslContext = SSLContext.getInstance("TLS");

            // initialise the keystore
            char[] password = "changeit".toCharArray();
            KeyStore ks = KeyStore.getInstance("JKS");
//            InputStream fis = new FileInputStream("ks.jks");
            InputStream fis = SimpleHTTPSServer2.class.getClassLoader().getResourceAsStream("ks.jks");
            ks.load(fis, password);

            // setup the key manager factory
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, password);

            // setup the trust manager factory
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(ks);

            // setup the HTTPS context and parameters
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
            httpsServer.setHttpsConfigurator(new HttpsConfigurator(sslContext) {
                public void configure(HttpsParameters params) {
                    try {
                        // initialise the SSL context
                        SSLContext context = getSSLContext();
                        SSLEngine engine = context.createSSLEngine();
                        params.setNeedClientAuth(false);
                        params.setCipherSuites(engine.getEnabledCipherSuites());
                        params.setProtocols(engine.getEnabledProtocols());

                        // Set the SSL parameters
                        SSLParameters sslParameters = context.getSupportedSSLParameters();
                        params.setSSLParameters(sslParameters);

                    } catch (Exception ex) {
                        System.out.println("Failed to create HTTPS port");
                    }
                }
            });
            httpsServer.createContext("/servers", new MyHandler());
            httpsServer.setExecutor(null); // creates a default executor
            httpsServer.start();
        } catch (Exception exception) {
            System.out.println("Failed to create HTTPS server on port " + 8000 + " of localhost");
            exception.printStackTrace();

        }
    }

}