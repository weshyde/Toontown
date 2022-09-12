package me.WesBag.Toontown.BattleCore.Cogs.CogBuildings;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.CogsController;
import me.WesBag.Toontown.BattleCore.Cogs.CogTraits.CogBuildingTrait;
import me.WesBag.Toontown.SchematicUtilities.SchematicPaster;
import me.WesBag.Toontown.SchematicUtilities.SchematicSaver;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.npc.ai.NPCHolder;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class CogBuildingController implements Listener {
	
	//public static List<CogBuilding> cogBuildings = new ArrayList<>();
	//public static Map<UUID, CogBuilding> cogBuildings = new HashMap<>();
	//public static Map<Location, CogBuilding> cogBuildings2 = new HashMap<>();
	//public static List<>
	public static Map<Location, UUID> readyCogBuildings = new LinkedHashMap<>();
	//public static Map<Location, UUID> readyCogBuildings2 = new LinkedHashMap<>();
	public static Map<Location, CogBuilding> cogBuildingElevators = new HashMap<>();
	//public static Map<UUID, CogBuildingCountdown> buildingCountdowns = new HashMap<>();
	public static Map<Location, CogBuildingCountdown> buildingCountdowns2 = new HashMap<>();
	public static volatile List<UUID> playersInCogBuildings = new ArrayList<>();
	private Main main;
	
	public CogBuildingController(Main mn) {
		this.main = mn;
	}
	
	//@SuppressWarnings("deprecation")
	public static void spawnBuilding(Player p, Location l, String cogFullName) {
		BlockFace direction = null;
		System.out.println("-----Spawn Building Debug-----");
		loop:
		for (int x = -6; x <= 6; x++) { //Loops through a cube of blocks searching for chest to find direction to place npc
			for (int z = -6; z <= 6; z++) {
				for (int y = -3; y <= -1; y++) {
					//Location testLocation = l;
					final Block block = l.getWorld().getBlockAt(l.getBlockX()+x, l.getBlockY()+y, l.getBlockZ()+z);
					//double printX = l.getBlockX() + x;
					//double printY = l.getBlockY() + y;
					//double printZ = l.getBlockZ() + z;
					//System.out.println("Block at (" + printX + ", " + printY + ", " + printZ + ") = ");
					final Material material = block.getType();
					//System.out.println("Block at (" + printX + ", " + printY + ", " + printZ + ") = " + material.toString());
					if (material == Material.CHEST) {
						if (block.getBlockData() instanceof Directional) {
							Directional blockD = (Directional) block.getBlockData();
							direction = blockD.getFacing();
						}
						//Chest chest = (Chest) block;
						//direction = chest.getBlockData().get
						l.add(x, y, z);
						//l = testLocation;
						break loop;
					}
						
				}
			}
		}
		if (direction == null) {
			p.sendMessage("Building Anchor not found!");
			return;
		}
		
		int x = 0;
		int z = 0;
		int buildingX1 = 0;
		int buildingZ1 = 0;
		int buildingX2 = 0;
		int buildingZ2 = 0;
		int anchorX = 0;
		int anchorZ = 0;
		int pasteX = 0; //These two are used to only subtract/add only 1 to EITHER x and z so it pastes correctly
		int pasteZ = 0; //^^
		int savedPasteX = 0;
		int savedPasteZ = 0;
		int rotateY = 0; //Rotating schmatic to paste based on direction;
		float yaw = 0;
		switch(direction) { //Direction of chest
		case NORTH:
			z = -8; //Increasing each 7 to 15 to place the cog building npc further back
			yaw = 0; //Cog faces opposite building direction
			System.out.println("CogBuildingCog Yaw: 0");
			buildingX1 = -3; //Getting bottom left corner of cuboid (when facing building)
			
			buildingZ1 = 4;
			
			buildingX2 = 4; //Getting top right corner of cuboid //Changed from 4 to 3 4-23-22 to fix pasting
			buildingZ2 = -7;
			
			anchorZ = -4;
			
			rotateY = 90;
			
			pasteZ = 1;
			
			savedPasteZ = -11;
			break;
			
		case EAST:
			x = 8;
			yaw = 90;
			System.out.println("CogBuildingCog Yaw: 90");
			buildingZ1 = -3;
			
			buildingX1 = -4;
			
			buildingZ2 = 4; //Changed from 4 to 3 4-23-22 to fix pasting
			buildingX2 = 7;
			
			anchorX = 4;
			
			rotateY = 0;
			
			pasteX = -1;
			break;
			
		case SOUTH:
			z = 8;
			yaw = -180;
			System.out.println("CogBuildingCog Yaw: -180");
			buildingX1 = 3;
			
			buildingZ1 = -4;
			
			buildingX2 = -4; //Changed from 4 to 3 4-23-22 to fix pasting
			buildingZ2 = 7;
			
			anchorZ = 4;
			
			rotateY = 270;
			
			pasteZ = -1;
			//pasteZ = 11; //TESTING 4-22-22 TO FIX PASTING
			//pasteX = -8;
			
			savedPasteX = -7;
			break;
			
		case WEST:
			x = -8;
			yaw = -90;
			System.out.println("CogBuildingCog Yaw: -90");
			buildingZ1 = 3;
			
			buildingX1 = 4;
			
			buildingZ2 = -4; //Changed from 4 to 3 4-23-22 to fix pasting
			buildingX2 = -7;
			
			anchorX = -4;
			
			rotateY = 180;
			
			pasteX = 1;
			
			savedPasteX = -11;
			savedPasteZ = -7;
			break;
			
		default:
			p.sendMessage("Anchor not facing a lockable direction!");
			return;
		}
		double xResult = l.getBlockX() + x;
		double yResult = l.getBlockY() + 3;
		double zResult = l.getBlockZ() + z;
		p.sendMessage("CHECKING FOR NPCs AT (" + xResult + ", " + yResult + ", " + (zResult) + ")");
		
		l.add(x, 3, z);
		String cogSuit = null;
		String cogName = null;
		loop2:
		for (int i = 0; i <= 3; i++) { //Calculates all Cog info
			if (cogFullName.contains(CogsController.cogSuits[i])) {
				cogSuit = CogsController.cogSuits[i];
				for (int j = 0; j < 7; j++) {
					if (cogFullName.contains(CogsController.cogNames[i][j])) {
						cogName = CogsController.cogNames[i][j];
						break loop2;
					}
				}
			}
		}
		
		String levelStr = cogFullName.replaceAll("[^0-9]", "");
		int level = Integer.parseInt(levelStr);
		
		if (cogSuit == null || cogName == null || level > 12 || level < 1) {
			p.sendMessage("Cog name entered wrong! Cancelling...");
			//p.sendMessage("Cog Name:);
			return;
		}
		
		
		int difficulty = level;
		if (difficulty > 9)
			difficulty = 9;
		int floors = calcFloors(difficulty);
		
		//Finds NPC near location
		NPC npc = null;
		Collection<Entity> nearbyEntities = l.getWorld().getNearbyEntities(l, 5, 5, 5);
		for (Entity e : nearbyEntities) {
			if (e.hasMetadata("NPC")) {
				NPCHolder npcH = (NPCHolder) e;
				npc = npcH.getNPC();
				break;
			}
		}
		if (npc == null) {
			p.sendMessage("No replaceable NPC! Cancelling...");
			return;
		}
		Location anchorL = l.clone().add(anchorX, 0, anchorZ);
		System.out.println("Org X: " + l.getBlockX());
		System.out.println("Org Y: " + l.getBlockY());
		System.out.println("Org Z: " + l.getBlockZ());
		readyCogBuildings.put(anchorL, npc.getUniqueId());
		//readyCogBuildings2.put(anchorL, npc.getUniqueId());
		System.out.println("Anchor X : " + anchorL.getBlockX());
		System.out.println("Anchor Y : " + anchorL.getBlockY());
		System.out.println("Anchor Z : " + anchorL.getBlockZ());
		//String buildingNPCName = "";
		npc.addTrait(CogBuildingTrait.class);
		npc.getOrAddTrait(CogBuildingTrait.class).setDifficulty(difficulty);
		l.setYaw(yaw);
		npc.teleport(l, TeleportCause.PLUGIN);
		//npc.getStoredLocation().setYaw(yaw);
		//npc.getOrAddTrait(CogBuildingTrait.class).getOldName();
		String buildingNPCName = floors + " Story " + cogSuit + " Building";
		npc.setName(buildingNPCName);
		
		//1. Save old building as schematic
		//2. Save old building schematic name to npc
		//3. Load in cog building schematic
		Random r = new Random();
		int result = r.nextInt(100000) + 1;
		String oldBuildingFileName = "savedBuilding-" + result;
		int x1 = l.getBlockX() + buildingX1;
		int z1 = l.getBlockZ() + buildingZ1;
		int x2 = l.getBlockX() + buildingX2;
		int z2 = l.getBlockZ() + buildingZ2;
		
		int y1 = l.getBlockY();
		int y2 = l.getBlockY() + 40; //Max height of building (5 Story) is 40 blocks
		//int x1, y1, z1;
		//int x2, y2, z2;
		
		npc.getOrAddTrait(CogBuildingTrait.class).setFileName(oldBuildingFileName);
		System.out.println("Set cogBuildingFileName: " + oldBuildingFileName);
		npc.getOrAddTrait(CogBuildingTrait.class).setX(x1+savedPasteX);
		npc.getOrAddTrait(CogBuildingTrait.class).setY(y1-1);
		npc.getOrAddTrait(CogBuildingTrait.class).setZ(z1+savedPasteZ); //Commented to test
		npc.getOrAddTrait(CogBuildingTrait.class).setYRotate(rotateY);
		System.out.println("-----Paste Debug-----");
		System.out.println("X1: " + x1);
		System.out.println("Y1: " + y1);
		System.out.println("Z1: " + z1);
		System.out.println("X2: " + x2);
		System.out.println("Y2: " + y2);
		System.out.println("Z2: " + z2);
		System.out.println("Y Rotate: " + rotateY);
		System.out.println("Pasted at:");
		System.out.println("X: " + (x1+pasteX));
		System.out.println("Y: " + y1);
		System.out.println("Z: " + (z1+pasteZ));
		SchematicSaver.saveSchematic4(oldBuildingFileName, "world", x1, y1-1, z1, x2, y2, z2);
		//SchematicSaver.saveSchematic(oldBuildingFileName, "world", x1, y1, z1, x2, y2, z2);
		
		File buildingFile = new File(Main.getInstance().getDataFolder().getAbsolutePath() + "/schematics/" + floors +  "-Floor.schem");
		SchematicPaster.pasteSchematic2("world", buildingFile, x1+pasteX, y1, z1+pasteZ, rotateY, false);
		
		//readyCogBuildings.put(l, npc.getUniqueId());
		//l.getWorld().spawnFallingBlock(l.add(x, 20, z), Material.GRAY_CONCRETE, l.getWorld().getBlockAt(l).getData());
		//l.getWorld().spawnFallingBlock(l.add(x, 20, z), Material.GRAY_CONCRETE, l.getWorld().getBlockAt(l).getData());
		//l.getWorld().spawnFallingBlock(l.add(x, 20, z), Material.GRAY_CONCRETE, l.getWorld().getBlockAt(l).getData());
		
		//Put this in bukkit runnable to set up explosions with increasing speed as building lands
		//l.getWorld().createExplosion(l.add(0, 5, 0), (float) 2.0);
	}
	
	
	public static int calcFloors(int difficulty) {
		int lowFloor = 1;
		int highFloor = 1;
		switch (difficulty) {
		case 1:
			break;
			
		case 2:
			highFloor = 2;
			break;
			
		case 3:
			highFloor = 3;
			break;
			
		case 4:
			lowFloor = 2;
			highFloor = 3;
			break;
			
		case 5:
			lowFloor = 2;
			highFloor = 4;
			break;
			
		case 6:
			lowFloor = 3;
			highFloor = 4;
			break;
			
		case 7:
			lowFloor = 3;
			highFloor = 5;
			break;
			
		case 8:
			lowFloor = 4;
			highFloor = 5;
			break;
		
		case 9:
			lowFloor = 5;
			highFloor = 5;
			break;
			
		default:
			break;
		}
		
		Random r = new Random();
		highFloor++; //Incremented because below generate excludes the highFloor number
		int floors = r.nextInt(highFloor-lowFloor) + lowFloor;
		return floors;
	}
	
	public void startBuildingCountdown() {
		new BukkitRunnable() {
			int time = 20;
			@Override
			public void run() {
				time--;
				if (time <= 0) {
					cancel();
				}
			}
		}.runTaskLater(Main.getInstance(), 3);
	}
	/*
	@EventHandler
	public void onPlayerRightClickCogBuildingNPC(NPCRightClickEvent e) {
		NPC npc = e.getNPC();
		Player player = e.getClicker();
		String unformattedNPCName = ChatColor.stripColor(npc.getFullName());
		if (unformattedNPCName.contains("Story")) {
			if (buildingCountdowns.containsKey(npc.getUniqueId())) {
				if (buildingCountdowns.get(npc.getUniqueId()).getPlayerAmount() < 4) {
					buildingCountdowns.get(npc.getUniqueId()).addPlayer(player.getUniqueId());
					CogBuildingCountdown.inCountdownPlayers.add(player.getUniqueId());
					for (int i = 0 ; i < 9; i++)
						player.getInventory().setItem(i, createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Leave Cog Building Group"));
				}
				else {
					player.sendMessage("Sorry, this building is full!");
				}
			}
			else {
				CogBuildingCountdown buildingCountdown = new CogBuildingCountdown(player.getUniqueId(), npc, npc.getStoredLocation());
				CogBuildingCountdown.inCountdownPlayers.add(player.getUniqueId());
				buildingCountdown.runTaskTimer(main, 0, 20);
				buildingCountdowns.put(npc.getUniqueId(), buildingCountdown);
				for (int i = 0 ; i < 9; i++)
					player.getInventory().setItem(i, createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Leave Cog Building Group"));
			}
		}
		
		else if (unformattedNPCName.contains("Next Floor")) {
			CogBuilding cBuilding = getCogBuildingNPC(npc.getUniqueId());
			if (cBuilding != null) {
				cBuilding.addPlayerElevator(player.getUniqueId());
			}
			else {
				player.sendMessage("No Cog Building exists with that NPC UUID!");
				Main.getInstance().getLogger().log(Level.SEVERE, "Next Floor NPC clicked with no attached Cog Building!");
				return;
			}
		}
		
	}
	*/
	@EventHandler
	public void onPlayerRightClickExitBlock(PlayerInteractEvent e) {
		if (!CogBuildingCountdown.playerInCogBuilding(e.getPlayer().getUniqueId())) return;
		if (e.getPlayer().getInventory().getItemInMainHand().getType() == Material.RED_CONCRETE) {
			CogBuildingCountdown tempCountdown = getBuildingCountdown(e.getPlayer().getUniqueId());
			tempCountdown.removePlayer(e.getPlayer().getUniqueId());
			//CogBuildingCountdown.removeCogBuildingPlayer(e.getPlayer().getUniqueId());
			tempCountdown.resetTime(); //Resets time
			e.getPlayer().getInventory().clear();
			e.getPlayer().sendMessage("You left the Cog Building group!");
			if (tempCountdown.getPlayerAmount() < 1) {
				//buildingCountdowns.remove(tempCountdown.getNPC().getUniqueId());
				tempCountdown.cancel();
			}
		}
	}
	
	@EventHandler
	public void onPlayerWalkIntoNextFloorElevator(PlayerMoveEvent e) {
		if (playersInCogBuildings.contains(e.getPlayer().getUniqueId())) {
			Player p = e.getPlayer();
			Block blockUnder = p.getLocation().subtract(0, 1, 0).getBlock();
			if (blockUnder.getType() == Material.IRON_BLOCK) {
				Location pL = p.getLocation();
				for (final Location cbL : cogBuildingElevators.keySet()) {
					System.out.println("cbl y: " + cbL.getY());
					if (pL.distance(cbL) < 4) {
						//System.out.println("key y: " + cogBui
						CogBuilding cogBuilding = getCogBuildingFromElevator(cbL);
						if (cogBuilding.getPlayers().contains(p.getUniqueId()) && cogBuilding.isElevatorActive() && !cogBuilding.isPlayerInElevator(p.getUniqueId())) {
							cogBuilding.addPlayerElevator(p.getUniqueId());
						}
					}
				}
				
				
				//for (Location cbL : cogBuildingElevators.keySet()) {
				//	if (pL.distance(cbL) < 4) {
				//		CogBuilding cBuilding = getCogBuildingFromElevator(cbL);
				//		if (cBuilding.getPlayers().contains(p.getUniqueId()) && cBuilding.isElevatorActive() && !cBuilding.isPlayerInElevator(p.getUniqueId())) {
				//			cBuilding.addPlayerElevator(p.getUniqueId());
				//		}
				//	}
				//}
			}
		}
	}
	
	@EventHandler
	public void onPlayerWalkIntoElevator(PlayerMoveEvent e) {
		Player p = e.getPlayer();
		
		Block blockUnder = p.getLocation().subtract(0, 1, 0).getBlock();
		if (blockUnder.getType() == Material.IRON_BLOCK) {
			if (playersInCogBuildings.contains(p.getUniqueId()))
				return;
			Location pL = p.getLocation();
			for (final Location cbL : readyCogBuildings.keySet()) {
				if (pL.distance(cbL) < 4) {
					if (buildingCountdowns2.containsKey(cbL)) { //This is for players joining existing building group
						if (buildingCountdowns2.get(cbL).containsPlayer(p.getUniqueId())) //Returns if player is already in group
							return;
						else if (buildingCountdowns2.get(cbL).getPlayerAmount() < 4) {
							buildingCountdowns2.get(cbL).addPlayer(p.getUniqueId());
							CogBuildingCountdown.inCountdownPlayers.add(p.getUniqueId());
							for (int i = 0 ; i < 9; i++)
								p.getInventory().setItem(i, createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Leave Cog Building Group"));
						}
						else {
							p.sendMessage("Sorry, this building is full!");
							e.setCancelled(true);
						}
					}
					else if (!CogBuildingCountdown.inCountdownPlayers.contains(p.getUniqueId())) { //If none exist, make new cog building group
						CogBuildingCountdown.inCountdownPlayers.add(p.getUniqueId());
						UUID uuid = readyCogBuildings.get(cbL);
		
						if (CitizensAPI.getNPCRegistry().getByUniqueId(readyCogBuildings.get(cbL)) == null) {
							System.out.println(uuid);
							System.out.println("ITS NULL!");
						}
						NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(readyCogBuildings.get(cbL));
						//String buildingName = npc.getFullName();
						CogBuildingCountdown buildingCountdown = new CogBuildingCountdown(p.getUniqueId(), npc, cbL);
						buildingCountdown.runTaskTimer(main, 0, 20);
						buildingCountdowns2.put(cbL, buildingCountdown);
						for (int i = 0 ; i < 9; i++)
							p.getInventory().setItem(i, createGuiItem(Material.RED_CONCRETE, ChatColor.RED + "Leave Cog Building Group"));
					}
				}
				else {
					System.out.println("Player walk on iron block but not within area!");
				}
			}
		}
	}
	
	public static CogBuildingCountdown getBuildingCountdown(UUID pUUID) {
		for (CogBuildingCountdown building : buildingCountdowns2.values()) {
			if (building.containsPlayer(pUUID))
				return building;
		}
		return null;
	}
	
	//public static CogBuildingCountdown getBuildingCountdown
	
	//public static CogBuilding getCogBuildingNPC(UUID npcUUID) {
	//	if (cogBuildings.containsKey(npcUUID))
	//		return cogBuildings.get(npcUUID);
	//	return null;
	//}
	
	public static CogBuilding getCogBuildingFromElevator(Location l) {
		if (cogBuildingElevators.containsKey(l))
			return cogBuildingElevators.get(l);
		System.out.println("------------Cog Building from elevator is null!");
		return null;
	}
	
	//public static CogBuilding getCogBuildingLocation(Location l) {
	//	if (cogBuildings2.containsKey(l))
	//		return cogBuildings2.get(l);
	//	return null;
	//}
	
	protected ItemStack createGuiItem(final Material material, final String name, final String... lore) {
		final ItemStack item = new ItemStack(material, 1);
		final ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(name);
		meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);

		return item;
	}
	

}
