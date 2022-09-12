package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.Downsizer;

public class Downsizer {
	public static final int[][] Canned = {
			{25, 25, 25, 25, 25},
			{5, 6, 8, 10, 12},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] Downsize = {
			{35, 35, 35, 35, 35},
			{8, 9, 11, 13, 15},
			{50, 65, 70, 75, 80}
	};
	public static final int[][] Pinkslip = {
			{25, 25, 25, 25, 25},
			{4, 5, 6, 7, 8},
			{60, 65, 75, 80, 85}
	};
	public static final int[][] Sacked = {
			{15, 15, 15, 15, 15},
			{5, 6, 7, 8, 9},
			{50, 50, 50, 50, 50}
	};
	
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Canned, Downsize, Pinkslip, Sacked};
	public static final String[] attackNames = {"Canned", "Downsize", "Pinkslip", "Sacked"};
	public static final boolean[] hitAll = {false, false, false, false};
}
