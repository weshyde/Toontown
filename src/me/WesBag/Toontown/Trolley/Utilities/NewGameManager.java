package me.WesBag.Toontown.Trolley.Utilities;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.WesBag.Toontown.Main;

public class NewGameManager {
	
	public static List<NewGameManager> allGameManagers = new ArrayList<>();
	public static final String[] allGameNames = {"Cannon-Game", "Ice-Slide", "Color-Match", "Catching-Game"};
	
	private GameState gameState;
	private NewRewardManager rewardManager;
	private NewCountdownClock countdownClock;
	private int gameChoosen;
	private int playground;
	private Location arena = null;
	private List<UUID> players = new ArrayList<>();
	private List<UUID> playersDone = new ArrayList<>();
	private Map<UUID, Location> playerSpawns = new HashMap<>();
	
	private final Main main;
	
	public NewGameManager(Main main, List<UUID> players, int playground) {
		this.main = main;
		allGameManagers.add(this);
		this.rewardManager = new NewRewardManager(this);
		this.players.addAll(players);
		this.playground = playground;
		
		int timesRan = 0; //Infinite loop proofing
		while (arena == null) {
			Random r = new Random();
			int gameChoosen = r.nextInt(allGameNames.length - 0);
			
			Method loadArenaMethod = null;
			try {
				Class<?> gameClass = Class.forName("me.WesBag.Toontown.Trolley.Minigames." + allGameNames[gameChoosen].replace("-", "") + "." + allGameNames[gameChoosen].replace("-", ""));
				loadArenaMethod = gameClass.getMethod("loadArena", NewGameManager.class);
				arena = (Location) loadArenaMethod.invoke(gameClass.getDeclaredConstructor().newInstance(), this);
			} catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | ClassNotFoundException | InstantiationException e) {
				e.printStackTrace();
			}
			
			timesRan++;
			if (timesRan > 10) {
				System.err.println("NewGameManager ran into an infinite loop!");
				break;
			}
		}
		
		this.setGameState(GameState.LOBBY);
	}
	
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
		
		switch(gameState) {
		case LOBBY:
			sendMessage(GamePrefixes.Trolley + " You're playing " + allGameNames[gameChoosen]);
			this.setGameState(GameState.READY);
			break;
		case READY:
			//Show tutorials
			this.setGameState(GameState.STARTING);
			break;
		case STARTING:
			//Start game countdown
			this.countdownClock = new NewCountdownClock(this, 5, GameState.RUNNING);
			this.countdownClock.runTaskTimer(main, 0, 20);
			break;
		case RUNNING:
			if (this.countdownClock != null) this.countdownClock.cancel();
			
			NewMinigameClock gameClock = new NewMinigameClock(this);
			gameClock.runTaskTimer(main, 20, 20);
			this.rewardManager.passClock(gameClock);
			break;
		case REWARD:
			this.rewardManager.giveRewards();
			break;
		case FINISH:
			break;
		default:
			break;
		}
	}
	
	public void playerDone(UUID uuid) {
		playersDone.add(uuid);
		if (playersDone.size() == players.size()) {
			setGameState(GameState.REWARD);
		}
	}
	
	public void sendMessage(String msg) {
		for (UUID player : players) {
			Bukkit.getPlayer(player).sendMessage(msg);
		}
	}
	
	public NewRewardManager getRewardManager() {
		return this.rewardManager;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
}
