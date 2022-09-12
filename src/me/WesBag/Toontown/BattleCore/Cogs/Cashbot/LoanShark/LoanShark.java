package me.WesBag.TTCore.BattleMenu.Cogs.Cashbot.LoanShark;

public class LoanShark {

	public static final int[][] Bite = {
			{30, 30, 30, 30, 30},
			{10, 11, 13, 15, 16},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] Chomp = {
			{35, 35, 35, 35, 35},
			{12, 15, 18, 21, 24},
			{60, 70, 75, 80, 90}
	};
	public static final int[][] PlayHardball = {
			{20, 20, 20, 20, 20},
			{9, 11, 12, 13, 15},
			{55, 65, 75, 85, 95}
	};
	public static final int[][] WriteOff = {
			{15, 15, 15, 15, 15},
			{6, 8, 10, 12, 14},
			{70, 75, 80, 85, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Bite, Chomp, PlayHardball, WriteOff};
	public static final String[] attackNames = {"Bite", "Chomp", "Play Hardball", "Write Off"};
	public static final boolean[] hitAll = {false, false, false, false};
}
