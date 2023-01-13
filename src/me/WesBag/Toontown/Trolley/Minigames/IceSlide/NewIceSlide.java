package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Trolley.Utilities.GameInventorys;
import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;
import net.md_5.bungee.api.ChatColor;
import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Minigames.MinigameInterface;

public class NewIceSlide implements MinigameInterface {
	
	//Arena Locations stored as: 0=Player1 Location, 1=Center Location, 2=Corner1 Obstacle Location, 3=Opposite Corner2 Obstacle Location
	public static List<List<Location>> arenas = new ArrayList<>();
	public static Map<Integer, Boolean> freeArenas = new HashMap<>();
	public static List<UUID> playersIn = new ArrayList<>();
	
	private GameManager gameManager;
	private int arenaIndex;
	
	private List<Location> obstacleLocations = new ArrayList<>();
	private List<UUID> playersDoneSelecting = new ArrayList<>();
	private List<Boat> boats = new ArrayList<>();
	private IceSlideClock roundClock;
	private IceSlideTimer slideTimer;
	private int roundNum;
	
	public NewIceSlide(GameManager gameManager) {
		this.gameManager = gameManager;
		this.roundNum = 1;
		loadArena();
	}
	
	@Override
	public void start() {
		roundClock = new IceSlideClock(this);
		roundClock.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	@Override
	public void loadArena() {
		List<Location> arenaLocations = findArena();
		if (this.arenaIndex == -1) return;
		
		Location playerLocation = arenaLocations.get(0).clone();
		
		for (UUID uuid : gameManager.getPlayers()) {
			playersIn.add(uuid);
			Player p = Bukkit.getPlayer(uuid);
			p.teleport(playerLocation);
			p.getInventory().clear();
			for (int i = 1; i < 10; i++) {
				p.getInventory().setItem((i-1), GameInventorys.createGuiItem(Material.ICE, ChatColor.RED + "Launch " + i, i));
			}
			Boat boat = (Boat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BOAT);
			boats.add(boat);
			boat.setPassenger(p);
		}
		
		spawnObstacles();
	}
	
	@Override
	public void unloadArena() {
		
		for (Boat b : boats) {
			b.remove();
		}
	}
	
	@Override
	public void sendMessage(String msg) {
		for (UUID uuid : playersIn) {
			Bukkit.getPlayer(uuid).sendMessage(msg);
		}
	}
	
	public void nextRound() {
		if (roundNum >= 6) { //End of game
			gameManager.setGameState(GameState.REWARD);
			return;
		}
		if (roundNum % 2 == 0) {
			calcClosest();
			for (Boat b : boats) {
				b.remove();
			}
			boats.clear();
			for (UUID uuid : playersIn) {
				Player p = Bukkit.getPlayer(uuid);
				gameManager.resetPlayer(uuid);
				Boat boat = (Boat) p.getWorld().spawnEntity(p.getLocation(), EntityType.BOAT);
				boats.add(boat);
				boat.addPassenger(p);
			}
			spawnObstacles();
		}
		for (UUID uuid : playersIn) {
			IceSlideListener.clearPlayer(uuid);
		}
		playersDoneSelecting.clear();
		setupClock();
		roundNum++;
	}
	
	public void doneSelecting(UUID uuid) { //THIS OR ROUND CLOCK END
		playersDoneSelecting.add(uuid);
		if (playersDoneSelecting.size() == playersIn.size()) { //Everyone selected
			if (roundClock != null) roundClock.cancel();
			launchPlayers();
		}
	}
	
	public void launchPlayers() {
		for (UUID uuid : playersIn) {
			Player p = Bukkit.getPlayer(uuid);
			if (IceSlideListener.playerChoose(uuid)) {
				p.getVehicle().setVelocity(IceSlideListener.getPlayer(uuid).multiply(10));
			}
			else {
				p.sendMessage(GamePrefixes.Trolley + " You didnt lock in your choice in time");
			}
		}
		this.slideTimer = new IceSlideTimer(this);
		this.slideTimer.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	public void calcClosest() {
		Location center = arenas.get(arenaIndex).get(1);
		
		for (UUID uuid : playersIn) {
			double distance = Bukkit.getPlayer(uuid).getLocation().distance(center);
			if (distance < 1) distance = 1;
			int reward = (int) Math.round(8 / distance);
			gameManager.getRewardManager().addReward(uuid, reward);
			Bukkit.getPlayer(uuid).sendMessage(GamePrefixes.Trolley + " Added " + reward + " jellybeans");
		}
	}
	
	private void setupClock() {
		if (roundClock != null) roundClock.cancel();
		
		roundClock = new IceSlideClock(this);
		roundClock.runTaskTimer(Main.getInstance(), 0, 20);
	}
	
	private void spawnObstacles() {
		
		if (!obstacleLocations.isEmpty()) {
			for (Location l : obstacleLocations) {
				l.getBlock().setType(Material.AIR);
			}
			obstacleLocations.clear();
		}
		
		List<Location> arenaLocations = arenas.get(arenaIndex);
		
		int maxObstacles = gameManager.getPlayground()/2 + 1;
		int minObstacles = (gameManager.getPlayground()/2) + 1; //TEMP ADDED +1 for TESTING 1-1-23
		int obstacles = ThreadLocalRandom.current().nextInt(minObstacles, maxObstacles + 1);
		
		for (int i = 0; i < obstacles; i++) {
			obstacleLocations.add(randomLocationBetweenBounds(arenaLocations.get(2), arenaLocations.get(3)));
		}
		
		for (Location l : obstacleLocations) {
			l.getBlock().setType(Material.BLACK_CONCRETE);
		}
	}
	
	private Location randomLocationBetweenBounds(Location loc1, Location loc2) {
		double minX = Math.min(loc1.getX(), loc2.getX());
        double minZ = Math.min(loc1.getZ(), loc2.getZ());

        double maxX = Math.max(loc1.getX(), loc2.getX());
        double maxZ = Math.max(loc1.getZ(), loc2.getZ());

        return new Location(loc1.getWorld(), randomDouble(minX, maxX), loc1.getY(), randomDouble(minZ, maxZ));
	}
	
	private double randomDouble(double min, double max) {
		return min + ThreadLocalRandom.current().nextDouble(Math.abs(max - min + 1));
	}
	
	private List<Location> findArena() {
		List<Location> arenaLocations = null;
		int index = 0;
		while (index < freeArenas.size()) { //Loop until free arena is found OR reaches end of arenas without finding one
			if (freeArenas.get(index) == false) {
				arenaLocations = arenas.get(index);
				freeArenas.put(index, true);
				break;
			}
			else {
				index++;
			}
		}
		if (arenaLocations == null) {
			this.arenaIndex = -1;
		}
		else {
			this.arenaIndex = index;
		}
		return arenaLocations;
	}
	
	@Override
	public Location getArena() {
		if (this.arenaIndex > 0) {
			return arenas.get(arenaIndex).get(0);
		}
		else {
			return null;
		}
	}
	
	public static void freeArena(int index) {
		freeArenas.put(index, false);
	}
	
	public static boolean isPlaying(UUID uuid) {
		if (playersIn.contains(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}
}
