package com.company;

import java.awt.image.BufferedImage;

public class Imopen extends ImageHandler {

    int radius; // promień koła (elementu strukturalnego)

    public Imopen(int radius){
        this.radius = radius;

    }


    public BufferedImage Erode(){
        BufferedImage afterErode = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        return afterErode;
    }




}
