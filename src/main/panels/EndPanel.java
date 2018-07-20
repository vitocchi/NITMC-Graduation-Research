package main.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class EndPanel extends JPanel {
	JTextArea jta = new JTextArea("協和・不協和判断課題が終了しました．\nボタンを押して実験を終了してください．");
	JButton btn = new JButton("終了する");
	public EndPanel() {
		this.add(jta,BorderLayout.CENTER);
		this.add(btn,BorderLayout.SOUTH);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
}
