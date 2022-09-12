package me.WesBag.TTCore.BattleMenu.Cogs.Cashbot.ShortChange;

public class ShortChange {

	public static final int[][] WaterCooler = {
			{20, 20, 20, 20, 20},
			{2, 2, 3, 4, 6},
			{50, 50, 50, 50, 50}
	};
	public static final int[][] BounceCheck = {
			{15, 15, 15, 15, 15},
			{3, 5, 7, 9, 11},
			{75, 80, 85, 90, 95}
	};
	public static final int[][] ClipOnTie = {
			{25, 25, 25, 25, 25},
			{1, 1, 2, 2, 3},
			{50, 50, 50, 50, 50}
	};
	public static final int[][] PickPocket = {
			{40, 40, 40, 40, 40},
			{2, 2, 3, 4, 6},
			{95, 95, 95, 95, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {WaterCooler, BounceCheck, ClipOnTie, PickPocket};
	public static final String[] attackNames = {"Water Cooler", "Bounce Check", "Clip On Tie", "Pick Pocket"};
	public static final boolean[] hitAll = {false, false, false, false};
}
