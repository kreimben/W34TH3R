package com.Kreimben;

import javax.swing.*;
import java.awt.*;

public class WTFrameController extends JFrame {

    private JPanel rootPanel;

    private JLabel currentWeather; // Immutable label
    private JLabel weatherLabel; // Mutable label
    private JLabel currentLocation; // Immutable label
    private JLabel locationLabel; // Mutable label

    public WTFrameController() {
        super();

        var mSize = new Dimension(500, 300);
        setMinimumSize(mSize);

        var pSize = new Dimension(600, 500);
        setPreferredSize(pSize);

        setVisible(true);
        setLocationRelativeTo(null);

        //this.rootPanel = new JPanel();
        //this.setContentPane(this.rootPanel);

        setTitle("W34TH3R // 실시간 날씨 어플리케이션");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().setMinimumSize(mSize);
        getContentPane().setPreferredSize(pSize);

        this.setBasicComponent((JPanel)getContentPane());
        //setBackground(new Color(255, 0,0));
    }

    private void setBasicComponent(JPanel anchorPanel) {
        System.out.format("ContentPane info %s\n", anchorPanel);
        this.makeCurrentWeatherLabel(anchorPanel);
        System.out.format("Current Weather Label info %s\n", this.currentWeather);
    }

    private void makeCurrentWeatherLabel(JPanel anchorPanel) {

        this.currentWeather = new JLabel("현재 날씨");
        this.currentWeather.setSize(100, 25);

        this.currentWeather.setVisible(true);
        //anchorPanel.add(this.currentWeather);
        add(this.currentWeather);
    }
}
