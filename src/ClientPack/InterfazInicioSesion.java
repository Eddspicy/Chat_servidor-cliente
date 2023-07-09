package ClientPack;

import java.awt.Font;
import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.*;

import org.w3c.dom.events.MouseEvent;

import java.awt.Color;

public class InterfazInicioSesion {

    public static void main(String[] args) throws IOException {

        // INTERFAZ
        JFrame interfaz = new JFrame();
        interfaz.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        interfaz.setSize(700, 200);
        interfaz.setLayout(null);
        interfaz.setVisible(true);
        interfaz.setFocusable(true);
        interfaz.setLocationRelativeTo(null);
        interfaz.setTitle("Login");

        //LABELS
        JLabel bienvenida = new JLabel();
        bienvenida.setText("Bienvenido, ingresa tu nombre");
        bienvenida.setBounds(200, 10, 400, 30);
        bienvenida.setFont(new Font("warung kopi", Font.PLAIN, 20));
        interfaz.add(bienvenida);
        JLabel textoNombre = new JLabel();
        textoNombre.setBounds(50, 55, 150, 20);
        textoNombre.setText("Nombre de Usuario: ");
        textoNombre.setVisible(true);
        interfaz.add(textoNombre);

        //TEXTFIELD
        JTextField espacioNombreUsuario = new JTextField();
        espacioNombreUsuario.setBounds(200, 50, 300, 30);
        espacioNombreUsuario.setBackground(new Color(236,214,192));
        espacioNombreUsuario.setVisible(true);
        interfaz.add(espacioNombreUsuario);
        espacioNombreUsuario.requestFocus();

        //BOTON
        JButton botonEnviar = new JButton("Entrar");
        interfaz.add(botonEnviar);
        botonEnviar.setBounds(510, 50, 100, 30);
        botonEnviar.setBackground(new Color(191,151,128));
        botonEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        
        botonEnviar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Tu nombre es: " + espacioNombreUsuario.getText());
                Cliente nuevoCliente = new Cliente(espacioNombreUsuario.getText());
                InterfazOnline sesionActiva = new InterfazOnline(nuevoCliente);
                
                interfaz.dispose();
            }
        });   
    }
}