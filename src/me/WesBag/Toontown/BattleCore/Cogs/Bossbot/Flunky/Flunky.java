package me.WesBag.TTCore.BattleMenu.Cogs.Bossbot.Flunky;

public class Flunky {
	public static final int numOfAttacks = 3;
	
	public static final int[][] PoundKey = {
			{30, 35, 40, 45, 50},
			{2, 2, 3, 4, 6},
			{75, 75, 80, 80, 90}
	};
	
	public static final int[][] Shred = {
			{10, 15, 20, 25, 30},
			{3, 4, 5, 6, 7},
			{50, 55, 60, 65, 70}
	};
	
	public static final int[][] ClipOnTie = {
			{60, 50, 40, 30, 20},
			{1, 1, 2, 2, 3},
			{75, 80, 85, 90, 95}
	};
	public static final int[][][] attacks = {PoundKey, Shred, ClipOnTie};
	public static final String[] attackNames = {"Pound Key", "Shred", "Clip On Tie"};
	public static final boolean[] hitAll = {false, false, false};
}
