package me.WesBag.Toontown.BattleCore;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.WesBag.Toontown.BattleCore.Cogs.Cog;
import me.WesBag.Toontown.BattleCore.Toons.Toon;
import me.WesBag.Toontown.Files.BattleData;
import me.WesBag.Toontown.Files.CogType;
import me.WesBag.Toontown.BattleCore.Toons.ShtickerBook.ShtickerBook;
import me.WesBag.Toontown.Tasks.CustomEvents.BattleFinishEvent;

public class BattleDriver {
	/*
	public void startStreetBattle(UUID pUUID, UUID npcUUID) {
		
	}
	
	public void nextRound(CogBattle cogBattle, boolean playersTurn) {
		
		cogBattle.setPlayersTurn(playersTurn);
		
		if (playersTurn) {
			
		}
		
	}
	
	public void chooseCogAttack(CogBattle cogBattle) {
		List<UUID> npcs = cogBattle.getNpcs();
		
		int atkFreq = 0;
		int newAtkFreq = 0;
		for (UUID npcUUID : npcs) {
			Cog cog = cogBattle.getCog(npcUUID);
			double r = (Math.random() * (99 - 0) + 0);
			for (int i = 0; i < cog.getNumOfCogAttacks(); i++) {
				newAtkFreq += cog.getAttackFreq(i);
				if (r > atkFreq && r < newAtkFreq) {
					// choosenAttack = i;
					cog.setSelectedAttack(i);
					break;
				}
				atkFreq += cog.getAttackFreq(i);
			}
		}
		cogBattle.nextLureRound();
		cogAttack(cogBattle);
	}
	
	public void cogAttack(CogBattle cogBattle) {
		List<UUID> players = cogBattle.getPlayers();
		List<UUID> npcs = cogBattle.getNpcs();

		for (UUID npcUUID : npcs) {
			Cog cog = cogBattle.getCog(npcUUID);
			if (cogBattle.isLured(npcUUID)) {
				cogBattle.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + cog.getJustCogName()
						+ " is lured. Skipping attack!");
				continue;
			}
			boolean missed = false;
			int selectedAttack = cog.getSelectedAttack();
			int random = (int) (Math.random() * 99 + 1);
			if (random < cog.getAttackAcc(selectedAttack) - 1)
				missed = true;
			cogBattle.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + cog.getJustCogName()
					+ " used " + cog.getAttackName(selectedAttack));

			if (!missed) {
				List<UUID> targetList = new ArrayList<>();
				System.out.println("Selected Attack: " + selectedAttack);
				if (!cog.getAttackHitAll(selectedAttack)) {
					System.out.println("Single Hit");
					int r = new Random().nextInt(players.size());
					targetList.add(players.get(r));
				} else {
					System.out.println("All Hit");
					targetList.addAll(players);
				}

				for (UUID pUUID : targetList) {
					Player p = Bukkit.getPlayer(pUUID);
					int playerLife = (int) p.getHealth() / 2;

					playerLife -= cog.getAttackDmg(selectedAttack);
					if (playerLife < 0)
						playerLife = 0;

					cogBattle.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE
							+ cog.getJustCogName() + " did " + String.valueOf(cog.getAttackDmg(selectedAttack))
							+ " damage to " + p.getName() + " [" + ChatColor.RED + playerLife + ChatColor.WHITE + "]");

					if (playerLife == 0) {
						playerDead(p, cogBattle);
						System.out.println("A player died!");
					} else
						p.setHealth(playerLife * 2);
				}
			}

			else {
				cogBattle.sendAllMessage(
						ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + cog.getJustCogName() + " missed!");
			}
		}
		startingNewRound(cogBattle, true);
	}
	
	public void playerDead(Player p, CogBattle cogBattle) {
		final PotionEffect slownessPotion = new PotionEffect(PotionEffectType.SLOW, 100, 3);
		p.sendMessage("You died!");
		p.setHealth(1);
		p.addPotionEffect(slownessPotion);
		// inputPlayer.teleport(inputPlayer.getBedSpawnLocation());
		// inputPlayer.teleport();
		cogBattle.removePlayer(p.getUniqueId());
		if (cogBattle.getPlayers().isEmpty())
			finishBattle(cogBattle, true);
		// inputPlayer.addPotionEffect(slownessPotion, true);
	}
	
	public boolean cogDead(UUID npcUUID, CogBattle cogBattle) {
		Cog cog = cogBattle.getCog(npcUUID);
		// List<UUID> playersWhoDestroyed = inputBattleData.getBattlePlayerList();
		cogBattle.sendAllMessage(
				ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + cog.getJustCogName() + " has been defeated!");
		cogBattle.removeNPC(npcUUID);
		cogBattle.deadCog(cog);
		main.registry.getByUniqueId(npcUUID).despawn();
		if (cogBattle.getNpcs().isEmpty()) {
			finishBattle(cogBattle, false);
			return true;
		}
		return false;
	}
	
	public void finishBattle(CogBattle cogBattle, boolean playersLost) {
		// Bukkit.getServer().broadcastMessage("finishBattle has been called!");

		if (cogBattle.getSpecialMode() && !playersLost) { // Special Mode WIP: For buildings, bosses, factories,
																// etc
			cogBattle.sendAllMessage(ChatColor.RED + "[DEBUG] " + ChatColor.WHITE
					+ "Special Mode On! Keeping battle active! Calling next floor...");
			// inputBattleData.getCogBuilding().nextFloor();
			// Instead of calling next floor, spawn next floor NPC and start counter to
			// kickout those who dont goto next floor when it ends
			// inputBattleData.getCogBuilding().spawnNextFloorNPC();
			cogBattle.getCogBuilding().startNextFloorTimer();
			if (cogBattle.getCogBuilding().getLevels() != -1)
				return;
		}

		cogBattle.battleOver();
		System.out.println("-----------Battle has been set to over!");

		if (!playersLost) {
			cogBattle.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "The battle has ended!");

			List<UUID> players = cogBattle.getPlayers();

			List<Toon> toons = new ArrayList<>();
			List<Cog> cogs = cogBattle.getDeadCogs();
			for (UUID pUUID : players)
				toons.add(cogBattle.getToon(pUUID));
			// for (UUID cUUID : tempNpcs)
			// tempCogs.add(inputBattleData.getCog(cUUID));
			for (Toon toon : toons) {
				for (Cog cog : cogs) {
					String cogType = cog.getCogType().toString();
					int index = CogType.valueOf(cogType).ordinal() + 1;
					int a = index % 8;
					int b = (int) Math.floor(index/8);
					toon.addCogGallery(b, (a-1), 1);
					System.out.println("Added Cog! a: " + a + " b: " + b);
				}
				if (toon.getShtickerBookSize() != 0)
					ShtickerBook.refreshGalleryPage(toon); //IF MORE PAGES HAVE BEEN ADDED !!!BEFORE!!! SINCE 2/19/22, THIS IS BROKEN
				//ShtickerBook.loadGalleryPage(toon);
			}
			BattleFinishEvent battleFinishEvent = new BattleFinishEvent(toons, cogs);
			Bukkit.getPluginManager().callEvent(battleFinishEvent);

			for (UUID pUUID : players) {
				Toon pToon = cogBattle.getToon(pUUID);
				// pToon.updateAllToonGagAmounts(); Commented 12/9/21 6:33pm
				// PlayerData pData = pToon.getToonData();
				int[] playerBattleExps = { 0, 0, 0, 0, 0, 0, 0 };
				for (int i = 0; i < 7; i++) // { Commented 1/22/22 5:54pm to fix potential extra exp bug
					playerBattleExps[i] = (int) cogBattle.getPlayerExp(pUUID, i);

				pToon.updateToonGagExp2(playerBattleExps); // Commented 12/9/21 6:33pm -- Uncommented 1/15/22 11:24pm
				// data.saveConfig();

				Player tempP = Bukkit.getPlayer(pUUID);
				tempP.closeInventory();
				tempP.sendMessage("Closed Inv. Battle Finished!");
				tempP.getInventory().clear(); // Added to remove any reminants of drop gags 1/21/22 4:13pm
				pToon.loadToonGagExpInv2();
				tempP.getInventory().setContents(pToon.getGagExp2Inventory().getContents());
				openInventoryLater(tempP, pToon.getGagExpInventory(), 40);
				// openWinInventory(tempP);
				// Players won, calc exp + task stuff
			}
		}
		// }

		else {
			if (cogBattle.isSpecialMode()) {
				cogBattle.getCogBuilding().finishBuilding(true);
			}

			cogBattle.sendAllMessage(ChatColor.BLUE + "[Battle] " + ChatColor.WHITE + "You lost! :(");

			List<UUID> npcs = cogBattle.getNpcs();

			for (UUID npcUUID : npcs) {
				main.registry.getByUniqueId(npcUUID).despawn();
			}
			// Cogs won, send all players back to playground, remove all remaining cogs
		}
		cogBattle.deleteAll();

		allBattles.remove(inputBattleData);
		cogBattle = null;

	}
	
	public void startingNewRound(CogBattle cogBattle, boolean bool) {
		
	}
	*/
}
