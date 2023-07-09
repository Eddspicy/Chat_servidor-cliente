package ServerPack;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor {

    static ArrayList<Intermediario> Clientes = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serversocket = new ServerSocket(9999);

        while (true) {
            Socket socket = null;
            try {
                socket = serversocket.accept();
                System.out.println("Nuevo cliente fue aceptado");
                Intermediario nuevoIntermediario = new Intermediario(socket);
                System.out.println("Creando un hilo para el cliente");
                Clientes.add(nuevoIntermediario);
                nuevoIntermediario.start();

            } catch (Exception e) {
                serversocket.close();
                e.printStackTrace();
            }
        }
    }

}