package me.WesBag.Toontown.Trolley.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Streets.StreetBattlePositioning;
import me.WesBag.Toontown.Trolley.NewTrolley;
import me.WesBag.Toontown.Trolley.Trolley;
import me.WesBag.Toontown.Trolley.Minigames.Minigame;
import me.WesBag.Toontown.Trolley.Minigames.MinigameInterface;
import me.WesBag.Toontown.Trolley.Minigames.CannonGame.CannonGame;
import me.WesBag.Toontown.Trolley.Minigames.IceSlide.NewIceSlide;
import me.WesBag.Toontown.Trolley.Minigames.TagGame.ToonTag;

public class GameManager {
	
	public static List<GameManager> allGameManagers = new ArrayList<>();
	public static final String[] allGameNames = {"Cannon-Game", "Ice-Slide", "Toon-Tag", "Catching-Game"};
	
	private NewTrolley trolley;
	private GameState gameState;
	private NewRewardManager rewardManager;
	private NewCountdownClock countdownClock;
	private int gameChoosen;
	private Minigame gameClass;
	private MinigameInterface gameClass2;
	private int playground; //Playground/Difficulty
	private Location arena = null;
	private List<UUID> players = new ArrayList<>();
	private List<UUID> playersDone = new ArrayList<>();
	private Map<UUID, Location> playerSpawns = new HashMap<>();
	
	private final Main main;
	
	public GameManager(Main main, NewTrolley trolley, List<UUID> players, int playground) {
		this.main = main;
		this.trolley = trolley;
		allGameManagers.add(this);
		this.rewardManager = new NewRewardManager(this);
		this.players.addAll(players);
		this.playground = playground;
		
		Random r = new Random();
		
		while(arena == null) {
		
			//gameChoosen = r.nextInt(2); //Make single player games lower, multiplayer games higher. Add in fix to not allow multiplayer game to be choosen with only one player 1-1-23
			gameChoosen = 2;
			System.out.println("Minigame choosen = " + gameChoosen);
			if (gameChoosen == 0) { //Cannon Game
				gameClass2 = new CannonGame(this);
				//this.arena = ((CannonGame) gameClass).getArena();
				this.arena = gameClass2.getArena();
			}
			else if (gameChoosen == 1) {
				gameClass2 = new NewIceSlide(this);
				this.arena = gameClass2.getArena();
			}
			else if (gameChoosen == 2) {
				gameClass2 = new ToonTag(this);
				this.arena = gameClass2.getArena();
			}
			break; //TEMPORARY
		}
		
		for (UUID uuid : players) {
			playerSpawns.put(uuid, Bukkit.getPlayer(uuid).getLocation());
		}
		
		/*
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
		*/
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
			gameClass2.start();
			//NewMinigameClock gameClock = new NewMinigameClock(this); Moved to individual minigames because of round based games
			//gameClock.runTaskTimer(main, 20, 20);
			//this.rewardManager.passClock(gameClock);
			break;
		case REWARD:
			this.rewardManager.giveRewards();
			this.gameClass2.unloadArena();
			/*
			switch(gameChoosen) {
			case 0:
				((CannonGame) this.gameClass).unloadArena();
				break;
			case 1:
				break;
			}
			break;
			*/
		case FINISH:
			this.rewardManager = null;
			this.gameClass2 = null;
			System.out.println("Game finished!");
			this.trolley.nextGameGUI();
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
	
	public static GameManager getGameManager(UUID uuid) {
		for (GameManager gm : allGameManagers) {
			if (gm.getPlayers().contains(uuid)) {
				return gm;
			}
		}
		return null;
	}
	
	public NewRewardManager getRewardManager() {
		return this.rewardManager;
	}
	
	public void passClock(NewMinigameClock clock) {
		this.rewardManager.passClock(clock);
	}
	
	public MinigameInterface getGame() {
		return gameClass2;
	}
	
	public GameState getGameState() {
		return gameState;
	}
	
	public List<UUID> getPlayers() {
		return players;
	}
	
	public int getPlayground() {
		return this.playground;
	}
	
	public void resetPlayer(UUID uuid) {
		Bukkit.getPlayer(uuid).teleport(playerSpawns.get(uuid));
	}
	
	public void resetPlayerVehicle(UUID uuid) {
		Bukkit.getPlayer(uuid).getVehicle().teleport(playerSpawns.get(uuid));
	}
	
	public static void loadMinigameArenas() {
		int index = 1;
		while (Main.fDataFile.getData().contains("minigames.cannongame." + index)) {
			Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + index + ".1"));
			Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.cannongame." + index + ".2"));
			List<Location> ls = new ArrayList<>();
			ls.add(l1);
			ls.add(l2);
			CannonGame.arenas.add(ls);
			CannonGame.freeArenas.put((index-1), false);
			index++;
		}
		index = 1;
		while (Main.fDataFile.getData().contains("minigames.iceslide." + index)) {
			Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + index + ".1"));
			Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + index + ".2"));
			Location l3 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + index + ".3"));
			Location l4 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.iceslide." + index + ".4"));
			List<Location> ls = new ArrayList<>();
			ls.add(l1);
			ls.add(l2);
			ls.add(l3);
			ls.add(l4);
			NewIceSlide.arenas.add(ls);
			NewIceSlide.freeArenas.put((index-1), false);
			index++;
		}
		index = 1;
		while (Main.fDataFile.getData().contains("minigames.toontag." + index)) {
			Location l1 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + index + ".1"));
			Location l2 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + index + ".2"));
			Location l3 = StreetBattlePositioning.stringToLocation(Main.fDataFile.getData().getString("minigames.toontag." + index + ".3"));
			List<Location> ls = new ArrayList<>();
			ls.add(l1);
			ls.add(l2);
			ls.add(l3);
			ToonTag.arenas.add(ls);
			ToonTag.freeArenas.put((index-1), false);
			index++;
		}
		//Add more eventually
	}
}
