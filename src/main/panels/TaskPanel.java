package main.panels;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MyFrame;
import main.myclass.ChordsManager;
import main.myclass.MyPath;

public class TaskPanel extends JPanel{

	//コンポーネント//////////////////////////
	JLabel taskNumL = new JLabel();
	JPanel p = new JPanel(new BorderLayout());
	JButton btn = new JButton("一時中断");
	//////////////////////////////////////////

	//課題の通し番号
	public int taskNum = 0;
	//コードリストのリーダー
	BufferedReader Br;
	MyFrame mf;

	//和音列のマネージャを１つ宣言．使いまわす
	public ChordsManager cm = new ChordsManager();

	//コンストラクタ
	public TaskPanel(MyFrame mf) {
		//レイアウト
		this.mf = mf;
		p.add(taskNumL,BorderLayout.NORTH);
		p.add(btn,BorderLayout.SOUTH);
		this.setLayout(new BorderLayout());
		this.add(taskNumL,BorderLayout.NORTH);
		this.add(btn,BorderLayout.SOUTH);

		//一時停止ボタンのアクションリスナー
		TaskPanel tp = this;
		btn.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mf.showPausePanel(taskNum);
				tp.pauseTask();
			}
		});
	}

	//入力されたコードリストを開いてBufferedReaderも登録
	public void openList(String List) {
		try {
			Br = new BufferedReader(new FileReader(new File(MyPath.pathOfList(List,getClass()))));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	//試行を一回行う
	public void doTask() {
		String line;
		String[] tmp;

		try {
			//コードリストが終わっていない場合
			if((line = Br.readLine())!=null) {
				tmp = line.split(",",0);
				taskNumL.setText(tmp[0]+"試行目");
				taskNum = Integer.parseInt(tmp[0]);
				cm.setChords(tmp[1],tmp[2]);
				cm.addPriLineListener(new LineListenerOfTpOfPri(cm,this));
				cm.addFolLineListener(new LineListenerOfTpOfFol(cm,this));
				cm.startPriClip();
			}
			//コードリストが終わった場合
			else {
				Br.close();
				mf.showEndPanel();
			}
		}catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

	}

	//先行和音が流れている現在の試行を一時停止する
	public void pauseTask() {
		cm.removePriLineListener();
		cm.stopPriClip();
	}

	//試行を再開する
	public void restartTask() {
		cm.addPriLineListener(new LineListenerOfTpOfPri(cm,this));
		cm.startPriClip();
	}

	//回答画面へ遷移
	//試行の通し番号を渡す
	public void showAnswerPanel() {
		mf.showAnswerPanel(taskNum);
	}
}




/////////////////リスナークラス////////////////////////////////////////////////////////

//課題の先行和音に登録するリスナー
class LineListenerOfTpOfPri implements LineListener{
	ChordsManager cm;
	TaskPanel tp;
	//コンストラクタ　フィールドの登録
	public LineListenerOfTpOfPri(ChordsManager cm,TaskPanel tp) {
		this.cm = cm;
		this.tp = tp;
	}

	//停止したらリスナーを解除し，後続和音を流し，回答画面へ遷移するメソッドを呼ぶ
	public void update(LineEvent event) {
		if(event.getType()== LineEvent.Type.STOP ) {
			cm.removePriLineListener();
			cm.startFolClip();
			tp.showAnswerPanel();
		}
	}
}

//課題の後続和音に登録するリスナー
class LineListenerOfTpOfFol implements LineListener{
	ChordsManager cm;
	TaskPanel tp;
	//コンストラクタ　フィールドの登録
	public LineListenerOfTpOfFol(ChordsManager cm,TaskPanel tp) {
		this.cm = cm;
		this.tp = tp;
	}
	//リスナーを解除し，クリップをクローズする
	public void update(LineEvent event) {
		if(event.getType()== LineEvent.Type.STOP ) {
			cm.removeFolLineListener();
			cm.closeFolClip();
			cm.closePriClip();
		}
	}
}