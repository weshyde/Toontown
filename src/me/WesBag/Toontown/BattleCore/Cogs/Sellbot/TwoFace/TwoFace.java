package me.WesBag.TTCore.BattleMenu.Cogs.Sellbot.TwoFace;

public class TwoFace {

	public static final int[][] EvilEye = {
			{30, 30, 30, 30, 30},
			{10, 12, 14, 16, 18},
			{60, 75, 80, 85, 90}
	};
	public static final int[][] HangUp = {
			{15, 15, 15, 15, 15},
			{7, 8, 10, 12, 13},
			{50, 60, 70, 80, 90}
	};
	public static final int[][] RazzleDazzle = {
			{30, 30, 30, 30, 30},
			{8, 10, 12, 14, 16},
			{60, 65, 70, 75, 80}
	};
	public static final int[][] RedTape = {
			{25, 25, 25, 25, 25},
			{6, 7, 8, 9, 10},
			{60, 65, 75, 85, 90}
	};
	public static final int numOfAttacks = 4;
	public static final int[][][] attacks = {EvilEye, HangUp, RazzleDazzle, RedTape};
	public static final String[] attackNames = {"Evil Eye", "Hang Up", "Razzle Dazzle", "Red Tape"};
	public static final boolean[] hitAll = {false, false, false, false};
	public static final String skinTexture = "ewogICJ0aW1lc3RhbXAiIDogMTY0NTA1OTAxMDUwOCwKICAicHJvZmlsZUlkIiA6ICIzYWJkYjI5ZDI2MTU0YTAxOWEzZWQ3OGRlMzI4OWUxNCIsCiAgInByb2ZpbGVOYW1lIiA6ICJFcmljSHViZXIiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE4ZDk1ZDEwNmVjZTJjZDJkMjBiNmNlYjNjZDFjZmRjNGIxNjU5MDU2ZjczN2EyMjViN2NmMTRkNDZhMzA4MiIKICAgIH0KICB9Cn0";
	public static final String skinSignature = "XhreFZPeJlNoZ/szmhz15xNKkA8OlVCRckKbiohdtEvGG3yudg+PO6dLWf+2lqtERsK3B2oOfqzQpDt6AakMVy0se6OhYohgMix1Pz1Gq8/yi+hyJpnkBDPiTn3+oRUtrSSKbKUIzZk6O8aP2RUYtVnmfp+pQUhmxLc5r4/xY2/RMNaTJvcO1nJ2cQ379CyDBke7SBBSDyyzFLHx/i0tqOre5S3SHMoAeskhU9Ud7PGMwDtWkVBgLS6d6BzvO4cys2yxHqE4HLITTyNLKWxL4MJW6X9h4YGWM11Lv3NE2A0X3ONyoQleIYwtFU2D5sXxC6M6tfq24oMjXUmbs6vh7g/ZUoVNeJhS7yoHylI2dDSXbvA0w6PJo474hd0mtlSiqMquYizs8Xf+bVUsF3D1jFP+ojbqyqJJ6JPLvTm9U9/musmpdmxAUPhqAHPPGQktHMpumKgIlbCGMLXytY2XGaD2BY+IQmUN8bS4nDIe/Sj4pCrjOLFQbL2QII+LFSWtaDeAyiUTSk7nUMEJzxCMP9LjgpoZKVgZia1jzOpKLxtsriKY1td5Q2g4a+QSP9V4/7pcGQNu7slFl2Qm8iA0u1js5KIQ3VvBcUiw2OslQZBiPC8RInHh/TPO0bbh+2XSquaFPaaeiFNMA4HonEqJrP2R5Ax+nNWfv32r0+LxYGM=";
}
