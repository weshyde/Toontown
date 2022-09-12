package me.WesBag.Toontown.BattleCore.Cogs.Cashbot.BeanCounter;

public class BeanCounter {

	public static final int[][] Audit = {
			{20, 20, 20, 20, 20},
			{4, 6, 9, 12, 15},
			{95, 95, 95, 95, 95}
	};
	public static final int[][] Calculate = {
			{25, 25, 25, 25, 25},
			{4, 6, 9, 12, 15},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] Tabulate = {
			{25, 25, 25, 25, 25},
			{4, 6, 9, 12, 15},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] WriteOff = {
			{30, 30, 30, 30, 30},
			{4, 6, 9, 12, 15},
			{95, 95, 95, 95, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Audit, Calculate, Tabulate, WriteOff};
	public static final String[] attackNames = {"Audit", "Calculate", "Tabulate", "Write Off"};
	public static final boolean[] hitAll = {false, false, false, false};
}
