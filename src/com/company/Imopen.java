package com.company;

import java.awt.image.BufferedImage;
import static com.company.ImageHandler.saveImage;

public class Imopen{

    int radius; // promień koła (elementu strukturalnego)
    BufferedImage sourceImage;


    public Imopen(int radius, BufferedImage sourceImage)  {
        this.radius = radius;
        this.sourceImage = sourceImage;
        opening();
    }

    private int checkImage(BufferedImage img) {
        return img.getType();
    }

    // metoda szukająca koloru o najniższych wartościach w skali szarosci w promieniu radius
    private int minimumColor( int x0, int y0, BufferedImage image) {
        int minimum = 255;
        for (int i = x0 - radius; i <= x0 + radius; i++)
            for (int j = y0 - radius; j <= y0 + radius; j++){
                if (i < 0 || i >= (int) image.getHeight() || j < 0 || j >= (int) image.getWidth())
                    continue;
                if ((i - x0) * (i - x0) + (j - y0) * (j - y0) <= radius * radius) { // jeśli jest w obrębie koła

                    int temp = image.getRaster().getSample(j,i,0);
                    if (temp < minimum) // szukam minmalna wartosc koloru
                        minimum = temp;
                    if (minimum == 0) return minimum;
                }
            }
        return minimum;
    }

    // metoda szukająca koloru o najwyższych wartościach w skali szarosci w promieniu radius
    private int maximumColor(int x0, int y0, BufferedImage image) {
        int maximum = -1;
        for (int i = x0 - radius; i <= x0 + radius; i++)
            for (int j = y0 - radius; j <= y0 + radius; j++){
                if (i < 0 || i >= (int) image.getHeight() || j < 0 || j >= (int) image.getWidth())
                    continue;
                if ((i - x0) * (i - x0) + (j - y0) * (j - y0) <= radius * radius) {

                    int temp = image.getRaster().getSample(j, i, 0);
                    if (temp > maximum)
                        maximum = temp;
                    if (maximum == 255) return maximum;
                }
            }
        return maximum;
    }


    private BufferedImage erosion(BufferedImage img){
        BufferedImage afterErode = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int k = 0; k < img.getHeight(); k++) {
            for (int l = 0; l < img.getWidth(); l++) {
                afterErode.getRaster().setSample(l, k,0 ,minimumColor(k,l,img));
            }
        }
        return afterErode;
    }


    private BufferedImage dilatation(BufferedImage img) {
        BufferedImage afterDilatation = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int k = 0; k < img.getHeight(); k++) {
            for (int l = 0; l < img.getWidth(); l++) {
                afterDilatation.getRaster().setSample(l, k,0 ,maximumColor(k,l,img));
            }
        }
        return afterDilatation;
    }


    private void opening(){
        if(radius <= 0)
            System.out.println("Radius must be > 0");

        BufferedImage picture = erosion(sourceImage);
        picture = dilatation(picture);

        if(checkImage(sourceImage) == 10) // jesli jest mono
            saveImage(picture, "after_openingMONO.png");
        else // w przeciwnym razie jest logiczny
            saveImage(picture, "after_openingLOG.png");
    }
}
