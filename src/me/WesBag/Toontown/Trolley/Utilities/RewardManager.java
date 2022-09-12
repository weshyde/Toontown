package me.WesBag.Toontown.Trolley.Utilities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.Trolley.Minigames.MinigameTimer;

public class RewardManager {
	private GameManager gameManager;
	private MinigameTimer gameTimer;
	private Map<UUID, Integer> playerWinnings = new HashMap<UUID, Integer>();

	
	public RewardManager(GameManager gameManager, List<UUID> players) {
		this.gameManager = gameManager;
		for (UUID pUUID : players) {
			playerWinnings.put(pUUID, 0);
		}
	}
	
	public void passTimer(MinigameTimer timer) {
		this.gameTimer = timer;
	}
	
	public void addReward(UUID pUUID, int winnings) {
		playerWinnings.put(pUUID, winnings);
	}
	
	public void giveTimeReward(UUID pUUID) {
		int reward = gameTimer.getTimeLeft();
		addReward(pUUID, reward);
	}
	
	public void giveRewards() {
		if (gameTimer != null) gameTimer.cancel();
		BasicCountdown timer = new BasicCountdown(gameManager, 7, GameState.FINISH, gameManager.getPlayers());
		//GameCountdown timer = new GameCountdown(gameManager, 7, " left!", GameState.FINISH);
		timer.runTaskTimer(Main.getInstance(), 0, 20);
		for (UUID pUUID : playerWinnings.keySet()) {
			Player p = Bukkit.getPlayer(pUUID);
			int amountWon = playerWinnings.get(pUUID);
			p.sendMessage("Congratulations, you won " + amountWon + " jellybeans!");
			//TODO ADD MONEY TO PLAYER'S ECO
		}
	}
	
	public int getReward(UUID pUUID) {
		if (playerWinnings.containsKey(pUUID))
			return playerWinnings.get(pUUID);
		return 0;
	}
}
