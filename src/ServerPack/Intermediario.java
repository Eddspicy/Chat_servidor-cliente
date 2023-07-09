package ServerPack;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.Socket;

public class Intermediario extends Thread {

    private String username;
    private BufferedReader clientIn;
    private PrintWriter clientOut;
    private Socket clientSocket;
    private boolean isOnline;

    public Intermediario(Socket newsocket) throws IOException {
        try {
            this.clientSocket = newsocket;
            this.clientIn = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
            this.clientOut = new PrintWriter(this.clientSocket.getOutputStream(), true);
            this.isOnline = true;
            this.username = this.clientIn.readLine();

        } catch (IOException e) {
            System.out.println("El flujo de datos del cliente no pudo ser abierto correctamente" + e.getStackTrace());
            this.clientIn.close();
            this.clientOut.close();
            this.clientSocket.close();
        }
    }

    public String getUsername()
    {
        return this.username;
    }

    public BufferedReader getInput()
    {
        return this.clientIn;
    }

    public PrintWriter getOutput()
    {
        return this.clientOut;
    }

    public Socket getSocket()
    {
        return this.clientSocket;
    }

    public Boolean getActivity()
    {
        return this.isOnline;
    }

    @Override
    public void run() {
        String incomingMessage = "null";
        try {
            while (true) 
            {
                incomingMessage = this.clientIn.readLine();

                switch(incomingMessage)
                {
                    case("Exit-codigo"):
                    {
                        this.isOnline = false;
                        this.clientIn.close();
                        this.clientOut.close();
                        this.clientSocket.close();
                        break;
                    }

                    case("Online-codigo"):
                    {
                        onlineClients();
                        System.out.println(this.username+"-pidio la lista de usuarios activos");
                        break;
                    }

                    case("Private-codigo"):
                    {
                        this.clientOut.println("Escribe el nombre del usuario");
                        String user = this.clientIn.readLine();
                        this.clientOut.println("\nEscribe el mensaje al usuario");
                        incomingMessage = this.clientIn.readLine();
                        privateMessage(incomingMessage, user);
                        System.out.println("El servidor recibio - "+incomingMessage+" - de forma privada de "+this.username);
                        break;
                    }

                    default:
                    {
                        printToAllClients(incomingMessage);
                        System.out.println("El servidor recibio -" + incomingMessage + "- de " + this.username); 
                        break;
                    }
                }

                if(incomingMessage.equals("Exit9"))
                {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Error al recibir mensajes por el servidor" + e.getStackTrace());
        }
    }

    private void printToAllClients(String outputmessage) {
        for (Intermediario currentClient : Servidor.Clientes) {
            if (currentClient.username != this.username) {
                currentClient.clientOut.println(this.username+":"+outputmessage);
            }
        }
    }

    private void privateMessage(String outputmessage, String user)
    {
        for(Intermediario currentClient: Servidor.Clientes){
            if(currentClient.username.equals(user))
            {
                currentClient.clientOut.println("Mensaje privado de "+this.username+":"+outputmessage);
            }
        }
    }

    private void onlineClients()
    {
        String online = "";
        for(Intermediario currentClient: Servidor.Clientes){
            if(currentClient.isOnline == true)
            {
                if(online.equals(""))
                {
                    online = (online+currentClient.username);
                }
                    else{
                        online = (online+", "+currentClient.username);
                    }
            }
        }
        this.clientOut.println(online);
    }
}