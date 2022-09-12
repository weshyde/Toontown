package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.BottomFeeder;

public class BottomFeeder {
	public static final int[][] RubberStamp = {
			{20, 20, 20, 20, 20},
			{2, 3, 4, 5, 6},
			{75, 80, 85, 90, 95}
	};
	public static final int[][] Shred = {
			{20, 20, 20, 20, 20},
			{2, 4, 6, 8, 10},
			{50, 55, 60, 65, 70}
	};
	public static final int[][] Watercooler = {
			{10, 10, 10, 10, 10},
			{3, 4, 5, 6, 7},
			{95, 95, 95, 95, 95}
	};
	public static final int[][] PickPocket = {
			{50, 50, 50, 50, 50},
			{1, 1, 2, 2, 3},
			{20, 30, 35, 40, 45}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {RubberStamp, Shred, Watercooler, PickPocket};
	public static final String[] attackNames = {"Rubber Stamp", "Shred", "Watercooler", "Pick Pocket"};
	public static final boolean[] hitAll = {false, false, false, false};
}
