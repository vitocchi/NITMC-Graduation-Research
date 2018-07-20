package main;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;


//メインクラス
//フォントの設定をした後MyFrameのインスタンスを生成する
public class LCexperiment {
	public static void main(String args[]) {

		//フォントの設定////////////
		Enumeration<Object> keys = UIManager.getDefaults().keys();
		while (keys.hasMoreElements()) {
		    Object key = keys.nextElement();
		    Object value = UIManager.get(key);
		    if (value instanceof javax.swing.plaf.FontUIResource) {
		        UIManager.put(key, new Font("メイリオ",Font.PLAIN,24));
		    }
		}
		////////////////////////////

		new MyFrame();
	}
}

