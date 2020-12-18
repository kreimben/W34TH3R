package com.Kreimben;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import org.json.simple.*;

public class WTMainViewController extends JFrame implements Runnable {

    private JLabel currentWeather; // Immutable label
    private JLabel weatherLabel; // Mutable label
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

        var frameSize = new Dimension(500, 370);

        setTitle("W34TH3R // 실시간 날씨 어플리케이션");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        setSize(frameSize);
        setResizable(false);

        setLocationRelativeTo(null);

        this.setBasicComponent(() -> {

            return this.fetchWeatherData();
        });
    }

    private HashMap<String, String> fetchWeatherData() {

        //String cityName = null;
        String description = null;

        var cityName = WTIOManager.getInstance().getSavedCityName();
        System.out.format("Saved city name is: %s\n", cityName);

        try {
            var result = WTNetworkManager.getInstance().fetchWeather(cityName);

            cityName = result.get("name").toString();
            var weatherObject = (JSONObject)( (JSONArray)result.get("weather") ).get(0);
            description = weatherObject.get("description").toString();
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            if (cityName != null) {
                var result = new HashMap<String, String>();
                result.put("cityName", cityName);
                result.put("description", description);

                return result;
            } else {
                var result = new HashMap<String, String>();
                result.put("cityName", "위치를 설정해 주세요!");
                result.put("description", "서버로 부터 불러오는 중...");

                return result;
            }
        }
    }

    private void setBasicComponent(WTMapCompletion completion) {

        var result = completion.completion();

        var cityName = result.get("cityName");
        var weather = result.get("description");

        this.makeCurrentWeather();
        this.makeWeatherLabel(weather);
        this.makeCurrentLocationAndButton();
        this.makeLocationLabel(cityName);
        this.makeAboutThisAppButton();

        this.setVisible(true);
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

    private void makeWeatherLabel(String text) {

        this.weatherLabel = new JLabel(text);//("서버로부터 데이터 불러오는 중...");
        this.weatherLabel.setSize(100, 25);
        this.weatherLabel.setFont(this.weatherLabel.getFont().deriveFont(32f));

        this.weatherLabel.setVisible(true);
        this.weatherLabel.setAlignmentX(this.alignment);

        this.weatherLabel.setBorder(new EmptyBorder(24, 0, 0, 0));

        getContentPane().add(this.weatherLabel);
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
                new WTSelectCityViewController();
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

        this.locationLabel.setFont(this.locationLabel.getFont().deriveFont(32f));

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
}
