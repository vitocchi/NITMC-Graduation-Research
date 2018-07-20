package main;
//メインフレームクラス

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.myclass.AnswerData;
import main.panels.AnswerPanel;
import main.panels.ConfirmPanel;
import main.panels.EndPanel;
import main.panels.PausePanel;
import main.panels.PracticePanel;
import main.panels.RoudMatchPanel;
import main.panels.StartPanel;
import main.panels.TaskPanel;
import main.panels.WaitPanel;
import main.panels.WaitPanelForPractice;


public class MyFrame extends JFrame{

	//アンサーデータのインスタンスを生成
	public AnswerData aD = new AnswerData();

	//画面遷移のための各画面パネルを生成
	public RoudMatchPanel rmp = new RoudMatchPanel(this);
	public StartPanel sp = new StartPanel(this);
	public ConfirmPanel cp = new ConfirmPanel(this);
	public WaitPanel wp  = new WaitPanel(this);
	public TaskPanel tp = new TaskPanel(this);
	public AnswerPanel ap = new AnswerPanel(this);
	public EndPanel ep = new EndPanel();
	public PausePanel pp = new PausePanel(this);
	public PracticePanel prp = new PracticePanel(this);
	public WaitPanelForPractice wpp = new WaitPanelForPractice(this);

	//カードレイアウトを設定
	public CardLayout layout = new CardLayout();

	//画面遷移のメインパネル
	public JPanel mp = new JPanel();

	//chordlistを記録
	String cList;
	//slistを記録
	String sList;
	//practiceフラグ
	boolean isPractice = false;

	private String la;
	//コンストラクタ//////////////////////////////////////////////////////////////////////////////////
	public MyFrame(){

		//メインパネルにカードレイアウトを設定
		mp.setLayout(layout);

		//タイトルを設定
		this.setTitle("LCexperiment");

		//ウィンドウの表示位置を設定
		this.setBounds(100,100,400,300);

		//ウィンドウの大きさを設定
		this.setSize(600,600);

		//クローズボタンで終了する
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//表示
		this.setVisible(true);

		//各パネルをカードに登録
		mp.add(rmp, "rmp");
		mp.add(sp,"sp");
		mp.add(cp,"cp");
		mp.add(wp, "wp");
		mp.add(tp, "tp");
		mp.add(ap, "ap");
		mp.add(ep,"ep");
		mp.add(pp,"pp");
		mp.add(prp,"prp");
		mp.add(wpp,"wpp");

		//カードをフレームに登録
		this.add(mp);

		//スタートパネル遷移メソッドへ
		this.showStartPanel();

	}
	////////////////////////////////////////////////////////////////////////////////////////////////////



	///////////////////////////////////////////////////////////////////////////////////////////////////
	/*  各画面に遷移するメソッド
	    遷移時の処理も行う
    	各画面のアクションリスナーにより呼び出される   */


	//スタート画面を表示する
	public void showStartPanel() {
		layout.show(mp,"sp");
	}

	//音量設定画面を表示して，カノンを再生する
	public void showRoudMatchPanel() {
		layout.show(mp,"rmp");
		rmp.startCanon();
	}

	//練習を開始する
	public void startPractice() {
		tp.openList("chordList_practice.csv");
		aD.openFiles("practice", "sortList_practice.csv");
		layout.show(mp, "wpp");
		//Practiceフラグを立てる
		isPractice = true;
	}

	//スタート画面で入力された情報を受け取り，確認画面を表示
	//リストをオープン
	public void showConfirmPanel(String name, String ID,String la,String cList,String sList) {
		tp.openList(cList);
		this.cList = cList;
		this.sList = sList;
		this.la = la;
		cp.setLabels(name, ID);
		layout.show(mp,"cp");
	}

	//確認された情報をアンサーデータに記録し，開始待機画面を表示
	public void showWaitPanel(String name, String ID) {
		aD.openFiles(name,sList);
		aD.writeHeader(name, ID,la,cList);
		layout.show(mp,"wp");
	}


	//時刻をアンサーデータに記録し，課題画面を表示
	//最初の課題を実施する
	public void showTaskPanel() {
		layout.show(mp, "tp");
		if(!isPractice) {
			aD.writeDandT();
		}
		tp.doTask();
	}

	//一時停止画面を表示
	public void showPausePanel(int num) {
		pp.setNum(num);
		layout.show(mp,"pp");
	}

	//課題を再開
	public void restartTaskPanel() {
		layout.show(mp, "tp");
		tp.restartTask();
	}

	//回答画面を表示
	//マウス入力の受付を開始
	//タイマーをスタート
	public void showAnswerPanel(int num) {
		ap.setNum(num);
		ap.setListener();
		aD.startTimer();
		layout.show(mp, "ap");

		//テスト用　setListenerをコメントアウト
//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		setAnswer(true);
		//テスト用　ここまで
	}

	//タイマーを終了
	//回答を記録
	//和音がなっている場合は停止
	//無音時間を作った後もう一度課題を実行
	public void setAnswer(boolean ans) {
		aD.stopTimer();
		aD.setAnswer(ans,tp.taskNum);
		tp.cm.stopFolClip();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		layout.show(mp, "tp");
		tp.doTask();
	}

	//実験終了画面を表示
	//練習時と実験時で異なった処理を行う
	public void showEndPanel() {
		if(isPractice) {
			prp.setRate(aD.calcRate(tp.taskNum));
			layout.show(mp, "prp");
			isPractice = false;
		}else {
			aD.writeAnswer(tp.taskNum);
			aD.writeRate(tp.taskNum);
			aD.writeDandT();
			aD.setReadOnly();
			layout.show(mp, "ep");
		}
	}
	//////////////////////////////////////////////////////////////////////////////////////////////////////
}