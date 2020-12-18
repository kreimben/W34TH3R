package com.Kreimben.About;

import com.Kreimben.WTColor;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

public class WTAboutThisAppViewController extends JFrame {

    static private WTAboutThisAppViewController _instance = null;

    static public WTAboutThisAppViewController showThisView(Component rootView) {
        if (_instance == null) { _instance = new WTAboutThisAppViewController(rootView); }
        return _instance;
    }

    private JLabel titleLabel;

    private JLabel myImage;
    private JTextArea introduceLabel;

    private JButton githubButton;
    private JButton websiteButton;

    private WTAboutThisAppViewController(Component rootView) {

        var frameSize = new Dimension(400, 400);

        this.setTitle("이 어플리케이션에 관하여");
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        this.setAlwaysOnTop(true);
        getContentPane().setBackground(WTColor.getRandomColor().getColor());
        this.setLocationRelativeTo(null);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {

                System.out.println("Closed!");

                _instance = null;
            }
        });

        this.setSize(frameSize);
        this.setResizable(false);

        this.setBasicComponent(null);

        this.setVisible(true);
    }

    private void setBasicComponent(WTCompletion completion) {
        this.makeTitleLabel();
        this.makeImageLabelAndIntroduceLabel();
        this.makeBottomButtons();

        if (completion != null) { completion.completion(); }
        this.pack();
    }

    private void makeTitleLabel() {
        this.titleLabel = new JLabel("이 어플리케이션에 관하여");
        this.titleLabel.setHorizontalAlignment(FlowLayout.LEFT);

        this.titleLabel.setBorder(new EmptyBorder(32, 0, 0, 0));
        this.titleLabel.setOpaque(false);

        this.titleLabel.setFont(this.titleLabel.getFont().deriveFont(24f));

        this.titleLabel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        this.add(this.titleLabel);
    }

    private void makeImageLabelAndIntroduceLabel() {

        var panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(24, 0, 24, 0));

        this.add(panel);

        panel.setOpaque(false);

        this.makeImageLabel(panel);
        this.makeIntroduceLabel(panel);
    }

    private void makeImageLabel(JPanel rootView) {

        //System.out.format("Current working directory is %s\n", System.getProperty("user.dir"));

        try {
            BufferedImage myPicture = ImageIO.read(new File("./src/com/Kreimben/kreimben_image.jpg"));
            this.myImage = new JLabel(new ImageIcon(myPicture));

            this.myImage.setSize(300, 300);

            rootView.add(this.myImage);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found!");
            e.getStackTrace();
        } catch (IOException e) {
            System.out.println("IOException e");
            e.getStackTrace();
        }
    }

    private void makeIntroduceLabel(JPanel rootView) {

        var introduce = new StringBuilder()
                .append("실시간으로 날씨를 가져오고 그에맞게 UI효과가 달라집니다.")
                .append("\n")
                .append("OpenWeatherMap의 open api를 이용해 만들었습니다.")
                .append("\n\n")
                .append("Multi-thread를 이용해 데이터를 주고 받기 때문에 더욱 빨리 데이터를 받을 수 있습니다.")
                .append("\n\n")
                .append("파일 입출력을 통해 설정 파일을 만들어 어느 도시의 날씨를 보여줄지 정할 수 있습니다.")
                .append("\n\n")
                .append("클로져가 없는 자바 환경을 극복하기 위해 FunctionalInterface를 채택하여 클로져를 구현하였습니다.")
                .append("\n\n")
                .append("간단하지만 기능에 충실하며, 무엇보다 잘 작동됩니다!")
                .toString();

        this.introduceLabel = new JTextArea(introduce);
        this.introduceLabel.setEditable(false);
        this.introduceLabel.setOpaque(false);
        this.introduceLabel.setFont(this.introduceLabel.getFont().deriveFont(16f));

        rootView.add(this.introduceLabel);
    }

    private void makeBottomButtons() {

        var panel = new JPanel();

        panel.setLayout(new FlowLayout());
        panel.setOpaque(false);

        panel.setBorder(new EmptyBorder(0, 0, 12, 0));

        this.add(panel);

        this.githubButton = new JButton("깃헙 링크");
        this.websiteButton = new JButton("개인 홈페이지 링크");

        panel.add(this.githubButton);
        panel.add(this.websiteButton);

        int length = 8;

        this.githubButton.setBorder(new EmptyBorder(length, length, length, length));
        this.websiteButton.setBorder(new EmptyBorder(length, length, length, length));

        this.githubButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var url_open = new String("https://github.com/kreimben/");
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
                }
                catch (MalformedURLException ex) { ex.getStackTrace(); }
                catch (IOException ex) { ex.getStackTrace(); }
            }
        });

        this.websiteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    var url_open = new String("https://www.kreimben.com/");
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(url_open));
                }
                catch (MalformedURLException ex) { ex.getStackTrace(); }
                catch (IOException ex) { ex.getStackTrace(); }
            }
        });
    }
}
