package me.WesBag.TTCore.BattleMenu.Cogs.Sellbot.ColdCaller;

public class ColdCaller {

	public static final int[][] FreezeAssets = {
			{5, 10, 15, 20, 25},
			{1, 1, 1, 1, 1},
			{90, 90, 90, 90, 90}
	};
	public static final int[][] PoundKey = {
			{25, 25, 25, 25, 25},
			{2, 2, 3, 4, 5},
			{75, 80, 85, 90, 95}
	};
	public static final int[][] DoubleTalk = {
			{25, 25, 25, 25, 25},
			{2, 3, 4, 6, 8},
			{50, 55, 60, 65, 70}
	};
	public static final int[][] HotAir = {
			{45, 40, 35, 30, 25},
			{3, 4, 6, 8, 10},
			{50, 50, 50, 50, 50}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {FreezeAssets, PoundKey, DoubleTalk, HotAir};
	public static final String[] attackNames = {"Freeze Assets", "Pound Key", "Double Talk", "Hot Air"};
	public static final boolean[] hitAll = {false, false, false, false};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1ODMwMjMzOSwKICAicHJvZmlsZUlkIiA6ICJiN2ZkYmU2N2NkMDA0NjgzYjlmYTllM2UxNzczODI1NCIsCiAgInByb2ZpbGVOYW1lIiA6ICJDVUNGTDE0IiwKICAic2lnbmF0dXJlUmVxdWlyZWQiIDogdHJ1ZSwKICAidGV4dHVyZXMiIDogewogICAgIlNLSU4iIDogewogICAgICAidXJsIiA6ICJodHRwOi8vdGV4dHVyZXMubWluZWNyYWZ0Lm5ldC90ZXh0dXJlLzk5ZjExYWU3OGI1YTFiOTQzOGRjODg5NzQyOGM0ZDQ4NmMzYTg4N2ZiODM3NjU3YmNhMjk3MzkwNGRkOWYwYmQiCiAgICB9CiAgfQp9";
	public static final String skinSignature = "ahdVvCa1CKWGOpw8OrZVGtzYZFujeSg1I0CoqybbLN9lKYPU1Uu/sUjphucw3fxtbIo+reCR0edKmg4pr4jfIIYXM+rkqTzHVHX2+eOrtqopvHw9mwTvxpFe+bA5crDGfADXS5/tOUu5S+H3VO5cBudr8H9MpreQpop3MRe+cFTf9jsw1XUnW53tz4afSNMpMr4xcggSX2CHDMD13DLSiZ2dw4c7ajBPL3oxSQuUoXHTxoc9POSBM7BJPnbA7QxpmUqHNVbZxEbmMkAeZlfaO3v7JDgJameNU8PgN85hQzxqZSc5S8nHpiv/y55QjAdYhHcwtYIdDVKEX/41n+vfb0um54MM99WniqM5U3z0puRFaU+Exma9jxCNDiWvDgGOUX6XPbw2LcUOhV7I22I5r2FTRaivRJFxCQfWlZU6NK290YhJNdm+d8L3IxBnDcYCm2pmijXwnsGl5PopOYadU8gLVlQsGIutqEubEwWvhRn7Ij8jTqy6JIbyhJFbVRM91ht8TXKLYloCROTsNbNyDXXu3YUwEGLjvPjrgSac7Ea1xxebNuj+YTYwhaJN7l4PW8k7EQ4vENyIrJj7qi4u5eVftBF7zMA9QxSaVmp11G7y9IeFxVNJI/DFrf//B4gnsZSzhv6CQd8QjE8VGTR571lMxvIKVURLNm+EKCxJbNs=";
}
