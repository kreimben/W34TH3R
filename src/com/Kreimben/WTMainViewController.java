package com.Kreimben;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.json.simple.*;

public class WTMainViewController extends JFrame implements Runnable {

    private JLabel currentWeather; // Immutable label
    private JLabel weatherLabel; // Mutable label
    private JLabel weatherImage; //Immutable label
    private JLabel currentLocation; // Immutable label
    private JButton changeLocationButton;
    private JLabel locationLabel; // Mutable label
    private JButton aboutThisAppButton;

    private float alignment = JFrame.CENTER_ALIGNMENT;

    public void run() {
        init();
    }

    public WTMainViewController() {
        super();
    }

    private void init() {

        var frameSize = new Dimension(500, 390);

        setTitle("W34TH3R // 실시간 날씨 어플리케이션");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().setBackground(WTColor.getRandomColor().getColor());

        setSize(frameSize);
        setResizable(false);

        setLocationRelativeTo(null);

        this.refreshThisView();
    }

    private void setBasicComponent(WTMapCompletion completion) {

        var result = completion.completion();

        var cityName = result.get("cityName");
        var weather = result.get("description");
        var icon = result.get("icon");

        this.makeCurrentWeather();
        if (icon != null) {
            this.makeWeatherLabelAndImage(weather, icon);
        } else {
            this.getContentPane().add(this.makeWeatherLabel(weather));
        }
        this.makeCurrentLocationAndButton();
        this.makeLocationLabel(cityName);
        this.makeAboutThisAppButton();

        this.setVisible(true);
    }

    private HashMap<String, String> fetchWeatherData() {

        String description = null;

        var line = WTIOManager.getInstance().getSavedCityName();
        var cityName = "";
        if (line != null) { cityName = line.split(" ")[0]; }
        var icon = "";
        System.out.format("Saved city name is: %s\n", line);

        try {
            var result = WTNetworkManager.getInstance().fetchWeather(cityName);

            var weatherObject = (JSONObject)( (JSONArray)result.get("weather") ).get(0);
            description = weatherObject.get("description").toString();
            icon = weatherObject.get("icon").toString();
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            if (description != null && !icon.isEmpty()) { //cityName != null) {
                var result = new HashMap<String, String>();
                result.put("cityName", line);//cityName);
                result.put("description", description);
                result.put("icon", icon);

                return result;
            } else {
                var result = new HashMap<String, String>();
                result.put("cityName", "위치를 설정해 주세요!");
                result.put("description", "서버로 부터 불러오는 중...");

                return result;
            }
        }
    }

    private void makeCurrentWeather() {

        this.currentWeather = new JLabel("현재 날씨");
        this.currentWeather.setSize(100, 25);
        this.currentWeather.setFont(this.currentWeather.getFont().deriveFont(25f));

        this.currentWeather.setVisible(true);
        this.currentWeather.setAlignmentX(this.alignment);

        this.currentWeather.setBorder(new EmptyBorder(24, 0, 0, 0));

        getContentPane().add(this.currentWeather);
    }

    private void makeWeatherLabelAndImage(String description, String icon) {

        System.out.format("weather description: %s\n", description);
        System.out.format("icon: %s\n", icon);

        var weatherLabel = makeWeatherLabel(description);
        var weatherImage = makeWeatherImage(icon);

        var panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.setMaximumSize(new Dimension(500, 100));
        panel.setAlignmentX(this.alignment);
        panel.setOpaque(false);

        if (weatherImage != null && weatherLabel != null) {
            panel.add(weatherLabel);
            panel.add(weatherImage);
        } else { System.out.format("weather label: %s\n", weatherImage, weatherLabel); }

        getContentPane().add(panel);
    }

    private JLabel makeWeatherLabel(String text) {

        var label = new JLabel(text);
        label.setSize(100, 25);
        label.setFont(label.getFont().deriveFont(32f));

        label.setVisible(true);
        label.setAlignmentX(this.alignment);

        label.setBorder(new EmptyBorder(24, 0, 0, 0));

        this.weatherLabel = label;
        return label;
    }

    private JLabel makeWeatherImage(String icon) {

        this.weatherImage = new JLabel(WTNetworkManager.getInstance().loadWeatherIcon(icon));
        return this.weatherImage;
    }

    private void makeCurrentLocationAndButton() {

        var detailPanel = new JPanel();
        detailPanel.setLayout(new FlowLayout());
        int width = 500;
        int height = 80;
        var size = new Dimension(width, height);
        detailPanel.setMaximumSize(size);
        detailPanel.setAlignmentX(this.alignment);
        detailPanel.setOpaque(false);

        this.currentLocation = new JLabel("현재 위치");
        this.currentLocation.setSize(100, 25);

        this.currentLocation.setFont(this.currentLocation.getFont().deriveFont(24f));

        this.changeLocationButton = new JButton("위치 바꾸기");
        this.changeLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                var vc = new WTSelectCityViewController();

                var thread = new Thread(vc);

                vc.doAfterClosedThisView = () -> {
                    getContentPane().removeAll();
                    refreshThisView();
                };

                thread.start();
            }
        });

        detailPanel.setBorder(new EmptyBorder(32, 0, 0, 0));

        detailPanel.add(this.currentLocation);
        detailPanel.add(this.changeLocationButton);
        getContentPane().add(detailPanel);
    }

    private void makeLocationLabel(String text) {

        this.locationLabel = new JLabel(text);//("정보 없음");
        this.locationLabel.setSize(100, 25);

        this.locationLabel.setFont(this.locationLabel.getFont().deriveFont(16f));

        this.locationLabel.setVisible(true);
        this.locationLabel.setAlignmentX(this.alignment);

        this.locationLabel.setBorder(new EmptyBorder(24, 0, 24, 0));

        getContentPane().add(this.locationLabel);
    }

    private void makeAboutThisAppButton() {

        this.aboutThisAppButton = new JButton("이 어플리케이션에 관하여...");
        this.aboutThisAppButton.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WTAboutThisAppViewController.showThisView(null);
            }
        });

        this.aboutThisAppButton.setAlignmentX(this.alignment);

        getContentPane().add(aboutThisAppButton);
    }

    synchronized private void refreshThisView() {

        this.setBasicComponent(() -> {

            return this.fetchWeatherData();
        });

        this.repaint();
    }
}
