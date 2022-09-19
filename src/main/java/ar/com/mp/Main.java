package ar.com.mp;

public class Main {

	private static Server server;

    public static void main(String[] args) throws Exception {
    	if (args.length<=0) {
    		System.out.println("Parametros de entrada son \"start\" o \"stop\"");
    		return;
    	}
    	if (args[0].toLowerCase().equals("start")) {
    		server = new Server();
    		server.start();
    	}else {
    		server.stop();
    	}
    }

	public static void windowsService(String[] args) {
    	if (args.length<=0) {
    		System.out.println("Parametros de entrada son \"start\" o \"stop\"");
    		return;
    	}
    	if (args[0].toLowerCase().equals("start")) {
    		server = new Server();
    		server.start();
    	}else {
    		server.stop();
    	}
	}
}