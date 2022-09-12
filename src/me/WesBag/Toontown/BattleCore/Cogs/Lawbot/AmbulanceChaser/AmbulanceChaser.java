package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.AmbulanceChaser;

public class AmbulanceChaser {
	public static final int[][] Shake = {
			{15, 15, 15, 15, 15}, //Freq
			{4, 6, 9, 12, 15}, //Dmg
			{75, 75, 75, 75, 75} //Acc
	};
	public static final int[][] RedTape = {
			{30, 30, 30, 30, 30},
			{6, 8, 12, 15, 19},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] Rolodex = {
			{20, 20, 20, 20, 20},
			{3, 4, 5, 6, 7},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] HangUp = {
			{35, 35, 35, 35, 35},
			{2, 3, 4, 5, 6},
			{75, 75, 75, 75, 75}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {Shake, RedTape, Rolodex, HangUp};
	public static final String[] attackNames = {"Shake", "Red Tape", "Rolodex", "Hang Up"};
	public static final boolean[] hitAll = {true, false, false, false};
}
