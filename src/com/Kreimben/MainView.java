package com.Kreimben;

import javax.swing.*;
import java.awt.*;

public class MainView {

    public JFrame frame;

    private JPanel panel;
    private JLabel currentWeather;
    private JLabel weatherLabel;
    private JButton changeLocationButton;
    private JLabel locationLabel;
    private JButton aboutThisAppButton;
    private JPanel locationPanel;

    private Dimension size;
    private Dimension minimumSize;

    private void setBackgroundColor(Color color) {

    }

    public static void main(String[] argv) {

        var mainView = new MainView();
        var frame = mainView.frame;

        frame = new JFrame("W34TH3R // 실시간 날씨 어플리케이션");
        frame.setContentPane(new MainView().panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainView.size = new Dimension(600, 500);
        frame.setSize(mainView.size);

        mainView.minimumSize = new Dimension(585, 300);
        frame.setMinimumSize(mainView.minimumSize);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

}