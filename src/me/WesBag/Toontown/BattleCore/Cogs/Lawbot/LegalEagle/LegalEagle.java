package me.WesBag.TTCore.BattleMenu.Cogs.Lawbot.LegalEagle;

public class LegalEagle {
	public static final int[][] EvilEye = {
			{20, 20, 20, 20, 20},
			{10, 11, 13, 15, 16},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] Jargon = {
			{15, 15, 15, 15, 15},
			{7, 9, 11, 13, 15},
			{60, 70, 75, 80, 90}
	};
	public static final int[][] Legalese = {
			{35, 35, 35, 35, 35},
			{11, 13, 16, 19, 21},
			{55, 65, 75, 85, 95}
	};
	public static final int[][] PeckingOrder = {
			{30, 30, 30, 30, 30},
			{12, 15, 17, 19, 22},
			{70, 75, 80, 85, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {EvilEye, Jargon, Legalese, PeckingOrder};
	public static final String[] attackNames = {"Evil Eye", "Jargon", "Legalese", "Pecking Order"};
	public static final boolean[] hitAll = {false, false, false, false};
}
