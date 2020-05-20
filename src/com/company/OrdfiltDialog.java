package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OrdfiltDialog {


    private static JDialog dialog;
    JLabel label1=new JLabel("Rozmiar maski");
    JLabel label2=new JLabel("Numer porządkowy");
    static JTextField in1Field=new JTextField();
    static JTextField in2Field=new JTextField();
    static String data1;
    static String data2;
    static int masksize;
    static int number;

    public OrdfiltDialog(){
        JFrame frame=new JFrame();
        dialog=new JDialog(frame, "Customize imOpen", true);
        init();
    }

    void init(){
        dialog.setTitle("Customize Ordfilt2");
        dialog.setLayout(new GridLayout(3,2));
        dialog.add(label1);
        dialog.add(in1Field);
        dialog.add(label2);
        dialog.add(in2Field);
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.setVisible(false);
            }
        });
        dialog.add(new JLabel("Confirm"));
        dialog.add(button);
        dialog.setSize(300,300);
        dialog.setVisible(true);
        generateData();

    }


    public static void generateData(){
        data1=in1Field.getText();
        data2=in2Field.getText();
        masksize = Integer.parseInt(data1);
        number = Integer.parseInt(data2);
    }

    public static int getMasksize() {
        return masksize;
    }

    public static int getNumber() {
        return number;
    }
}
