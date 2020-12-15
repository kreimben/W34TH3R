package com.Kreimben;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;

public class WTAboutThisAppViewController extends JFrame {

    static private WTAboutThisAppViewController _instance = null;

    static public WTAboutThisAppViewController showThisView(Component rootView) {
        if (_instance == null) { _instance = new WTAboutThisAppViewController(rootView); }
        return _instance;
    }

    private JLabel testText;

    private WTAboutThisAppViewController(Component rootView) {

        var frameSize = new Dimension(600, 300);

        this.setTitle("이 어플리케이션에 관하여");
        this.setLayout(new FlowLayout());
        this.setAlwaysOnTop(true);
        getContentPane().setBackground(new Color(100, 200, 50));
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("Closed!");

                _instance = null;
            }
        });

        this.setMinimumSize(frameSize);
        this.setMaximumSize(frameSize);

        this.setBasicComponent(null);

        this.setVisible(true);
    }

    private void setBasicComponent(WTCompletion completion) {
        this.testText();

        if (completion != null) { completion.completion(); }
    }

    private void testText() {
        this.testText = new JLabel("this is test text");
        this.testText.setHorizontalAlignment(FlowLayout.LEFT);

        this.testText.setBackground(new Color(255, 0, 0));

        this.testText.setOpaque(true);

        this.add(this.testText);
    }
}
