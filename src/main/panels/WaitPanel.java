package main.panels;
//開始待機画面

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import main.MyFrame;

public class WaitPanel extends JPanel {

	//コンポーネント////////////////////////////////////
	JTextArea jta = new JTextArea("開始ボタンを押してください.\n押してから３秒後に最初の和音列が流れます．");
	JButton button = new JButton("開始");
	JPanel p = new JPanel(new BorderLayout());
	///////////////////////////////////////////////////

	//コンストラクタ//////////////////////////////////
	public WaitPanel(MyFrame mf){
		this.setLayout(new BorderLayout());
		p.add(jta,BorderLayout.CENTER);
		p.add(button, BorderLayout.SOUTH);
		this.add(p,BorderLayout.NORTH);

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
					mf.showTaskPanel();
				}
		});
	}
}
