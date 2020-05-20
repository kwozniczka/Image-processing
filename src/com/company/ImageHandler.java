package com.company;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageHandler {

    JFrame frame;
    BufferedImage sourceImage;
    String imagePath;

    public ImageHandler(){

    }

    public ImageHandler(String imagePath) throws IOException {

        this.imagePath = imagePath;
        loadImage();
        displayImage();

    }

    private void loadImage() throws IOException{
        sourceImage = ImageIO.read(new File(imagePath));
    }

    public void displayImage() throws IOException{
        ImageIcon icon = new ImageIcon(sourceImage);
        frame = new JFrame("Obraz");
        JLabel label = new JLabel();
        label.setIcon(icon);
        frame.getContentPane().add(label, BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void saveImage(BufferedImage image, String name){
        File file = new File(name);
        try {
            ImageIO.write(image, "jpg", file);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }


}

