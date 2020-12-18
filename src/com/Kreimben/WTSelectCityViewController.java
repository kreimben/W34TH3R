package com.Kreimben;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.Date;

public class WTSelectCityViewController extends JFrame {

    private JTextField textField;
    private JList listOfCities;

    private int windowWidth = 400;

    public WTSelectCityViewController() {

        var size = new Dimension(this.windowWidth, 500);

        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setBasicComponent(() -> {

        });
    }

    private void setBasicComponent(WTCompletion completion) {

        this.makeTextField();
        this.makeScrollList(this.testFunction(200));

        this.setVisible(true);

        if (completion != null) completion.completion();
    }

    private void makeTextField() {

        this.textField = new JTextField();

        this.textField.setBorder(new EmptyBorder(24, 12, 12, 0));

        this.textField.setOpaque(true);
        this.textField.setBackground(WTColor.TEAL.getColor());

        this.textField.setSize(this.windowWidth, 30);

        System.out.format("Text field's size is %s\n", this.textField.getSize());

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

    private String[] testFunction(int numberOfRepeat) {
        var result = new String[0];

        for (int i = 0; i < numberOfRepeat; i++) {

            result = Arrays.copyOf(result, result.length + 1);
            result[result.length - 1] = String.valueOf(i);
        }

        return result;
    }

    private void makeScrollList(String[] data) {

        this.listOfCities = new JList();

        this.listOfCities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOfCities.setLayoutOrientation(JList.VERTICAL);
        this.listOfCities.setBorder(new EmptyBorder(24, 8, 8, 8));
        this.listOfCities.setVisibleRowCount(25);
        this.listOfCities.setOpaque(true);
        this.listOfCities.setBackground(WTColor.BLUE.getColor());

        this.listOfCities.setListData(data);

        this.listOfCities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.format("This is selected as city! %s\n", e.getLastIndex());
            }
        });

        this.add(new JScrollPane(this.listOfCities));
    }
}
