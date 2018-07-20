package main.myclass;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//	和音列クリップの取得，再生，停止，解放を担当
//　和音列クリップにはこのクラスのメソッドを使ってアクセス

public class ChordsManager {
	//先行和音と後続和音のクリップ
	protected Clip priClip;
	protected Clip folClip;

	//先行和音と高属和音に登録されているリスナー
	protected LineListener Lop;
	protected LineListener Lof;


	//コード名を渡してクリップを取得
	public void setChords(String nameOfpriChord , String nameOffolChord) {
		try {
			final AudioInputStream st1 = AudioSystem.getAudioInputStream(new File(MyPath.pathOfchords("pri",nameOfpriChord,getClass())));
			final AudioInputStream st2 = AudioSystem.getAudioInputStream(new File(MyPath.pathOfchords("fol",nameOffolChord,getClass())));
			st1.getFormat();
			st2.getFormat();
			priClip = AudioSystem.getClip();
			folClip = AudioSystem.getClip();
			priClip.open(st1);
			folClip.open(st2);
			st1.close();
			st2.close();
		} catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
			e.printStackTrace();
		}
	}

	//priClipを再生
	public void startPriClip() {
		if(priClip!=null) {
			if(!priClip.isActive()) {
				priClip.start();
			}
		}
	}

	//folClipを再生
	public void startFolClip() {
		if(folClip!=null) {
			if(!folClip.isActive()) {
				folClip.start();
			}
		}
	}

	//priClipを停止
	public void stopPriClip() {
		if(priClip!=null) {
			if(priClip.isActive()) {
				priClip.stop();
				resetPriClip();
			}
		}
	}

	//folClipを停止
	public void stopFolClip() {

		if(folClip!=null) {
			if(folClip.isActive()) {
				folClip.stop();
				resetFolClip();
			}
		}
	}

	public void resetPriClip() {
		priClip.setFramePosition(0);
	}

	public void resetFolClip() {
		folClip.setFramePosition(0);
	}

	//priClipを解放
	public void closePriClip() {
		priClip.close();
	}

	//folClipを解放
	public void closeFolClip() {
		folClip.close();
	}

	//priClipにラインリスナーlを登録，登録したリスナーをLopに記録
	public void addPriLineListener(LineListener l) {
		Lop = l;
		priClip.addLineListener(l);
	}

	//LopのリスナーをPriClipから削除
	public void removePriLineListener() {
		priClip.removeLineListener(Lop);
	}

	//folClipにラインリスナーlを登録，登録したリスナーをLofに記録
	public void addFolLineListener(LineListener l) {
		Lof = l;
		folClip.addLineListener(l);
	}

	//LofのリスナーをfolClipから削除
	public void removeFolLineListener() {
		folClip.removeLineListener(Lof);
	}

}
