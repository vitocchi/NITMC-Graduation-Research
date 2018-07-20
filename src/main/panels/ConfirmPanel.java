package main.panels;
//確認画面

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.MyFrame;

public class ConfirmPanel extends JPanel{

	//コンポーネント///////////////////////////////////////
	public String name;
	public String ID;
	JTextArea label1 = new JTextArea();
	JButton confirm = new JButton("確認");
	JButton revice = new JButton("修正");

	JPanel p1 = new JPanel();
	JPanel p2 = new JPanel();
	///////////////////////////////////////////////////////

	//画面を描画
	public ConfirmPanel(MyFrame mf) {

		//確認ボタンを押すと開始待機画面に遷移
		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.showWaitPanel(name,ID);
			}
		});

		//修正ボタンを押すとスタート画面に遷移
		revice.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mf.showStartPanel();
			}
		});

		//レイアウト
		p1.add(confirm);
		p1.add(revice);
		this.setLayout(new BorderLayout());
		this.add(p1,BorderLayout.CENTER);
		this.add(label1,BorderLayout.NORTH);
	}

	//確認用のラベルに名前とIDを設定
	public void setLabels(String name,String ID) {
		label1.setText("入力事項を確認してください\n名前　　："+name+"\n学生番号："+ID);
		this.name = name;
		this.ID = ID;
	}
}
