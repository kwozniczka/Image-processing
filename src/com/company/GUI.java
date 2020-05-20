package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GUI extends JFrame implements ActionListener {

    ImageHandler image;
    JButton buttonQuitProgram;
    JButton buttonOpenFile;
    JButton buttonImageErode;
    Imopen imageAfterErode;

    public GUI() {
        //setSize(1280,720);
        setSize(640, 900);
        setTitle("Aplikacja Obrazy");
        setLayout(null);

        buttonOpenFile = new JButton("Otwórz obraz");
        buttonOpenFile.setBounds(50, 50, 200, 50); // nadanie parametrow przyciskowi
        add(buttonOpenFile);
        buttonOpenFile.addActionListener(this);

        buttonImageErode = new JButton("Erozja");
        buttonImageErode.setBounds(50, 125, 200, 50);
        add(buttonImageErode);
        buttonImageErode.addActionListener(this);
//
//        buttonQuitProgram = new JButton("Wyjście");
//        buttonQuitProgram.setBounds(450, 325, 100, 50);
//        add(buttonQuitProgram);
//        buttonQuitProgram.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (buttonOpenFile.equals(source)) {
            JFileChooser fileChoose = new JFileChooser();


            if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChoose.getSelectedFile();
                try {
                    image = new ImageHandler(file.getName());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    Component frame = null;
                    JOptionPane.showMessageDialog(null, "Błąd otwarcia pliku\n");
                }
            }
        }


        if (buttonImageErode.equals(source)) {

            System.out.println("Erozja Obrazu");
            new OpenDialog();
            imageAfterErode = new Imopen(OpenDialog.getData());



        }



    }


}
