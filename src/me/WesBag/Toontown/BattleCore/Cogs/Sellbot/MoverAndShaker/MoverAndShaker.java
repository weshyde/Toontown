package me.WesBag.Toontown.BattleCore.Cogs.Sellbot.MoverAndShaker;

public class MoverAndShaker {
	public static final int[][] BrainStorm = {
			{15, 15, 15, 15, 15},
			{5, 6, 8, 10, 12},
			{60, 65, 80, 85, 90}
	};
	public static final int[][] HalfWindsor = {
			{20, 20, 20, 20, 20},
			{6, 9, 11, 13, 16},
			{50, 65, 70, 75, 80}
	};
	public static final int[][] Quake = {
			{20, 20, 20, 20, 20},
			{9, 12, 15, 18, 21},
			{60, 65, 75, 80, 85}
	};
	public static final int[][] Shake = {
			{25, 25, 25, 25, 25},
			{6, 8, 10, 12, 14},
			{70, 75, 80, 85, 90}
	};
	public static final int[][] Tremor = {
			{20, 20, 20, 20, 20},
			{5, 6, 7, 8, 9},
			{50, 50, 50, 50, 50}
	};
	public static final int numOfAttacks = 5;
	public static final int[][][] attacks = {BrainStorm, HalfWindsor, Quake, Shake, Tremor};
	public static final String[] attackNames = {"Brain Storm", "Half Windsor", "Quake", "Shake", "Tremor"};
	public static final boolean[] hitAll = {false, false, true, true, true};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1ODkyODE4OSwKICAicHJvZmlsZUlkIiA6ICIxYTc1ZTNiYmI1NTk0MTc2OTVjMmY4NTY1YzNlMDAzZCIsCiAgInByb2ZpbGVOYW1lIiA6ICJUZXJvZmFyIiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzRjMTViYWE5ZDM2MjBjNDY3YmJhMjNhZDA2ZTM4ZjM4YzhiMDYxMDliYTI1ODYxMmQwN2E2ZDE2Y2Q1ZDE4MDUiCiAgICB9CiAgfQp9";
	public static final String skinSignature = "Eh5zUNT/EYoAWTOkz0khrNb3ZXt4cUE/YYILnlP2Vf2sCUe9cNPME5r35OZMg35QFYaDjNaaEhJrlCLw1yj+zlPCy1aZPhGV8Zd3jxEvB3PuqQJT/Ms18K/1JWC0H2hAv683gModVnAQWYri7paEBryaX1fHRh35eSG47nYpkcrF8O0GRsHwvvTsUh5t6nYaJhrVpvKeh80QGszqzF2YRTQFySlQGYM74lcB8eptLPMPQvKLnMoXEAAUE3i8AAXWLoTwlKtv8oT6vnB8lGFzsSGV7cZ3WmhFftHOD6JL27HFR+CeXF8illYauuZtyAGLJ+L7SJWq2gAZNmZvQU2XhFwIgthA2lwhhFXRdkiHEZ8BOql9RA0anjTqJu5+loQ4ZZcMdStx+lvfXg9rFoAK/+lLxKNelbia1kVMqsUSKozDpxD/NTIUhS9/A2DrabMyrvXgDVEwNrCvqOsBedaKR2h0ztoR/O7P2PJwKO1d29YDwPe62GVbYxigFU4h8Lbn0Ti7kwCeoQ35BCaUB3RRGhqjq0joP6mf9EPQhBayPrRBqJkRI+8dF1OWuvHiDmV1u3MAg7ww1VrSCa3RQxTSQiPT6qbxuQHmG75kBJif1FYXyH8UIhlvyf0qSIwJji/mkxXE5b+UkgVUiztQOj+WxRJLiCbWo5c4bJpMUGh4qco=";
}
