package me.WesBag.Toontown.Animations.Toon;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Teleport implements Listener {
	
	public static Set<UUID> playersTeleporting = new HashSet<>();
	
	public static void animateTeleport(Player p) {
		Location l = p.getLocation();
		World w = l.getWorld();
		Block underPlayerBlock = p.getLocation().getBlock().getRelative(BlockFace.DOWN);
		Particle bParticle = Particle.DRAGON_BREATH;
		
		w.spawnParticle(bParticle, l.subtract(0, 1, 0), 10);
		p.teleport(l.subtract(l.subtract(0, 1, 0)));
		//p.wait(0);
		//if (underPlayerBlock.getType() == Material.AIR || underPlayerBlock == null) {
		//	Main.getInstance().getLogger().log(Level.SEVERE, null);
		
		//}
	}
	
	public static boolean isPlayerTeleporting(UUID pUUID) {
		if (playersTeleporting.contains(pUUID))
			return true;
		return false;
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		if (!(isPlayerTeleporting(e.getPlayer().getUniqueId()))) return;
		e.getPlayer().sendMessage("Teleporting...");
		e.setCancelled(true);
	}
}
