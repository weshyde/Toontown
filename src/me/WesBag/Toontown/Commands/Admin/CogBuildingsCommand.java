package me.WesBag.Toontown.Commands.Admin;

import java.util.Map.Entry;
import java.io.File;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import me.WesBag.Toontown.Main;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuilding;
import me.WesBag.Toontown.BattleCore.Cogs.CogBuildings.CogBuildingController;
import me.WesBag.Toontown.BattleCore.Cogs.CogTraits.CogBuildingTrait;
import me.WesBag.Toontown.Commands.Prefixes;
import me.WesBag.Toontown.Files.DataFile;
import me.WesBag.Toontown.SchematicUtilities.SchematicPaster;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.md_5.bungee.api.ChatColor;

public class CogBuildingsCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			Player player = (Player) sender;
			if (label.equalsIgnoreCase("cogbuildings")) {
				if (!player.hasPermission("ttc.admin")) {
					player.sendMessage(Prefixes.NoPerm);
				}
				else if (args.length != 2) {
					player.sendMessage(ChatColor.AQUA + "/cogbuildings spawn <Cog-Takeover-Name>");
					player.sendMessage(ChatColor.AQUA + "/cogbuildings remove <CogBuildingNum>");
					player.sendMessage(ChatColor.WHITE + "[" + ChatColor.GRAY + "Ready Cog Buildings" + ChatColor.WHITE + "]");
					int looped = 0;
					for (Entry<Location, UUID> entry : CogBuildingController.readyCogBuildings.entrySet()) {
						looped++;
						Location l = entry.getKey();
						UUID uuid = entry.getValue();
						int x = l.getBlockX();
						int y = l.getBlockY();
						int z = l.getBlockZ();
						//String world = l.getWorld().getName();
						NPC npc = CitizensAPI.getNPCRegistry().getByUniqueId(uuid);
						String buildingName = npc.getFullName();
						String shopKeeperName = npc.getOrAddTrait(CogBuildingTrait.class).getOldName();
						
						player.sendMessage(ChatColor.AQUA + "[" + looped + "]" + ChatColor.WHITE + " " + buildingName + " | " + shopKeeperName + " | X:" + x + " Y:" + y + " Z:" + z);
					}
				}
				else if (args[0].equalsIgnoreCase("remove") && IsIntUtil.isInt(args[1])) {
					int choosenBuilding = IsIntUtil.getInt(args[1]);
					if (choosenBuilding > 0 && choosenBuilding <= CogBuildingController.readyCogBuildings.size()) {
						Location cbL = (Location) CogBuildingController.readyCogBuildings.keySet().toArray()[--choosenBuilding];
						String serialL = DataFile.serializeLocation(cbL);
						if (Main.dataFile.getData().contains("buildings." + serialL)) {
							Main.dataFile.getData().set("buildings." + serialL, null); //Clears building from saved file if it exists
							Main.dataFile.saveDataFile();
						}
						NPC buildingNPC = Main.getInstance().registry.getByUniqueId(CogBuildingController.readyCogBuildings.get(cbL));
						String oldName = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getOldName();
						buildingNPC.setName(oldName);
						//float savedYaw = buildingNPC.getStoredLocation().getYaw();
						//savedYaw *= -1;
						//float savedPitch = buildingNPC.getStoredLocation().getPitch();
						//savedPitch *= -1;
						float savedYaw = 0f;
						int yaw = (int) buildingNPC.getStoredLocation().getYaw();
						int npcX = 0;
						int npcZ = 0;
						if (yaw > 160 || yaw < -160) {
							npcZ = -4;
							savedYaw = 180f;
						}
						else if (yaw > -110 && yaw < -70) {
							npcX = 4;
							savedYaw = -90f;
						}
						else if ((yaw > -20 && yaw < 0) || yaw < 20) {
							npcZ = 4;
							savedYaw = 0f;
						}
						else if (yaw > 70 && yaw < 110) {
							npcX = -4;
							savedYaw = 90f;
						}
						Location orgNPCLocation = cbL.clone().add(npcX, 0, npcZ);
						orgNPCLocation.setYaw(savedYaw);
						buildingNPC.teleport(orgNPCLocation, TeleportCause.PLUGIN);
						//buildingNPC.getStoredLocation().setYaw(savedYaw);
						//buildingNPC.getStoredLocation().setPitch(savedPitch);
						
						String oldBuildingStr = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getFileName();
						int pasteX = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getX();
						int pasteY = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getY();
						int pasteZ = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getZ();
						//int yRotate = buildingNPC.getOrAddTrait(CogBuildingTrait.class).getYRotate();
						File oldBuildingFile = new File(Main.getInstance().getDataFolder() + "/schematics/temp", oldBuildingStr + ".schem");
						SchematicPaster.pasteSchematic2("world", oldBuildingFile, pasteX, pasteY, pasteZ, 0, false);
						buildingNPC.removeTrait(CogBuildingTrait.class);
						oldBuildingFile.deleteOnExit();
						
						CogBuildingController.readyCogBuildings.remove(cbL);
						
						player.sendMessage(Prefixes.Admin + ChatColor.GREEN + " Succesfully deleted the cog building");
					}
					else {
						player.sendMessage(Prefixes.Admin + ChatColor.RED + " Choosen Building out of range!");
					}
				}
				else if (args[0].equalsIgnoreCase("spawn")) {
					String cogName = args[1];
					cogName.replace("-", " ");
					CogBuildingController.spawnBuilding(player, player.getLocation(), cogName);
				}
				else {
					player.sendMessage(Prefixes.Admin + ChatColor.RED + " Wrong Syntax! /cogbuildings for help");
				}
			}
		}
		return false;
	}
}
