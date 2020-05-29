package com.company;
import java.awt.image.BufferedImage;

import static com.company.ImageHandler.saveImage;

public class Imfill {

    int radius; // promień koła (elementu strukturalnego)
    BufferedImage sourceImage; // obraz wejsciowy, ktory bedzie poddany rekonstrukcji
    BufferedImage tempImage; // pomocniczy obraz, gdzie przechowuje aktualne przekształcenia

    public Imfill( BufferedImage sourceImage)  {
        this.radius = 1;
        this.sourceImage = sourceImage;
        fillingImageHolesWithReconstruction();
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

    // tworze maske: czyli obraz w postaci ramki, z 1 na krawędzi i 0 w srodku (o rozmiarze obrazu wejsciowego)
    private BufferedImage createMask(BufferedImage img){
        BufferedImage mask = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i < img.getHeight(); i++){
            mask.getRaster().setSample(0,i,0,255);
            mask.getRaster().setSample(img.getWidth() - 1, i, 0, 255);
        }
        for(int i = 0; i < img.getWidth(); i++){
            mask.getRaster().setSample(i,0, 0, 255);
            mask.getRaster().setSample(i, img.getHeight() -1, 0, 255);
        }
        return mask;
    }

    //tworzenie negacji obrazu wejsciowego (czyli dopełnienie, tam gdzie 1 tam 0, gdzie 0 tam 1)
    private BufferedImage imageNegation(BufferedImage img){
        BufferedImage imageAfterNegation = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
        for (int i = 0; i<img.getWidth(); i++){
            for(int j = 0; j<img.getHeight(); j++){
                int color = img.getRaster().getSample(i,j,0);
                imageAfterNegation.getRaster().setSample(i,j,0,~color);
            }
        }
        return imageAfterNegation;
    }

    // iloczyn logiczny (czyli koniunkcja)
    private BufferedImage imageConjunction(BufferedImage img1, BufferedImage img2){
        BufferedImage imageAfterConjunction = new BufferedImage(img1.getWidth(), img1.getHeight(), img1.getType());
        for (int i = 0; i<img1.getWidth(); i++){
            for(int j = 0; j<img1.getHeight(); j++){
                int color1 = img1.getRaster().getSample(i,j,0);
                int color2 = img2.getRaster().getSample(i,j,0);
                imageAfterConjunction.getRaster().setSample(i,j,0, color1&color2);
            }
        }
        return imageAfterConjunction;
    }

    // sprawdzenie czy obrazy są sobie równe
    private boolean areImagesEqual(BufferedImage img1, BufferedImage img2){
        boolean equal = true;
        for (int i = 0; i<img1.getWidth(); i++){
            for(int j = 0; j<img1.getHeight(); j++){
                if(img1.getRaster().getSample(i,j,0) != img2.getRaster().getSample(i,j,0)){
                    equal = false;
                    break;
                }
            }
            if(!equal)
                break;
        }
        return equal;
    }


    private void fillingImageHolesWithReconstruction(){

        BufferedImage mask = createMask(sourceImage);
        BufferedImage complementImage = imageNegation(sourceImage); // (obraz ktory jest negacja obrazu wejsciowego - czyli dopelnieniem)

        while(true){
            // jednostkowa dylatacja maski
            tempImage = dilatation(mask);

            //iloczyn logiczny z negacją obrazu wejsciowego (czyli z imageAfterNegation)
            tempImage = imageConjunction(tempImage, complementImage);

            //sprawdzam czy istnieją róznice pomiedzy kolejnymi iteracjami, jesli tak to przerywam
            if(areImagesEqual(tempImage, mask))
                break;

            // nowa maska
            mask = tempImage;
        }
        saveImage(imageNegation(mask), "after_imfill.png");
    }

}