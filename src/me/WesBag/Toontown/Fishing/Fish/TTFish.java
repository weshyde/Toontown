package me.WesBag.Toontown.Fishing.Fish;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

//import io.netty.util.internal.ThreadLocalRandom;

public class TTFish {
	private String name;
	private String species;
	private int fishNum;
	private int speciesNum;
	//private int rarity;
	private int rodNeeded;
	private int minWeight;
	private int maxWeight;
	//private int minBeans;
	private int maxBeans;
	
	
	public TTFish(String fishSpecies, String fishName, int fishNum, int speciesNum) {
		this.name = fishName;
		this.species = fishSpecies;
		this.fishNum = fishNum;
		this.speciesNum = speciesNum;
		loadFishInfo();
	}
	
	public void loadFishInfo() {
		try {
			String editedName = name;
			String editedSpecies = species;
			Class<?> fishClass = Class.forName("me.WesBag.Toontown.Fishing.Fish." + editedSpecies.replace(" ", "") + "." + editedName.replace(" ", ""));
			//this.rarity = (int) fishClass.getField("rarity").getInt(fishClass);
			//this.rodNeeded = (int) fishClass.getField("rod").getInt(fishClass);
			//this.minBeans = (int) fishClass.getField("minBeans").getInt(fishClass);
			this.maxBeans = fishClass.getField("maxBeans").getInt(fishClass);
			this.minWeight = fishClass.getField("minWeight").getInt(fishClass);
			this.maxWeight = fishClass.getField("maxWeight").getInt(fishClass);
			this.rodNeeded = fishClass.getField("rodNeeded").getInt(fishClass);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//public int getRarity() {
	//	return rarity;
	//}
	//public int getRodNeeded() {
	//	return rodNeeded;
	//}
	//public int getBeans(int rodOwned) {
	//	Random r = new Random();
	//	int beans = r.nextInt((maxBeans+1) - minBeans) + minBeans;
	//	return beans;
	//}
	public String getName() {
		return name;
	}
	public int getWeight(int rodWeightLimit) {
		Random r = new Random();
		rodWeightLimit++;
		System.out.println("GetWeight: rodWeightLimit: " + (rodWeightLimit));
		System.out.println("MinWeight: " + minWeight);
		System.out.println("MaxWeight: " + maxWeight);
		System.out.println("NextInt: " + (rodWeightLimit - minWeight));
		//int weight = r.nextInt((rodWeightLimit) - minWeight) + minWeight;
		//int weight = r.nextInt(minWeight, rodWeightLimit);
		int weight = ThreadLocalRandom.current().nextInt(minWeight, rodWeightLimit);
		if (weight > maxWeight) weight = maxWeight;
		return weight;
	}
	
	public int getBeans2(int fishWeight, int rarity) {
		int value = (int) Math.ceil(15*((Math.pow((0.2*rarity), 1.5) + Math.pow(0.05*fishWeight, 1.1))));
		if (value > maxBeans) value = maxBeans;
		return value;
	}
	
	public int getFishNum() {
		return fishNum;
	}
	public int getFishSpecies() {
		return speciesNum;
	}
	public int getRodNeeded() {
		return rodNeeded;
	}
}
