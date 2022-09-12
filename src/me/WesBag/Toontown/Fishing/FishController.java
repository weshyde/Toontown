package me.WesBag.Toontown.Fishing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.World;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import me.WesBag.Toontown.Fishing.Fish.TTFish;

public class FishController {

	//public static List<ProtectedRegion> allPondRegions = new ArrayList<>();
	public static Map<ProtectedRegion, List<TTFish>> regionFish = new HashMap<>(); //OUTDATED
	public static Map<ProtectedRegion, Map<Integer, List<TTFish>>> regionFishingPonds = new HashMap<>();
	
	public static final String[][] allFishNames = {
			{"Balloon Fish", "Hot Air Balloon Fish", "Weather Balloon Fish", "Water Balloon Fish", "Red Balloon Fish"},
			{"Clown Fish", "Sad Clown Fish", "Circus Clown Fish", "Party Clown Fish"},
			{"Star Fish", "Five Star Fish", "Rock Star Fish", "Shining Star Fish", "All Star Fish"},
			{"PB And J Fish", "Grape PB And J Fish", "Crunchy PB And J Fish", "Strawberry PB And J Fish", "Concord Grape PB And J Fish"},
			{"Cat Fish", "Siamese Cat Fish", "Alley Cat Fish", "Tabby Cat Fish", "Tom Cat Fish"},
			{"King Crab", "Alaskan King Crab", "Old King Crab"},
			{"Amore Eel", "Electric Amore Eel"},
			{"Nurse Shark", "Clara Nurse Shark", "Florence Nurse Shark"},
			{"Dog Fish", "Bull Dog Fish", "Hot Dog Fish", "Dalmatian Dog Fish", "Puppy Dog Fish"},
			{"Cutthroat Trout", "Captain Cutthroat Trout", "Scurvy Cutthroat Trout"},
			{"Moon Fish", "Full Moon Fish", "Half Moon Fish", "New Moon Fish", "Crescent Moon Fish", "Harvest Moon Fish"},
			{"Sea Horse", "Rocking Sea Horse", "Clydesdale Sea Horse", "Arabian Sea Horse"},
			{"Pool Shark", "Kiddie Pool Shark", "Swimming Pool Shark", "Olympic Pool Shark"},
			{"Bear Acuda", "Black Bear Acuda", "Koala Bear Acuda", "Honey Bear Acuda", "Polar Bear Acuda", "Panda Bear Acuda", "Kodiac Bear Acuda", "Grizzly Bear Acuda"},
			{"Piano Tuna", "Grand Piano Tuna", "Baby Grand Piano Tuna", "Upright Piano Tuna", "Player Piano Tuna"},
			{"Frozen Fish"},
			{"Holey Mackerel"},
			{"Devil Ray"}
	};
	
	public static final TTFish[][] allFish = {
			{null, null, null, null, null}, //Balloon Fish
			{null, null, null, null}, //Clown Fish
			{null, null, null, null, null}, //Star Fish
			{null, null, null, null, null}, //PBJ Fish
			{null, null, null, null, null}, //Cat Fish
			{null, null, null}, //King Crab
			{null, null}, //Amore Eel
			{null, null, null}, //Nurse Shark
			{null, null, null, null, null}, //Dog Fish
			{null, null, null}, //Cutthroat Troat
			{null, null, null, null, null, null}, //Moon Fish
			{null, null, null, null}, //Sea Horse
			{null, null, null, null}, //Pool Shark
			{null, null, null, null, null, null, null, null}, //Bear Acuda
			{null, null, null, null, null}, //Piano Tuna
			{null}, //Frozen Fish
			{null}, //Holey Mackerel
			{null} //Devil Ray
	};
	
	//GLOBAL
	List<List<TTFish>> allPonds = new ArrayList<>();
	List<TTFish> anywhereFish = new ArrayList<>();
	//TTC
	List<TTFish> ttcPond = new ArrayList<>();
	List<TTFish> loopyLanePond = new ArrayList<>();
	List<TTFish> punchlinePlacePond = new ArrayList<>();
	List<TTFish> sillyStreetPond = new ArrayList<>();
	
	public FishController() {
		allPonds.add(ttcPond); //Four outdated?
		allPonds.add(loopyLanePond);
		allPonds.add(punchlinePlacePond);
		allPonds.add(sillyStreetPond);
		
		loadAllFish(); //New
		loadAllRegionFishingPonds(); //New
	}
	
	public static void initPonds() {
		loadAllFish();
		loadAllRegionFishingPonds();
	}
	
	public static void loadAllFish() { //LOAD FIRST
		int[] amtPerSpecies = {5, 4, 5, 5, 5, 3, 2, 3, 5, 3, 6, 4, 4, 8, 5, 1, 1, 1};
		for (int row = 0; row < 18; row++) {
			for (int fishNum = 0; fishNum < amtPerSpecies[row]; fishNum++) {
				TTFish fish = new TTFish(allFishNames[row][0], allFishNames[row][fishNum], fishNum, row);
				allFish[row][fishNum] = fish;
			}
		}
	}
	
	public static void loadAllRegionFishingPonds() { //LOAD SECOND
		//Starting Init
		World world = Bukkit.getWorld("world");
		com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(world);
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regions = container.get(wgWorld);
		//List<ProtectedRegion> allPondRegions = new ArrayList<>();
		//for (int i = 0; i < 3; i++) { //I < amount of ponds, increasing
			
		//}
		
		//Ending Init
		
		//---Anywhere Fish---
		List<TTFish> anywhereR1 = new ArrayList<>();
		anywhereR1.add(allFish[0][0]); //BF
		anywhereR1.add(allFish[2][0]); //Star Fish
		List<TTFish> anywhereR2 = new ArrayList<>();
		anywhereR2.add(allFish[4][0]); //CatFish
		anywhereR2.add(allFish[1][0]); //ClownFish
		List<TTFish> anywhereR3 = new ArrayList<>();
		anywhereR3.add(allFish[8][0]); //DogFish
		anywhereR3.add(allFish[2][1]); //FiveStarFish
		anywhereR3.add(allFish[1][3]); //PartyClown
		anywhereR3.add(allFish[9][0]); //Cuttthroat
		anywhereR3.add(allFish[12][0]); //Pool Shark
		List<TTFish> anywhereR4 = new ArrayList<>();
		anywhereR4.add(allFish[6][0]); //Amore Eel
		anywhereR4.add(allFish[8][4]); //PuppyDog
		anywhereR4.add(allFish[3][0]); //PB&J
		anywhereR4.add(allFish[11][0]); //SeaHorse
		anywhereR4.add(allFish[5][0]); //KingCrab
		List<TTFish> anywhereR5 = new ArrayList<>();
		anywhereR5.add(allFish[11][1]); //RockingSeaHorse
		anywhereR5.add(allFish[3][1]); //GrapePB&J
		anywhereR5.add(allFish[0][1]); //HotAirBF
		anywhereR5.add(allFish[1][1]); //SadClownFish
		List<TTFish> anywhereR6 = new ArrayList<>();
		anywhereR6.add(allFish[2][3]); //ShiningStarFish
		anywhereR6.add(allFish[14][0]); //PianoTuna
		List<TTFish> anywhereR7 = new ArrayList<>();
		anywhereR7.add(allFish[7][0]); //Nurse Shark
		List<TTFish> anywhereR8 = new ArrayList<>();
		anywhereR8.add(allFish[2][2]); //RockStarFish
		List<TTFish> anywhereR9 = new ArrayList<>();
		List<TTFish> anywhereR10 = new ArrayList<>();
		anywhereR10.add(allFish[17][0]); //DevilRay
		anywhereR10.add(allFish[16][0]); //HolyMack
		
		List<List<TTFish>> allAnywhereRarities = new ArrayList<>();
		allAnywhereRarities.add(anywhereR1);
		allAnywhereRarities.add(anywhereR2);
		allAnywhereRarities.add(anywhereR3);
		allAnywhereRarities.add(anywhereR4);
		allAnywhereRarities.add(anywhereR5);
		allAnywhereRarities.add(anywhereR6);
		allAnywhereRarities.add(anywhereR7);
		allAnywhereRarities.add(anywhereR8);
		allAnywhereRarities.add(anywhereR9);
		allAnywhereRarities.add(anywhereR10);
		
		
		//---TTC---
		//TTC Playground
		ProtectedRegion ttcPR = regions.getRegion("ttc-playground");
		//allPondRegions.add(ttcPR);
		Map<Integer, List<TTFish>> ttcPondFishMap = new HashMap<>();
		
		
		List<TTFish> ttcPondR1 = new ArrayList<>();
		ttcPondR1.add(allFish[1][0]);
		List<TTFish> ttcPondR2 = new ArrayList<>();
		ttcPondR2.add(allFish[0][1]);
		ttcPondR2.add(allFish[1][3]);
		ttcPondR2.add(allFish[3][0]);
		List<TTFish> ttcPondR4 = new ArrayList<>();
		ttcPondR4.add(allFish[0][1]);
		ttcPondR4.add(allFish[1][1]);
		List<TTFish> ttcPondR6 = new ArrayList<>();
		ttcPondR6.add(allFish[1][2]);
		
		ttcPondFishMap.put(1, ttcPondR1);
		ttcPondFishMap.put(2, ttcPondR2);
		ttcPondFishMap.put(4, ttcPondR4);
		ttcPondFishMap.put(6, ttcPondR6);
		
		regionFishingPonds.put(ttcPR, ttcPondFishMap);
		
		//TTC Loopy Lane
		ProtectedRegion ttcLLPR = regions.getRegion("ttc-loopylane");
		//allPondRegions.add(ttcLLPR);
		Map<Integer, List<TTFish>> ttcLLPondFishMap = new HashMap<>();
		
		List<TTFish> ttcLLPondR2 = new ArrayList<>();
		ttcLLPondR2.add(allFish[0][4]);
		
		ttcLLPondFishMap.put(2, ttcLLPondR2);
		
		regionFishingPonds.put(ttcLLPR, ttcLLPondFishMap);
		
		//TTC PunchlinePlace
		ProtectedRegion ttcPLPPR = regions.getRegion("ttc-punchlineplace");
		//allPondRegions.add(ttcPLPPR);
		Map<Integer, List<TTFish>> ttcPLPPondFishMap = new HashMap<>();
		
		List<TTFish> ttcPLPPondR5 = new ArrayList<>();
		ttcPLPPondR5.add(allFish[0][2]);
		
		ttcPLPPondFishMap.put(5, ttcPLPPondR5);
		
		regionFishingPonds.put(ttcPLPPR, ttcPLPPondFishMap);
		
		//TTC Silly Street
		ProtectedRegion ttcSSPR = regions.getRegion("ttc-sillystreet");
		//allPondRegions.add(ttcSSPR);
		Map<Integer, List<TTFish>> ttcSSPondFishMap = new HashMap<>();
		
		List<TTFish> ttcSSPondR3 = new ArrayList<>();
		ttcSSPondR3.add(allFish[0][3]);
		
		ttcSSPondFishMap.put(3, ttcSSPondR3);
		
		regionFishingPonds.put(ttcSSPR, ttcSSPondFishMap);
		
		//---DD---
		
		//---DG---
		
		//---MML---
		
		//---B---
		
		
		//---DDL---
		
		
		//Adding anywhere fish, do this after everything else
		//for (ProtectedRegion pRegion : allPondRegions) {
		for (ProtectedRegion pRegion : regionFishingPonds.keySet()) {
			Map<Integer, List<TTFish>> tempPondRegionsFish = regionFishingPonds.get(pRegion);
			for (int i = 1; i <= 10; i++) {
				if (tempPondRegionsFish.containsKey(i)) {
					List<TTFish> anywhereRF = allAnywhereRarities.get(i-1);
					for (TTFish fish : anywhereRF) {
						int upAmt = 1;
						List<TTFish> tempFish = tempPondRegionsFish.get(i);
						while (tempFish.contains(fish)) {
							tempFish = tempPondRegionsFish.get(i+upAmt);
							upAmt++;
						}
						tempFish.add(fish);
					}
				}
				else {
					List<TTFish> anywhereRF = allAnywhereRarities.get(i-1);
					tempPondRegionsFish.put(i, anywhereRF);
				}	
			}
		}
		
	}
	
	public void loadAllRegions() { //OUTDATED
		World world = Bukkit.getWorld("world");
		com.sk89q.worldedit.world.World wgWorld = BukkitAdapter.adapt(world);
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		RegionManager regions = container.get(wgWorld);
		

		//---TTC---
		//TTC MAIN PLAYGROUND
		List<TTFish> ttcPGFish = new ArrayList<>(anywhereFish);
		
		//ProtectedRegion ttcPG = regions.getRegion("ttc-playground"); --Uncomment once regions are done!!!
		//regionFish.put(ttcPG, ttcPGFish); --Uncomment once regions are done!!!
		
		//TTC LOOPYLANE
		//List<TTFish>
		
		//TTC PUNCHLINEPLACE
		
		
		//TTC SILLYSTREET
		
		
		//---DD---
		
		//---DG---
		
		//---MML---
		
		//---B---
		
		//---DDL---
	}
	
	public void loadAllPondFish() { //OUTDATED
		//Region Fish
		
		//TTC POND
		ttcPond.add(allFish[0][1]);
		ttcPond.add(allFish[0][2]);
		ttcPond.add(allFish[0][4]);
		ttcPond.add(allFish[1][0]);
		ttcPond.add(allFish[1][1]);
		ttcPond.add(allFish[1][2]);
		ttcPond.add(allFish[1][3]);
		ttcPond.add(allFish[3][0]);
		
		//LoopyLane Pond
		loopyLanePond.add(allFish[0][4]);
		
		//Punchline Place
		punchlinePlacePond.add(allFish[0][2]);
		
		//Silly Street
		sillyStreetPond.add(allFish[0][3]);
		
		//Anywhere Fish
		anywhereFish.add(allFish[0][0]); //BF
		anywhereFish.add(allFish[0][1]); //HABF
		anywhereFish.add(allFish[4][0]); //CatF
		anywhereFish.add(allFish[1][0]); //ClownF
		anywhereFish.add(allFish[1][1]); //SClownF
		anywhereFish.add(allFish[1][3]); //PClownF
		anywhereFish.add(allFish[2][0]); //StarF
		anywhereFish.add(allFish[2][1]); //FStarF
		anywhereFish.add(allFish[2][3]); //SStarF
		anywhereFish.add(allFish[2][2]); //RStarF
		anywhereFish.add(allFish[2][4]); //AStarF
		anywhereFish.add(allFish[8][0]); //DF
		anywhereFish.add(allFish[8][4]); //PDF
		anywhereFish.add(allFish[6][0]); //AE
		anywhereFish.add(allFish[7][0]); //NS
		anywhereFish.add(allFish[5][0]); //KC
		anywhereFish.add(allFish[11][0]); //SH
		anywhereFish.add(allFish[11][1]); //RSH
		anywhereFish.add(allFish[12][0]); //PS
		anywhereFish.add(allFish[9][0]); //CTF
		anywhereFish.add(allFish[14][0]); //PT
		anywhereFish.add(allFish[3][0]); //PB&J
		anywhereFish.add(allFish[3][1]); //GPB&J
		anywhereFish.add(allFish[17][0]); //Devil Ray
		anywhereFish.add(allFish[16][0]); //HoleyMack
	}
	
	
}
