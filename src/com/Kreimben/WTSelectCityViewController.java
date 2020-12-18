package com.Kreimben;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

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

    private JLabel noticeLabel;
    private JTextField textField;
    private JList listOfCities;

    private int windowWidth = 400;

    public WTSelectCityViewController() {

        var size = new Dimension(this.windowWidth, 500);

        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        this.setBasicComponent(null);
    }

    private void setBasicComponent(WTCompletion completion) {

        getContentPane().setBackground(WTColor.RED.getColor());

        this.makeNoticeLabel();
        this.makeTextField();
        this.makeScrollList(null);

        this.setVisible(true);

        if (completion != null) completion.completion();
    }

    private void makeNoticeLabel() {

        this.noticeLabel = new JLabel("영어로 입력해야 해요. 한국어 적으면 서버가 인식하지 못해요.");

        this.noticeLabel.setBorder(new EmptyBorder(24, 0, 0, 0));

        this.noticeLabel.setOpaque(false);

        this.add(noticeLabel);
    }

    private void makeTextField() {

        this.textField = new JTextField();

        this.textField.setBorder(new EmptyBorder(12, 12, 12, 12));

        this.textField.setOpaque(false);

        this.textField.setSize(this.windowWidth, 30);

        this.textField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

                if (e.getKeyChar() == '\n') {

                    try {

                        var data = WTNetworkManager.getInstance().searchCities(textField.getText());
                        var convertedData = (JSONArray) data.get("data");

                        setCities(convertedData);

                    } catch (Exception error) { System.out.println("Error occured!"); error.getMessage(); }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) { }

            @Override
            public void keyReleased(KeyEvent e) { }
        });

        this.add(this.textField);
    }

    private void makeScrollList(String[] data) {

        this.listOfCities = new JList();

        this.listOfCities.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.listOfCities.setLayoutOrientation(JList.VERTICAL);
        this.listOfCities.setBorder(new EmptyBorder(24, 8, 8, 8));
        this.listOfCities.setVisibleRowCount(25);
        this.listOfCities.setOpaque(false);

        this.setInitialList();

        this.listOfCities.setBackground(WTColor.GREEN.getColor());

        this.listOfCities.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                System.out.format("This is selected as city! %s\n", getSelectedValue());
            }
        });

        var scroll = new JScrollPane(this.listOfCities);

        scroll.setOpaque(false);
        scroll.setBackground(WTColor.GREEN.getColor());

        this.add(scroll);
    }

    private String getSelectedValue() {
        var selected = this.listOfCities.getSelectedValue().toString();

        var value = selected.split(" ");

        return value[0];
    }

    private void setInitialList() {
        this.listOfCities.setListData(new String[] { "검색해보세요!" } );
    }

    private void setNoDataList() {
        this.listOfCities.setListData(new String[] { "서버로부터 데이터를 불러오지 못했습니다.", "다시 검색해보세요!"} );
    }

    private void setCities(JSONArray json) {
        var data = new String[0];

        if (json.isEmpty() == true) {
            System.out.println("There is no data!");
            this.setNoDataList();
        } else {

            for (int i = 0; i < json.size(); i++) {

                data = Arrays.copyOf(data, data.length + 1);

                var line = (JSONObject)json.get(i);

                data[data.length - 1] = (String)line.get("city") + " (" + (String)line.get("country") + " / " + (String)line.get("region") + ")";
            }

            for (var city : data) {
                System.out.format("city info: %s\n", city);
            }

            this.listOfCities.setListData(data);
        }
    }
}