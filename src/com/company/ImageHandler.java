package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {

    public static void saveImage(BufferedImage image, String name){
        File file = new File(name);
        try {
            ImageIO.write(image, "png", file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


    public static BufferedImage loadImage(String imagePath) throws IOException{
        return ImageIO.read(new File(imagePath));
    }

    public static void displayImage(BufferedImage image, String nameFrame){
        ImageIcon icon = new ImageIcon(image);
        JFrame frame = new JFrame(nameFrame);
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }
}

