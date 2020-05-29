package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static com.company.ImageHandler.*;


public class GUI extends JFrame implements ActionListener {

    BufferedImage sourceImage;
    JButton buttonOpenFile;
    JButton buttonImopen;
    JButton buttonImfill;
    JButton buttonRegionprops;
    JButton buttonOrdfilt;
    Imopen imageAfterErode;
    Ordfilt imageAfterOrdfilt;

    public GUI() {

        setSize(500, 500);
        setTitle("Aplikacja do przetwarzania obrazów");
        setLayout(null);

        buttonOpenFile = new JButton("Otwórz obraz");
        buttonOpenFile.setBounds(50, 50, 400, 50);
        add(buttonOpenFile);
        buttonOpenFile.addActionListener(this);

        buttonImopen= new JButton("Otwarcie");
        buttonImopen.setBounds(50, 125, 400, 50);
        add(buttonImopen);
        buttonImopen.addActionListener(this);

        buttonOrdfilt= new JButton("Ordfilt");
        buttonOrdfilt.setBounds(50, 200, 400, 50);
        add(buttonOrdfilt);
        buttonOrdfilt.addActionListener(this);

        buttonImfill= new JButton("Imfill");
        buttonImfill.setBounds(50, 275, 400, 50);
        add(buttonImfill);
        buttonImfill.addActionListener(this);

        buttonRegionprops= new JButton("Regionprops");
        buttonRegionprops.setBounds(50, 350, 400, 50);
        add(buttonRegionprops);
        buttonRegionprops.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        Object source = e.getSource();

        if (buttonOpenFile.equals(source)) {
            JFileChooser fileChoose = new JFileChooser("/home/karolina/workspace/Image-processing");

            if (fileChoose.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File file = fileChoose.getSelectedFile();
                try {
                    sourceImage = loadImage(file.getName());
                    displayImage(sourceImage, "Obraz wejściowy");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                    Component frame = null;
                    JOptionPane.showMessageDialog(null, "Błąd otwarcia pliku\n");
                }
            }
        }


        if (buttonImopen.equals(source)) {
            System.out.println("Otwarcie Obrazu");
            OpenDialog openDialog = new OpenDialog();
            imageAfterErode = new Imopen(openDialog.getData(), sourceImage);
        }

        if(buttonOrdfilt.equals(source)){
            System.out.println("Ordfilt");
            OrdfiltDialog ordfiltDialog = new OrdfiltDialog();
            imageAfterOrdfilt= new Ordfilt(ordfiltDialog.getMasksize(), ordfiltDialog.getNumber(), sourceImage);
        }

        if(buttonImfill.equals(source)){
            System.out.println("Imfill");
            Imfill imfill = new Imfill(sourceImage);
        }

        if(buttonRegionprops.equals(source)){
            System.out.println("Regionprops");
            try {
                Regionprops regionprops = new Regionprops(sourceImage, "after_regionprops.txt");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
