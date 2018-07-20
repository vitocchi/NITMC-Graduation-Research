package main.panels;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import main.MyFrame;

//回答画面
public class AnswerPanel extends JPanel {

	//コンポーネント////////////////
	JLabel taskNumL = new JLabel();
	JLabel left =  new JLabel("<html>　　 協和 　<br>　　　↓　　<br>　左クリック");
	JLabel right = new JLabel("<html>　不協和　　<br>　　↓　　　<br>右クリック　");
	JPanel p = new JPanel(new BorderLayout());
	////////////////////////////////
	MyFrame mf;

	//コンストラクタ/////////////////////
	public AnswerPanel(MyFrame mf) {
		this.mf = mf;
		this.setLayout(new BorderLayout());
		p.add(left,BorderLayout.WEST);
		p.add(right,BorderLayout.EAST);
		this.add(taskNumL,BorderLayout.NORTH);
		this.add(p, BorderLayout.CENTER);
	}


	public void setNum(int num) {
		taskNumL.setText(String.valueOf(num)+"試行目");
	}

	//マウスリスナーをセット
	//画面を表示する度に呼び出す
	public void setListener() {
		AnswerPanel ap = this;
		ap.addMouseListener(new MouseListener() {

			//マウスの左右ボタンが押されたらリスナー解除し，回答内容を記録するメソッドを呼ぶ
			public void mousePressed(MouseEvent e) {

				if(SwingUtilities.isLeftMouseButton(e)) {
					ap.removeMouseListener(this);
					mf.setAnswer(true);
				}

				else if(SwingUtilities.isRightMouseButton(e)){
					ap.removeMouseListener(this);
					mf.setAnswer(false);
				}
			}

			//以下は処理なし
			public void mouseClicked(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
		});
	}
}

