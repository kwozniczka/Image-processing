package com.company;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class Regionprops {

    private PrintWriter centroidsFile;
    private BufferedImage image;
    private String fileName;

    public Regionprops(BufferedImage sourceImage, String fileName) throws IOException  {

        this.image = sourceImage;
        this.fileName = fileName;
        regionprops();

    }

   private void regionprops() throws IOException {

        File file = new File(this.fileName);
        FileWriter fw = new FileWriter(file);
        this.centroidsFile = new PrintWriter(fw);
        centroidAndDiameterEquivalent(image, centroidsFile);
        centroidsFile.close();
    }

    private void centroidAndDiameterEquivalent(BufferedImage sourceImage, PrintWriter pw){
        double[] colorsX = new double[256];
        double[] colorsY = new double[256];
        double[] centroids= new double[256];


        for (int i=0; i< sourceImage.getHeight(); i++){
            for(int j=0; j< sourceImage.getWidth(); j++){
                int color = sourceImage.getRaster().getSample(i, j, 0);
                colorsX[color] += i; // tutaj zliczam wszystkie wspolrzedne x dla danego odcienia szarosci
                colorsY[color] += j; // tutaj zliczam wszystkie wspolrzedne y dla danego odcienia szarosci
                centroids[color] ++; // tutaj zliczam ile razy wystąpil dany odcien, potem bede dzielic colorsX i colorsY przez tą wartosc, zeby dostac wspolrzedne centroidu
            }
        }


        for(int i = 0; i <= 255; i++){
            if(centroids[i] != 0) {// nie mogę podzielić jesli mam 0 elementow
                centroidsFile.print(i);
                double centroidX = colorsX[i] / centroids[i]; // wyliczam wspolrzedne
                double centroidY = colorsY[i] / centroids[i];
                centroidsFile.print("  X: ");
                centroidsFile.print(centroidX);
                centroidsFile.print("  Y: ");
                centroidsFile.println(centroidY);
            }
        }

        centroidsFile.println(" EKWIWALENT ŚREDNICY:");
        for (int i = 0; i<= 255; i++){
            centroidsFile.print(i);
            centroidsFile.print("  :  ");
            double diameterEquivalent = 2 * Math.sqrt(centroids[i] / 3.14);
            centroidsFile.println(diameterEquivalent);
        }

    }


}

