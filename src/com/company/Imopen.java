package com.company;

import java.awt.image.BufferedImage;
import java.io.IOException;

import static com.company.ImageHandler.saveImage;

public class Imopen{

    int radius; // promień koła (elementu strukturalnego)
    BufferedImage image;



    public Imopen(int radius, BufferedImage image)  {
        this.radius = radius;
        this.image = image;
        opening();

    }

    // funkcja szukająca koloru o najniższych wartościach w skali szarosci w promieniu radius

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

    // funkcja szukając koloru o najwyższych wartościach w skali szarosci w promieniu radius
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

        for (int k = 0; k < (int) img.getHeight(); k++) {
            for (int l = 0; l < (int) img.getWidth(); l++) {
                afterErode.getRaster().setSample(l, k,0 ,minimumColor(k,l,img));
            }
        }
        return afterErode;
    }


    private BufferedImage dilatation(BufferedImage img) {
        BufferedImage afterDilatation = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());

        for (int k = 0; k < (int) img.getHeight(); k++) {
            for (int l = 0; l < (int) img.getWidth(); l++) {
                afterDilatation.getRaster().setSample(l, k,0 ,maximumColor(k,l,img));
            }
        }

        return afterDilatation;
    }

    private void opening(){
        if(radius <= 0)
            System.out.println("Radius must be > 0");

        BufferedImage picture = erosion(image);
        picture = dilatation(picture);
        saveImage(picture, "po_otwarciu.png");

//        BufferedImage afterOpening = dilatation(erosion());
//        displayImage(afterOpening, "After Opening");
//        saveImage(afterOpening, "po_otwarciu2.png");


    }


}
