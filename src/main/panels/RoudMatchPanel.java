package main.panels;

import java.awt.BorderLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JTextArea;

import main.MyFrame;
import main.myclass.MyPath;

//音量設定画面
public class RoudMatchPanel extends Panel{
	Clip clip;

	//コンポーネント
	JTextArea jta = new JTextArea("音量設定してください．");
	JButton btn1 = new JButton("設定を終了する");
	JButton btn2 = new JButton("一時停止");
	/////////////////////////////////////////////////////////

	//コンストラクタ
	public RoudMatchPanel(MyFrame mf) {
		//レイアウト
		this.add(jta,BorderLayout.CENTER);
		this.add(btn1,BorderLayout.SOUTH);
		this.add(btn2,BorderLayout.NORTH);
		setCanon();
		//設定終了ボタンが押されたらクリップをクローズしてスタート画面へ遷移する
		btn1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				mf.showStartPanel();
				clip.close();
			}
		});
		btn2.addActionListener(new ALForSp(clip,btn2));
	}

	//カノンを読み込む
	public void setCanon() {
		try {
				AudioInputStream st = AudioSystem.getAudioInputStream(new File(MyPath.pathOfcanon(getClass())));
				st.getFormat();
				clip=AudioSystem.getClip();
				clip.open(st);
				st.close();
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public void startCanon() {
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
}

class ALForSp implements ActionListener {

	boolean isPlaying = true;
	JButton btn;
	Clip clip;

	public ALForSp (Clip clip,JButton btn) {
		this.clip = clip;
		this.btn = btn;
	}

	public void actionPerformed(ActionEvent e) {
		if(isPlaying) {
			clip.stop();
			clip.setFramePosition(0);
			btn.setText("再生");
		}
		else {
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			btn.setText("一時停止");
		}
		isPlaying = !isPlaying;
	}
}
