package me.WesBag.Toontown.BattleCore.Cogs.Sellbot.GladHander;

public class GladHander {

	public static final int[][] RubberStamp = {
			{40, 30, 20, 10, 5},
			{4, 3, 3, 2, 1},
			{90, 70, 50, 30, 10}
	};
	public static final int[][] FountainPen = {
			{40, 30, 20, 10, 5},
			{3, 3, 2, 1, 1},
			{70, 60, 50, 40, 30}
	};
	public static final int[][] Filibuster = {
			{10, 20, 30, 40, 45},
			{4, 6, 9, 12, 15},
			{30, 40, 50, 60, 70}
	};
	public static final int[][] Schmooze = {
			{10, 20, 30, 40, 45},
			{5, 7, 11, 15, 20},
			{55, 65, 75, 85, 95}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {RubberStamp, FountainPen, Filibuster, Schmooze};
	public static final String[] attackNames = {"Rubber Stamp", "Fountain Pen", "Filibuster", "Schmooze"};
	public static final boolean[] hitAll = {false, false, false, false};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1ODg4MjEwNywKICAicHJvZmlsZUlkIiA6ICJjNjc3MGJjZWMzZjE0ODA3ODc4MTU0NWRhMGFmMDI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE2IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlL2VkZDBlMzlhYzZjMzZmNGM5YzA1ZTY3ZWFkODMyOTVlZTRjZjM1MzA0MThiNTdlYjk0NDU3OTVkOThiOWJjMjgiCiAgICB9CiAgfQp9";
	public static final String skinSignature = "V5k1BrCBN+J0vICWNJYTKZ2MZ+hUy/6mBJnlafTgionYaW/tC1sxDUbWa/BfKcOpsaF9+cxfYVTVmxPBXTp6JHavkuQvr65mh13pyzXV4bkSu0D4Hu6x/xFxjKHkNkdzrSOnjZhuvCVmqkGGRSI6ozsBKgcpIXDji0HS+KoP/+CL+yuV99cAcgfR0EX3+nTEe2yKu5e5iXoKgCbUiiLgXakRWN52JddBBCZVhy5V8+pEBAlauhCZXFixIwDJrhSl4BTu3GkM/UfD7M3M7ntpfXWwP8wnf6uTEZfH3FFVfYTmoKEjs6GkjI+vzwJC1ooSaDA+CzQKg86BCBUhrKdPqkBvSbe/bCIymvidezBqzYPifHYiPph+PUYMl08i+WAdEkOU9rmMn4mAeItQVv3DNBew6s6z+06tKAQ8Knpz20k8Fjd+LtJmlWQOeEoww+22MSW2TyQ6BxbrETa356jRphR1aF6eBZOWc+DCW5CfoOWeG9LpDuBgPnf4HZfZTSWy95i3PmvehZXEb6nxEseBh2UpYTZXYSq81QlUz/NF9eMXImIrEHMI+GnXu8lV+GCV2m6Ebcdz5CdkwQ9ku93VdzXaeoIVsiJhL6zJkN0twkoos0vHuvFrmtBcxQLMEdxqdLM+ySHH8gdFpVFTTkV3y9BXQm+bYrMdjoFb9UgzqX4=";
}
