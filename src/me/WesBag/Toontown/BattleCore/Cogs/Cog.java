package me.WesBag.Toontown.BattleCore.Cogs;

import java.lang.reflect.InvocationTargetException;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.Files.BattleData;
import me.WesBag.Toontown.Files.CogType;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import net.minecraft.server.level.EntityPlayer;


public class Cog {
	int level;
	int health;
	String fullName;
	String cogName;
	String cogSuit;
	CogType cogType;
	//String cogSuitInitals;
	int baseLevel;
	NPC npc;
	EntityPlayer eNpc;
	LivingEntity e;
	
	int[][] storedAttacks = {};
	String[] storedAttackNames = {};
	boolean[] storedAttacksHitAll = {};
	int numOfCogAttacks = 0;
	int selectedAttack = 0;
	
	
	public Cog(int lvl, String inputName, Entity eInput, NPC inputNPC) {
		level = lvl;
		fullName = inputName;
		npc = inputNPC;
		e = (LivingEntity) eInput;
		for (int i = 0; i < 4; i++) {
			if (fullName.contains(CogsController.cogSuits[i])) {
				cogSuit = CogsController.cogSuits[i];
				for (int j = 0; j < 8; j++) {
					if (fullName.contains(CogsController.cogNames[i][j])) {
						cogName = CogsController.cogNames[i][j];
						baseLevel = j + 1;
						break;
					}
				}
			}
		}
		String cogTypeStr = cogName;
		cogType = CogType.valueOf(cogTypeStr.replace(" ", "_").toUpperCase());
		
		
		BattleData battleData = BattleCore.getBattleDataNPC(npc);
		if (battleData != null)
			battleData.addCog(npc.getUniqueId(), this);
		else
			Main.getInstance().getLogger().log(Level.WARNING, "Cog created doesn't have any battle data!");
		loadCogInfo();
		if (cogSuit.contains("Sellbot"))
			loadCogSkin();
	}
	
	
	//
	//
	//
	// 10/20/21 10:48pm
	// Trying to figure out how to get the attacks[][] from the cogName class and setting it
	// to be stored within this object...
	//
	//
	//
	//
	//
	//
	public void loadCogInfo() {
		int health = ((level + 1) * (level + 2)) * 2;
		if (level == 12)
			health = 400;
		this.health = health;
		e.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
		e.setHealth(health);
		//e.setMaxHealth(health);
		//Bukkit.getServer().broadcastMessage("Cog Health: " + e.getHealth());
		//Bukkit.getServer().broadcastMessage("Cog Health Saved: " + health);
		
		storedAttacks = newGetCogAttacks();
		/* Commented out to implement method called above to not have to put getCogAttacks method within each cog class
		try {
			Class<?> cogInfo = Class.forName("me.WesBag.TTCore.BattleMenu.Cogs." + cogSuit + "." + cogName.replace(" ", "") + "." + cogName.replace(" ", ""));
			Method getAttacksMethod = null;
			numOfCogAttacks = cogInfo.getField("numOfAttacks").getInt(cogInfo.getDeclaredConstructor().newInstance());
			getAttacksMethod = cogInfo.getMethod("getCogAttacks", int.class);
			int relativeLevel = level - baseLevel;
			System.out.println("RELATIVE LEVEL: " + relativeLevel);
			System.out.println("LEVEL: " + level);
			System.out.println("BASE LEVEL: " + baseLevel);
			for (int i = 0; i < numOfCogAttacks; i++) { //This loop is seemingly pointless!!!!!!!
				storedAttacks = (int[][]) getAttacksMethod.invoke(cogInfo, relativeLevel);
			}
		} catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public int[][] newGetCogAttacks() {
		try {
			String editedName = cogName;
			Class<?> cogClass = Class.forName("me.WesBag.TTCore.BattleMenu.Cogs." + cogSuit + "." + editedName.replace(" ", "") + "." + editedName.replace(" ", ""));
			numOfCogAttacks = cogClass.getField("numOfAttacks").getInt(cogClass.getDeclaredConstructor().newInstance());
			storedAttackNames = (String[]) cogClass.getField("attackNames").get(cogClass.getDeclaredConstructor().newInstance());
			storedAttacksHitAll = (boolean[]) cogClass.getField("hitAll").get(cogClass.getDeclaredConstructor().newInstance());
			int[][][] allAttacks = (int[][][]) cogClass.getField("attacks").get(cogClass.getDeclaredConstructor().newInstance());
			int relativeLevel = level - baseLevel;
			int[][] cogAttacks = new int[numOfCogAttacks][3];
			for (int i = 0; i < numOfCogAttacks; i++) {
				for (int j = 0; j < 3; j++) {
					cogAttacks[i][j] = allAttacks[i][allAttacks[0].length-1-j][relativeLevel];
				}
			}
			return cogAttacks;
		} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void loadCogSkin() {
		try {
			String editedName = cogName;
			Class<?> cogClass = Class.forName("me.WesBag.TTCore.BattleMenu.Cogs." + cogSuit + "." + editedName.replace(" ", "") + "." + editedName.replace(" ", ""));
			String tex = (String) cogClass.getField("skinTexture").get(cogClass.getDeclaredConstructor().newInstance());
			String sig = (String) cogClass.getField("skinSignature").get(cogClass.getDeclaredConstructor().newInstance());
			npc.getOrAddTrait((SkinTrait.class)).setSkinPersistent(cogName, sig, tex);
		} catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
			e.printStackTrace();
		}
	}
	
	public String getAttackName(int attackNum) {
		return storedAttackNames[attackNum];
	}
	
	public int getAttackFreq(int attackNum) {
		return storedAttacks[attackNum][0];
	}
	
	public int getAttackAcc(int attackNum) {
		return storedAttacks[attackNum][2];
	}
	
	public int getAttackDmg(int attackNum) {
		return storedAttacks[attackNum][1];
	}
	
	public boolean getAttackHitAll(int attackNum) {
		return storedAttacksHitAll[attackNum];
	}
	
	public int getNumOfCogAttacks() {
		return numOfCogAttacks;
	}
	
	public String getCogFullName() {
		return this.fullName;
	}
	
	public String getJustCogName() {
		return this.cogName;
	}
	
	public String getCogSuit() {
		return cogSuit;
	}
	
	public int getCogLevel() {
		return this.level;
	}
	
	public int getCogHealth() {
		return this.health;
	}
	
	public CogType getCogType() {
		return cogType;
	}
	
	public void setCogHealth(int iHealth) {
		health = iHealth;
	}
	
	public void setSelectedAttack(int iSelectedAttack) {
		selectedAttack = iSelectedAttack;
	}
	
	public UUID getUUID() {
		return npc.getUniqueId();
	}
	
	public NPC getNPC() {
		return npc;
	}
	
	public int getSelectedAttack() {
		return selectedAttack;
	}
	
	public void getCogEntity(NPC npc) {
		if (npc.isSpawned()) {
			e =(LivingEntity) npc.getEntity();
		}
	}
	
	public void createCog(Location l, String name) {
		NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, name);
		npc.spawn(l);
		if (npc.isSpawned()) {
			e = (LivingEntity) npc.getEntity();
		}
		/* Do this instead when the cog is about to battle
		String npcName = npc.getFullName();
		String levelStr = npcName.replaceAll("[^0-9]", "");
		int tempLevel = Integer.parseInt(levelStr);
		e.setHealth(tempLevel * 2);
		*/
	}
}
