package me.WesBag.TTCore.BattleMenu.Cogs.Bossbot.CorporateRaider;

public class CorporateRaider {
	public static final int[][] Canned = {
			{20, 20, 20, 20, 20},
			{6, 7, 8, 9, 10},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] EvilEye = {
			{35, 35, 35, 35, 35},
			{12, 15, 18, 21, 24},
			{60, 70, 75, 80, 90}
	};
	public static final int[][] PlayHardball = {
			{30, 30, 30, 30, 30},
			{7, 8, 12, 15, 16},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] Rolodex = {
			{15, 15, 15, 15, 15},
			{10, 12, 14, 16, 18},
			{65, 75, 80, 85, 95}
	};
	
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Canned, EvilEye, PlayHardball, Rolodex};
	public static final String[] attackNames = {"Canned", "Evil Eye", "Play Hardball", "Rolodex"};
	public static final boolean[] hitAll = {false, false, false, false};
}
