package ClientPack;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.*;
import java.awt.Color;

public class InterfazOnline {

    private JFrame interfaz = new JFrame("interfaz");

    public InterfazOnline(Cliente cliente) {
        
        this.interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.interfaz.setSize(500, 400);
        this.interfaz.setLayout(null);
        this.interfaz.setVisible(true);
        this.interfaz.setFocusable(true);
        this.interfaz.setLocationRelativeTo(null);
        this.interfaz.setLocationRelativeTo(null);
        JLabel bienvenida = new JLabel();
        JLabel estado = new JLabel();
        estado.setBounds(0, 30, 100, 20);
        this.interfaz.add(estado);
        JLabel numSocket = new JLabel();
        numSocket.setBounds(0, 60, 600, 30);
        this.interfaz.add(numSocket);
        bienvenida.setText("Bienvenido! " + cliente.getUsuario() + "!");
        bienvenida.setFont(new Font("Helvetica Neue", Font.PLAIN, 20));
        estado.setText("Estado: En linea");
        numSocket.setText("Socket: " + cliente.toString());
        bienvenida.setBounds(0, 0, 500, 30);
        this.interfaz.add(bienvenida);
        JButton mostrarUsuariosConectados = new JButton("Actualizar");
        mostrarUsuariosConectados.setBounds(50, 270, 100, 50);
        mostrarUsuariosConectados.setBackground(new Color(191,151,128));
        mostrarUsuariosConectados.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.interfaz.add(mostrarUsuariosConectados);
        JButton irAlChat = new JButton("Continuar");
        irAlChat.setBounds(200, 270, 100, 50);
        irAlChat.setBackground(new Color(191,151,128));
        irAlChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.interfaz.add(irAlChat);
        JLabel txtUsuCon = new JLabel("Usuarios en linea");
        txtUsuCon.setBounds(50,180,300,20);
        this.interfaz.add(txtUsuCon);
        JComboBox listaUsuariosOnline = new JComboBox<>();
        listaUsuariosOnline.setBounds(50, 200, 300, 50);
        listaUsuariosOnline.setBackground(new Color(236,214,192));
        listaUsuariosOnline.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        this.interfaz.add(listaUsuariosOnline);
        listaUsuariosOnline.setFont(new Font("warung kopi", Font.PLAIN, 20));
        listaUsuariosOnline.setVisible(true);
        
        mostrarUsuariosConectados.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    PrintWriter output = cliente.getOutput();
                    BufferedReader input = cliente.getInput();
                    output.println("Online-codigo");
                    String outputMessage = input.readLine();
                    String[] save = new String[outputMessage.length()];
                    save = outputMessage.split(", "); 

                    for (int i = 0; i < save.length; i++) {
                        if(!save[i].equals(listaUsuariosOnline.getItemAt(i)))
                        {
                            listaUsuariosOnline.addItem(save[i]);
                        }   
                    }
                } catch (IOException u) {
                    System.out.println("Error en el flujo del cliente"+u.getStackTrace());
                }
            }
        });

        irAlChat.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Chat chatActivo = new Chat(cliente);
                getInterface().dispose();
            }
        });
    }

    private JFrame getInterface()
    {
        return this.interfaz;
    }
}
