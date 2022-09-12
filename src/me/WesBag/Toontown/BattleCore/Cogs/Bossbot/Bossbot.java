package me.WesBag.Toontown.BattleCore.Cogs.Bossbot;

import java.lang.reflect.InvocationTargetException;

import me.WesBag.Toontown.Main;

public class Bossbot {
	Main main;
	
	//public static String[] cogNames = {"Flunky", "PencilPusher"};
	
	public Bossbot(Main mn) {
		main = mn;
	}
	
	public void cogAttack() {
		
	}
	
	public static int getNumOfAttacks(String cogName) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Cogs.Bossbot." + cogName + "." + cogName);
			return gagc.getField("attacks").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
	
	public static int getAttacks(String cogName) {
		Class<?> gagc;
		try {
			gagc = Class.forName("me.WesBag.Toontown.BattleCore.Cogs.Bossbot." + cogName + "." + cogName);
			return gagc.getField("minDamage").getInt(gagc.getDeclaredConstructor().newInstance());
		} catch (ClassNotFoundException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
			e.printStackTrace();
		return -1;
		}
	}
}
