package me.WesBag.Toontown.BattleCore.Gags.Squirt;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import org.bukkit.Sound;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Gags.Gag;

public class Squirt {
	
	public static Gag useSquirtGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = false;
		//boolean missed = false;
		//int minDmg = getMinDmg(gag);
		if (numInTrack == 7)
			hitAll = true;
		int maxDmg = getMaxDmg(gag.replace("-", ""));
		int acc = getAcc(gag.replace("-", ""));
		Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, maxDmg, acc, track, hitAll, numInTrack, snd);
		return tempGag;
	}

	public static int getMinDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Squirt." + gag + ".Gag");
			return gagc.getField("minDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getMaxDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Squirt." + gag + ".Gag");
			return gagc.getField("maxDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAcc(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Squirt." + gag + ".Gag");
			return gagc.getField("acc").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Squirt." + gag + ".Gag");
			return (Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
