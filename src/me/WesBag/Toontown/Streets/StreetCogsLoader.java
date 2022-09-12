package me.WesBag.Toontown.Streets;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.scheduler.BukkitRunnable;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.CogsController;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.waypoint.LinearWaypointProvider;
import net.citizensnpcs.trait.waypoint.Waypoint;
import net.citizensnpcs.trait.waypoint.Waypoints;
import net.md_5.bungee.api.ChatColor;

public class StreetCogsLoader {
	
	private static Main main = Main.getInstance();
	//public static List<List<Location>> allStreetLocations = new ArrayList<>(); //List of all streets of all pathfinding locations
	//public static List<List<Waypoint>> allStreetWaypoints = new ArrayList<>();
	//public static List<List<Vector>> allStreetVectors = new ArrayList<>();
	public static List<List<UUID>> allStreetNPCs = new ArrayList<>();
	public static List<List<Location>> allPlaygroundStreets = new ArrayList<>();
	
	public static List<Location> loopyLaneLocations = new ArrayList<>(); //NPC Pathfinding Locations
	public static List<Location> punchlinePlaceLocations = new ArrayList<>();
	public static List<Location> sillyStreetLocations = new ArrayList<>();
	
	public static List<UUID> loopyLaneNPCs = new ArrayList<>();
	public static List<UUID> punchlinePlaceNPCs = new ArrayList<>();
	public static List<UUID> sillyStreetNPCs = new ArrayList<>();
	
	public static List<String> cogInvasions = new ArrayList<>();
	
	//public static List<Location> sillyStreetLocations = new ArrayList<>();
	//public static List<Vector> loopyLaneVectors = new ArrayList<>(); //NPC Pathfinding Vectors
	//public static List<Waypoint> loopyLaneWaypoints = new ArrayList<>();
	//public static List<Vector> sillyStreetVectors = new ArrayList<>();
	//public static List<NPC> loopyLaneNPCs = new ArrayList<>();
	public static List<int[]> streetPercentages2 = new ArrayList<>();
	//public static HashMap<Integer, int[]> streetPercentages = new HashMap<>();
	public static void loadStreetVariables() {
		System.out.println("------------Loading street variable!");
		//allStreetLocations.add(loopyLaneLocations);
//		/allStreetVectors.add(loopyLaneVectors);
		//allStreetWaypoints.add(loopyLaneWaypoints);
		
		//GLOBALS START
		allStreetNPCs.add(loopyLaneNPCs);
		allStreetNPCs.add(punchlinePlaceNPCs);
		allStreetNPCs.add(sillyStreetNPCs);
		
		allPlaygroundStreets.add(loopyLaneLocations);
		allPlaygroundStreets.add(punchlinePlaceLocations);
		allPlaygroundStreets.add(sillyStreetLocations);
		//GLOBALS END
		
		
		
		
		//PERCENTAGES START:
		int[] loopyLaneP = {10, 70, 10, 10};
		int[] punchlinePlaceP = {10, 10, 40, 40};
		int[] sillyStreetP = {25, 25, 25, 25};
		//streetPercentages.put(0, loopyLaneP);
		streetPercentages2.add(loopyLaneP);
		//streetPercentages.put(1, punchlinePlaceP);
		streetPercentages2.add(punchlinePlaceP);
		//streetPercentages.put(2, sillyStreetP);
		streetPercentages2.add(sillyStreetP);
		//PERCENTAGES END
		
		
		//WAYPOINTS START
		World world = Bukkit.getWorld("world");
		Location l1 = new Location(world, 1369.5, 57, -100.5);
		Location l2 = new Location(world, 1369.5, 57, -95.5);
		Location l3 = new Location(world, 1378.5, 57, -95.5);
		Location l4 = new Location(world, 1378.5, 57, -73.5);
		Location l5 = new Location(world, 1390.5, 57, -73.5);
		Location l6 = new Location(world, 1390.5, 57, -78.5);
		Location l7 = new Location(world, 1402.5, 57, -78.5);
		Location l8 = new Location(world, 1402.5, 57, -65.5);
		Location l9 = new Location(world, 1397.5, 57, -65.5);
		Location l10 = new Location(world, 1397.5, 57, -49.5);
		Location l11 = new Location(world, 1382.5, 57, -49.5);
		Location l12 = new Location(world, 1382.5, 57, -39.5);
		Location l13 = new Location(world, 1404.5, 57, -39.5);
		Location l14 = new Location(world, 1404.5, 57, -50.5);
		Location l15 = new Location(world, 1433.5, 57, -50.5);
		Location l16 = new Location(world, 1433.5, 57, -32.5);
		Location l17 = new Location(world, 1415.5, 57, -32.5);
		Location l18 = new Location(world, 1415.5, 57, -22.5);
		Location l19 = new Location(world, 1404.5, 57, -22.5);
		Location l20 = new Location(world, 1404.5, 57, -17.5);
		Location l21 = new Location(world, 1387.5, 57, -17.5);
		Location l22 = new Location(world, 1387.5, 57, -22.5);
		Location l23 = new Location(world, 1378.5, 57, -22.5);
		Location l24 = new Location(world, 1378.5, 57, -8.5);
		Location l25 = new Location(world, 1387.5, 57, -8.5);
		//LANE SWITCH
		Location l26 = new Location(world, 1387.5, 57, -6.5);
		Location l27 = new Location(world, 1376.5, 57, -6.5);
		Location l28 = new Location(world, 1376.5, 57, -24.5);
		Location l29 = new Location(world, 1387.5, 57, -24.5);
		Location l30 = new Location(world, 1387.5, 57, -29.5);
		Location l31 = new Location(world, 1404.5, 57, -29.5);
		Location l32 = new Location(world, 1404.5, 57, -24.5);
		Location l33 = new Location(world, 1413.5, 57, -24.5);
		Location l34 = new Location(world, 1413.5, 57, -34.5);
		Location l35 = new Location(world, 1421.5, 57, -34.5);
		Location l36 = new Location(world, 1421.5, 57, -48.5);
		Location l37 = new Location(world, 1406.5, 57, -48.5);
		Location l38 = new Location(world, 1406.5, 57, -37.5);
		Location l39 = new Location(world, 1368.5, 57, -37.5);
		Location l40 = new Location(world, 1368.5, 57, -51.5);
		Location l41 = new Location(world, 1395.5, 57, -51.5);
		Location l42 = new Location(world, 1395.5, 57, -65.5);
		Location l43 = new Location(world, 1390.5, 57, -65.5);
		Location l44 = new Location(world, 1390.5, 57, -71.5);
		Location l45 = new Location(world, 1376.5, 57, -71.5);
		Location l46 = new Location(world, 1376.5, 57, -93.5);
		Location l47 = new Location(world, 1367.5, 57, -93.5);
		Location l48 = new Location(world, 1367.5, 57, -100.5);
		
		loopyLaneLocations.add(l1);
		loopyLaneLocations.add(l2);
		loopyLaneLocations.add(l3);
		loopyLaneLocations.add(l4);
		loopyLaneLocations.add(l5);
		loopyLaneLocations.add(l6);
		loopyLaneLocations.add(l7);
		loopyLaneLocations.add(l8);
		loopyLaneLocations.add(l9);
		loopyLaneLocations.add(l10);
		loopyLaneLocations.add(l11);
		loopyLaneLocations.add(l12);
		loopyLaneLocations.add(l13);
		loopyLaneLocations.add(l14);
		loopyLaneLocations.add(l15);
		loopyLaneLocations.add(l16);
		loopyLaneLocations.add(l17);
		loopyLaneLocations.add(l18);
		loopyLaneLocations.add(l19);
		loopyLaneLocations.add(l20);
		loopyLaneLocations.add(l21);
		loopyLaneLocations.add(l22);
		loopyLaneLocations.add(l23);
		loopyLaneLocations.add(l24);
		loopyLaneLocations.add(l25);
		loopyLaneLocations.add(l26);
		loopyLaneLocations.add(l27);
		loopyLaneLocations.add(l28);
		loopyLaneLocations.add(l29);
		loopyLaneLocations.add(l30);
		loopyLaneLocations.add(l31);
		loopyLaneLocations.add(l32);
		loopyLaneLocations.add(l33);
		loopyLaneLocations.add(l34);
		loopyLaneLocations.add(l35);
		loopyLaneLocations.add(l36);
		loopyLaneLocations.add(l37);
		loopyLaneLocations.add(l38);
		loopyLaneLocations.add(l39);
		loopyLaneLocations.add(l40);
		loopyLaneLocations.add(l41);
		loopyLaneLocations.add(l42);
		loopyLaneLocations.add(l43);
		loopyLaneLocations.add(l44);
		loopyLaneLocations.add(l45);
		loopyLaneLocations.add(l46);
		loopyLaneLocations.add(l47);
		loopyLaneLocations.add(l48);
		
		Location plp1 = new Location(world, 1306.5, 57, -128.5);
		Location plp2 = new Location(world, 1306.5, 57, -112.5);
		Location plp3 = new Location(world, 1320.5, 57, -112.5);
		Location plp4 = new Location(world, 1320.5, 57, -97.5);
		Location plp5 = new Location(world, 1328.5, 57, -97.5);
		Location plp6 = new Location(world, 1328.5, 57, -102.5);
		Location plp7 = new Location(world, 1340.5, 57, -102.5);
		Location plp8 = new Location(world, 1340.5, 57, -90.5);
		Location plp9 = new Location(world, 1328.5, 57, -90.5);
		Location plp10 = new Location(world, 1328.5, 57, -95.5);
		Location plp11 = new Location(world, 1320.5, 57, -95.5);
		Location plp12 = new Location(world, 1320.5, 57, -80.5);
		Location plp13 = new Location(world, 1300.5, 57, -80.5);
		Location plp14 = new Location(world, 1300.5, 57, -55.5);
		Location plp15 = new Location(world, 1308.5, 57, -55.5);
		Location plp16 = new Location(world, 1308.5, 57, -58.5);
		Location plp17 = new Location(world, 1331.5, 57, -58.5);
		Location plp18 = new Location(world, 1331.5, 57, -43.5);
		Location plp19 = new Location(world, 1300.5, 57, -43.5);
		Location plp20 = new Location(world, 1300.5, 57, -22.5);
		Location plp21 = new Location(world, 1311.5, 57, -22.5);
		Location plp22 = new Location(world, 1311.5, 57, -10.5);
		//LANE SWITCH
		Location plp23 = new Location(world, 1309.5, 57, -10.5);
		Location plp24 = new Location(world, 1309.5, 57, -20.5);
		Location plp25 = new Location(world, 1298.5, 57, -20.5);
		Location plp26 = new Location(world, 1298.5, 57, -46.5);
		Location plp27 = new Location(world, 1317.5, 57, -46.5);
		Location plp28 = new Location(world, 1317.5, 57, -56.5);
		Location plp29 = new Location(world, 1310.5, 57, -56.5);
		Location plp30 = new Location(world, 1310.5, 57, -53.5);
		Location plp31 = new Location(world, 1298.5, 57, -53.5);
		Location plp32 = new Location(world, 1298.5, 57, -82.5);
		Location plp33 = new Location(world, 1318.5, 57, -82.5);
		Location plp34 = new Location(world, 1318.5, 57, -95.5);
		Location plp35 = new Location(world, 1310.5, 57, -95.5);
		Location plp36 = new Location(world, 1310.5, 57, -90.5);
		Location plp37 = new Location(world, 1298.5, 57, -90.5);
		Location plp38 = new Location(world, 1298.5, 57, -102.5);
		Location plp39 = new Location(world, 1310.5, 57, -102.5);
		Location plp40 = new Location(world, 1310.5, 57, -97.5);
		Location plp41 = new Location(world, 1318.5, 57, -97.5);
		Location plp42 = new Location(world, 1318.5, 57, -110.5);
		Location plp43 = new Location(world, 1304.5, 57, -110.5);
		Location plp44 = new Location(world, 1304.5, 57, -128.5);
		
		punchlinePlaceLocations.add(plp1);
		punchlinePlaceLocations.add(plp2);
		punchlinePlaceLocations.add(plp3);
		punchlinePlaceLocations.add(plp4);
		punchlinePlaceLocations.add(plp5);
		punchlinePlaceLocations.add(plp6);
		punchlinePlaceLocations.add(plp7);
		punchlinePlaceLocations.add(plp8);
		punchlinePlaceLocations.add(plp9);
		punchlinePlaceLocations.add(plp10);
		punchlinePlaceLocations.add(plp11);
		punchlinePlaceLocations.add(plp12);
		punchlinePlaceLocations.add(plp13);
		punchlinePlaceLocations.add(plp14);
		punchlinePlaceLocations.add(plp15);
		punchlinePlaceLocations.add(plp16);
		punchlinePlaceLocations.add(plp17);
		punchlinePlaceLocations.add(plp18);
		punchlinePlaceLocations.add(plp19);
		punchlinePlaceLocations.add(plp20);
		punchlinePlaceLocations.add(plp21);
		punchlinePlaceLocations.add(plp22);
		punchlinePlaceLocations.add(plp23);
		punchlinePlaceLocations.add(plp24);
		punchlinePlaceLocations.add(plp25);
		punchlinePlaceLocations.add(plp26);
		punchlinePlaceLocations.add(plp27);
		punchlinePlaceLocations.add(plp28);
		punchlinePlaceLocations.add(plp29);
		punchlinePlaceLocations.add(plp30);
		punchlinePlaceLocations.add(plp31);
		punchlinePlaceLocations.add(plp32);
		punchlinePlaceLocations.add(plp33);
		punchlinePlaceLocations.add(plp34);
		punchlinePlaceLocations.add(plp35);
		punchlinePlaceLocations.add(plp36);
		punchlinePlaceLocations.add(plp37);
		punchlinePlaceLocations.add(plp38);
		punchlinePlaceLocations.add(plp39);
		punchlinePlaceLocations.add(plp40);
		punchlinePlaceLocations.add(plp41);
		punchlinePlaceLocations.add(plp42);
		punchlinePlaceLocations.add(plp43);
		punchlinePlaceLocations.add(plp44);
		
		Location ss1 = new Location(world, 1392.5, 57, -168.5);
		Location ss2 = new Location(world, 1393.5, 57, -168.5);
		Location ss3 = new Location(world, 1393.5, 57, -181.5);
		Location ss4 = new Location(world, 1417.5, 57, -181.5);
		Location ss5 = new Location(world, 1417.5, 57, -170.5);
		Location ss6 = new Location(world, 1429.5, 57, -170.5);
		Location ss7 = new Location(world, 1429.5, 57, -194.5);
		Location ss8 = new Location(world, 1419.5, 57, -194.5);
		Location ss9 = new Location(world, 1419.5, 57, -189.5);
		Location ss10 = new Location(world, 1402.5, 57, -189.5);
		Location ss11 = new Location(world, 1402.5, 57, -194.5);
		Location ss12 = new Location(world, 1387.5, 57, -194.5);
		Location ss13 = new Location(world, 1387.5, 57, -210.5);
		Location ss14 = new Location(world, 1398.5, 57, -210.5);
		Location ss15 = new Location(world, 1398.5, 57, -230.5);
		Location ss16 = new Location(world, 1424.5, 57, -230.5);
		Location ss17 = new Location(world, 1424.5, 57, -216.5);
		Location ss18 = new Location(world, 1412.5, 57, -216.5);
		Location ss19 = new Location(world, 1412.5, 57, -209.5);
		Location ss20 = new Location(world, 1435.5, 57, -209.5);
		Location ss21 = new Location(world, 1435.5, 57, -223.5);
		Location ss22 = new Location(world, 1447.5, 57, -223.5);
		Location ss23 = new Location(world, 1447.5, 57, -243.5);
		Location ss24 = new Location(world, 1466.5, 57, -243.5);
		Location ss25 = new Location(world, 1466.5, 57, -224.5);
		Location ss26 = new Location(world, 1485.5, 57, -224.5);
		Location ss27 = new Location(world, 1485.5, 57, -241.5);
		Location ss28 = new Location(world, 1482.5, 57, -241.5);
		//LANE SWITCH
		Location ss29 = new Location(world, 1482.5, 57, -243.5);
		Location ss30 = new Location(world, 1487.5, 57, -243.5);
		Location ss31 = new Location(world, 1487.5, 57, -222.5);
		Location ss32 = new Location(world, 1464.5, 57, -222.5);
		Location ss33 = new Location(world, 1464.5, 57, -241.5);
		Location ss34 = new Location(world, 1449.5, 57, -241.5);
		Location ss35 = new Location(world, 1449.5, 57, -221.5);
		Location ss36 = new Location(world, 1437.5, 57, -221.5);
		Location ss37 = new Location(world, 1437.5, 57, -207.5);
		Location ss38 = new Location(world, 1410.5, 57, -207.5);
		Location ss39 = new Location(world, 1410.5, 57, -228.5);
		Location ss40 = new Location(world, 1400.5, 57, -228.5);
		Location ss41 = new Location(world, 1400.5, 57, -208.5);
		Location ss42 = new Location(world, 1389.5, 57, -208.5);
		Location ss43 = new Location(world, 1389.5, 57, -196.5);
		Location ss44 = new Location(world, 1402.5, 57, -196.5);
		Location ss45 = new Location(world, 1402.5, 57, -201.5);
		Location ss46 = new Location(world, 1419.5, 57, -201.5);
		Location ss47 = new Location(world, 1419.5, 57, -196.5);
		Location ss48 = new Location(world, 1431.5, 57, -196.5);
		Location ss49 = new Location(world, 1431.5, 57, -168.5);
		Location ss50 = new Location(world, 1415.5, 57, -168.5);
		Location ss51 = new Location(world, 1415.5, 57, -179.5);
		Location ss52 = new Location(world, 1395.5, 57, -179.5);
		Location ss53 = new Location(world, 1395.5, 57, -166.5);
		Location ss54 = new Location(world, 1392.5, 57, -166.5);
		
		sillyStreetLocations.add(ss1);
		sillyStreetLocations.add(ss2);
		sillyStreetLocations.add(ss3);
		sillyStreetLocations.add(ss4);
		sillyStreetLocations.add(ss5);
		sillyStreetLocations.add(ss6);
		sillyStreetLocations.add(ss7);
		sillyStreetLocations.add(ss8);
		sillyStreetLocations.add(ss9);
		sillyStreetLocations.add(ss10);
		sillyStreetLocations.add(ss11);
		sillyStreetLocations.add(ss12);
		sillyStreetLocations.add(ss13);
		sillyStreetLocations.add(ss14);
		sillyStreetLocations.add(ss15);
		sillyStreetLocations.add(ss16);
		sillyStreetLocations.add(ss17);
		sillyStreetLocations.add(ss18);
		sillyStreetLocations.add(ss19);
		sillyStreetLocations.add(ss20);
		sillyStreetLocations.add(ss21);
		sillyStreetLocations.add(ss22);
		sillyStreetLocations.add(ss23);
		sillyStreetLocations.add(ss24);
		sillyStreetLocations.add(ss25);
		sillyStreetLocations.add(ss26);
		sillyStreetLocations.add(ss27);
		sillyStreetLocations.add(ss28);
		sillyStreetLocations.add(ss29);
		sillyStreetLocations.add(ss30);
		sillyStreetLocations.add(ss31);
		sillyStreetLocations.add(ss32);
		sillyStreetLocations.add(ss33);
		sillyStreetLocations.add(ss34);
		sillyStreetLocations.add(ss35);
		sillyStreetLocations.add(ss36);
		sillyStreetLocations.add(ss37);
		sillyStreetLocations.add(ss38);
		sillyStreetLocations.add(ss39);
		sillyStreetLocations.add(ss40);
		sillyStreetLocations.add(ss41);
		sillyStreetLocations.add(ss42);
		sillyStreetLocations.add(ss43);
		sillyStreetLocations.add(ss44);
		sillyStreetLocations.add(ss45);
		sillyStreetLocations.add(ss46);
		sillyStreetLocations.add(ss47);
		sillyStreetLocations.add(ss48);
		sillyStreetLocations.add(ss49);
		sillyStreetLocations.add(ss50);
		sillyStreetLocations.add(ss51);
		sillyStreetLocations.add(ss52);
		sillyStreetLocations.add(ss53);
		sillyStreetLocations.add(ss54);
		//WAYPOINTS END
	}
	
	public static void loadStreetCogs(int streetNum) {
		int lowLevel = 1;
		int highLevel = 6;
		if (streetNum > 15) {
			lowLevel += 5;
			highLevel += 5;
		}
		else if (streetNum > 12) {
			lowLevel += 4;
			highLevel += 4;
		}
		else if (streetNum > 9) {
			lowLevel += 3;
			highLevel += 3;
		}
		else if (streetNum > 6) {
			lowLevel += 2;
			highLevel += 2;
		}
		else if (streetNum > 3) {
			lowLevel++;
			highLevel++;
		}
		
		Random rand = new Random();
		int cogsOnStreet = rand.nextInt(11-5)+5;
		streetNum--; //DECREMENTING STREETNUM CAUSE PASSING 1 OVER
		List<Location> streetLocations = allPlaygroundStreets.get(streetNum);
		List<UUID> streetNPCs = allStreetNPCs.get(streetNum);
		List<Integer> takenSpawnLocations = new ArrayList<>();
		
		for (int i = 0; i < cogsOnStreet; i++) {
			String cogName = "";
			int cogFreq = 0;
			int newCogFreq = 0;
			int level = (int) (Math.random() * (highLevel - lowLevel)) + lowLevel;
			int cogNameRandom = (int) (Math.random() * ((highLevel-2) - lowLevel)) + lowLevel;
			int[] tempCogFreq = streetPercentages2.get(streetNum);
			cogNameRandom--;
			if (level <= cogNameRandom)
				level++;
			double r = (Math.random() * 99);
			for (int j = 0; j < 4; j++) {
				newCogFreq += tempCogFreq[j];
				if (r > cogFreq && r < newCogFreq) {
					cogName += CogsController.cogSuits[j] + " " + CogsController.cogNames[j][cogNameRandom] + " Level " + level;
					break;
				}
			}
			NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, cogName);
			Random spawnRand = new Random();
			int spawnInt = spawnRand.nextInt(streetLocations.size() - 2) + 1;
			int loopProtect = 0;
			while (takenSpawnLocations.contains(spawnInt) || takenSpawnLocations.contains(spawnInt+1)) {
				spawnInt = spawnRand.nextInt(streetLocations.size() - 2) + 1;
				loopProtect++;
				if (loopProtect > 15) {
					main.getLogger().log(Level.WARNING, "Failed to load a free spot to spawn cog during street spawning!");
					break;
				}
			}
			
			npc.spawn(streetLocations.get(spawnInt));
			
			List<Location> npcLocations = new ArrayList<>();
			for (int k = spawnInt+1; k < streetLocations.size(); k++) {
				npcLocations.add(streetLocations.get(k));
			}
			for (int k = 0; k <= spawnInt; k++) {
				npcLocations.add(streetLocations.get(k));
			}
			
			streetNPCs.add(npc.getUniqueId());
			setupAllWaypoints(npc, npcLocations);
		}
	}
	
	public static void loadAllStreetCogs() {
		main.getLogger().log(Level.INFO, "Loading and spawning all street cogs....");
		int lowLevel = 1;
		int highLevel = 6; //One above because random method is exclusive to highLevel's num
		for (int i = 0; i < allPlaygroundStreets.size(); i++) {
			
	      	if (i % 3 == 0 && i != 0) {
	      		lowLevel++;
	      		highLevel++;
	      	}
	      	
	      	if (i % 11 == 0 && i != 0)
	      		highLevel++;
	      	
			List<Location> streetLocations = allPlaygroundStreets.get(i);
			List<UUID> streetNPCs = allStreetNPCs.get(i);
			List<Integer> takenSpawnLocations = new ArrayList<>();
			
			int[] tempCogFreq = streetPercentages2.get(i);
			Random rand = new Random();
			int cogsOnStreet = rand.nextInt(11-5)+5; //Between 5 and 10 cog per street?
			for (int j = 0; j < cogsOnStreet; j++) {
				String cogName = "";
				int cogFreq = 0;
				int newCogFreq = 0;
				int level = (int) (Math.random() * (highLevel - lowLevel)) + lowLevel;
				int cogNameRandom = (int) (Math.random() * ((highLevel-2) - lowLevel)) + lowLevel; //Maybe highLevel - 1 or - 2
				cogNameRandom--;
				if (level <= cogNameRandom)
					level++;
				double r = (Math.random() * 99);
				//System.out.println("CogNameRandom: " + cogNameRandom);
				//System.out.println("Spawn Cog Level: " + level);
				for (int k = 0; k < 4; k++) {
					newCogFreq += tempCogFreq[k];
					if (r > cogFreq && r < newCogFreq) {
						cogName += CogsController.cogSuits[k] + " " + CogsController.cogNames[k][cogNameRandom] + " Level " + level;
						break;
					}
				}
				NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.PLAYER, cogName);
				Random spawnRandom = new Random();
				int spawnInt = spawnRandom.nextInt(streetLocations.size() - 2) + 1;
				int loopProtect = 0;
				while (takenSpawnLocations.contains(spawnInt) || takenSpawnLocations.contains(spawnInt+1)) {
					spawnInt = spawnRandom.nextInt(streetLocations.size() - 2) + 1;
					loopProtect++;
					if (loopProtect > 15) {
						main.getLogger().log(Level.WARNING, "Failed to load a free spot to spawn cog during street spawning!");
						break;
					}
				}
				//int loopProtect = 0;
				//while (allCogLocationMaps.get(i).containsValue(spawnInt+1) || allCogLocationMaps.get(i).containsValue(spawnInt)) {
				//	spawnInt = spawnRandom.nextInt(streetLocations.size() - 2) + 1;
				//	loopProtect++;
				//	if (loopProtect > 15) {
				//		Main.getInstance().getLogger().log(Level.WARNING, "Failed to load a free spot to spawn cog during street spawning");
				//		break;
				//	}
				//}
				npc.spawn(streetLocations.get(spawnInt));
				
				List<Location> npcLocations = new ArrayList<>();
				for (int k = spawnInt+1; k < streetLocations.size(); k++) {
					npcLocations.add(streetLocations.get(k));
				}
				for (int k = 0; k <= spawnInt; k++) {
					npcLocations.add(streetLocations.get(k));
				}
				
				streetNPCs.add(npc.getUniqueId());
				//setupFirstWaypoint(npc, streetLocations.get(spawnInt+1));
				setupAllWaypoints(npc, npcLocations);
				//StreetsController.allCogLocationMaps.get(i).put(npc.getUniqueId(), (spawnInt+1));
			}
		}
		main.getLogger().log(Level.INFO, "Done with streets!");
	}
	
	
	
	
	public static void startInvasion(int streetNum, int cogSuit, int cogName) {
		List<UUID> streetNPCs = allStreetNPCs.get(streetNum);
		//List<Location> streetLocations = StreetsController.allPlaygroundStreets.get(streetNum);
		Random r = new Random();
		
		String startCogName = CogsController.cogSuits[cogSuit] + " " + CogsController.cogNames[cogSuit][cogName];
		cogInvasions.add(startCogName);
		for (UUID cUUID : streetNPCs) {
			int level = r.nextInt((cogName+6)-cogName)+cogName;
			String tempCogName = startCogName;
			tempCogName += " Level " + level;
			NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(cUUID);
			//((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).setPaused(true);
			//npc.getNavigator().cancelNavigation();
			//npc.getNavigator().getPathStrategy().stop();
			//npc.getOrAddTrait(Waypoints.class).getCurrentProvider().setPaused(true);
			//npc.ge
			//npc.getNavigator().get
			//npc.despawn(DespawnReason.PLUGIN);
			npc.setName(tempCogName);
			//npc.get
			//int spawnInt = r.nextInt(streetLocations.size() - 2) + 1;
			//int loopProtect = 0;
			//while (StreetsController.allCogLocationMaps.get(streetNum).containsValue(spawnInt+1) || StreetsController.allCogLocationMaps.get(streetNum).containsValue(spawnInt)) {
			//	spawnInt = r.nextInt(streetLocations.size() - 2) + 1;
			//	loopProtect++;
			//	if (loopProtect > 15) {
			//		main.getLogger().log(Level.WARNING, "Failed to load a spot to place cog while spawning invasion!");
					//break;
				//}
			//}
			//npc.spawn(streetLocations.get(spawnInt), SpawnReason.PLUGIN);
			//setupFirstWaypoint(npc, streetLocations.get(spawnInt+1));
			//StreetsController.allCogLocationMaps.get(streetNum).put(npc.getUniqueId(), (spawnInt+1));
		}
		Bukkit.getServer().broadcastMessage(ChatColor.LIGHT_PURPLE + "[" + ChatColor.AQUA + "Toon HQ" + ChatColor.LIGHT_PURPLE + "] " + ChatColor.WHITE + "Invasion of " + startCogName + "s starting on street number: " + streetNum);
	}
	
	public static void setupAllWaypoints(NPC npc, List<Location> locations) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				for (Location l : locations) {
					((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).addWaypoint(new Waypoint(l));
				}
				npc.getNavigator().getLocalParameters().distanceMargin(0.4);
				npc.getNavigator().getLocalParameters().baseSpeed((float) 0.75);
				npc.getNavigator().getLocalParameters().useNewPathfinder(true);
			}
		}.runTaskLater(main, 10);
	}
	
	public static void setupFirstWaypoint(NPC npc, Location l) {
		new BukkitRunnable() {
			
			@Override
			public void run() {
				((LinearWaypointProvider) npc.getOrAddTrait(Waypoints.class).getCurrentProvider()).addWaypoint(new Waypoint(l));
				npc.getNavigator().getLocalParameters().distanceMargin(0.4);
				npc.getNavigator().getLocalParameters().baseSpeed((float) 0.75);
				npc.getNavigator().getLocalParameters().useNewPathfinder(true);
			}
		}.runTaskLater(main, 10);
	}
	
}
