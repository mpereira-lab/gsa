package ar.com.mp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpsExchange;
import com.sun.net.httpserver.HttpHandler;

public class MyHandler implements HttpHandler{
	
	private static List<String> servers;
	
	public MyHandler() {
		servers = new ArrayList<>();
		servers.add("127.0.0.1:27000");
		servers.add("127.0.0.1:27001");
		servers.add("172.20.226.47:27000");
		servers.add("172.20.226.47:27001");
		servers.add("172.20.40.220:27000");
		servers.add("172.20.40.220:27001");
		servers.add("172.20.50.60:27000");
		servers.add("172.20.50.60:27001");
		servers.add("172.20.50.61:27000");
		servers.add("172.20.50.61:27001");
		servers.add("172.20.50.62:27000");
		servers.add("172.20.50.62:27001");
		servers.add("172.20.50.30:27000");
		servers.add("172.20.50.30:27001");
		servers.add("172.20.50.31:27000");
		servers.add("172.20.50.31:27001");
		servers.add("172.20.50.32:27000");
		servers.add("172.20.50.32:27001");
		servers.add("172.20.50.33:27000");
		servers.add("172.20.50.33:27001");
		servers.add("172.20.50.34:27000");
		servers.add("172.20.50.34:27001");
		servers.add("172.20.0.93:27000");
		servers.add("172.20.0.93:27001");
		
		servers.add("127.0.0.1:27002");
		servers.add("127.0.0.1:27003");
		servers.add("172.20.226.47:27002");
		servers.add("172.20.226.47:27003");
		servers.add("172.20.40.220:27002");
		servers.add("172.20.40.220:27003");
		servers.add("172.20.50.60:27002");
		servers.add("172.20.50.60:27003");
		servers.add("172.20.50.61:27002");
		servers.add("172.20.50.61:27003");
		servers.add("172.20.50.62:27002");
		servers.add("172.20.50.62:27003");
		servers.add("172.20.50.30:27002");
		servers.add("172.20.50.30:27003");
		servers.add("172.20.50.31:27002");
		servers.add("172.20.50.31:27003");
		servers.add("172.20.50.32:27002");
		servers.add("172.20.50.32:27003");
		servers.add("172.20.50.33:27002");
		servers.add("172.20.50.33:27003");
		servers.add("172.20.50.34:27002");
		servers.add("172.20.50.34:27003");
		servers.add("172.20.0.93:27002");
		servers.add("172.20.0.93:27003");
		
	}
	
	@Override
    public void handle(HttpExchange t) throws IOException {
    	String response = servers.stream().map(Object::toString)
        	    .reduce((x, u) -> x + ";" + u)
        	    .orElse("");
        HttpsExchange httpsExchange = (HttpsExchange) t;
        t.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        t.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
	
}
