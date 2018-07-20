package main.panels;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.swing.JButton;

import main.MyFrame;
import main.myclass.ChordsManager;

//練習用の開始待機画面
//WaitPanelの子クラス

public class WaitPanelForPractice extends WaitPanel{
	//再生フラグ
	boolean isChordsPlaying = false;

	//コンストラクタ
	public WaitPanelForPractice(MyFrame mf) {
		super(mf);
		jta.setText("これは練習です\n開始ボタンを押してください.\n押してから３秒後に最初の和音列が流れます．\n協和音，不協和音の例が聴けます．");
		JButton btnC = new JButton("協和音　　　G→C");
		JButton btnD = new JButton("不協和音　　G→agC");

		//ChordsManagerのインスタンスは１つで使いまわす．
		ChordsManager cm = new ChordsManager();

		btnC.addActionListener(new ActionListenerForWPFP(cm,"G_1","C",this));
		btnD.addActionListener(new ActionListenerForWPFP(cm,"G_1", "agC",this));
		this.add(btnC,BorderLayout.WEST);
		this.add(btnD,BorderLayout.EAST);
	}
}

//以下リスナー//////////////////////////////////////////////////////////////

//協和音，不協和音ボタンのリスナー
//ボタンが押されるごとにクリップを取得し，リスナーを登録して，再生する
//ボタンが押されたときフラグを立てる
class ActionListenerForWPFP implements ActionListener{
	ChordsManager cm;
	WaitPanelForPractice wpfp;
	String pri;
	String fol;
	public ActionListenerForWPFP(ChordsManager cm, String pri, String fol, WaitPanelForPractice wpfp) {
		this.cm = cm;	this.wpfp = wpfp;	this.pri = pri;	this.fol=fol;
	}

	public void actionPerformed(ActionEvent e) {
		if(!wpfp.isChordsPlaying) {
			wpfp.isChordsPlaying = true;
			cm.setChords(pri, fol);
			cm.addPriLineListener(new PriLineListenerForWPFP(cm));
			cm.addFolLineListener(new FolLineListenerForWPFP(cm,wpfp));
			cm.startPriClip();
		}
	}
}

//和音列の例の先行和音のリスナー
//ストップしたら高属和音を流す
class PriLineListenerForWPFP implements LineListener{
	ChordsManager cm;
	public PriLineListenerForWPFP(ChordsManager cm) {
		this.cm = cm;
	}
	public void update(LineEvent event) {
		if(event.getType()==LineEvent.Type.STOP) {
			cm.startFolClip();
		}
	}
}

//和音列の例の後続和音のリスナー
//ストップしたらリスナーを解除し，クリップをクローズし，フラグを降ろす
class FolLineListenerForWPFP implements LineListener{
	ChordsManager cm;
	WaitPanelForPractice wpfp;
	public FolLineListenerForWPFP(ChordsManager cm, WaitPanelForPractice wpfp) {
		this.cm = cm;
		this.wpfp = wpfp;
	}
	public void update(LineEvent event) {
		if(event.getType()==LineEvent.Type.STOP) {
			cm.removePriLineListener();
			cm.removeFolLineListener();
			cm.closePriClip();
			cm.closeFolClip();
			wpfp.isChordsPlaying = false;
		}
	}
}
