package me.WesBag.Toontown.Trolley.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.Listener;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Trolley;
import me.WesBag.Toontown.Trolley.Minigames.MinigameTimer;

public class GameManager {
	
	public static List<Class<?>> allGames = new ArrayList<Class<?>>();
	public static String[] allGameNames = {"Cannon-Game", "Ice-Slide"}; //Add as minigames are added
	public static List<GameManager> allGMs = new ArrayList<>();
	private RewardManager rewardManager;
	private GameListener gameListener; //Delete maybe?
	private Listener gameListener2;
	private GameState gameState;
	private String gameName;
	private Trolley trolley;
	private Location arena;
	private Class<?> gameClass;
	private BasicCountdown countdown;
	private MinigameTimer gameTimer;
	private List<UUID> playersIn = new ArrayList<UUID>();
	//private List<Integer> playerPoints = new ArrayList<Integer>();
	private List<UUID> playersDone = new ArrayList<UUID>();
	private Map<UUID, Location> playerSpawns = new HashMap<>();
	private final Main main;
	
	public GameManager(Main main, Trolley trolley, List<UUID> players) {
		int low = 1; //SUPPOSED TO BE 0, CHANGED TO 1 to DEBUG
		int high = 2; //Increase as minigames are added
		allGMs.add(this);
		//int conditionType = 0;
		//int gamesTried = 0;
		
		playersIn.addAll(players);
		
		this.arena = null;
		int timesRan = 0;
		while (this.arena == null) {
			Random r = new Random();
			int result = r.nextInt(high - low) + low;
		
			this.gameName = allGameNames[result].replace("-", " ");
			this.gameClass = allGames.get(result);
			this.rewardManager = new RewardManager(this, playersIn);
			//Class<?> testClass = Class.forName("me.WesBag.Toontown.Trolley.Minigames." + allGameNames[result].replace("-", "") + "." + allGameNames[result].replace(" ", ""));
			Method loadArenaMethod = null;
			try {
				Class<?> testClass = Class.forName("me.WesBag.Toontown.Trolley.Minigames." + allGameNames[result].replace("-", "") + "." + allGameNames[result].replace("-", ""));
				loadArenaMethod = testClass.getMethod("loadArena", GameManager.class, RewardManager.class, List.class);
				//conditionType = gameClass.getField("earnCondition").getInt(gameClass.getDeclaredConstructor().newInstance());
				//testClass object = new testClass();
				arena = (Location) loadArenaMethod.invoke(testClass.getDeclaredConstructor().newInstance(), this, this.rewardManager, this.playersIn);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
				e.printStackTrace();
			}
			//gamesTried++;
			//if (gamesTried == high)
			timesRan++;
			if (timesRan > 4)
				break;
		}
		
		for (UUID pUUID : playersIn) {
			playerSpawns.put(pUUID, Bukkit.getPlayer(pUUID).getLocation());
		}
		
		this.main = main;
		this.trolley = trolley;
		this.setGameState(GameState.LOBBY);
		/*
		switch(conditionType) {
		
		case 1: //Earn points by reaching set area (Material)
			Material[] earnBlocks = null;
			try {
				earnBlocks = (Material[]) gameClass.getField("earnBlocks").get(gameClass);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			this.gameListener = new GameListener(this, conditionType, earnBlocks);
			break;
		
		case 2:
			earnBlocks = null;
			try {
				earnBlocks = (Material[]) gameClass.getField("earnBlocks").get(gameClass);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			this.gameListener = new GameListener(this, conditionType, earnBlocks);
			break;
			
		case 3: //Earn points by collecting items
			Item[] earnItems = null;
			try {
				earnItems = (Item[]) gameClass.getField("earnItems").get(gameClass);
			} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e) {
				e.printStackTrace();
			}
			this.gameListener = new GameListener(this, conditionType, earnItems);
			break;
			
		case 4: //Earn points by meeting specific conditions
			break;
		
		}
		*/
		//this.setGameState(GameState.READY);
	}
	
	public static GameManager getGameManager(UUID pUUID) {
		for (GameManager GM : allGMs) {
			if (GM.getPlayers().contains(pUUID))
				return GM;
		}
		return null;
	}
	
	public RewardManager getRewardManager() {
		return this.rewardManager;
	}
	
	public void resetPlayerLocation(UUID pUUID) {
		if (playerSpawns.containsKey(pUUID))
			Bukkit.getPlayer(pUUID).teleport(playerSpawns.get(pUUID));
	}
	
	
	
	public void setGameState(GameState gameState) {
		if (this.gameState == GameState.RUNNING && gameState == GameState.STARTING) return;
		
		this.gameState = gameState;
		
		switch(gameState) {
		
		case LOBBY:
			Bukkit.broadcastMessage(GamePrefixes.Trolley + " You're playing " + gameName + "!");
			this.setGameState(GameState.READY);
			break;
		
		case READY:
			//this.rewardManager = new RewardManager(this, playersIn);
			//SHOW TUTORIALS?
			this.setGameState(GameState.STARTING);
			break;
			
		case STARTING:
			Bukkit.broadcastMessage("Starting!");
				
			this.countdown = new BasicCountdown(this, 5, GameState.RUNNING, playersIn);
			this.countdown.runTaskTimer(main, 0, 20);
			break;
			
		case RUNNING:
			Bukkit.broadcastMessage("Running!");
			if (this.countdown != null) this.countdown.cancel();
			this.gameTimer = new MinigameTimer(this, playersIn);
			this.rewardManager.passTimer(gameTimer);
			//this.rewardManager = new RewardManager(this, gameTimer, playersIn);
			this.gameTimer.runTaskTimer(main, 20, 20);
			break;
		
		case REWARD:
			this.rewardManager.giveRewards();
			Bukkit.broadcastMessage("Reward time!");
			break;
		
		case FINISH:
			this.rewardManager = null;
			this.gameListener = null;
			this.gameName = null;
			this.trolley.nextGameGUI();
			break;
			
		default:
			Bukkit.getLogger().log(Level.SEVERE ,"GAME STATE WRONG!");
			break;
				
		
		}
	}
	
	public List<UUID> getPlayers() {
		return playersIn;
	}
	
	public void playerDone(UUID pUUID) {
		playersDone.add(pUUID);
		if (playersDone.size() == playersIn.size()) {
			this.setGameState(GameState.REWARD);
		}
	}
	
	public GameState getState() {
		return gameState;
	}

}
