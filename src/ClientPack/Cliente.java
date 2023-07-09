package ClientPack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import javax.swing.JTextArea;

public class Cliente {

    private String nombreUsuario;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public Cliente(String nombreUsuario) {
        try {
            this.nombreUsuario = nombreUsuario;
            this.socket = new Socket(InetAddress.getLocalHost(), 9999);
            this.input = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.output = new PrintWriter(this.socket.getOutputStream(), true);
            
            output.println(this.nombreUsuario);
        } catch (IOException e) {
            System.out.println("Error al abrir el flujo del cliente"+e.getStackTrace());
        }
    }

    public String getUsuario()
    {
        return this.nombreUsuario;
    }

    public Socket getSocket()
    {
        return this.socket;
    }

    public BufferedReader getInput()
    {
        return this.input;
    }

    public PrintWriter getOutput()
    {
        return this.output;
    }

    public void enviarMensaje(String mensaje) throws IOException {
            String mensajito = mensaje;
            output.println(mensajito);
    }

    public void esperarMensaje(JTextArea areaTxt) {
        Thread waitingforMessages = new Thread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        String responseMessages = getInput().readLine();
                        System.out.println(responseMessages);
                        String ayuda = areaTxt.getText();
                        areaTxt.setText(ayuda+"\n" + responseMessages + "\n");
                    }
                } catch (IOException e) {
                    System.out.println("Los mensajes no han llegado correctamente" + e.getStackTrace());

                } finally {
                    try {
                        input.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        waitingforMessages.start();
    }
}
