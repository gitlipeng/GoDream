package com.godream.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.godream.R;

public class TrivalNote {
	private String title;
	private int cover;
	private String numComment;
	private String numVisit;
	private boolean treasure;

	private static String[] titleArr = new String[12];
	private static int[] coverArr = new int[12];
	private static String[] numCommentArr = new String[12];
	private static String[] numVisitArr = new String[12];
	static {
		titleArr[0] = "偷渡涠洲岛";
		titleArr[1] = "5月我们在涠洲岛烫一下(完结篇)";
		titleArr[2] = "没别的，那只是一座岛";
		titleArr[3] = "火山铸就的艺术";
		titleArr[4] = "一次无缘日出的涠洲行";
		titleArr[5] = "7月里.梦寐涠洲";
		titleArr[6] = "我们去看海";
		titleArr[7] = "~一起去涠洲岛看海吧~武汉触发，北海涠洲岛5天游！";
		titleArr[8] = "我也来发手绘游记~~~八月阳光剪不断";
		titleArr[9] = "【找个地方海誓山盟】CHEERY带你游一座有记忆的到——涠洲岛";
		titleArr[10] = "10天穷游桂林、阳朔、北海、涠洲岛详细攻略~~~";
		titleArr[11] = "再见最好的时光——涠洲岛";

		coverArr[0] = R.drawable.note1;
		coverArr[1] = R.drawable.note2;
		coverArr[2] = R.drawable.note3;
		coverArr[3] = R.drawable.note4;
		coverArr[4] = R.drawable.note5;
		coverArr[5] = R.drawable.note6;
		coverArr[6] = R.drawable.note7;
		coverArr[7] = R.drawable.note8;
		coverArr[8] = R.drawable.note9;
		coverArr[9] = R.drawable.note10;
		coverArr[10] = R.drawable.note11;
		coverArr[11] = R.drawable.note12;

		numCommentArr[0] = "1776";
		numCommentArr[1] = "14556";
		numCommentArr[2] = "234";
		numCommentArr[3] = "436";
		numCommentArr[4] = "654";
		numCommentArr[5] = "2234";
		numCommentArr[6] = "5443";
		numCommentArr[7] = "656";
		numCommentArr[8] = "45325";
		numCommentArr[9] = "1235";
		numCommentArr[10] = "66";
		numCommentArr[11] = "7";

		numVisitArr[0] = "12";
		numVisitArr[1] = "23";
		numVisitArr[2] = "567";
		numVisitArr[3] = "26";
		numVisitArr[4] = "45";
		numVisitArr[5] = "345";
		numVisitArr[6] = "109";
		numVisitArr[7] = "213";
		numVisitArr[8] = "234";
		numVisitArr[9] = "345";
		numVisitArr[10] = "425";
		numVisitArr[11] = "643";

	}

	public static List<TrivalNote> getData(int i) {
		List<TrivalNote> list = new ArrayList<TrivalNote>();
		int count = 0;
		while(count < i){
			list.add(getNote());
			count++;
		}
		return list;
	}

	private static TrivalNote getNote() {
		int i = new Random().nextInt(12);
		TrivalNote note = new TrivalNote();
		note.setTitle(titleArr[i]);
		i = new Random().nextInt(12);
		note.setCover(coverArr[i]);
		i = new Random().nextInt(12);
		note.setNumComment(numCommentArr[i]);
		i = new Random().nextInt(12);
		note.setNumVisit(numVisitArr[i]);
		i = new Random().nextInt(12);
		if(i%2 ==0)
			note.setTreasure(true);
		else
			note.setTreasure(false);
		return note;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCover() {
		return cover;
	}

	public void setCover(int cover) {
		this.cover = cover;
	}

	public String getNumComment() {
		return numComment;
	}

	public void setNumComment(String numComment) {
		this.numComment = numComment;
	}

	public String getNumVisit() {
		return numVisit;
	}

	public void setNumVisit(String numVisit) {
		this.numVisit = numVisit;
	}

	public boolean isTreasure() {
		return treasure;
	}

	public void setTreasure(boolean treasure) {
		this.treasure = treasure;
	}

}
