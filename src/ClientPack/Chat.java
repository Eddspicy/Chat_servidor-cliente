package ClientPack;

import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.awt.Color;

public class Chat {

    public Chat(Cliente cliente) {
        
        //INTERFAZ
        JFrame interfaz = new JFrame();
        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setSize(785, 600);
        interfaz.setLayout(null);
        interfaz.setVisible(true);
        interfaz.setFocusable(true);
        interfaz.setLocationRelativeTo(null);
        
        //JLABELS
        JLabel lblUs1 = new JLabel();
        lblUs1.setText("Hola! "+cliente.getUsuario());
        lblUs1.setBounds(365,10,100,50);
        interfaz.add(lblUs1);

        //Cuadro Chat
        JTextArea chat = new JTextArea();
        chat.setBounds(110, 50, 550, 300);
        chat.setBackground(new Color(236,214,192));
        chat.setLineWrap(true);
        chat.setVisible(true);
        chat.setEditable(false);
        chat.requestFocus();
        interfaz.add(chat);

        JScrollPane scroll = new JScrollPane(chat, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setBounds(110, 50, 550, 300);
        interfaz.add(scroll);
        
        //Cuadro para escribir
        JTextField textoChat = new JTextField();
        textoChat.setBounds(110, 360, 250, 20);
        textoChat.setBackground(new Color(236,214,192));
        textoChat.setVisible(true);
        interfaz.add(textoChat);

        //Botones
        JButton botonEnviar = new JButton("Enviar");
        botonEnviar.setBounds(365, 360, 150, 20);
        botonEnviar.setBackground(new Color(191,151,128));
        botonEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        interfaz.add(botonEnviar);

        JButton botonMensajePrivado = new JButton("Enviar mensaje privado");
        botonMensajePrivado.setBounds(300, 390, 180, 40);
        botonMensajePrivado.setBackground(new Color(191,151,128));
        botonMensajePrivado.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        interfaz.add(botonMensajePrivado);

        cliente.esperarMensaje(chat);

        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(cliente.getUsuario() + ":  " + textoChat.getText());
                String chatCompleto = chat.getText();
                String mensajeEnviado = textoChat.getText();
                chat.setText(chatCompleto + "\n " + mensajeEnviado);
                textoChat.setText("");           
                textoChat.requestFocus();
                
                try {
                    cliente.enviarMensaje(mensajeEnviado);
                } catch (IOException u) {
                    System.out.println("Problema al ejecutar el boton Enviar"+u.getStackTrace());
                }
            }
        });

        botonMensajePrivado.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e)
            {
                try{
                    cliente.enviarMensaje("Private-codigo");
                } catch(IOException u)
                {
                    System.out.println("Problema al ejecutar el boton Private"+u.getStackTrace());
                }
            }
        });
    }
}