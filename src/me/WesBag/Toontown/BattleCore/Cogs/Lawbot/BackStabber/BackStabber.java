package me.WesBag.TTCore.BattleMenu.Cogs.Lawbot.BackStabber;

public class BackStabber {
	public static final int[][] GuiltTrip = {
			{40, 40, 40, 40, 40},
			{8, 11, 13, 15, 18},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] RestrainingOrder = {
			{25, 25, 25, 25, 25},
			{6, 7, 9, 11, 13},
			{50, 65, 70, 75, 90}
	};
	public static final int[][] FingerWag = {
			{35, 35, 35, 35, 35},
			{5, 6, 7, 8, 9},
			{50, 55, 65, 75, 80}
	};
	public static final int numOfAttacks = 3;
	public static final int[][][] attacks = {GuiltTrip, RestrainingOrder, FingerWag};
	public static final String[] attackNames = {"Guilt Trip", "Restraining Order", "Finger Wag"};
	public static final boolean[] hitAll = {true, false, false};
}
