package me.WesBag.TTCore.BattleMenu.Gags.Sound;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

import me.WesBag.TTCore.Main;
import me.WesBag.TTCore.BattleMenu.Gags.Gag;

public class Sound {
	
	
	public static Gag useSoundGag(UUID playerUUID, Main main, String gag, int track, int numInTrack) {
		boolean hitAll = true;
		int maxDmg = getMaxDmg(gag.replace("-", ""));
		int acc = getAcc(gag.replace("-", ""));
		org.bukkit.Sound snd = getSound(gag.replace("-", ""));
		Gag tempGag = new Gag(playerUUID, gag, maxDmg, acc, track, hitAll, numInTrack, snd);
		//Gag tempGag = new Gag(playerUUID, gag, maxDmg, acc, track, hitAll, numInTrack, snd);
		return tempGag;
	}

	public static int getMinDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Sound." + gag + ".Gag");
			return gagc.getField("minDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getMaxDmg(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Sound." + gag + ".Gag");
			return gagc.getField("maxDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAcc(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Sound." + gag + ".Gag");
			return gagc.getField("acc").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public static org.bukkit.Sound getSound(String gag) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.TTCore.BattleMenu.Gags.Sound." + gag + ".Gag");
			return (org.bukkit.Sound) gagc.getField("snd").get(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		return null;
	}
}
