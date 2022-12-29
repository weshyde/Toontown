package me.WesBag.Toontown.Trolley.Utilities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;

public class NewRewardManager {
	
	private NewGameManager gameManager;
	private NewMinigameClock gameClock;
	private Map<UUID, Integer> playersWinnings = new HashMap<>();
	
	public NewRewardManager(NewGameManager gameManager) {
		this.gameManager = gameManager;
	}
	
	public void addReward(UUID uuid, int winnings) {
		playersWinnings.put(uuid, winnings);
	}
	
	public void addTimeReward(UUID uuid) {
		addReward(uuid, gameClock.getTimeLeft());
	}
	
	public void giveRewards() {
		if (this.gameClock != null) this.gameClock.cancel();
		
		NewCountdownClock countdownClock = new NewCountdownClock(gameManager, 7, GameState.FINISH);
		countdownClock.runTaskTimer(Main.getInstance(), 0, 20);
		for (UUID uuid : playersWinnings.keySet()) {
			Player p = Bukkit.getPlayer(uuid);
			p.sendMessage("Congratulations! You won " + getReward(uuid) + " jellybeans");
			//TODO Add money to player's bank
		}
	}
	
	public int getReward(UUID uuid) {
		if (playersWinnings.containsKey(uuid)) {
			return playersWinnings.get(uuid);
		}
		else {
			return 0;
		}
	}
	
	public void passClock(NewMinigameClock gameClock) {
		this.gameClock = gameClock;
	}
}
