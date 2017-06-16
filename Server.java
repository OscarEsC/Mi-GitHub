import java.net.Socket;
import java.net.ServerSocket;
import java.io.IOException;
class Server{
	public static void main(String[] args) {
		try{
			ServerSocket server = new ServerSocket(8888);
			System.out.println("Servidor en ejecucion, puerto: "+server.getLocalPort());
			boolean activado = true; //si mantenemos activo el servidor
			while(activado){
				Socket s = server.accept();
				Servidor servicio = new Servidor(s); //la ejecución de ese usuario la mandamos a un hilo secundario
				servicio.start();
			}
		}catch(IOException e){
			System.out.println("no se pudo establecer coneccion con usuario");
		}
	}
}