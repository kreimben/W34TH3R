package com.Kreimben;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class WTSelectCityViewController extends JFrame {

    private JTextField textField;
    private JList listOfCities;

    private int windowWidth = 400;

    public WTSelectCityViewController() {
        var size = new Dimension(this.windowWidth, 500);
        setMinimumSize(size);
        setMaximumSize(size);
        setLocationRelativeTo(null);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setBasicComponent(null);
    }

    private void setBasicComponent(WTCompletion completion) {

        this.makeTextField();
        this.makeList();

        this.setVisible(true);

        if (completion != null) completion.completion();
    }

    private void makeTextField() {

        this.textField = new JTextField();

        this.textField.setBorder(new EmptyBorder(24, 12, 12, 0));
        this.textField.setSize(this.windowWidth, 24);

        System.out.format("text field size: %s\n", this.textField.getSize());
        this.textField.setOpaque(true);
        this.textField.setBackground(new Color(255, 0, 0));

        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyChar() == '\n') {

                    System.out.format("Key typed: %s\n", e.getKeyChar());
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });

        this.add(this.textField);
    }

    private void makeList() {

        this.listOfCities = new JList();

        System.out.format("List info: %s\n", this.listOfCities);

        this.listOfCities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOfCities.setLayoutOrientation(JList.VERTICAL);
        this.listOfCities.setVisibleRowCount(-1);
        this.listOfCities.setBorder(new EmptyBorder(24, 8, 8, 8));

        this.add(this.listOfCities);
    }
}
