package me.WesBag.TTCore.BattleMenu.Cogs.Cashbot.RobberBaron;

public class RobberBaron {
	
	public static final int[][] PowerTrip = {
			{50, 50, 50, 50, 50},
			{11, 14, 16, 18, 21},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] TeeOff = {
			{50, 50, 50, 50, 50},
			{10, 12, 14, 16, 18},
			{50, 65, 75, 85, 90}
	};
	public static final int numOfAttacks = 2;
	public static final int[][][] attacks = {PowerTrip, TeeOff};
	public static final String[] attackNames = {"Power Trip", "Tee Off"};
	public static final boolean[] hitAll = {true, false};
}
