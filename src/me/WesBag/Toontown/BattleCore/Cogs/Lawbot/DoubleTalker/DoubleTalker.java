package me.WesBag.Toontown.BattleCore.Cogs.Lawbot.DoubleTalker;

public class DoubleTalker {
	public static final int[][] RubberStamp = {
			{5, 5, 5, 5, 5},
			{1, 1, 1, 1, 1},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] BounceCheck = {
			{5, 5, 5, 5, 5},
			{1, 1, 1, 1, 1},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] BuzzWord = {
			{20, 20, 20, 20, 20},
			{1, 2, 3, 5, 6},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] DoubleTalk = {
			{25, 25, 25, 25, 25},
			{6, 6, 9, 13, 18},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] Jargon = {
			{25, 25, 25, 25, 25},
			{3, 4, 6, 9, 12},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] MumboJumbo = {
			{20, 20, 20, 20, 20},
			{3, 4, 6, 9, 12},
			{50, 60, 70, 80, 90}
	};
	public static final int numOfAttacks = 6;
	public static final int[][][] attacks = {RubberStamp, BounceCheck, BuzzWord, DoubleTalk, Jargon, MumboJumbo};
	public static final String[] attackNames = {"Rubber Stamp", "Bounce Check", "Buzz Word", "Double Talk", "Jargon", "Mumbo Jumbo"};
	public static final boolean[] hitAll = {false, false, true, false, false, false};
}
