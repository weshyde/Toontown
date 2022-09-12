package me.WesBag.Toontown.BattleCore.Cogs.Bossbot.PencilPusher;

public class PencilPusher {
	public static final int[][] FountainPen = {
			{20, 20, 20, 20, 20},
			{2, 3, 4, 6, 9},
			{75, 75, 80, 80, 90}
	};
	
	public static final int[][] RubOut = {
			{20, 20, 20, 20, 20},
			{4, 5, 6, 8, 12},
			{75, 75, 75, 75, 75}
	};
	
	public static final int[][] FingerWag = {
			{35, 30, 25, 20, 15},
			{1, 2, 2, 3, 4},
			{75, 75, 75, 75, 75}
	};
	
	public static final int[][] WriteOff = {
			{5, 10, 15, 20, 25},
			{4, 6, 8, 10, 12},
			{75, 75, 75, 75, 75}
	};
	
	public static final int[][] FillWithLead = {
			{20, 20, 20, 20, 20},
			{3, 4, 5, 6, 7},
			{75, 75, 75, 75, 75}
	};
	
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {FountainPen, RubOut, FingerWag, WriteOff, FillWithLead};
	public static final String[] attackNames = {"Fountain Pen", "Rub Out", "Finger Wag", "Write Off", "Fill with Lead"};
	public static final boolean[] hitAll = {false, false, false, false, false};
}
