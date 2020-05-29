package com.company;

import java.util.*;
import java.awt.image.BufferedImage;
import static com.company.ImageHandler.saveImage;

public class Ordfilt {

    int maskSize;
    int orderNumber;
    BufferedImage sourceImage;

    public Ordfilt(int maskSize, int orderNumber, BufferedImage sourceImage) {
        this.maskSize = maskSize;
        this.orderNumber = orderNumber;
        this.sourceImage = sourceImage;
        doOrdfilt();
    }

    private int checkImage(BufferedImage img){
        return img.getType();
    }

    private Vector<Integer> createMask(int maskSize){
        Vector<Integer> indexes = new Vector<>();
        int tmp = maskSize / 2;

        if (maskSize == 0)
            indexes.add(0);
        else {
            if (maskSize % 2 == 1)
                for (int x = -tmp; x <= tmp; x++)
                    indexes.add(x);
            else
                for (int x = -tmp + 1; x <= tmp; x++)
                    indexes.add(x);
        }
        return indexes;
    }


    private BufferedImage ordfiltMono(BufferedImage img){
        BufferedImage afterOrdfilt = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Vector indexes = createMask(maskSize);
        Vector<Integer> pixels = new Vector<>();

        //Filtracja
        for (int kz = 0; kz < img.getHeight(); kz++) {
            for (int kx = 0; kx < img.getWidth(); kx++) {

                for (int k = (int)indexes.elementAt(0); k <= (int)indexes.lastElement(); k++)
                    for (int l = (int)indexes.elementAt(0); l <= (int)indexes.lastElement(); l++) {
                        int tmp = (kz + k >= 0 && kz + k < img.getHeight() && kx + l >= 0 && kx + l< img.getWidth()) ? img.getRaster().getSample(kx + l, kz +k, 0) : 0;
                        pixels.add(tmp);
                    }

                Collections.sort(pixels); // sortowanie rosnąco
                afterOrdfilt.getRaster().setSample(kx,kz,0, pixels.elementAt(orderNumber - 1));
                pixels.clear();
            }
        }
        return afterOrdfilt;
    }

    private BufferedImage ordfiltRGB(BufferedImage img){
        BufferedImage afterOrdfilt = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        Vector indexes = createMask(maskSize);
        Vector<Integer> pixels = new Vector<>();
        int newRed;
        int newGreen;
        int newBlue;

        for (int kz = 0; kz < img.getHeight(); kz++) {
            for (int kx = 0; kx < img.getWidth(); kx++) {

                // CZERWONY
                for (int k = (int)indexes.elementAt(0); k <= (int)indexes.lastElement(); k++)
                    for (int l = (int)indexes.elementAt(0); l <= (int)indexes.lastElement(); l++) {
                        int tmp = (kz + k >= 0 && kz + k < img.getHeight() && kx + l >= 0 && kx + l < img.getWidth()) ? img.getRaster().getSample(kx + l, kz +k, 0) : 0;
                        pixels.add(tmp);
                    }

                Collections.sort(pixels);
                newRed = pixels.elementAt(orderNumber - 1);
                pixels.clear();

                // ZIELONY
                for (int k = (int)indexes.elementAt(0); k <= (int)indexes.lastElement(); k++)
                    for (int l = (int)indexes.elementAt(0); l <= (int)indexes.lastElement(); l++) {
                        int tmp = (kz + k >= 0 && kz + k < img.getHeight() && kx + l >= 0 && kx + l < img.getWidth()) ? img.getRaster().getSample(kx + l, kz +k, 1) : 0;
                        pixels.add(tmp);
                    }
                Collections.sort(pixels);
                newGreen = pixels.elementAt(orderNumber - 1);
                afterOrdfilt.setRGB(kx,kz, newGreen);
                pixels.clear();

                // NIEBIESKI
                for (int k = (int)indexes.elementAt(0); k <= (int)indexes.lastElement(); k++)
                    for (int l = (int)indexes.elementAt(0); l <= (int)indexes.lastElement(); l++) {
                        int tmp = (kz + k >= 0 && kz + k < img.getHeight() && kx + l >= 0 && kx + l < img.getWidth()) ? img.getRaster().getSample(kx + l, kz +k, 2) : 0;
                        pixels.add(tmp);
                    }

                Collections.sort(pixels);
                newBlue = pixels.elementAt(orderNumber - 1);
                pixels.clear();

                afterOrdfilt.getRaster().setSample(kx,kz,0, newRed);
                afterOrdfilt.getRaster().setSample(kx,kz,1, newGreen);
                afterOrdfilt.getRaster().setSample(kx,kz,2, newBlue);
            }
        }
        return afterOrdfilt;
    }

    private void doOrdfilt(){
        if(maskSize < 0 || orderNumber < 0)
            System.out.println("Numer porzadkowy oraz rozmiar maski musza byc wieksze od 0!");

        if(orderNumber > maskSize*maskSize)
            System.out.println("Numer porzadkowy musi być mniejszy lub rowny kwadratowi rozmiaru maski!");


        if(checkImage(sourceImage) == 10) // jesli jest mono, (dla RGB jest 5)
            saveImage(ordfiltMono(sourceImage), "after_ordfiltMONO.png");
        else // w przeciwnym razie jest RGB
            saveImage(ordfiltRGB(sourceImage), "after_ordfiltRGB.png");
    }

}
