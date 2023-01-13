package me.WesBag.Toontown.Trolley.Minigames.IceSlide;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.vehicle.VehicleBlockCollisionEvent;
import org.bukkit.event.vehicle.VehicleDamageEvent;
import org.bukkit.event.vehicle.VehicleEntityCollisionEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.event.vehicle.VehicleMoveEvent;
import org.bukkit.util.Vector;

import me.WesBag.Toontown.Trolley.Utilities.GameManager;
import me.WesBag.Toontown.Trolley.Utilities.GamePrefixes;
import me.WesBag.Toontown.Trolley.Utilities.GameState;

public class IceSlideListener implements Listener {
	
	public static Map<UUID, Vector> playerDirections = new HashMap<>();
	public static Map<UUID, Integer> playerLaunchSpeeds = new HashMap<>();
	
	public IceSlideListener() {
		
	}
	
	@EventHandler
	public void onPlayerRightClick(PlayerInteractEvent e) {
		if (!(NewIceSlide.isPlaying(e.getPlayer().getUniqueId()))) return;
		if (e.getItem().getItemMeta() == null) return;
		if (GameManager.getGameManager(e.getPlayer().getUniqueId()).getGameState() != GameState.RUNNING) return;
		
		final String str = e.getItem().getItemMeta().getDisplayName();
		if (str.contains("Launch") && e.getItem().getType() == Material.ICE) {
			Player p = e.getPlayer();
			
			if (playerLaunchSpeeds.containsKey(p.getUniqueId())) return;
			
			Vector v = p.getLocation().getDirection();
			
			System.out.println("Condition 1 = " + (!(playerDirections.containsKey(p.getUniqueId()))));
			System.out.println("Condition 2 = " + ((playerDirections.get(p.getUniqueId()) != v)));
			System.out.println("Condition 3 = " + (!(playerLaunchSpeeds.containsKey(p.getUniqueId()))));
			
			//if ((!(playerDirections.containsKey(p.getUniqueId()))) || ((playerDirections.get(p.getUniqueId()) != v) && (!(playerLaunchSpeeds.containsKey(p.getUniqueId()))))) {
			if (!(playerDirections.containsKey(p.getUniqueId()))) {
				p.sendMessage(GamePrefixes.Trolley + " Right click again to lock in your choice");
				playerDirections.put(p.getUniqueId(), v);
			}
			else {
				playerLaunchSpeeds.put(p.getUniqueId(), p.getInventory().getHeldItemSlot() + 1);
				((NewIceSlide)GameManager.getGameManager(e.getPlayer().getUniqueId()).getGame()).doneSelecting(p.getUniqueId());
				p.sendMessage(GamePrefixes.Trolley + " Locked in");
			}
		}
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(NewIceSlide.isPlaying(e.getPlayer().getUniqueId()))) return;
		
		GameManager gameManager = GameManager.getGameManager(e.getPlayer().getUniqueId());
		
		if (gameManager.getGameState() != GameState.RUNNING) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerExitBoat(VehicleExitEvent e) {
		if (!(NewIceSlide.isPlaying(e.getExited().getUniqueId()))) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBoatBreak(VehicleDamageEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(NewIceSlide.isPlaying(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onBoatBlockCollision(VehicleBlockCollisionEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(NewIceSlide.isPlaying(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		
		if (e.getBlock().getType() != Material.BLACK_CONCRETE) return;
		
		e.getVehicle().getPassengers().get(0).sendMessage(GamePrefixes.Trolley + " Boom!");
		
		e.getVehicle().getVelocity().multiply(-1);
	}
	
	@EventHandler
	public void onBoatBoatCollision(VehicleEntityCollisionEvent e) {
		if (e.getVehicle().getPassengers().isEmpty()) return;
		if (!(NewIceSlide.isPlaying(e.getVehicle().getPassengers().get(0).getUniqueId()))) return;
		
		e.getVehicle().getPassengers().get(0).sendMessage(GamePrefixes.Trolley + " Smashed into " + e.getEntity().getName());
		
		e.getVehicle().getVelocity().multiply(-1);
	}
	
	public static void clearPlayer(UUID uuid) {
		if (playerDirections.containsKey(uuid)) playerDirections.remove(uuid);
		if (playerLaunchSpeeds.containsKey(uuid)) playerLaunchSpeeds.remove(uuid);
	}
	
	public static boolean playerChoose(UUID uuid) {
		if (playerDirections.containsKey(uuid) && playerLaunchSpeeds.containsKey(uuid)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public static Vector getPlayer(UUID uuid) {
		return playerDirections.get(uuid).multiply(playerLaunchSpeeds.get(uuid));
	}
}
