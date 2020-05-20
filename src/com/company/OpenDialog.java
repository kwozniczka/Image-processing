package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OpenDialog {

    private static JDialog dialog;
    JLabel label1=new JLabel("Radius");
    static JTextField in1Field=new JTextField();
    static String data;
    static int radius;

    public OpenDialog(){
        JFrame frame=new JFrame();
        dialog=new JDialog(frame, "Customize imOpen", true);
        init();
    }

    void init(){
        dialog.setTitle("Customize imOpen");
        dialog.setLayout(new GridLayout(2,2));
        dialog.add(label1);
        dialog.add(in1Field);
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
        getData();

    }


    public static int getData(){
        data=in1Field.getText();
        radius = Integer.parseInt(data);
        return radius;


    }



}



