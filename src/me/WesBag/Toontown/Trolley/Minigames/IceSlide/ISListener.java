package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Boat;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.util.Vector;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;
import me.WesBag.Toontown.Trolley.Utilities.RewardManager;

public class ISListener implements Listener {
	
	private GameManager gameManager;
	private RewardManager rewardManager;
	private Map<UUID, Vector> playerSelectedLaunches = new HashMap<>();
	private Map<UUID, Integer> playersLaunchSpeed = new HashMap<>();
	private List<UUID> playersDoneSelecting = new ArrayList<>();
	public ISListener(GameManager gameManager, RewardManager rewardManager) {
		this.gameManager = gameManager;
		this.rewardManager = rewardManager;
	}
	
	public ISListener() {
		
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if (!IceSlide.playerInGame(e.getPlayer().getUniqueId())) return;
		if (e.getItem().getItemMeta() == null) return;
		
		
		if (e.getItem().getItemMeta().getDisplayName().contains("Launch") && e.getItem().getType() == Material.ICE) {
			Player p = e.getPlayer();
			
			if (playersDoneSelecting.contains(p.getUniqueId())) return;
			
			Vector v = p.getLocation().getDirection();
			
			if (playerSelectedLaunches.get(p.getUniqueId()) == null || playerSelectedLaunches.get(p.getUniqueId()) != v || playersLaunchSpeed.get(p.getUniqueId()) != (p.getInventory().getHeldItemSlot() + 1)) {
				p.sendMessage(GamePrefixes.Trolley + " Right click again to lock in your direction and speed!");
			}
			else {
				p.sendMessage(GamePrefixes.Trolley + " Locked in!");
				playersDoneSelecting.add(p.getUniqueId());
			}
			playerSelectedLaunches.put(p.getUniqueId(), v);
			playersLaunchSpeed.put(p.getUniqueId(), p.getInventory().getHeldItemSlot() + 1);
			//for (int i = 1; i < 9; i+= 1) {
			//	String strNum = Integer.toString(i);
			//	if (e.getItem().getItemMeta().getDisplayName().contains(strNum)) {
			//		playersLaunchSpeed.put(p.getUniqueId(), i);
			//	}
			//}
		}
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(IceSlide.playerInGame(e.getPlayer().getUniqueId()))) return;
		
		//GameManager tempGM = GameManager.getGameManager(e.getPlayer().getUniqueId());
		//if (tempGM == null) return;
		
		//if (tempGM.getState() == GameState.STARTING || tempGM.getState() == GameState.LOBBY) {
			//e.setCancelled(true);
			//return;
		//}
		
		//if ()e.getTo()
	}
	
	@EventHandler
	public void onPlayerBoatExit(VehicleExitEvent e) {
		if (!(IceSlide.playerInGame(e.getExited().getUniqueId()))) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBoatBreak(VehicleDamageEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(IceSlide.playerInGame(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		//if (e.getVehicle().getPassengers() == null) return;
		
		if (e.getVehicle() instanceof Boat)
			e.setCancelled(true);
	}
	
	@EventHandler //Commented 1/22/22 5:31pm temporarily
	public void onBoat2BoatCollision(VehicleEntityCollisionEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(IceSlide.playerInGame(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		//if (e.getVehicle().getPassengers() == null) return;
		
	}
	
	@EventHandler
	public void onBoat2BlockCollision(VehicleBlockCollisionEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(IceSlide.playerInGame(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		//if (e.getVehicle().getPassengers() == null) return;
		
	}
	
	/*
	public boolean checkPlayersDone() {
		if (playersDoneSelecting.size() == gameManager.getPlayers().size())
			return true;
		return false;
	}
	*/
	public int playersDoneSize() {
		return playersDoneSelecting.size();
	}
	
	public void launchPlayers() {
		/*
		if (playersDoneSelecting.size() != gameManager.getPlayers().size()) {
			for (UUID pUUID : gameManager.getPlayers())
				if (!(playersDoneSelecting.contains(pUUID))) {
					playerSelectedLaunches.put(pUUID, Bukkit.getPlayer(pUUID).getLocation().getDirection());
					playersLaunchSpeed.put(pUUID, 0);
				}
		}
		*/
		for (UUID pUUID : gameManager.getPlayers()) {
			Player p = Bukkit.getPlayer(pUUID);
			if (playersDoneSelecting.contains(pUUID)) {
				p.getVehicle().setVelocity(playerSelectedLaunches.get(pUUID).multiply(playersLaunchSpeed.get(pUUID)));
				//p.setVelocity(playerSelectedLaunches.get(pUUID).multiply(playersLaunchSpeed.get(pUUID)));
				p.sendMessage(GamePrefixes.Trolley + " Launched!");
			}
			
			else {
				p.sendMessage(GamePrefixes.Trolley + " You didn't select a launch course!");
			}
			
		}
		playersDoneSelecting.clear();
		playerSelectedLaunches.clear();
		playersLaunchSpeed.clear();
	}
}
