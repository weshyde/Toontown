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
	
	public static void saveAllStreetBattleLocationsFromFile() {
		for (int i = 0; i < 10; i++) {
			Main.fDataFile.getData().set("sLocations.loopyLane." + i, availableStreetLocs);
		}
	}
	
	public static void loadAllStreetBattleLocationsFromFile() {
		Main.fDataFile.getData();
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
		List<List<Double>> tempList = new ArrayList<>();
		
		while (streetLocs.containsKey(regionLocID) && (availableStreetLocs.get(regionLocID) == false)) { //Formulates list of available spots on the same street
			tempList.add(streetLocs.get(regionLocID));
			regionLocID++;
		}
		
		Location anchoredBattle = null;
		
		for (int i = 0; i < tempList.size(); i++) { //Loop to find the nearest place to anchor the street battle
			double distance = curLoc.distance(intsToLoc(tempList.get(i)));
			if ( (distance < 15) && (anchoredBattle != null) && (distance < anchoredBattle.distance(curLoc))) {
				anchoredBattle = intsToLoc(tempList.get(i));
			}
		}
		
		
		return anchoredBattle;
	}
}
