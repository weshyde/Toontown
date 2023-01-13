package me.WesBag.Toontown.Trolley.Utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.WesBag.Toontown.Trolley.NewTrolley;

public class NewTrolleyCountdown extends BukkitRunnable {
	
	private List<Player> players = new ArrayList<>();
	private NewTrolley trolley;
	private int time;

	public NewTrolleyCountdown(NewTrolley trolley, UUID playerOne) {
		this.trolley = trolley;
		this.players.add(Bukkit.getPlayer(playerOne));
		this.time = 9;
	}
	
	@Override
	public void run() {
		if (time <= 0) {
			cancel();
			this.trolley.start();;
			return;
		}
		
		for (Player p : players) {
			p.sendMessage(GamePrefixes.Trolley + " " + time + " seconds until starting");
		}
		time--;
	}
	
	public void addPlayer(Player p) {
		players.add(p);
	}
}
