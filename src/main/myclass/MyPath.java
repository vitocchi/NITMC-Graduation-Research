package main.myclass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

//各ファイルのパスを返す
//ファイル構造を変えるときはこのクラスのパスを変更
public class MyPath {

	//アンサーデータのパス　nameには学生の氏名
	public static String pathOfAnswerData(String name,  Class<?> cls) {
		//DataBase/answerData/学生氏名.csv
		try {
			return URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8")+ "DataBase/answerData/"+name + ".csv";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String pathOfSortList(String list, Class<?> cls) {
		try {
			return URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8")+ "DataBase/SortList/"+list;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String pathOfcanon(Class<?> cls) {
		try {
			return URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8")+"DataBase/chords/canon.wav";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	//和音音源のパス strにはpri,folのいずれか　IDにはコード名
	public static String pathOfchords(String str,String ID,Class<?> cls) {
		try {
			return URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8")+"DataBase/chords/"+str+"/"+ID+".wav";
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	//コードリストのパス
	public static String pathOfList(String list,Class<?> cls) {
		try {
			return URLDecoder.decode(cls.getProtectionDomain().getCodeSource().getLocation().getPath(),"UTF-8")+"DataBase/chordList/"+list;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
}
