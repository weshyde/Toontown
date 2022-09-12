package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.Yesman;

public class Yesman {
	
	public static final int[][] RubberStamp = {
			{35, 35, 35, 35, 35},
			{2, 2, 3, 3, 4},
			{75, 75, 75, 75, 75}
	};
	
	public static final int[][] RazzleDazzle = {
			{25, 20, 15, 10, 5},
			{1, 1, 1, 1, 1},
			{50, 50, 50, 50, 50}
	};
	
	public static final int[][] Synergy = {
			{5, 10, 15, 25, 30},
			{4, 5, 6, 7, 8},
			{50, 60, 70, 80, 90}
	};
	
	public static final int[][] TeeOff = {
			{35, 35, 35, 35, 35},
			{3, 3, 4, 4, 5},
			{50, 60, 70, 80, 90}
	};
	
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {RubberStamp, RazzleDazzle, Synergy, TeeOff};
	public static final String[] attackNames = {"Rubber Stamp", "Razzle Dazzle", "Synergy", "Tee Off"};
	public static final boolean[] hitAll = {false, false, true, false};
}
