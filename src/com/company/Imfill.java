package com.company;
import java.awt.image.BufferedImage;

public class Imfill {


    public BufferedImage imfill(BufferedImage src){

        BufferedImage imfillImage = new BufferedImage(src.getWidth(),src.getHeight(),src.getType());

        int[][] imageColor = new int[src.getWidth()][src.getHeight()];
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++)
                imageColor[i][j] = src.getRGB(i , j);
        }
        imageColor = imfillImage(30,imageColor);
        imageColor = imfillImage(20,imageColor);
        imageColor = imfillImage(10,imageColor);
        for (int i = 0; i < src.getWidth(); i++) {
            for (int j = 0; j < src.getHeight(); j++) {
                imfillImage.setRGB(i, j, imageColor[i][j]);
            }
        }

        return imfillImage;
    }

    private static int[][] imfillImage(int distance, int[][] imageColor) {

        int[][] newImageColor;
        newImageColor = imageColor;
        int counter;
        for (int i = distance; i < imageColor.length - distance; i++) {
            for (int j = distance; j < imageColor[1].length - distance; j++) {
                counter = 0;

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i-k1][j]  == 0xffffffff)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j-k1]  == 0xffffffff)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i+k1][j]  == 0xffffffff)
                    {
                        counter++;
                        break;
                    }
                }

                for (int k1 = 0; k1 < distance; k1++) {
                    if (imageColor[i][j+k1]  == 0xffffffff)
                    {
                        counter++;
                        break;
                    }
                }
                if (counter == 4)
                    newImageColor[i][j] = 0xffffffff;
            }
        }
        return newImageColor;
    }
}