package me.WesBag.Toontown.Trolley.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.Trolley;

public class TrolleyCountdown extends BukkitRunnable {
	
	private GameManager gameManager;
	private Trolley trolley;
	private List<Player> playersToMessage = new ArrayList<>();
	//private String gameName = "";
	private int time;
	
	public TrolleyCountdown(GameManager gameManager, Trolley trolley, int time, List<UUID> players) {
		this.gameManager = gameManager;
		this.trolley = trolley;
		this.time = time;
		
		for (UUID pUUID : players) {
			this.playersToMessage.add(Bukkit.getPlayer(pUUID));
		}
		
		if (time < 1)
			time = 1;
	}
	
	
	public void setTime(int time) {
		if (time < 1)
			time = 1;
		this.time = time;
	}
	
	@Override
	public void run() {
		time--;
		if (time <= 0) {
			cancel();
			//gameManager.setGameState(newGameState);
			this.trolley.startGames();
			return;
		}
		for (Player p : playersToMessage)
			p.sendMessage(GamePrefixes.Trolley + " " + time + " until start!");
	}

}
