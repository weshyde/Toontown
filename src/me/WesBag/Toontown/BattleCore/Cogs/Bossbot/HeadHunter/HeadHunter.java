package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.HeadHunter;

public class HeadHunter {
	public static final int[][] FountainPen = {
			{15, 15, 15, 15, 15},
			{5, 6, 8, 10, 12},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] GlowerPower = {
			{20, 20, 20, 20, 20},
			{7, 8, 10, 12, 13},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] HalfWindsor = {
			{20, 20, 20, 20, 20},
			{8, 10, 12, 14, 16},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] HeadShrink = {
			{35, 35, 35, 35, 35},
			{10, 12, 15, 18, 21},
			{65, 75, 80, 85, 95}
	};
	public static final int[][] Rolodex = {
			{10, 10, 10, 10, 10},
			{6, 7, 8, 9, 10},
			{60, 65, 70, 75, 80}
	};
	
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {FountainPen, GlowerPower, HalfWindsor, HeadShrink, Rolodex};
	public static final String[] attackNames = {"Fountain Pen", "Glower Power", "Half Windsor", "Head Shrink", "Rolodex"};
	public static final boolean[] hitAll = {false, false, false, false, false};
}
