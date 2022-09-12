package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.Micromanager;

public class Micromanager {
	public static final int[][] Demotion = {
			{30, 30, 30, 30, 30},
			{6, 8, 12, 15, 18},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] FingerWag = {
			{10, 10, 10, 10, 10},
			{4, 6, 9, 12, 15},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] FountainPen = {
			{15, 15, 15, 15, 15},
			{3, 4, 6, 8, 10},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] BrainStorm = {
			{25, 25, 25, 25, 25},
			{4, 6, 9, 12, 15},
			{5, 5, 5, 5, 5}
	};
	public static final int[][] Buzzword = {
			{20, 20, 20, 20, 20},
			{4, 6, 9, 12, 15},
			{50, 60, 70, 80, 90}
	};
	
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {Demotion, FingerWag, FountainPen, BrainStorm, Buzzword};
	public static final String[] attackNames = {"Demotion", "Finger Wag", "Fountain Pen", "Brain Storm", "Buzzword"};
	public static final boolean[] hitAll = {false, false, false, false, false};
}
