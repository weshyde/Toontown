package me.WesBag.Toontown.BattleCore.Cogs.CogBuildings;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.BattleCore;
import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Cogs.CogsController;
import me.WesBag.Toontown.BattleCore.Cogs.CogTraits.CogBuildingTrait;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.BattleCore.Toons.ToonsController;
import me.WesBag.Toontown.Files.BattleData;
import me.WesBag.Toontown.Files.DataFile;
import me.WesBag.Toontown.SchematicUtilities.SchematicPaster;
import me.WesBag.Toontown.Tasks.CustomEvents.PlayerDestroyBuildingEvent;
import net.citizensnpcs.Citizens;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.SkinTrait;
import net.md_5.bungee.api.ChatColor;

public class CogBuilding {
	
	public static Main main = Main.getInstance();
	
	private NPC buildingNPC;
	private List<UUID> playersIn;
	private Map<UUID, Boolean> playersInElevator = new HashMap<>();
	private List<UUID> playersInElevator2 = new ArrayList<>();
	private List<Toon> toonsIn = new ArrayList<>();
	private Map<Integer, List<String>> cogsToSpawn = new HashMap<Integer, List<String>>();
	//private List<Cog> spawnedCogs = new ArrayList<>();
	private Location finishLocation;
	private List<Cog> allCogs = new ArrayList<>();
	private List<Location> cogBattleLocations = new ArrayList<>();
	private NextLevelCountdown nextFloorCountdown;
	//private Map<Boolean, Location> cogBattleLocations2 = new HashMap<>();
	private Map<Location, Boolean> cogBattleLocations3 = new HashMap<>();
	private List<Location> playerBattleLocations = new ArrayList<>();
	private Location nextFloorNPCLocation;
	private BattleData battleData;
	//private List<>
	private String buildingSuit;
	private Location orgLocation;
	private Location orgNPCLocation;
	private Location location;
	private Location elevatorLocation;
	private int suitNum;
	private int difficulty;
	private float facingYaw;
	//private int cogsToSpawn;
	private int currentLevel;
	private int levels;
	
	
	//public CogBuilding(List<UUID> players, int levels, int difficulty, int suitName, )
	
	public CogBuilding(List<UUID> players, int levels, int difficulty, int suitNum, Location location, NPC npc) {
		//CogBuildingController.cogBuildings.put(npc.getUniqueId(), this);
		CogBuildingController.readyCogBuildings.remove(location);
		//CogBuildingController.readyCogBuildings2.remove(location);
		this.orgLocation = location.clone();
		this.elevatorLocation = location.clone().add(0, 7, 0);
		CogBuildingController.cogBuildingElevators.put(elevatorLocation.clone(), this);
		//CogBuildingController.cogBuildingElevators.put(elevatorLocation, this);
		//CogBuildingController.cogBuildings2.put(elevatorLocation, this);
		CogBuildingController.playersInCogBuildings.addAll(players);
		this.playersIn = new ArrayList<>(players);
		//for (UUID p : playersIn)
		//	playersInElevator.put(p, false);
		this.levels = levels;
		this.suitNum = suitNum;
		this.buildingNPC = npc;
		this.buildingSuit = CogsController.cogSuits[suitNum];
		this.difficulty = difficulty;
		this.currentLevel = 1;
		this.facingYaw = npc.getStoredLocation().getYaw();
		this.location = location;


		int battleLockX = 0;
		int battleLockZ = 0;
		int cogX = 0;
		int cogZ = 0;
		int playerX = 0;
		int playerZ = 0;
		int orgLocNPCX = 0;
		int orgLocNPCZ = 0;
		int orgLocPlayerX = 0;
		int orgLocPlayerZ = 0;
		int cogYaw = 0;
		int yawInt = (int) facingYaw;
		//if (yawS < 0)
			//syawS *= -1;
		System.out.println("CB: yawInt: " + yawInt);
		if (yawInt > 160 || yawInt < -160) { //COG FACING NORTH, PLAYER FACE SOUTH
			System.out.println("CB: Cog Facing North!");
			cogYaw = 180;
			//playerZ = 3;
			cogX = -1;
			playerZ = -3;
			
			battleLockZ = -3;
			battleLockX = 1;
			
			orgLocNPCZ = -4;
			orgLocPlayerZ = -10;
		}
		else if (yawInt > -110 && yawInt < -70) { //COG FACING EAST, PLAYER FACE WEST
			System.out.println("CB: Cog Facing EAST!");
			cogYaw = -90;
			
			cogZ = -1;
			playerX = 3;
			
			battleLockX = 3;
			battleLockZ = 1;
			
			orgLocNPCX = 4;
			orgLocPlayerX = 10;
		}
		else if ((yawInt > -20 && yawInt < 0) || yawInt < 20) { //COG -> SOUTH, PLAYER -> NORTH
			System.out.println("CB: Cog Facing SOUTH!");
			cogYaw = 0;
			
			cogX = 1;
			playerZ = 3;
			
			battleLockZ = 3;
			battleLockX = -1;
			
			orgLocNPCZ = 4;
			orgLocPlayerZ = 10;
		}
		else if (yawInt > 70 && yawInt < 110) { //COG -> WEST, PLAYER -> EAST
			System.out.println("CB: Cog Facing WEST!");
			cogYaw = 90;
			
			cogZ = 1;
			playerX = -3;
			
			battleLockX = -3;
			battleLockZ = -1;
			
			orgLocNPCX = -4;
			orgLocPlayerX = -10;
		}
		else {
			Main.getInstance().getLogger().log(Level.SEVERE, "COGBUILDING COG NOT FACING THE RIGHT DIRECTION");
		}
		
		for (int i = 0; i < players.size(); i++) {
			Toon toon = ToonsController.getToon(players.get(i));
			toonsIn.add(toon);
		}
		finishLocation = location.clone().add(orgLocPlayerX, 0, orgLocPlayerZ);
		this.orgNPCLocation = location.clone().add(orgLocNPCX, 0, orgLocNPCZ);
		location.add(0, 7, 0);
		Location cL = location.clone().add(battleLockX, 0, battleLockZ);
		System.out.println("Building Anchor 2 X: " + cL.getBlockX());
		System.out.println("Building Anchor 2 Y: " + cL.getBlockY());
		System.out.println("Building Anchor 2 Z: " + cL.getBlockZ());
		Location pL = cL.clone();
		System.out.println("pL X: " + pL.getBlockX());
		System.out.println("pL Y: " + pL.getBlockY());
		System.out.println("pL Z: " + pL.getBlockZ());
		cL.setYaw(cogYaw); //Setting cog Yaw for their battle locations
		
		//this.playerBattleLocations.add((pL.add(playerX, 0, playerZ).clone()));
		this.playerBattleLocations.add(pL.add(playerX, 0, playerZ).clone());
		System.out.println("First pL X: " + playerBattleLocations.get(0).getBlockX());
		System.out.println("First pL Y: " + playerBattleLocations.get(0).getBlockY());
		System.out.println("First pL Z: " + playerBattleLocations.get(0).getBlockZ());
		this.cogBattleLocations.add(cL.clone()); //Adds first locations
		//this.cogBattleLocations2.put(false, location);
		this.cogBattleLocations3.put(cL.clone(), false);
		for (int i = 0; i < 3; i++) {
			cL.add(cogX, 0, cogZ);
			pL.add(cogX, 0, cogZ); //Adds cog values because its the same as the cog value, except for earlier change
			
			this.playerBattleLocations.add(pL.clone());
			
			this.cogBattleLocations.add(cL.clone()); //Adds other 3
			//this.cogBattleLocations2.put(false, location);
			this.cogBattleLocations3.put(cL.clone(), false);
		}
		//List<UUID> emptyNpcs = new ArrayList<>();
		//this.battleData = new BattleData(main, players, emptyNpcs, location);
		calcBuildingCogs();
		System.out.println("CogsToSpawn Level Size: " + this.cogsToSpawn.size());
		startBuilding();
		
		
		//Get cogBattleLocations and set
	}
	
	public void startBuilding() {
		System.out.println("Cog Building: Start Building ran!");
		List<String> cogs = this.cogsToSpawn.get(0);
		System.out.println("SB: CogsToSpawn size: " + cogs.size());
		System.out.println("SB COG DEBUG!");
		List<UUID> npcs = new ArrayList<>();
		int amountLooped = 0;
		for (int i = 0; i < cogs.size(); i++) { //i > 3 protects from adding more than 4 cogs to battle
			
			NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, cogs.get(i));
			npc.spawn(cogBattleLocations.get(i));
			npc.getStoredLocation().setYaw(facingYaw);
			//cogs.remove(i);
			npcs.add(npc.getUniqueId());
			
			String npcName = npc.getFullName();
			String levelStr = npcName.replaceAll("[^0-9]", "");
			int level = Integer.parseInt(levelStr);
			System.out.println("CogBuilding NPCNAME: " + npcName);
			System.out.println("CogBuilding Level: " + level);
			Cog cog = new Cog(level, cogs.get(i), npc.getEntity(), npc);
			allCogs.add(cog);
			
			//battleData.addCog(npc.getUniqueId(), cog);
					
			//cogs.remove(i);
			amountLooped++;
			if (amountLooped == 4) //Protects from spawning more than 4 cogs
				break;
		}
		for (int i = 0; i < amountLooped; i++) { //Removes cogs after the fact to avoid issue caused by cogs.remove(i)
			cogs.remove(0);
		}
		this.cogsToSpawn.put(currentLevel-1, cogs);
		
		this.battleData = new BattleData(main, playersIn, npcs, location.clone(), true, this);
		//REMEMBER TO CHANGE BATTLEDATA LOCATION WHEN PLAYERS MOVE FLOORS
		BattleCore.allBattles.add(battleData);
		for (Cog cog : allCogs) {
			this.battleData.addCog(cog.getUUID(), cog);
		}
		
		for (int i = 0; i < playersIn.size(); i++) {
			Toon tempToon = toonsIn.get(i);
			tempToon.getToon().sendMessage("Teleported 1");
			tempToon.getToon().sendMessage(playerBattleLocations.get(i).toString());
			tempToon.getToon().teleport(playerBattleLocations.get(i));
	
			tempToon.getToon().getInventory().setContents(tempToon.getGag2Inventory().getContents());
			BattleCore.openInventoryLater(tempToon.getToon(), tempToon.getGagInventory());
		}
	}
	
	
	public void nextFloor() {
		//Teleport players not in elevator out
		//Teleport players in elevator up
		System.out.println("Cog Building: Next floor ran!");
		currentLevel++;
		System.out.println("Current Level: " + currentLevel);
		if (nextFloorCountdown != null)
			nextFloorCountdown.cancel();
		
		if (playersInElevator2.size() != playersIn.size()) {
			for (UUID pUUID : playersIn) {
				Player p = Bukkit.getPlayer(pUUID);
				CogBuildingController.playersInCogBuildings.remove(pUUID);
				playersIn.remove(pUUID);
				p.sendMessage(ChatColor.GRAY + "[Building] " + ChatColor.WHITE + "You didn't get in the elevator in time!");
				p.sendMessage("Teleported 2");
				p.teleport(finishLocation); //CALC "finishLocation" on creation then teleport the player there!
			}
		}
		playersInElevator2.clear();
		//Increase Battle Data Location Key according to next floor!
		this.battleData.addToBattleLocationY(7);
		
		//Increasing battle locations by floor y increase:
		//nextFloorNPCLocation.add(0, 7, 0);
		CogBuildingController.cogBuildingElevators.remove(elevatorLocation);
		elevatorLocation.add(0, 7, 0);
		CogBuildingController.cogBuildingElevators.put(elevatorLocation.clone(), this);
		for (int i = 0; i < 4; i++) {
			playerBattleLocations.get(i).add(0, 7, 0);
			cogBattleLocations.get(i).add(0, 7, 0);
		}
		for (Location l : cogBattleLocations3.keySet()) {
			l.add(0, 7, 0);
		}

		List<String> cogs = this.cogsToSpawn.get(currentLevel-1);
		int amountLooped = 0;
		for (int i = 0; i < cogs.size(); i++) {
			
			if (battleData.getBattleNPCList().size() < 4) {
				NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, cogs.get(i));
				npc.spawn(cogBattleLocations.get(i));
				npc.getStoredLocation().setYaw(facingYaw);
				npc.getOrAddTrait((SkinTrait.class)).setSkinPersistent("Flunky", "XqaLAGK3A3MizXzdR52DTkLvzC0Cjw0DsRVFaoM3I0wGOf2JHBML/2OZI/rx4NJ/BiyetHGqvgNThXrMdGSOJTVc5UUPUF1CEbpMkmYlq8Uf1iDBOLuj4jmSrUFM5BPHgrWXItJyflL7Ljy4ZQmBuqY2zblXPGXrmTnlVU9LJZqvgm/emz/FXSrcPdmHmTfUH25ZU8tabQZwa3fdrbTAJVCLJ2pT0lc6SAvxSven3mmbTKNq0/smMSaA/YJYUmj/b6RqmpOFnOdRMOjDaBjjcRUvDK2PvdxyKPYxYrSB6N6OiC8KxDpvDHsbrNGAQ05bQ8I5QyLsjSMB8FFmOZPoqymxBU2HZVA4bXNzdf7fEV0ObXNT+fCCHeydci86fuQTneyxL9mo2vUdXZ/++BcwTgI7AzjlkC9/q197cpYr1u/ld6sE+KnNJW16oL99OcwmEyFMDm6owyzoNYbFydtbwDfxm7PSNOlY5W+SqlZVCveo9fqTUjf0XtkBfku1fDZDt/Z+pbPlnr3JRkt8mqLBgf1iZGlFM54VNhHka2qLmNPem6oky625wp/+OAvZD1nAMOWhUNdTGKSPg7dNg6h8D14arsmkGftZ580Xwj3c7q+6QxtvgSvMURpwTXHk09viCEcOsiykaK3r87lAIh7k/uk0yIeJyDuibQTaY1edOEU=", "ewogICJ0aW1lc3RhbXAiIDogMTY0MjY1NDYzOTkxNywKICAicHJvZmlsZUlkIiA6ICI2OTBkMDM2OGM2NTE0OGM5ODZjMzEwN2FjMmRjNjFlYyIsCiAgInByb2ZpbGVOYW1lIiA6ICJ5emZyXzciLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGQyZjAwMWJlNmQ1MzUyZTdjYzY4MDdmZDQ2NWEwZDQzZjEzMmZkOTVkYmI0MGMyMzE4MjU5OTVhYzAyZjYzYyIKICAgIH0KICB9Cn0=");
				//npc.getOrAddTrait(SkinTrait.class).set
				battleData.addNPC(npc.getUniqueId());
				
				String npcName = npc.getFullName();
				String levelStr = npcName.replaceAll("[^0-9]", "");
				int level = Integer.parseInt(levelStr);
				
				Cog cog = new Cog(level, cogs.get(i), npc.getEntity(), npc);
				allCogs.add(cog);
				
				battleData.addCog(npc.getUniqueId(), cog);
				
				//cogs.remove(i);
				amountLooped++;
			}
			
		}
		
		for (int i = 0; i < amountLooped; i++) { //Removes cogs after the fact to avoid issue caused by cogs.remove(i) within loop
			cogs.remove(0);
		}
		this.cogsToSpawn.put(currentLevel-1, cogs);
		
		for (int i = 0; i < playersIn.size(); i++) {
			Toon tempToon = toonsIn.get(i);
			tempToon.getToon().sendMessage("Teleported 3");
			tempToon.getToon().teleport(playerBattleLocations.get(i));
			
			tempToon.getToon().getInventory().setContents(tempToon.getGag2Inventory().getContents());
			BattleCore.openInventoryLater(tempToon.getToon(), tempToon.getGagInventory());
		}
	}
	
	public void nextBuildingRound() { //
		System.out.println("Cog Building: Next Building Floor Round ran! CogsToSpawnEmpty? " + String.valueOf(cogsToSpawn.get(currentLevel-1).isEmpty()));
		System.out.println("Cog Building: NBR Floor cog amount ");
		if (!(cogsToSpawn.get(currentLevel-1).isEmpty())) {
			List<String> cogs = cogsToSpawn.get(currentLevel-1);
			//int loopAmount = cogs.size();
			int amountLooped = 0;
			for (int i = 0; i < cogs.size(); i++) {
				if (battleData.getBattleNPCList().size() < 4) {
					NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, cogs.get(i));
					npc.spawn(cogBattleLocations.get(i));
					npc.getStoredLocation().setYaw(facingYaw);
					
					battleData.addNPC(npc.getUniqueId());
					
					String npcName = npc.getFullName();
					String levelStr = npcName.replaceAll("[^0-9]", "");
					int level = Integer.parseInt(levelStr);
					
					Cog cog = new Cog(level, cogs.get(i), npc.getEntity(), npc);
					allCogs.add(cog);
					
					battleData.addCog(npc.getUniqueId(), cog);
					
					//cogs.remove(i);
					amountLooped++;
				}
			}
			for (int i = 0; i < amountLooped; i++) { //Removes cogs after the fact to avoid issue caused by cogs.remove(i) within loop
				cogs.remove(0);
			}
			this.cogsToSpawn.put(currentLevel-1, cogs);
		}
		
		//else if (battleData.getBattleNPCList().size() < 1){
		//	nextFloor();
		//}
	}
	
	public void adminRemoveBuilding() {
		CogBuildingController.cogBuildingElevators.remove(elevatorLocation);
		String serialL = DataFile.serializeLocation(orgLocation);
		if (Main.dataFile.getData().contains("buildings." + serialL)) {
			Main.dataFile.getData().set("buildings." + serialL, null); //Clears building from saved file if it exists
			Main.dataFile.saveDataFile();
		}
		this.levels = -1; //This indicates the building is over to BattleMenu
		
		String oldName = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getOldName();
		buildingNPC.setName(oldName);
		buildingNPC.teleport(orgNPCLocation, TeleportCause.PLUGIN);
		
		//REPLACING COG BUILDING WITH ORIGINAL BUILDING
		String oldBuildingStr = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getFileName();
		int pasteX = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getX();
		int pasteY = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getY();
		int pasteZ = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getZ();
		int rotateY = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getYRotate();
		File oldBuildingFile = new File(Main.getInstance().getDataFolder() + "/schematics/temp", oldBuildingStr + ".schem");
		SchematicPaster.pasteSchematic2("world", oldBuildingFile, pasteX, pasteY, pasteZ, rotateY, false);
		buildingNPC.removeTrait(CogBuildingTrait.class);
		oldBuildingFile.deleteOnExit();
	}
	
	public void finishBuilding(boolean playersLost) {
		
		for (Cog cog : allCogs) {
			cog.getNPC().destroy();
		}
		allCogs.clear();
		
		if (!playersLost) {
			for (UUID uuid : playersIn) {
				Player p = Bukkit.getPlayer(uuid);
				p.sendMessage("You finished the building!");
				p.teleport(finishLocation);
			}
			//BattleFinishEvent battleFinishEvent = new BattleFinishEvent(toonsIn, allCogs);
			PlayerDestroyBuildingEvent playerDestroyBuildingEvent = new PlayerDestroyBuildingEvent(toonsIn, buildingSuit, levels);
		
			//Bukkit.getPluginManager().callEvent(battleFinishEvent); This is already called in finishBattle in BattleMenu
			Bukkit.getPluginManager().callEvent(playerDestroyBuildingEvent);
		}
		
		else {
			CogBuildingController.readyCogBuildings.put(elevatorLocation, buildingNPC.getUniqueId());
			//CogBuildingController.readyCogBuildings2.put(elevatorLocation, buildingNPC.getUniqueId());
			return;
			//Cog won stuff
		}
		//CogBuildingController.cogBuildings.remove(buildingNPC.getUniqueId());
		//CogBuildingController.cogBuildings2.remove(orgLocation);
		CogBuildingController.cogBuildingElevators.remove(elevatorLocation);
		String serialL = DataFile.serializeLocation(orgLocation);
		if (Main.dataFile.getData().contains("buildings." + serialL)) {
			Main.dataFile.getData().set("buildings." + serialL, null); //Clears building from saved file if it exists
			Main.dataFile.saveDataFile();
		}
		this.levels = -1; //This indicates the building is over to BattleMenu
		
		String oldName = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getOldName();
		buildingNPC.setName(oldName);
		buildingNPC.teleport(orgNPCLocation, TeleportCause.PLUGIN);
		
		//REPLACING COG BUILDING WITH ORIGINAL BUILDING
		String oldBuildingStr = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getFileName();
		System.out.println("OldBuildingStr to paste: " + oldBuildingStr);
		int pasteX = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getX();
		int pasteY = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getY();
		int pasteZ = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getZ();
		//int rotateY = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getYRotate();
		//Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + floors +  "-Floor.schem"
		//File oldBuildingFile = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/temp/" + oldBuildingStr +  ".schem");
		File oldBuildingFile = new File(Main.getInstance().getDataFolder() + "/schematics/temp", oldBuildingStr + ".schem");
		//File dataDirectory = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics", "temp");
		//File oldBuildingFile = new File(dataDirectory, oldBuildingStr + ".schem");
		SchematicPaster.pasteSchematic2("world", oldBuildingFile, pasteX, pasteY, pasteZ, 0, false);
		System.out.println("---------Schematic Pasted!");
		System.out.println("X: " + pasteX);
		System.out.println("Y: " + pasteY);
		System.out.println("Z: " + pasteZ);
		
		buildingNPC.removeTrait(CogBuildingTrait.class);
		oldBuildingFile.deleteOnExit(); //Maybe make delete?
		
		//Reward players, call building finish event, teleport players out, remove building
		//Also call battle finish event. BuildingFinishEvent is for building tasks, BattleFinishEvent is for cog tasks
	}
	
	public void calcBuildingCogs() { 
		//int amtOfCogs = (difficulty * 2) + 1;
		int lowCogLevel = difficulty;
		int highCogLevel = lowCogLevel + 3; //Adding 3 is like adding 2 because the random will never result in 11 without an extra 1
		System.out.println("CogBuilding Debug:");
		System.out.println("Difficulty: " + difficulty);
		System.out.println("LowCogLevel: " + lowCogLevel);
		System.out.println("HighCogLevel: " + highCogLevel);
		Random r = new Random();
		int result;
		
		for (int i = 0; i < levels; i++) {
			List<String> cogsPerLevel = new ArrayList<>();
			double cogsCalc = ((difficulty/4.5) * 1.2) + ((i * 1.8) / 2);
			int cogTotal = (int) Math.ceil(cogsCalc);
			System.out.println("Floor " + i + " has " + cogTotal + " cogs!");
			for (int cogNum = 0; cogNum < cogTotal; cogNum++) {
				String cogToAddName;
				result = r.nextInt(highCogLevel - lowCogLevel) + lowCogLevel;
				cogToAddName = buildingSuit + " ";
				cogToAddName += CogsController.cogNames[suitNum][result-1];
				cogToAddName += " Level " + result;
				cogsPerLevel.add(cogToAddName);
			}
			this.cogsToSpawn.put(i, cogsPerLevel);
			//for (int j = 0; j < cogsPerLevel.size(); j++)
			//	System.out.println("Index " + j + " = " + cogsPerLevel.get(j));
			//System.out.println("Putting cogs (Size = " + cogsPerLevel.size() + ") with index = " + i);
		}
		
	}
	
	public void spawnNextFloorNPC() {
		if ((currentLevel + 1) > levels) {
			finishBuilding(false);
			return;
		}
		buildingNPC.setName("Next Floor");
		buildingNPC.spawn(nextFloorNPCLocation);
		NextLevelCountdown nlc = new NextLevelCountdown(this);
		nlc.runTaskTimer(main, 0, 20);
		this.nextFloorCountdown = nlc;
	}
	
	public void startNextFloorTimer() {
		playersInElevator2.clear();
		if ((currentLevel + 1) > levels) {
			finishBuilding(false);
			return;
		}
		NextLevelCountdown nlc = new NextLevelCountdown(this);
		nlc.runTaskTimer(main, 0, 20);
		this.nextFloorCountdown = nlc;
	}
	
	public void addPlayerElevator(UUID pUUID) {
		playersInElevator2.add(pUUID);
		if (playersInElevator2.size() == playersIn.size())
			nextFloor();
	}
	
	public boolean isPlayerInElevator(UUID pUUID) {
		if (playersInElevator2.contains(pUUID))
			return true;
		else
			return false;
	}
	
	public boolean isElevatorActive() {
		if (nextFloorCountdown != null)
			return true;
		else
			return false;
	}
	
	public void sendAllMessage(String str) {
		for (UUID pUUID : playersIn)
			Bukkit.getPlayer(pUUID).sendMessage(ChatColor.GRAY + "[Building] " + ChatColor.WHITE + str);
	}
	
	
	public List<UUID> getPlayers() {
		return playersIn;
	}
	
	public int getLevels() {
		return levels;
	}
	
	public Location getElevatorLocation() {
		return elevatorLocation;
	}
	
	
	public String getSuit() {
		return buildingSuit;
	}
	
	/*
	public void startingNewRound(BattleData inputBattleData, boolean playersTurn) {
		new BukkitRunnable() {

			@Override
			public void run() {
				System.out.println("startingnewRound-battleOver: " + String.valueOf(inputBattleData.isBattleOver()));
				if (!inputBattleData.isBattleOver())
					nextBattleRound(inputBattleData, playersTurn);
			}
			
		}.runTaskLater(main, 40);
	}
	*/
}
