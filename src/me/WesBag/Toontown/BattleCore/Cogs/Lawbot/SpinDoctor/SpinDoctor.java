package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.SpinDoctor;

public class SpinDoctor {
	public static final int[][] ParadigmShift = {
			{30, 30, 30, 30, 30},
			{9, 10, 13, 16, 17},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] Quake = {
			{20, 20, 20, 20, 20},
			{8, 10, 12, 14, 16},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] Spin = {
			{35, 35, 35, 35, 35},
			{10, 12, 15, 18, 20},
			{70, 75, 80, 85, 90}
	};
	public static final int[][] WriteOff = {
			{15, 15, 15, 15, 15},
			{6, 7, 8, 9, 10},
			{60, 65, 75, 85, 90}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {ParadigmShift, Quake, Spin, WriteOff};
	public static final String[] attackNames = {"Paradigm Shift", "Quake", "Spin", "Write Off"};
	public static final boolean[] hitAll = {true, true, false, false};
}
