package ar.com.mp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.net.ssl.HttpsURLConnection;

public class HttpsExampleRequest {

	public static void main (String[] args) throws IOException {
		
//		String url = "https://127.0.0.1:8000/servers";
		String url = "https://172.20.40.220:8000/servers";
		URL myurl = new URL(url);
		HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		con.setDoOutput(true);
		con.setDoInput(true);

//		System.out.println("Resp Code:"+con.getResponseCode());
//		System.out.println(con.getResponseMessage());
		
		BufferedReader br = null;
		if (100 <= con.getResponseCode() && con.getResponseCode() <= 399) {
		    br = new BufferedReader(new InputStreamReader(con.getInputStream()));
		} else {
		    br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
		}
		String bodyResponse = br.lines().collect(Collectors.joining());
		String[] servers = bodyResponse.split(";");
		Map<String, String> map = new HashMap<>();
		for (String server:servers) {
			map.put(server, server);
		}
		
		map.values().stream().forEach(x->System.out.println(x));
		
	}

//	private static void configureSsl(HttpsURLConnection con) {
//        try {
//
//        	SSLContext sslContext = SSLContext.getInstance("TLS");
//
//            // initialise the keystore
//            char[] password = "changeit".toCharArray();
//            KeyStore ks = KeyStore.getInstance("JKS");
//            InputStream fis = new FileInputStream("ks.txt");
//            ks.load(fis, password);
//
//            // setup the key manager factory
//            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
//            kmf.init(ks, password);
//
//            // setup the trust manager factory
//            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
//            tmf.init(ks);
//
//            // setup the HTTPS context and parameters
//            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
////            con.setSSLSocketFactory(kmf);
//        } catch (Exception exception) {
//            System.out.println("Failed to create HTTPS server on port " + 8000 + " of localhost");
//            exception.printStackTrace();
//
//        }
//    }		
}
