package me.WesBag.Toontown.Streets;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import me.WesBag.Toontown.Main;

public class StreetBattlePositioning {
	
	// Integer XYZZ
	// X: Playground
	// Y: Street
	// ZZ: Loc # on street
	//X -> TTC = 1, DD = 2, etc
	public static Map<Integer, Boolean> availableStreetLocs = new HashMap<>();
	public static Map<Integer, List<Double>> streetLocs = new HashMap<>();
	//StreetLocs List of double -> 0: X, 1: Y, 2: Z, 3: Yaw, 4: Pitch
	/*
	public static void loadAllStreetBattleLocations() {
		
		//---- LOOPY LANE ----
		List<Double> p1 = new ArrayList<Double>();
		p1.add(1370.5);
		p1.add(56.0);
		p1.add(-94.5);
		p1.add(-90.0);
		p1.add(0.0);
		
		
		//---- PUNCHLINE PLACE ----
		
		
		
		//---- SILLY STREET ----
	}
	*/
	
	public static void loadAllStreetBattleLocationsFromFile() {
		String[] streets = {"loopyLane", "punchlinePlace", "sillyStreet"};
		int locIndex = 1;
		int streetIndex = 0;
		int streetLocsID = 1101;
		int toRemove = 0;
		String street = streets[streetIndex];
		
		while (streetIndex < streets.length) {
			street = streets[streetIndex];
			
			while (Main.fDataFile.getData().contains("sLocations." + street + "." + locIndex)) {
				String s = Main.fDataFile.getData().getString("sLocations." + street + "." + locIndex);
				String[] parts = s.split("_");
				streetLocs.put(streetLocsID, stringToDoubles(parts));
				availableStreetLocs.put(streetLocsID, false);
				
				locIndex++;
				streetLocsID++;
				toRemove++;
			}
			
			streetLocsID -= toRemove;
			toRemove = 0;
			locIndex = 1;
			streetIndex++;
			
			if (streetIndex < 3) { //Indicates TTC, adds 100
				streetLocsID += 100;
			}
			/* ADD BACK WHEN MORE STREETS ARE IMPLEMENTED!
			else if (streetIndex < 6) { //Indicates DD
				if (streetIndex == 3) { //If first street, 2100
					streetLocsID = 2101;
				}
				else { //Else next street
					streetLocsID += 100;
				}
			}
			else if (streetIndex < 9) { //Indicates DG
				if (streetIndex == 6) { //If first street in DG, 3100
					streetLocsID = 3101;
				}
				else { //Else next street
					streetLocsID += 100;
				}
			}
			else if (streetIndex < 12) { //Indicates MML
				if (streetIndex == 9) {
					streetLocsID = 4101;
				}
				else {
					streetLocsID += 100;
				}
			}
			else if (streetIndex < 15)  { //Indicates Brrrgh
				if (streetIndex == 12) {
					streetLocsID = 5101;
				}
				else {
					streetLocsID += 100;
				}
			}
			else if (streetIndex < 17) { //Indicates DDL
				if (streetIndex == 15) {
					streetLocsID = 6101;
				}
				else {
					streetLocsID += 100;
				}
			}
			*/
		}
	}
	
	public static List<Double> stringToDoubles(String[] strs) {
		List<Double> doubles = new ArrayList<>();
		for (int i = 0; i < strs.length; i++) {
			double strDouble = Double.valueOf(strs[i]);
			doubles.add(strDouble);
		}
		return doubles;
	}
	
	public static String doublesToString(List<Double> doubles) {
		String s = doubles.get(0).toString() + "_" + doubles.get(1).toString() + "_" + doubles.get(2).toString() + "_" + doubles.get(3).toString() + "_" + doubles.get(4).toString();
		return s;
	}
	
	public static String locationToString(Location l) {
		double x = (double)Math.round(l.getX() * 10) / 10;
		double y = (double)Math.round(l.getY() * 10) / 10;
		double z = (double)Math.round(l.getZ() * 10) / 10;
		double yaw = (double)Math.round(l.getYaw() * 10) / 10;
		double pitch = (double)Math.round(l.getPitch() * 10) / 10;
		String s = x + "_" + y + "_" + z + "_" + yaw + "_" + pitch;
		
		return s;
	}
	
	public static List<Double> locationToDoubles(Location l) {
		
		List<Double> doubles = new ArrayList<>();
		
		double x = (double)Math.round(l.getX() * 10) / 10;
		double y = (double)Math.round(l.getY() * 10) / 10;
		double z = (double)Math.round(l.getZ() * 10) / 10;
		double yaw = (double)Math.round(l.getYaw() * 10) / 10;
		double pitch = (double)Math.round(l.getPitch() * 10) / 10;
		
		doubles.add(x);
		doubles.add(y);
		doubles.add(z);
		doubles.add(yaw);
		doubles.add(pitch);
		
		return doubles;
	}
	
	public static Location intsToLoc(int streetLocID) {
		List<Double> tempList = streetLocs.get(streetLocID);
		double yaw = tempList.get(3);
		double pitch = tempList.get(4);
		
		World w = Bukkit.getWorld("world");
		Location l = new Location(w, tempList.get(0), tempList.get(1), tempList.get(2), (float)yaw, (float)pitch);
		return l;
	}
	
	public static Location intsToLoc(List<Double> ints) {
		double yaw = ints.get(3);
		double pitch = ints.get(4);
		
		World w = Bukkit.getWorld("world");
		Location l = new Location(w, ints.get(0), ints.get(1), ints.get(2), (float)yaw, (float)pitch);
		return l;
	}
	
	public static Location anchorStreetBattle(int regionLocID, Location curLoc) {
		System.out.println("RegionLocID: " + regionLocID);
		regionLocID*= 100;
		regionLocID++;
		int originalLocID = regionLocID;
		System.out.println("StreetLocs size: " + streetLocs.size());
		List<List<Double>> tempList = new ArrayList<>();
		Map<Integer, List<Double>> tempList2 = new HashMap<>();
		
		while (streetLocs.containsKey(regionLocID) && (availableStreetLocs.get(regionLocID) == false)) { //Formulates list of available spots on the same street
			//tempList.add(streetLocs.get(regionLocID));
			tempList2.put(regionLocID, streetLocs.get(regionLocID));
			regionLocID++;
			System.out.println("Found available street loc on street!");
		}
		
		Location anchoredBattle = null;
		/*
		for (int i = 0; i < tempList.size(); i++) { //Loop to find the nearest place to anchor the street battle
			double distance = curLoc.distance(intsToLoc(tempList.get(i)));
			if (((distance < 15) && (anchoredBattle != null) && (distance < anchoredBattle.distance(curLoc))) | ((distance < 15) && (anchoredBattle == null))) {
				anchoredBattle = intsToLoc(tempList.get(i));
			}
		} Commenting to replace with Map of Int and List instead of ListList
		*/
		for (int i = 0; i < tempList2.size(); i++) {
			originalLocID += i;
			double distance = curLoc.distance(intsToLoc(tempList2.get(originalLocID)));
			if (((distance < 15) && (anchoredBattle != null) && (distance < anchoredBattle.distance(curLoc))) | ((distance < 15) && (anchoredBattle == null))) {
				anchoredBattle = intsToLoc(tempList2.get(originalLocID));
			}
		}
		
		return anchoredBattle;
	}
	
	public static void saveNewLocation(int locInt, List<Double> locList) {
		streetLocs.put(locInt, locList);
		availableStreetLocs.put(locInt, false);
	}
}
