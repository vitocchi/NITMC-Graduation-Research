package main.panels;
//スタート画面

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.MyFrame;

public class StartPanel extends JPanel{
	//コンポーネントの宣言/////////////////////////////
	JTextArea jta = new JTextArea("名前と学生番号を入力してください\n練習問題をする場合は練習ボタンを押してください");
	JLabel l1 = new JLabel("名前");
	JLabel l2 = new JLabel("学生番号");
	JLabel l3 = new JLabel("コードリスト");
	JLabel l4 = new JLabel("ソートリスト");
	JLabel l5 = new JLabel("音圧レベル");
	JTextField name = new JTextField("名前");
	JTextField ID = new JTextField("学生番号");
	JTextField la = new JTextField("音圧レベル");
	JTextField cList = new JTextField("chordList_12_1.csv");
	JTextField sList = new JTextField("sortList_12_1.csv");


	JButton button = new JButton("決定");
	JButton pbutton = new JButton("練習");
	JButton rbutton = new JButton("ラウドネスマッチング");

	JPanel p1 = new JPanel(new BorderLayout());
	JPanel p2 = new JPanel(new BorderLayout());
	JPanel p3 = new JPanel(new BorderLayout());
	JPanel p4 = new JPanel(new BorderLayout());
	JPanel p5 = new JPanel(new BorderLayout());
	JPanel p6 = new JPanel(new BorderLayout());

	JPanel buts = new JPanel(new BorderLayout());
	///////////////////////////////////////////////////

	//決定ボタンで確認画面に遷移
	//ラベルのテキストを確認画面に渡す
	//練習ボタンで練習画面に遷移
	public StartPanel(MyFrame mf) {
		this.setLayout(new BorderLayout());
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.showConfirmPanel(name.getText(),ID.getText(),la.getText(),cList.getText(),sList.getText());
			}
		});
		pbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.startPractice();
			}
		});
		rbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.showRoudMatchPanel();
			}
		});


		buts.add(button,BorderLayout.NORTH);
		buts.add(pbutton,BorderLayout.CENTER);
		buts.add(rbutton,BorderLayout.SOUTH);

		p1.add(name,BorderLayout.PAGE_START);
		p1.add(ID,BorderLayout.CENTER);
		p1.add(la,BorderLayout.PAGE_END);
		p2.add(l1,BorderLayout.PAGE_START);
		p2.add(l5,BorderLayout.CENTER);
		p2.add(l2,BorderLayout.PAGE_END);
		p3.add(l3,BorderLayout.CENTER);
		p3.add(cList,BorderLayout.EAST);
		p5.add(l4,BorderLayout.CENTER);
		p5.add(sList,BorderLayout.EAST);
		p6.add(p3,BorderLayout.SOUTH);
		p6.add(p5,BorderLayout.NORTH);
		p4.add(jta, BorderLayout.NORTH);
		p4.add(p2,BorderLayout.WEST);
		p4.add(p1,BorderLayout.CENTER);
		p4.add(buts,BorderLayout.SOUTH);
		this.add(p4,BorderLayout.NORTH);
		this.add(p6,BorderLayout.SOUTH);
	}
}