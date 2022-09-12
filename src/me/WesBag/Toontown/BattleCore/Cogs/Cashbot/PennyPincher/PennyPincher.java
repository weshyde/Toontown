package me.WesBag.TTCore.BattleMenu.Cogs.Cashbot.PennyPincher;

public class PennyPincher {

	public static final int[][] BounceCheck = {
			{45, 45, 45, 45, 45},
			{4, 5, 6, 8, 12},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] FreezeAssets = {
			{20, 20, 20, 20, 20},
			{2, 3, 4, 6, 9},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] FingerWag = {
			{35, 35, 35, 35, 35},
			{1, 2, 3, 4, 6},
			{50, 50, 50, 50, 50}
	};
	public static final int numOfAttacks = 3;
	public static final int[][][] attacks = {BounceCheck, FreezeAssets, FingerWag};
	public static final String[] attackNames = {"Bounce Check", "Freeze Assets", "Finger Wag"};
	public static final boolean[] hitAll = {false, false, false};
}
