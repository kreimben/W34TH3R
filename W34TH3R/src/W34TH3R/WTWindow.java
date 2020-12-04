package W34TH3R;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Font;
import java.awt.Point;
import javax.swing.border.EmptyBorder;

public class WTWindow extends JFrame {

	public WTWindow() {
		
		// System configuration.
		this.setTitle("W34TH3R // 실시간 날씨 어플리케이션");
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.setSize(1024, 786);
		
		this.setResizable(true);
		
		this.setVisible(true);
		
		this.setLocationRelativeTo(null);
		
		JLabel lblNewLabel = new JLabel("현재 날씨");
		lblNewLabel.setBorder(new EmptyBorder(64, 0, 0, 0));
		lblNewLabel.setOpaque(true);
		lblNewLabel.setFont(new Font("Dotum", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(lblNewLabel, BorderLayout.NORTH);
	}
}
