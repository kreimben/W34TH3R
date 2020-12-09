package com.Kreimben;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.*;

public class WTFrameController extends JFrame implements Runnable {

    private JLabel currentWeather; // Immutable label
    private JLabel weatherLabel; // Mutable label
    private JLabel currentLocation; // Immutable label
    private JButton changeLocationButton;
    private JLabel locationLabel; // Mutable label
    private JButton aboutThisAppButton;

    public void run() {
        init();
    }

    public WTFrameController() {
        super();
    }

    private void init() {

        var mSize = new Dimension(500, 350);
        setMinimumSize(mSize);
        setPreferredSize(mSize);
        setMaximumSize(mSize);

        setTitle("W34TH3R // 실시간 날씨 어플리케이션");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().setMinimumSize(mSize);
        getContentPane().setPreferredSize(mSize);

        setVisible(true);
        setLocationRelativeTo(null);

        this.setBasicComponent( () -> {
            //return this.fetchWeatherData();
            return new HashMap<String, String>();
        });
    }

    private HashMap<String, String> fetchWeatherData() {

        String cityName = null;
        String description = null;

        try {
            var result = WTNetworkManager.getInstance().getWeather("seoul");

            cityName = result.get("name").toString();
            var weatherObject = (JSONObject)( (JSONArray)result.get("weather") ).get(0);
            description = weatherObject.get("description").toString();
        }
        catch (Exception e) { System.out.println(e.getMessage()); }
        finally {
            var result = new HashMap<String, String>();
            result.put("cityName", cityName);
            result.put("description", description);

            return result;
        }
    }

    private void setBasicComponent(WTCompletion completion) {

        var result = completion.completion();

        var cityName = result.get("cityName");
        var weather = result.get("description");

        this.makeCurrentWeather();
        this.makeWeatherLabel(weather);
        this.makeCurrentLocationAndButton();
        this.makeLocationLabel(cityName);
        this.makeAboutThisAppButton();

        this.setVisible(true);

        //System.out.format("Locations: %s\n", this.weatherLabel.getAlignmentX());
    }

    private void makeCurrentWeather() {

        this.currentWeather = new JLabel("현재 날씨");
        this.currentWeather.setSize(100, 25);

        var f = new Font("Arial", Font.BOLD, 24);
        this.currentWeather.setFont(f);

        this.currentWeather.setVisible(true);
        this.currentWeather.setAlignmentX(JFrame.CENTER_ALIGNMENT);

        this.currentWeather.setBorder(new EmptyBorder(24, 0, 0, 0));

        getContentPane().add(this.currentWeather);
    }

    private void makeWeatherLabel(String text) {

        this.weatherLabel = new JLabel(text);//("서버로부터 데이터 불러오는 중...");
        this.weatherLabel.setSize(100, 25);

        var f = new Font("Arial", Font.BOLD, 32);
        this.weatherLabel.setFont(f);

        this.weatherLabel.setVisible(true);
        this.weatherLabel.setAlignmentX(JFrame.CENTER_ALIGNMENT);

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
        detailPanel.setAlignmentX((float)0.3);
        detailPanel.setOpaque(false);

        this.currentLocation = new JLabel("현재 위치");
        this.currentLocation.setSize(100, 25);

        var f = new Font("Arial", Font.BOLD, 24);
        this.currentLocation.setFont(f);

        this.changeLocationButton = new JButton("위치 바꾸기");

        detailPanel.setBorder(new EmptyBorder(32, 0, 0, 0));

        detailPanel.add(currentLocation);
        detailPanel.add(this.changeLocationButton);
        getContentPane().add(detailPanel);
    }

    private void makeLocationLabel(String text) {

        this.locationLabel = new JLabel(text);//("정보 없음");
        this.locationLabel.setSize(100, 25);

        var f = new Font("Arial", Font.BOLD, 32);
        this.locationLabel.setFont(f);

        this.locationLabel.setVisible(true);
        this.locationLabel.setAlignmentX(JFrame.CENTER_ALIGNMENT);

        this.locationLabel.setBorder(new EmptyBorder(24, 0, 24, 0));

        getContentPane().add(this.locationLabel);
    }

    private void makeAboutThisAppButton() {

        this.aboutThisAppButton = new JButton("이 어플리케이션에 관하여...");

        this.aboutThisAppButton.setAlignmentX(0.0F);

        //var size = new Dimension(170, 25);
        //this.aboutThisAppButton.setSize(size);
        //this.aboutThisAppButton.setPreferredSize(size);
        //this.aboutThisAppButton.setMinimumSize(size);
        //this.aboutThisAppButton.setMaximumSize(size);
        //System.out.format("Button info: %s\n", this.aboutThisAppButton);

        getContentPane().add(aboutThisAppButton);
    }
}
