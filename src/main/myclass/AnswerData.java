//アンサーデータ

package main.myclass;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class AnswerData {

	long timer1;
	long timer2;

	//アンサーデータファイル
	File file1;

	String answers[] = new String[100];
	int sortKey[] = new int [100];

	//nameで受け取った名前のcsvファイルを開く
	//ソートキーを作る
	public void openFiles(String name,String sList) {
		file1 = new File(MyPath.pathOfAnswerData(name,getClass()));
		setSortKey(sList);
	}

	//ソートキーを取得
	public void setSortKey(String sList) {
		String line;
		String[] tmp;
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(MyPath.pathOfSortList(sList,getClass()))));
			line = br.readLine();
			tmp = (line.split(",",0));
			for(int i = 0; i<tmp.length;i++) {
				sortKey[i] = Integer.parseInt(tmp[i]);
			}
			br.close();
			//is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//回答をセット
	public void setAnswer(boolean ans,int num) {
		StringBuilder sb = new StringBuilder();
		if(ans == true) {
			sb.append("C,");
		}
		else if(ans == false) {
			sb.append("D,");
		}
		sb.append(String.valueOf(timer2));
		answers[num-1] = new String(sb.toString());
	}

	//タイマーをスタート
	public void startTimer() {
		timer1 = System.currentTimeMillis();
	}

	//タイマーをストップ
	public void stopTimer() {
		try {
		if((timer2 = System.currentTimeMillis()-timer1)<0)	throw new TimerException("timer is lower than 0||stop",timer2);
		}catch(TimerException e) {
			e.printStackTrace();
		}
	}

	//Header {BOM,実験情報（名前，学生番号）}を出力
	public void writeHeader(String name,String ID,String la,String clist){
		try {
//				BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file1));
//				bos.write(0xef);
//				bos.write(0xbb);
//				bos.write(0xbf);
//				bos.close();
				PrintWriter pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file1),"Shift-JIS")));
				pw.println(name + "," + ID + "," +la+"[dB],"+ clist);
				pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//現在の日時を出力
	public void writeDandT() {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
			pw.println(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd,HH:mm:ss")));
			pw.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//回答率を出力
	public void writeRate(int num) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
			pw.println(String.valueOf(calcRate(num)+",%"));
			pw.close();
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	//アンサーデータに回答を出力
	public void writeAnswer(int numOfans) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file1,true)));
			pw.println("番号,出題順,回答,時間[ms]");
			for(int i = 0;i<numOfans;i++) {
				pw.println(String.valueOf(i)+","+String.valueOf(sortKey[i])+","+answers[sortKey[i]]);
			}
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//回答率を算出（返り値　回答率[%]）
	public int calcRate(int length) {
		String  tmp[];
		int num = 0;
		for(int i = 0;i<length;i++) {
			tmp= answers[sortKey[i]].split(",",0);
			if((tmp[0].equals("C")&&i%2==0)||tmp[0].equals("D")&&i%2==1) {
				num++;
			}
		}
		return (int)(num/(double)length*100);
	}

	//アンサーデータを読み取り専用にする
	public void setReadOnly() {
		file1.setReadOnly();
	}
}



//タイマー関連の独自例外
class TimerException extends Exception {
	long timer;
	public TimerException(String str, long timer) {
		super(str);
		this.timer = timer;
	}
	public void printStackTrace() {
		System.err.println("timer = "+String.valueOf(timer));
		super.printStackTrace();
	}
}