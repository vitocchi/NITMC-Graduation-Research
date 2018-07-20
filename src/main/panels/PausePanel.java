package main.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.MyFrame;

//一時停止画面
public class PausePanel extends JPanel{

	//コンポーネント//////////////////////////////////
	JTextArea jta = new JTextArea("再開ボタンを押してください.\n押してから３秒後に最初の和音列が流れます．");
	JLabel l1 = new JLabel();
	JButton button = new JButton("再開");
	JPanel p = new JPanel(new BorderLayout());
	/////////////////////////////////////////////////

	//コンストラクタ////////////////////////////
	public PausePanel(MyFrame mf) {

		//レイアウト///////////////
		this.setLayout(new BorderLayout());
		p.add(jta,BorderLayout.CENTER);
		p.add(button, BorderLayout.SOUTH);
		p.add(l1, BorderLayout.NORTH);
		this.add(p,BorderLayout.NORTH);
		////////////////////////////

		//開始ボタンでカウントダウン
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int sec=3;sec>0;sec--) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						// TODO 自動生成された catch ブロック
						e1.printStackTrace();
					}
				}
				mf.restartTaskPanel();
			}
		});
	}

	//現在の課題の通し番号をラベルにセット
	public void setNum(int num) {
		l1.setText(Integer.toString(num)+"試行目");
	}
}


