package me.WesBag.TTCore.BattleMenu.Cogs.Cashbot.Tightwad;

public class Tightwad {

	public static final int[][] Fired = {
			{75, 5, 5, 5, 5},
			{3, 4, 5, 5, 6},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] GlowerPower = {
			{10, 15, 20, 25, 30},
			{3, 4, 6, 9, 12},
			{95, 95, 95, 95, 95}
	};
	public static final int[][] FingerWag = {
			{5, 70, 5, 5, 5},
			{3, 3, 4, 4, 5},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] FreezeAssets = {
			{5, 5, 65, 5, 30},
			{3, 4, 6, 9, 12},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] BounceCheck = {
			{5, 5, 5, 60, 30},
			{5, 6, 9, 13, 18},
			{75, 75, 75, 75, 75}
	};
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {Fired, GlowerPower, FingerWag, FreezeAssets, BounceCheck};
	public static final String[] attackNames = {"Fired", "Glower Power", "Finger Wag", "Freeze Assets", "Bounce Check"};
	public static final boolean[] hitAll = {false, false, false, false, false};
}
