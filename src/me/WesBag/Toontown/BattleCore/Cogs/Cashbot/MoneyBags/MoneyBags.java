package me.WesBag.Toontown.BattleCore.Cogs.Cashbot.MoneyBags;

public class MoneyBags {

	public static final int[][] Liquidate = {
			{30, 30, 30, 30, 30},
			{10, 12, 14, 16, 18},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] MarketCrash = {
			{45, 45, 45, 45, 45},
			{8, 10, 12, 14, 16},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] PowerTie = {
			{25, 25, 25, 25, 25},
			{6, 7, 8, 9, 10},
			{60, 65, 75, 85, 90}
	};
	public static final int numOfAttacks = 3;
	public static final int[][][] attacks = {Liquidate, MarketCrash, PowerTie};
	public static final String[] attackNames = {"Liquidate", "Market Crash", "Power Tie"};
	public static final boolean[] hitAll = {false, false, false};
}
