package me.WesBag.Toontown.BattleCore.Cogs.Sellbot.Telemarketer;

public class Telemarketer {

	public static final int[][] ClipOnTie = {
			{15, 15, 15, 15, 15},
			{2, 2, 3, 3, 4},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] PickPocket = {
			{15, 15, 15, 15, 15},
			{1, 1, 1, 1, 1},
			{75, 75, 75, 75, 75}
	};
	public static final int[][] Rolodex = {
			{30, 30, 30, 30, 30},
			{4, 6, 7, 9, 12},
			{50, 50, 50, 50, 50}
	};
	public static final int[][] DoubleTalk = {
			{40, 40, 40, 40, 40},
			{4, 6, 7, 9, 12},
			{75, 80, 85, 90, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {ClipOnTie, PickPocket, Rolodex, DoubleTalk};
	public static final String[] attackNames = {"Clip On Tie", "Pick Pocket", "Rolodex", "Double Talk"};
	public static final boolean[] hitAll = {false, false, false, false};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1ODk5MTUwMCwKICAicHJvZmlsZUlkIiA6ICJmMTYwZTMxMzJjYWM0YjRiOWM5OTk2NDQ1OGIxOWM0ZSIsCiAgInByb2ZpbGVOYW1lIiA6ICJUb255S0tLIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2RjN2EzOTlkZDBhY2VmYTBhYTMxMDY0MWU5NzAyZTVkYWQ5Y2Q1ZDQ5Njk1MTg1NTNjYzY4NTkwZjRkMzY0ODMiCiAgICB9CiAgfQp9";
	public static final String skinSignature = "uDoUjeYGSvCHq7FNr5KbI5bZViRpN2w8IcTYO9ZIkFIWbHfcOQc82L7I6DusvSCxHf4iCT0aWOnwgWnL4rxI0vxPpt9F3XO3Kpbd9QvhsiiaWXB8q1zqaEMX+RRhUC9uEjH/C60CrCipS2T0mE1YrXzC8mY4thOOOfIgSzR9eyS/DVFrlVBEFAQTvwua1zT3+0ik0vpYYqus+in7AbHxpJEk3MRI/PN4LZhClQ24QCiktzs+bibTyfYiM8DMikUjU+Y/ba3yaN3j0Fa5Wwuto7dUhF3+dZYBA2YdN6w7RuFTzfVVDHim7s9B2pHWwty04v5eXoo5JjSHn7e7iXD9X+GW6lZr07ZTz3crLAGD80cFqeQTc9NVIIZCRyW8MKcMDG+cC3ZXipNS/tGhnBshba6f8HkaQEScn57Ub9sFka3PXq/SoCNM//FumMiRWhm7Cv6ID+ZfuJ4cF06I2E2bLv0DF31WuN1GYgOtquGaQTxpg+hTolnjAjqm0t3utSKyGm+AVd9BZSCEvhdey65F03mRKPYk9T+l9cUZdtHO/GywFHzdWUB/k3gxVbVWeu3wljmutjYjDI1Uoh/r9PDcy20iH5fvUV0ALUTqhOAE5B9PiwcE1kuYqQiDHwPgkh3nd7nBSw6QSfyQzDidIicMmumStIIF5zIMyNrdk3iAvCk=";
}
