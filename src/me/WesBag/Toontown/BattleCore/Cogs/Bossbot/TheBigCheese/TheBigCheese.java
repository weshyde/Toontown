package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.TheBigCheese;

public class TheBigCheese {
	public static final int[][] TeeOff = {
			{50, 50, 50, 50, 50},
			{8, 11, 14, 17, 20},
			{55, 65, 70, 75, 80}
	};
	public static final int[][] GlowerPower = {
			{50, 50, 50, 50, 50},
			{14, 15, 17, 19, 22},
			{60, 70, 75, 85, 95}
	};
	
	public static final int numOfAttacks = 2;
	public static final int[][][] attacks = {TeeOff, GlowerPower};
	public static final String[] attackNames = {"Tee Off", "Glower Power"};
	public static final boolean[] hitAll = {false, false};
}
