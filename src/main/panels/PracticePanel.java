package main.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.MyFrame;

public class PracticePanel extends JPanel {
	JTextArea jta = new JTextArea();
	JButton btn = new JButton("実験を続ける");
	public PracticePanel(MyFrame mf) {
		this.add(jta,BorderLayout.CENTER);
		this.add(btn,BorderLayout.SOUTH);
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mf.showStartPanel();
			}
		});
	}
	public void setRate(int rate) {
		jta.setText("協和・不協和判断課題の練習が終了しました．\n正解率は"+rate+"%でした．\n実験を続ける場合はボタンを押してください．");
	}
}
