package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.Bloodsucker;

public class Bloodsucker {
	public static final int[][] EvictionNotice = {
			{20, 20, 20, 20, 20},
			{1, 2, 3, 3, 4},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] RedTape = {
			{20, 20, 20, 20, 20},
			{2, 3, 4, 6, 9},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] Withdrawl = {
			{10, 10, 10, 10, 10},
			{6, 8, 10, 12, 14},
			{95, 95, 95, 95, 95}
	};
	public static final int[][] Liquidate = {
			{50, 50, 50, 50, 50},
			{2, 3, 4, 6, 9},
			{50, 60, 70, 80, 90}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {EvictionNotice, RedTape, Withdrawl, Liquidate};
	public static final String[] attackNames = {"Eviction Notice", "Red Tape", "Withdrawl", "Liquidate"};
	public static final boolean[] hitAll = {false, false, false, false};
}
