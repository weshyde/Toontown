package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.BigWig;

public class BigWig {
	public static final int[][] PowerTrip = {
			{50, 50, 50, 50, 50},
			{10, 11, 13, 15, 16},
			{75, 80, 85, 90, 95}
	};
	public static final int[][] FingerWag = {
			{50, 50, 50, 50, 50},
			{13, 15, 17, 19, 21},
			{80, 85, 85, 85, 90}
	};
	public static final int numOfAttacks = 2;
	public static final int[][][] attacks = {PowerTrip, FingerWag};
	public static final String[] attackNames = {"Power Trip", "Finger Wag"};
	public static final boolean[] hitAll = {true, false};
	
}
