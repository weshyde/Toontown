package me.WesBag.TTCore.BattleMenu.Gags.Lure;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Sound;
import me.WesBag.TTCore.Main;
import me.WesBag.TTCore.BattleMenu.Gags.Gag;

public class Lure {
	
	public static Gag useLureGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = false;
		//boolean missed = false;
		//int minDmg = getMinDmg(gag);
		if (numInTrack == 7 || numInTrack == 2 || numInTrack == 4 || numInTrack == 6)
			hitAll = true;
		//int maxDmg = getMaxDmg(gag.replace("-", ""));
		int rounds = getRounds(gag.replace("-", ""));
		int acc = getAcc(gag.replace("-", ""));
		Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, rounds + 1, acc, track, hitAll, numInTrack, snd, true);
		return tempGag;
	}

	public static int getRounds(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Lure." + gag + ".Gag");
			return gagc.getField("rounds").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAcc(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Lure." + gag + ".Gag");
			return gagc.getField("acc").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Lure." + gag + ".Gag");
			return (Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
