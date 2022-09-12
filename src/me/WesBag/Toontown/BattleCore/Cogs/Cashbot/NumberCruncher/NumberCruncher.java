package me.WesBag.Toontown.BattleCore.Cogs.Cashbot.NumberCruncher;

public class NumberCruncher {
	
	public static final int[][] Audit = {
			{15, 15, 15, 15, 15},
			{5, 6, 8, 10, 12},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] Calculate = {
			{30, 30, 30, 30, 30},
			{6, 7, 9, 11, 13},
			{50, 65, 70, 75, 80}
	};
	public static final int[][] Crunch = {
			{35, 35, 35, 35, 35},
			{8, 9, 11, 13, 15},
			{60, 65, 75, 80, 85}
	};
	public static final int[][] Tabulate = {
			{20, 20, 20, 20, 20},
			{5, 6, 7, 8, 9},
			{50, 50, 50, 50, 50}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Audit, Calculate, Crunch, Tabulate};
	public static final String[] attackNames = {"Audit", "Calculate", "Crunch", "Tabulate"};
	public static final boolean[] hitAll = {false, false, false, false};
}
